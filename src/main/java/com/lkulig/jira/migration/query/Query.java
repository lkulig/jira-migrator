package com.lkulig.jira.migration.query;

public class Query {

    private StringBuilder query = new StringBuilder();

    public Query append(String queryPart) {
        query.append(queryPart);
        return this;
    }

    public String jql() {
        return query.toString();
    }
}
