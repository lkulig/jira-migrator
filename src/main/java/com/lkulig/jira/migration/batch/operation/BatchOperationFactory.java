package com.lkulig.jira.migration.batch.operation;

import static com.google.common.collect.Lists.newArrayList;
import com.lkulig.jira.migration.client.JiraClient;
import com.lkulig.jira.migration.data.MigrationData;
import com.lkulig.jira.migration.domain.JiraSearchResult;
import com.lkulig.jira.migration.domain.issue.JiraIssueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BatchOperationFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchOperationFactory.class);

    @Autowired
    @Qualifier(value = "destinationJira")
    private JiraClient destinationJira;
    @Autowired
    @Qualifier(value = "sourceJira")
    private JiraClient sourceJira;
    @Autowired
    private JiraIssueFactory jiraIssueFactory;

    public List<BatchOperation> createBatchOperationsBasedOn(MigrationData migrationData) {
        List<BatchOperation> batchOperations = newArrayList();
        for (int batchOperationNumber = 0; batchOperationNumber < migrationData.batchOperationsCount; batchOperationNumber++) {
            BatchOperation batchOperation = createBatchOperationFor(batchOperationNumber, migrationData);
            batchOperations.add(batchOperation);
        }
        return batchOperations;
    }

    private BatchOperation createBatchOperationFor(int batchOperationNumber, MigrationData migrationData) {
        int searchOffset = (batchOperationNumber * migrationData.issueCountInBatchOperation);
        JiraSearchResult searchResult = searchForIssues(migrationData.sourceProject.getKey(),
            migrationData.issueCountInBatchOperation, searchOffset);
        return new BatchOperation(migrationData, batchOperationNumber, jiraIssueFactory, destinationJira, searchResult, sourceJira);
    }

    private JiraSearchResult searchForIssues(String sourceProjectKey, int issueCountInBatchOperation, int searchOffset) {
        return sourceJira.searchForProjectIssues(sourceProjectKey, issueCountInBatchOperation, searchOffset);
    }
}
