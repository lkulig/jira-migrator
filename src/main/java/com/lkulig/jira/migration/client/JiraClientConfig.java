package com.lkulig.jira.migration.client;

import java.net.URI;

public class JiraClientConfig {

    private String username;
    private String password;
    private URI uri;
    private String projectKey;

    public JiraClientConfig(String username, String password, String projectKey, URI uri) {
        this.username = username;
        this.password = password;
        this.projectKey = projectKey;
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public URI getUri() {
        return uri;
    }
}
