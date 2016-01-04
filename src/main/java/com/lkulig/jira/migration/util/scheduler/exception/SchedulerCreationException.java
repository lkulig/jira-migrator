package com.lkulig.jira.migration.util.scheduler.exception;

public class SchedulerCreationException extends SchedulerException {

    private static final String MESSAGE = "Failed to create scheduler";

    public SchedulerCreationException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
