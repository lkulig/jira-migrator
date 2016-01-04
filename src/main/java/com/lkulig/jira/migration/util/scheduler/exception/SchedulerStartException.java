package com.lkulig.jira.migration.util.scheduler.exception;

public class SchedulerStartException extends SchedulerException {

    private static final String MESSAGE = "Failed to start scheduler";

    public SchedulerStartException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
