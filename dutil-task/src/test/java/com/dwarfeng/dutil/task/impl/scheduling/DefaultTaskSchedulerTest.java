package com.dwarfeng.dutil.task.impl.scheduling;

import com.dwarfeng.dutil.task.sdk.executor.TaskExecutors;
import com.dwarfeng.dutil.task.sdk.scheduling.TaskSchedulers;
import com.dwarfeng.dutil.task.sdk.scheduling.TaskSchedules;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutor;
import com.dwarfeng.dutil.task.stack.scheduling.ScheduledTaskHandle;
import com.dwarfeng.dutil.task.stack.scheduling.TaskScheduler;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link DefaultTaskScheduler} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DefaultTaskSchedulerTest {

    @Test
    public void testOnce() throws Exception {
        CountDownLatch completed = new CountDownLatch(1);

        try (TaskExecutor executor = TaskExecutors.virtualThreads();
             TaskScheduler scheduler = TaskSchedulers.from(executor)) {
            ScheduledTaskHandle handle = scheduler.schedule(
                    context -> "done", TaskSchedules.once(Duration.ofMillis(10)), result -> completed.countDown()
            );

            assertTrue(completed.await(5, TimeUnit.SECONDS));
            assertEquals(1, handle.executionCount());
            long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(1);
            while (!handle.isDone() && System.nanoTime() < deadline) {
                Thread.onSpinWait();
            }
            assertTrue(handle.isDone());
            assertFalse(handle.isCancelled());
        }
    }

    @Test
    public void testFixedDelayCancellation() throws Exception {
        AtomicInteger executions = new AtomicInteger();
        CountDownLatch completed = new CountDownLatch(1);

        try (TaskExecutor executor = TaskExecutors.virtualThreads();
             TaskScheduler scheduler = TaskSchedulers.from(executor)) {
            ScheduledTaskHandle handle = scheduler.schedule(
                    context -> executions.incrementAndGet(),
                    TaskSchedules.fixedDelay(Duration.ZERO, Duration.ofMillis(200)),
                    result -> completed.countDown()
            );

            assertTrue(completed.await(5, TimeUnit.SECONDS));
            assertTrue(handle.cancel());
            int countAfterCancellation = executions.get();
            Thread.sleep(50);

            assertTrue(handle.isCancelled());
            assertEquals(countAfterCancellation, executions.get());
        }
    }
}
