package com.lkulig.jira.migration.batch;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import com.lkulig.jira.migration.AbstractUnitTest;
import com.lkulig.jira.migration.batch.operation.BatchOperation;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.task.TaskExecutor;
import java.util.List;

public class BatchExecutorTest extends AbstractUnitTest {

    @Mock
    private BatchOperation batchOperation;

    @Mock
    private TaskExecutor taskExecutor;

    @InjectMocks
    private BatchExecutor batchExecutor;

    @Test
    public void shouldExecuteBatchOperations() {
//        // given
//        List<BatchOperation> batchOperations = givenBatchOperations();
//
//        // when
//        batchExecutor.execute(batchOperations);
//
//        // then
//        verify(batchOperation).execute();
    }

    private List<BatchOperation> givenBatchOperations() {
        return newArrayList(batchOperation);
    }
}
