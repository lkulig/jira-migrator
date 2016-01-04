package com.lkulig.jira.migration.domain;

public abstract class JiraEntity<TYPE> {

    protected TYPE underlying;

    public JiraEntity(TYPE underlying) {
        this.underlying = underlying;
    }

    public TYPE getUnderlying() {
        return underlying;
    }
}
