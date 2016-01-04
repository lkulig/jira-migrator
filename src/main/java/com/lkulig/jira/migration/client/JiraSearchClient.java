package com.lkulig.jira.migration.client;

import static com.lkulig.jira.migration.query.OrderBy.ASC;
import static com.lkulig.jira.migration.query.QueryBuilder.ISSUE_KEY;
import static com.lkulig.jira.migration.query.QueryBuilder.newQuery;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;
import com.google.common.collect.Sets;
import com.lkulig.jira.migration.domain.JiraProject;
import com.lkulig.jira.migration.domain.JiraSearchResult;
import com.lkulig.jira.migration.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraSearchClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraSearchClient.class);

    private SearchRestClient searchRestClient;

    JiraSearchClient(SearchRestClient searchRestClient) {
        this.searchRestClient = searchRestClient;
    }

    public JiraSearchResult searchJql(Query query, int limit, int offset) {
        LOGGER.debug("Executing [query='{}'].", query.jql());
        Promise<SearchResult> searchResultPromise = searchRestClient.searchJql(query.jql(), limit,
                offset, Sets.<String>newHashSet());
        return new JiraSearchResult(searchResultPromise);
    }

    public JiraSearchResult searchForIssuesInProject(String projectKey, int limit, int offset) {
        Query query = newQuery().project(projectKey).orderBy(ISSUE_KEY, ASC).build();
        return searchJql(query, limit, offset);
    }

    public boolean doesIssueWithSummaryExistForProject(String summary, JiraProject jiraProject) {
        Query query = newQuery().project(jiraProject.getKey()).and().summary(summary)
            .build();
        LOGGER.debug("Executing [query='{}'].", query.jql());
        Promise<SearchResult> searchResultPromise = searchRestClient.searchJql(query.jql());
        return searchResultPromise.claim().getIssues().iterator().hasNext();
    }
}
