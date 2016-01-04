package com.lkulig.jira.migration.client;

import static com.google.common.base.Preconditions.checkNotNull;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.util.concurrent.Promise;
import com.lkulig.jira.migration.domain.JiraAttachment;
import com.lkulig.jira.migration.domain.JiraComment;
import com.lkulig.jira.migration.domain.issue.JiraIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class JiraIssueClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraIssueClient.class);

    private IssueRestClient issueRestClient;

    JiraIssueClient(IssueRestClient issueRestClient) {
        this.issueRestClient = checkNotNull(issueRestClient);
    }

    public JiraIssue createIssue(IssueInput issueInput) {
        Promise<BasicIssue> issue = issueRestClient.createIssue(issueInput);
        return getIssueByKey(issue.claim().getKey());
    }

    public JiraIssue getIssueByKey(String issueKey) {
        return JiraIssue.createFrom(issueRestClient.getIssue(issueKey));
    }

    public void addComments(List<JiraComment> comments, URI uri) {
        for (JiraComment comment : comments) {
            LOGGER.debug("Adding comment [body={}].", comment.getBody());
            issueRestClient.addComment(uri, comment.getUnderlying());
        }
    }

    public void addAttachment(JiraAttachment attachment, InputStream attachmentData, URI uri) {
            LOGGER.debug("Uploading attachment [fileName={}].", attachment.getFilename());
            issueRestClient.addAttachment(uri, attachmentData, attachment.getFilename());
    }
}
