package com.lkulig.jira.migration.client;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.lkulig.jira.migration.domain.JiraAttachment;
import com.lkulig.jira.migration.domain.JiraComment;
import com.lkulig.jira.migration.domain.JiraProject;
import com.lkulig.jira.migration.domain.JiraSearchResult;
import com.lkulig.jira.migration.domain.issue.JiraIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class JiraClient implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraClient.class);

    private final JiraClientConfig config;
    private final JiraRestClient jiraClient;
    private final JiraSearchClient jiraSearchClient;
    private final JiraProjectClient jiraProjectClient;
    private final JiraIssueClient jiraIssueClient;

    public JiraClient(JiraClientConfig config) {
        this.config = config;
        this.jiraClient = new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(config.getUri(),
            config.getUsername(), config.getPassword());
        this.jiraSearchClient = new JiraSearchClient(jiraClient.getSearchClient());
        this.jiraProjectClient = new JiraProjectClient(jiraClient.getProjectClient());
        this.jiraIssueClient = new JiraIssueClient(jiraClient.getIssueClient());
    }

    public JiraSearchResult searchForProjectIssues(String projectKey, int limit, int offset) {
        LOGGER.debug("Searching for issues [projectKey={}] [limit={}] [offset={}].", projectKey, limit, offset);
        return jiraSearchClient.searchForIssuesInProject(projectKey, limit, offset);
    }

    public JiraProject loadProject(String projectKey) {
        LOGGER.debug("Loading project by [projectKey={}].", projectKey);
        return jiraProjectClient.getProjectByKey(projectKey);
    }

    public JiraIssue addIssue(IssueInput issueInput) {
        LOGGER.debug("Adding issue with [summary={}].", issueInput.getField("summary").getValue());
        return jiraIssueClient.createIssue(issueInput);
    }

    public JiraIssue getIssue(String key) {
        LOGGER.debug("Getting issue by [key={}].", key);
        return jiraIssueClient.getIssueByKey(key);
    }

    public void addComments(URI uri, List<JiraComment> comments) {
        LOGGER.debug("Adding comments to [uri={}].", uri);
        jiraIssueClient.addComments(comments, uri);
    }

    public boolean doesIssueWithSummaryExist(String summary, JiraProject destinationProject) {
        LOGGER.debug("Checking whether issue with [summary={}] exists for project [projectKey={}].", summary,
            destinationProject.getKey());
        boolean exists = jiraSearchClient.doesIssueWithSummaryExistForProject(summary, destinationProject);
        LOGGER.debug("Issue with [summary={}] [exists={}].", summary, exists);
        return exists;
    }

    public int issuesCountForProject(String projectKey) {
        LOGGER.debug("Checking issue count for [projectKey={}].", projectKey);
        int totalIssues = searchForProjectIssues(projectKey, 1, 0).getTotal();
        LOGGER.debug("Found total of [{}] issues.", totalIssues);
        return totalIssues;
    }

    public InputStream getAttachment(URI uri) {
        return jiraClient.getIssueClient().getAttachment(uri).claim();
    }

    public void addAttachment(URI attachmentUri, InputStream attachmentData, JiraAttachment attachment) {
        jiraIssueClient.addAttachment(attachment, attachmentData, attachmentUri);
    }

    @Override
    public void close() throws Exception {
        logout();
    }

    public void logout() {
        LOGGER.debug("Logging out of Jira@{}", config.getUri());
        try {
            jiraClient.close();
        } catch (IOException e) {
            LOGGER.error("Failed to close connection to Jira@{}", config.getUri());
        }
    }
}
