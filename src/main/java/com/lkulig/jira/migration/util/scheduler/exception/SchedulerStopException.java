package com.lkulig.jira.migration.util.scheduler.exception;

public class SchedulerStopException extends SchedulerException {

    private static final String MESSAGE = "Failed to stop scheduler";

    public SchedulerStopException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
