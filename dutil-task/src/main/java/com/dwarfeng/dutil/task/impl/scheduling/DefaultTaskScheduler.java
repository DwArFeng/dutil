package com.dwarfeng.dutil.task.impl.scheduling;

import com.dwarfeng.dutil.task.internal.i18n.TaskMessageKey;
import com.dwarfeng.dutil.task.internal.i18n.TaskMessages;
import com.dwarfeng.dutil.task.stack.Task;
import com.dwarfeng.dutil.task.stack.TaskResult;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutor;
import com.dwarfeng.dutil.task.stack.scheduling.ScheduledTaskHandle;
import com.dwarfeng.dutil.task.stack.scheduling.TaskSchedule;
import com.dwarfeng.dutil.task.stack.scheduling.TaskScheduler;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * 默认轻量任务调度器。
 *
 * <p>
 * 调度线程只负责触发任务，实际任务仍由指定 {@link TaskExecutor} 执行。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class DefaultTaskScheduler implements TaskScheduler {

    private final TaskExecutor taskExecutor;
    private final ScheduledExecutorService scheduler;
    private final AtomicBoolean closed = new AtomicBoolean();

    public DefaultTaskScheduler(TaskExecutor taskExecutor) {
        this.taskExecutor = Objects.requireNonNull(taskExecutor, "taskExecutor");
        this.scheduler = Executors.newSingleThreadScheduledExecutor(
                Thread.ofPlatform().daemon().name("dutil-task-scheduler-", 0).factory()
        );
    }

    @Override
    public <T> ScheduledTaskHandle schedule(
            Task<T> task, TaskSchedule schedule, Consumer<TaskResult<T>> resultConsumer
    ) {
        Objects.requireNonNull(task, "task");
        Objects.requireNonNull(schedule, "schedule");
        Objects.requireNonNull(resultConsumer, "resultConsumer");
        if (closed.get()) {
            throw new IllegalStateException(TaskMessages.message(TaskMessageKey.SCHEDULER_CLOSED));
        }

        AtomicLong executionCount = new AtomicLong();
        Runnable command = () -> {
            executionCount.incrementAndGet();
            taskExecutor.submit(task).completion().thenAccept(resultConsumer);
        };
        long initialNanos = schedule.initialDelay().toNanos();
        ScheduledFuture<?> future = switch (schedule.mode()) {
            case ONCE -> scheduler.schedule(command, initialNanos, TimeUnit.NANOSECONDS);
            case FIXED_DELAY -> scheduler.scheduleWithFixedDelay(
                    command, initialNanos, schedule.interval().toNanos(), TimeUnit.NANOSECONDS
            );
            case FIXED_RATE -> scheduler.scheduleAtFixedRate(
                    command, initialNanos, schedule.interval().toNanos(), TimeUnit.NANOSECONDS
            );
        };
        return new DefaultScheduledTaskHandle(future, executionCount);
    }

    @Override
    public void close() {
        if (closed.compareAndSet(false, true)) {
            scheduler.close();
        }
    }
}
