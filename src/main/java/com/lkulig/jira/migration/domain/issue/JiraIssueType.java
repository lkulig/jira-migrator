package com.lkulig.jira.migration.domain.issue;

import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.lkulig.jira.migration.domain.JiraEntity;

public class JiraIssueType extends JiraEntity<IssueType> {

    public JiraIssueType(IssueType underlying) {
        super(underlying);
    }

    public long getId() {
        return underlying.getId();
    }

    public String getName() {
        return underlying.getName();
    }
}
