package com.dwarfeng.dutil.task.impl.executor;

import com.dwarfeng.dutil.base.sdk.i18n.MessageContext;
import com.dwarfeng.dutil.task.sdk.executor.TaskExecutors;
import com.dwarfeng.dutil.task.stack.TaskResult;
import com.dwarfeng.dutil.task.stack.TaskState;
import com.dwarfeng.dutil.task.stack.event.TaskEvent;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutionOptions;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutor;
import com.dwarfeng.dutil.task.stack.executor.TaskHandle;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link DefaultTaskExecutor} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DefaultTaskExecutorTest {

    @Test
    public void testSuccessProgressAndEvents() throws Exception {
        CountDownLatch entered = new CountDownLatch(1);
        CountDownLatch released = new CountDownLatch(1);
        List<TaskEvent.Type> eventTypes = new CopyOnWriteArrayList<>();
        TaskExecutor executor = TaskExecutors.virtualThreads();

        try (executor) {
            TaskHandle<String> handle = executor.submit(context -> {
                entered.countDown();
                assertTrue(released.await(5, TimeUnit.SECONDS));
                context.reportProgress(0.5, "half");
                return "done";
            }, new TaskExecutionOptions("success-task", Duration.ZERO));
            handle.subscribe(event -> eventTypes.add(event.type()));

            assertTrue(entered.await(5, TimeUnit.SECONDS));
            released.countDown();

            assertEquals("done", handle.get(5, TimeUnit.SECONDS));
            assertEquals("success-task", handle.name());
            assertEquals(TaskState.SUCCEEDED, handle.taskState());
            assertEquals(0.5, handle.progress().fraction(), 0.0);
            assertEquals("half", handle.progress().message());
            assertEquals(TaskState.SUCCEEDED, handle.result().orElseThrow().state());
            assertTrue(eventTypes.contains(TaskEvent.Type.PROGRESS_CHANGED));
            assertEquals(TaskEvent.Type.COMPLETED, eventTypes.getLast());
        }

        IllegalStateException exception = MessageContext.call(
                Locale.SIMPLIFIED_CHINESE,
                () -> assertThrows(IllegalStateException.class, () -> executor.submit(Object::toString))
        );
        assertEquals("任务执行器已关闭。", exception.getMessage());
    }

    @Test
    public void testFailure() throws Exception {
        IllegalArgumentException expected = new IllegalArgumentException("failed");

        try (TaskExecutor executor = TaskExecutors.virtualThreads()) {
            TaskHandle<String> handle = executor.submit(context -> {
                throw expected;
            });

            ExecutionException exception = assertThrows(ExecutionException.class, handle::get);
            assertSame(expected, exception.getCause());
            assertEquals(TaskState.FAILED, handle.taskState());
            assertSame(expected, handle.result().orElseThrow().failure());
        }
    }

    @Test
    public void testCancellation() throws Exception {
        CountDownLatch entered = new CountDownLatch(1);

        try (TaskExecutor executor = TaskExecutors.virtualThreads()) {
            TaskHandle<String> handle = executor.submit(context -> {
                entered.countDown();
                while (true) {
                    Thread.sleep(Duration.ofSeconds(1));
                    context.throwIfCancellationRequested();
                }
            });

            assertTrue(entered.await(5, TimeUnit.SECONDS));
            assertTrue(handle.cancel(true));
            assertThrows(CancellationException.class, handle::get);
            assertEquals(TaskState.CANCELLED, handle.taskState());
            assertTrue(handle.isCancelled());
            assertTrue(handle.isDone());
        }
    }

    @Test
    public void testTimeout() throws Exception {
        try (TaskExecutor executor = TaskExecutors.virtualThreads()) {
            TaskHandle<String> handle = executor.submit(context -> {
                Thread.sleep(Duration.ofSeconds(10));
                return "late";
            }, new TaskExecutionOptions("timeout-task", Duration.ofMillis(50)));

            ExecutionException exception = assertThrows(ExecutionException.class, handle::get);
            assertInstanceOf(TimeoutException.class, exception.getCause());
            assertEquals(TaskState.TIMED_OUT, handle.taskState());
            TaskResult<String> result = handle.result().orElseThrow();
            assertEquals(TaskState.TIMED_OUT, result.state());
            assertInstanceOf(TimeoutException.class, result.failure());
        }
    }

    @Test
    public void testTaskDefinitionCanExecuteRepeatedly() throws Exception {
        AtomicInteger counter = new AtomicInteger();

        try (TaskExecutor executor = TaskExecutors.virtualThreads()) {
            var task = com.dwarfeng.dutil.task.sdk.Tasks.fromSupplier(counter::incrementAndGet);

            assertEquals(Integer.valueOf(1), executor.submit(task).get());
            assertEquals(Integer.valueOf(2), executor.submit(task).get());
        }
    }
}
