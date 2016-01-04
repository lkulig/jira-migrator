package com.lkulig.jira.migration.util.scheduler.job;

import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobDetailFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobDetailFactory.class);

    public static JobDetail create(Class<?> clazz, String name) {
        LOGGER.info("Creating new Job for [class={}] with [name={}].", clazz, name);
        JobDetail jobDetail = new JobDetail();
        jobDetail.setName(name);
        jobDetail.setJobClass(clazz);

        return jobDetail;
    }
}
