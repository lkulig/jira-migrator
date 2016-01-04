package com.lkulig.jira.migration.util.scheduler.exception;

public abstract class SchedulerException extends RuntimeException {

    public SchedulerException(String message, Throwable cause) {
        super(message, cause);
    }
}
