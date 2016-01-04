package com.lkulig.jira.migration.data;

import com.lkulig.jira.migration.client.JiraClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MigrationDataFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationDataFactory.class);

    @Autowired
    @Qualifier(value = "sourceJira")
    private JiraClient sourceJira;
    @Value("${jira.from.project}")
    private String sourceProjectKey;
    @Autowired
    @Qualifier(value = "destinationJira")
    private JiraClient destinationJira;
    @Value("${jira.to.project}")
    private String destinationProjectKey;
    @Value("${batch.operation.issues.count}")
    private double issuesInBatchOperationCount;

    public MigrationData createBatchMigrationData() {
        MigrationData migrationData = new MigrationData();
        migrationData.issuesCount = sourceJira.issuesCountForProject(sourceProjectKey);
        migrationData.issueCountInBatchOperation = (int) issuesInBatchOperationCount;
        migrationData.batchOperationsCount = (long) Math.ceil((migrationData.issuesCount
                / issuesInBatchOperationCount));
        migrationData.destinationProject = destinationJira.loadProject(destinationProjectKey);
        migrationData.sourceProject = sourceJira.loadProject(sourceProjectKey);
        return migrationData;
    }
}
