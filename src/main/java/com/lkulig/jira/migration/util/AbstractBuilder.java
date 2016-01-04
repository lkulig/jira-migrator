package com.lkulig.jira.migration.util;

public abstract class AbstractBuilder<TYPE, BUILDER extends AbstractBuilder<TYPE, BUILDER>> {

    protected TYPE buildable;
    protected BUILDER builder;

    public AbstractBuilder() {
        buildable = createBuildable();
        builder = getBuilder();
    }

    public abstract TYPE build();

    protected abstract TYPE createBuildable();

    protected abstract BUILDER getBuilder();
}
