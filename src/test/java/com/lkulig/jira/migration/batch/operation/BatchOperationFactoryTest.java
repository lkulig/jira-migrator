package com.lkulig.jira.migration.batch.operation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import com.lkulig.jira.migration.AbstractUnitTest;
import com.lkulig.jira.migration.client.JiraClient;
import com.lkulig.jira.migration.data.MigrationData;
import com.lkulig.jira.migration.domain.JiraProject;
import com.lkulig.jira.migration.domain.JiraSearchResult;
import com.lkulig.jira.migration.domain.issue.JiraIssueFactory;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.List;

public class BatchOperationFactoryTest extends AbstractUnitTest {

    private static final int BATCH_OPERATIONS_COUNT = 10;
    private static final int ISSUES_COUNT = 10;
    private static final int ISSUES_IN_BATCH_OPERATION = 1;
    public static final String SOURCE_PROJECT_KEY = "SOURCE_PROJECT";
    @Mock
    private JiraProject sourceProject;
    @Mock
    private JiraProject destinationProject;
    @Mock
    private JiraSearchResult jiraSearchResult;

    @Mock
    private JiraClient sourceJira;

    @Mock
    private JiraClient destinationJira;

    @Mock
    private JiraIssueFactory jiraIssueFactory;

    @InjectMocks
    private BatchOperationFactory batchOperationFactory;

    @Test
    public void shouldCreateBatchOperationsBasedOnMigrationData() {
        // given
        givenSearchResult();
        MigrationData migrationData = givenMigrationData();

        // when
        List<BatchOperation> batchOperations = batchOperationFactory.createBatchOperationsBasedOn(migrationData);

        // then
        assertThat(batchOperations).hasSize(BATCH_OPERATIONS_COUNT);
    }

    private MigrationData givenMigrationData() {
        MigrationData migrationData = new MigrationData();
        migrationData.batchOperationsCount = BATCH_OPERATIONS_COUNT;
        migrationData.issuesCount = ISSUES_COUNT;
        migrationData.issueCountInBatchOperation = ISSUES_IN_BATCH_OPERATION;
        migrationData.sourceProject = sourceProject;
        migrationData.destinationProject = destinationProject;
        return migrationData;
    }

    private void givenSearchResult() {
        given(sourceProject.getKey()).willReturn(SOURCE_PROJECT_KEY);
        given(sourceJira.searchForProjectIssues(eq(SOURCE_PROJECT_KEY), eq(ISSUES_IN_BATCH_OPERATION), anyInt()))
            .willReturn(
                jiraSearchResult);
    }

}
