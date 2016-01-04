package com.lkulig.jira.migration.util.scheduler.exception;

public class TriggerCreationException extends SchedulerException {

    private static final String MESSAGE = "Failed to create cron trigger.";

    public TriggerCreationException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
