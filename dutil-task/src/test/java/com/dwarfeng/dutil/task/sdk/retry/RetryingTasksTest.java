package com.dwarfeng.dutil.task.sdk.retry;

import com.dwarfeng.dutil.task.sdk.executor.TaskExecutors;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link RetryingTasks} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RetryingTasksTest {

    @Test
    public void testRetryUntilSuccess() throws Exception {
        AtomicInteger attempts = new AtomicInteger();

        try (TaskExecutor executor = TaskExecutors.virtualThreads()) {
            int value = executor.submit(RetryingTasks.retry(context -> {
                int current = attempts.incrementAndGet();
                if (current < 3) {
                    throw new IllegalStateException("retry");
                }
                return current;
            }, RetryPolicies.noDelay(3))).get();

            assertEquals(3, value);
            assertEquals(3, attempts.get());
        }
    }

    @Test
    public void testRetryPredicateStopsExecution() {
        IllegalArgumentException expected = new IllegalArgumentException("stop");
        AtomicInteger attempts = new AtomicInteger();

        try (TaskExecutor executor = TaskExecutors.virtualThreads()) {
            ExecutionException exception = assertThrows(ExecutionException.class, () -> executor.submit(
                    RetryingTasks.retry(context -> {
                        attempts.incrementAndGet();
                        throw expected;
                    }, new com.dwarfeng.dutil.task.stack.retry.RetryPolicy(
                            5, (attempt, failure) -> java.time.Duration.ZERO,
                            failure -> !(failure instanceof IllegalArgumentException)
                    ))
            ).get());

            assertSame(expected, exception.getCause());
            assertEquals(1, attempts.get());
        }
    }
}
