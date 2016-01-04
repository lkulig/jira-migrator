package com.lkulig.jira.migration.client;

import static com.google.common.base.Preconditions.checkNotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraClientFactory.class);

    public JiraClient createFor(JiraClientConfig jiraClientConfig) {
        LOGGER.debug("Creating new Jira client for Jira@{}", jiraClientConfig.getUri());
        return new JiraClient(checkNotNull(jiraClientConfig));
    }
}
