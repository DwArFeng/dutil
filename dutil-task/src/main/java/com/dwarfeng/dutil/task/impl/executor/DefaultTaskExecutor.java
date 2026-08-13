package com.dwarfeng.dutil.task.impl.executor;

import com.dwarfeng.dutil.task.internal.i18n.TaskMessageKey;
import com.dwarfeng.dutil.task.internal.i18n.TaskMessages;
import com.dwarfeng.dutil.task.stack.Task;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutionOptions;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutor;
import com.dwarfeng.dutil.task.stack.executor.TaskHandle;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 默认任务执行器。
 *
 * <p>
 * 默认构造器使用 JDK 虚拟线程执行每次任务，并使用单独的轻量调度器处理超时。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class DefaultTaskExecutor implements TaskExecutor {

    private final ExecutorService executor;
    private final ScheduledExecutorService timeoutScheduler;
    private final boolean ownsExecutor;
    private final AtomicBoolean closed = new AtomicBoolean();

    public DefaultTaskExecutor() {
        this(Executors.newVirtualThreadPerTaskExecutor(), true);
    }

    public DefaultTaskExecutor(ExecutorService executor) {
        this(executor, false);
    }

    private DefaultTaskExecutor(ExecutorService executor, boolean ownsExecutor) {
        this.executor = Objects.requireNonNull(executor, "executor");
        this.timeoutScheduler = Executors.newSingleThreadScheduledExecutor(
                Thread.ofPlatform().daemon().name("dutil-task-timeout-", 0).factory()
        );
        this.ownsExecutor = ownsExecutor;
    }

    @Override
    public <T> TaskHandle<T> submit(Task<T> task, TaskExecutionOptions options) {
        Objects.requireNonNull(task, "task");
        Objects.requireNonNull(options, "options");
        if (closed.get()) {
            throw new IllegalStateException(TaskMessages.message(TaskMessageKey.EXECUTOR_CLOSED));
        }
        DefaultTaskHandle<T> handle = new DefaultTaskHandle<>(options.name());
        Future<?> future = executor.submit(() -> handle.execute(task));
        handle.bind(future);
        if (options.hasTimeout()) {
            ScheduledFuture<?> timeoutFuture = timeoutScheduler.schedule(
                    () -> handle.timeout(options.timeout()), options.timeout().toNanos(), TimeUnit.NANOSECONDS
            );
            handle.completion().whenComplete((result, throwable) -> timeoutFuture.cancel(false));
        }
        return handle;
    }

    @Override
    public void close() {
        if (!closed.compareAndSet(false, true)) {
            return;
        }
        timeoutScheduler.close();
        if (ownsExecutor) {
            executor.close();
        }
    }
}
