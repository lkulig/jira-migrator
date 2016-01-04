package com.lkulig.jira.migration.domain;

import static com.google.common.base.MoreObjects.toStringHelper;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.util.concurrent.Promise;

public class JiraProject extends JiraEntity<Promise<Project>> {

    public JiraProject(Promise<Project> underlying) {
        super(underlying);
    }

    public String getKey() {
        return underlying.claim().getKey();
    }

    @Override
    public String toString() {
        return toStringHelper(this)
            .add("projectKey", getKey())
            .toString();
    }
}
