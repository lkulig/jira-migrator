package com.lkulig.jira.migration.util;

import com.lkulig.jira.migration.data.MigrationData;
import com.lkulig.jira.migration.domain.JiraProject;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class MigrationDataAssert extends AbstractAssert<MigrationDataAssert, MigrationData> {

    protected MigrationDataAssert(MigrationData actual) {
        super(actual, MigrationDataAssert.class);
    }

    public static MigrationDataAssert assertThat(MigrationData actual) {
        return new MigrationDataAssert(actual);
    }

    public MigrationDataAssert hasIssueCount(int issueCount) {
        if (actual.issuesCount != issueCount) {
            failWithMessage("Expected issues count to be <%s> but was <%s>", issueCount, actual.issuesCount);
        }
        return this;
    }

    public MigrationDataAssert hasBatchIssueCount(int issueCount) {
        if (actual.issueCountInBatchOperation != issueCount) {
            failWithMessage("Expected issues count in batch operation to be <%s> but was <%s>", issueCount,
                actual.issueCountInBatchOperation);
        }
        return this;
    }

    public MigrationDataAssert hasBatchOperationsCount(int batchOperationsCount) {
        if (actual.batchOperationsCount != batchOperationsCount) {
            failWithMessage("Expected batch operation count to be <%s> but was <%s>", batchOperationsCount,
                actual.batchOperationsCount);
        }
        return this;
    }

    public MigrationDataAssert hasSourceProject(JiraProject sourceProject) {
        Assertions.assertThat(actual.sourceProject).isSameAs(sourceProject);
        return this;
    }

    public MigrationDataAssert hasDestinationProject(JiraProject destinationProject) {
        Assertions.assertThat(actual.destinationProject).isSameAs(destinationProject);
        return this;
    }
}
