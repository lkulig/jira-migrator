package com.lkulig.jira.migration;

import com.lkulig.jira.migration.data.MigrationData;
import com.lkulig.jira.migration.data.MigrationDataFactory;
import com.lkulig.jira.migration.service.JiraMigrationService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import java.text.ParseException;

@Component
public class JiraMigrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraMigrator.class);

    @Autowired
    private JiraMigrationService service;
    @Autowired
    private MigrationDataFactory migrationDataFactory;

    public static void main(String[] args) throws ParseException, SchedulerException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "META-INF/applicationContext.xml");
        JiraMigrator jiraMigrator = applicationContext.getBean(JiraMigrator.class);
        jiraMigrator.migrate();
    }

    private void migrate() throws ParseException, SchedulerException {
        MigrationData migrationData = migrationDataFactory.createBatchMigrationData();
        LOGGER.info("Performing migration with following data [{}].", migrationData);
        service.migrate(migrationData);
        System.exit(1);
    }
}
