package com.lkulig.jira.migration.data;

import static com.lkulig.jira.migration.util.MigrationDataAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import com.lkulig.jira.migration.AbstractUnitTest;
import com.lkulig.jira.migration.client.JiraClient;
import com.lkulig.jira.migration.domain.JiraProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class MigrationDataFactoryTest extends AbstractUnitTest {

    private static final String SOURCE_PROJECT_KEY = "SOURCE";
    private static final String DESTINATION_PROJECT_KEY = "DESTINATION";
    private static final int ISSUES_IN_BATCH_COUNT = 10;
    private static final int ISSUES_COUNT = 20;
    private static final int EXPECTED_BATCH_OPERATIONS_COUNT = 2;

    @Mock
    private JiraProject sourceProject;
    @Mock
    private JiraProject destinationProject;

    @Mock
    private JiraClient sourceJira;

    @Mock
    private JiraClient destinationJira;

    @InjectMocks
    private MigrationDataFactory migrationDataFactory;

    @Before
    public void onSetup() {
        setInternalState(migrationDataFactory, "sourceProjectKey", SOURCE_PROJECT_KEY);
        setInternalState(migrationDataFactory, "destinationProjectKey", DESTINATION_PROJECT_KEY);
        setInternalState(migrationDataFactory, "issuesInBatchOperationCount", ISSUES_IN_BATCH_COUNT);
    }

    @Test
    public void shouldProperlyCreateMigrationData() {
        // given
        givenSourceProject();
        givenDestinationProject();
        givenIssuesCountForSourceProject();

        // when
        MigrationData migrationData = migrationDataFactory.createBatchMigrationData();

        // then
        assertThat(migrationData)
            .hasIssueCount(ISSUES_COUNT)
            .hasBatchIssueCount(ISSUES_IN_BATCH_COUNT)
            .hasBatchOperationsCount(EXPECTED_BATCH_OPERATIONS_COUNT)
            .hasSourceProject(sourceProject)
            .hasDestinationProject(destinationProject);
    }

    private void givenIssuesCountForSourceProject() {
        given(sourceJira.issuesCountForProject(SOURCE_PROJECT_KEY)).willReturn(ISSUES_COUNT);
    }

    private void givenSourceProject() {
        given(sourceJira.loadProject(SOURCE_PROJECT_KEY)).willReturn(sourceProject);
    }

    private void givenDestinationProject() {
        given(destinationJira.loadProject(DESTINATION_PROJECT_KEY)).willReturn(destinationProject);
    }

}
