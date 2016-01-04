package com.lkulig.jira.migration.batch;

import static com.google.common.collect.FluentIterable.from;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.lkulig.jira.migration.batch.operation.BatchOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class BatchExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchExecutor.class);

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private ListeningExecutorService executor = MoreExecutors.listeningDecorator(executorService);

    public void execute(final List<BatchOperation> batchOperations) throws InterruptedException {
        executor.invokeAll(from(Lists.newArrayList(batchOperations.get(0)))
            .transform(new Function<BatchOperation, Callable<Void>>() {

                @Override
                public Callable<Void> apply(final BatchOperation batchOperation) {
                    return new Callable<Void>() {

                        @Override
                        public Void call() throws Exception {
                            LOGGER.info("Starting export operation [{}/{}].", batchOperation.operationNumber() + 1,
                                    batchOperations.size());
                            batchOperation.execute();
                            LOGGER.info("Finished export operation [{}/{}].", batchOperation.operationNumber() + 1,
                                    batchOperations.size());
                            return null;
                        }
                    };
                }
            }).toList());
    }
}
