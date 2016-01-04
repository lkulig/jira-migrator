package com.lkulig.jira.migration.domain;

import static com.google.common.collect.FluentIterable.from;
import static com.lkulig.jira.migration.domain.issue.JiraIssue.TO_JIRA_ISSUE;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.util.concurrent.Promise;
import com.lkulig.jira.migration.domain.issue.JiraIssue;
import java.util.List;

public class JiraSearchResult extends JiraEntity<Promise<SearchResult>> {

    public JiraSearchResult(Promise<SearchResult> underlying) {
        super(underlying);
    }

    public List<JiraIssue> getIssues() {
        return from(underlying.claim().getIssues()).transform(TO_JIRA_ISSUE).toList();
    }

    public int getTotal() {
        return underlying.claim().getTotal();
    }
}
