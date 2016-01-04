package com.lkulig.jira.migration.service;

import com.lkulig.jira.migration.batch.BatchExecutor;
import com.lkulig.jira.migration.batch.operation.BatchOperation;
import com.lkulig.jira.migration.batch.operation.BatchOperationFactory;
import com.lkulig.jira.migration.data.MigrationData;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.util.List;

@Component
public class JiraMigrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraMigrationService.class);

    @Autowired
    private BatchOperationFactory batchOperationFactory;

    @Autowired
    private BatchExecutor batchExecutor;

    public void migrate(MigrationData data) throws ParseException, SchedulerException {
        try {
            List<BatchOperation> batchOperations = batchOperationFactory.createBatchOperationsBasedOn(data);
            LOGGER.info("Executing batch operations [{}].", batchOperations.size());
            batchExecutor.execute(batchOperations);
        } catch (Exception e) {
            LOGGER.error("Error occurred during migration.", e);
            System.exit(1);
        }
    }
}
