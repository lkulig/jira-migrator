package com.lkulig.jira.migration.util;

import com.lkulig.jira.migration.data.MigrationData;
import com.lkulig.jira.migration.util.progress.ProgressLogger;
import com.lkulig.jira.migration.util.scheduler.Scheduler;
import com.lkulig.jira.migration.util.scheduler.SchedulerFactory;
import com.lkulig.jira.migration.util.scheduler.exception.SchedulerException;
import com.lkulig.jira.migration.util.scheduler.job.JobDetailFactory;
import com.lkulig.jira.migration.util.scheduler.trigger.TriggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private SchedulerFactory schedulerFactory;

    @Value("${trigger.expression}")
    private String expression;

    public Object monitorExportProgress(ProceedingJoinPoint point) {
        try {
            Scheduler scheduler = schedulerFactory.create();
            JobDetail jobDetail = JobDetailFactory.create(ProgressLogger.class, "exportProgressLogging");
            CronTrigger trigger = TriggerFactory.create("exportProgressLoggingTrigger", expression);

            MigrationData migrationData = (MigrationData) point.getArgs()[0];
            ProgressLogger.setTotalIssues(migrationData.issuesCount);

            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);

            Object toReturn = point.proceed();
            scheduler.stop();
            return toReturn;
        } catch (SchedulerException e) {
            LOGGER.error("Error occurred during execution of logging aspect.", e);
        } catch (Throwable throwable) {
            LOGGER.error("Unknown error occurred during execution of logging aspect.", throwable);
        }
        return null;
    }
}
