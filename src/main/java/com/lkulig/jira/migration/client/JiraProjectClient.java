package com.lkulig.jira.migration.client;

import static com.google.common.base.Preconditions.checkNotNull;
import com.atlassian.jira.rest.client.api.ProjectRestClient;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.util.concurrent.Promise;
import com.lkulig.jira.migration.domain.JiraProject;

public class JiraProjectClient {

    private ProjectRestClient projectRestClient;

    JiraProjectClient(ProjectRestClient projectRestClient) {
        this.projectRestClient = checkNotNull(projectRestClient);
    }

    public JiraProject getProjectByKey(String projectKey) {
        Promise<Project> project = projectRestClient.getProject(projectKey);
        return new JiraProject(project);
    }
}
