package com.lkulig.jira.migration.util.progress;

import static com.google.common.base.Strings.repeat;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgressLogger implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgressLogger.class);
    private static final String HASH = "#";
    private static final String EQUALS = "=";
    private static final int PROGRESS_BAR_LENGTH = 100;
    private static AtomicInteger processedIssues = new AtomicInteger();
    private static int totalIssues;

    public static void setTotalIssues(int issuesCount) {
        totalIssues = issuesCount;
    }

    public static void incrementProcessedIssues() {
        processedIssues.incrementAndGet();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        int percentage = processedIssues.get() * 100 / totalIssues;
        LOGGER.info("[{}{}] {}/{} issues {}%", repeat(HASH, percentage),
            repeat(EQUALS, PROGRESS_BAR_LENGTH - percentage),
            processedIssues.get(), totalIssues, percentage);
    }
}
