package com.lkulig.jira.migration.data;

import static com.google.common.base.MoreObjects.toStringHelper;
import com.lkulig.jira.migration.domain.JiraProject;

public class MigrationData {

    public int issuesCount;
    public long batchOperationsCount;
    public int issueCountInBatchOperation;
    public JiraProject destinationProject;
    public JiraProject sourceProject;

    @Override
    public String toString() {
        return toStringHelper(this)
            .add("issuesCount", issuesCount)
            .add("batchOperationsCount", batchOperationsCount)
            .add("issueCountInBatchOperation", issueCountInBatchOperation)
            .add("destinationProject", destinationProject)
            .add("sourceProject", sourceProject)
            .toString();
    }
}
