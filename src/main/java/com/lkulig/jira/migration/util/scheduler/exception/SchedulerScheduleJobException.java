package com.lkulig.jira.migration.util.scheduler.exception;

public class SchedulerScheduleJobException extends SchedulerException {

    private static final String MESSAGE = "Failed to schedule job";

    public SchedulerScheduleJobException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
