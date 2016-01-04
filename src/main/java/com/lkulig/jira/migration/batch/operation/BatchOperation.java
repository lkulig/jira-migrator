package com.lkulig.jira.migration.batch.operation;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.lkulig.jira.migration.util.parser.IssueSummaryParser.parse;
import static org.slf4j.LoggerFactory.getLogger;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.google.common.base.Optional;
import com.lkulig.jira.migration.client.JiraClient;
import com.lkulig.jira.migration.data.MigrationData;
import com.lkulig.jira.migration.domain.JiraAttachment;
import com.lkulig.jira.migration.domain.JiraSearchResult;
import com.lkulig.jira.migration.domain.issue.JiraIssue;
import com.lkulig.jira.migration.domain.issue.JiraIssueFactory;
import com.lkulig.jira.migration.util.progress.ProgressLogger;
import org.slf4j.Logger;
import java.util.List;

public class BatchOperation {

    private static final Logger LOGGER = getLogger(BatchOperation.class);
    private int operationNumber;
    private JiraIssueFactory jiraIssueFactory;
    private JiraClient destinationJira;
    private MigrationData migrationData;
    private JiraSearchResult jiraSearchResult;
    private JiraClient sourceJira;

    public BatchOperation(MigrationData migrationData, int operationNumber, JiraIssueFactory jiraIssueFactory,
                          JiraClient destinationJira, JiraSearchResult jiraSearchResult, JiraClient sourceJira) {
        this.migrationData = migrationData;
        this.operationNumber = operationNumber;
        this.jiraIssueFactory = jiraIssueFactory;
        this.destinationJira = destinationJira;
        this.jiraSearchResult = jiraSearchResult;
        this.sourceJira = sourceJira;
    }

    public void execute() {
        List<JiraIssue> issues = jiraSearchResult.getIssues();
        for (JiraIssue issueToExport : issues) {
            try {
                JiraIssue issue = sourceJira.getIssue(issueToExport.getKey());
                // if (!issueAlreadyExists(issue)) {
                LOGGER.debug("Exporting issue [summary={}].", issueToExport.getSummary());
                Optional<JiraIssue> addedIssue = addIssue(issueToExport);
                if (addedIssue.isPresent()) {
                    addCommentsTo(addedIssue.get(), issue);
                    addAttachmentsTo(addedIssue.get(), issue.getAttachments());
                }
                // }
                ProgressLogger.incrementProcessedIssues();
                LOGGER.debug("Exported issue [summary={}].", issueToExport.getSummary());
            } catch (Exception e) {
                LOGGER.error("Error occurred during adding issue [summary={}].", issueToExport.getSummary(), e);
            }
        }
    }

    private void addAttachmentsTo(JiraIssue addedIssue, List<JiraAttachment> attachments) {
        for (JiraAttachment attachment : attachments) {
            destinationJira.addAttachment(addedIssue.getAttachmentsUri(), sourceJira.getAttachment(attachment.getContentUri()), attachment);
        }
    }

    private Optional<JiraIssue> addIssue(JiraIssue issue) {
        Optional<IssueInput> optionalIssue = jiraIssueFactory.createFrom(issue, migrationData.destinationProject);
        if (optionalIssue.isPresent()) {
            return of(destinationJira.addIssue(optionalIssue.get()));
        }
        return absent();
    }

    private boolean issueAlreadyExists(JiraIssue issue) {
        return destinationJira.doesIssueWithSummaryExist(parse(issue.getSummary()),
            migrationData.destinationProject);
    }

    private void addCommentsTo(JiraIssue addedIssue, JiraIssue issueToExport) {
        destinationJira.addComments(addedIssue.getCommentsUri(), issueToExport.getComments());
    }

    public int operationNumber() {
        return operationNumber;
    }
}
