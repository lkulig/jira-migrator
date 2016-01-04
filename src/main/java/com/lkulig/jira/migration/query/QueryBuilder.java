package com.lkulig.jira.migration.query;

import static com.google.common.base.Joiner.on;
import com.lkulig.jira.migration.util.AbstractBuilder;

public class QueryBuilder extends AbstractBuilder<Query, QueryBuilder> {

    public static final String ISSUE_KEY = "issuekey";
    private static final String PROJECT = "project";
    private static final String SUMMARY = "summary~";
    private static final String UPDATED = "updated";
    private static final String CREATED = "created";
    private static final String EMPTY_START_OF_WEEK = "startOfWeek()";
    private static final String START_OF_WEEK_MINUS_WEEK_COUNT = "startOfWeek(-%dw)";
    private static final String ORDER_BY = " ORDER BY";
    private static final String EQUALS = "=";
    private static final String GREATER_THAN = ">";
    private static final String LOWER_THAN = "<";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ")";
    private static final String DOUBLE_QUOTATION = "\"";
    private static final String SINGLE_QUOTATION = "\'";
    private static final String SPACE = " ";

    public static QueryBuilder newQuery() {
        return new QueryBuilder();
    }

    public QueryBuilder project(String projectKey) {
        buildable.append(on(EQUALS).join(PROJECT, projectKey));
        return this;
    }

    public QueryBuilder and() {
        buildable.append(AND);
        return this;
    }

    public QueryBuilder and(Query query) {
        buildable.append(AND + OPEN_BRACKET + query.jql() + CLOSE_BRACKET);
        return this;
    }

    public QueryBuilder or() {
        buildable.append(OR);
        return this;
    }

    public QueryBuilder summary(String summary) {
        buildable.append(SUMMARY).append(SINGLE_QUOTATION).append(DOUBLE_QUOTATION).append(summary)
            .append(DOUBLE_QUOTATION).append("~1").append(SINGLE_QUOTATION);
        return this;
    }

    public QueryBuilder createdOrUpdatedDuring(int weekCount) {
        createdDuring(weekCount);
        or();
        updatedDuring(weekCount);
        return this;
    }

    public QueryBuilder createdDuring(int weekCount) {
        openBracket();
        createdAfter(weekCount);
        and();
        createdBefore();
        closeBracket();
        return this;
    }

    public QueryBuilder createdAfter(int weekCount) {
        buildable.append(on(SPACE).join(CREATED, GREATER_THAN, startOfWeek(weekCount)));
        return this;
    }

    public QueryBuilder createdBefore() {
        buildable.append(on(SPACE).join(CREATED, LOWER_THAN, EMPTY_START_OF_WEEK));
        return this;
    }

    public QueryBuilder updatedDuring(int weekCount) {
        openBracket();
        updatedAfter(weekCount);
        and();
        updatedBefore();
        closeBracket();
        return this;
    }

    public QueryBuilder updatedAfter(int weekCount) {
        buildable.append(on(SPACE).join(UPDATED, GREATER_THAN, startOfWeek(weekCount)));
        return this;
    }

    private String startOfWeek(int weekCount) {
        return String.format(START_OF_WEEK_MINUS_WEEK_COUNT, weekCount);
    }

    public QueryBuilder updatedBefore() {
        buildable.append(on(SPACE).join(UPDATED, LOWER_THAN, EMPTY_START_OF_WEEK));
        return this;
    }

    public QueryBuilder orderBy(String field, OrderBy orderBy) {
        buildable.append(on(SPACE).join(ORDER_BY, field, orderBy.toString()));
        return this;
    }

    private QueryBuilder openBracket() {
        buildable.append(OPEN_BRACKET);
        return this;
    }

    private QueryBuilder closeBracket() {
        buildable.append(CLOSE_BRACKET);
        return this;
    }

    @Override
    public Query build() {
        return buildable;
    }

    @Override
    protected Query createBuildable() {
        return new Query();
    }

    @Override
    protected QueryBuilder getBuilder() {
        return this;
    }

}
