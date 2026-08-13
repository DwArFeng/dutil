package com.dwarfeng.dutil.task.impl.scheduling;

import com.dwarfeng.dutil.task.stack.scheduling.ScheduledTaskHandle;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 默认调度任务句柄。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class DefaultScheduledTaskHandle implements ScheduledTaskHandle {

    private final ScheduledFuture<?> future;
    private final AtomicLong executionCount;

    DefaultScheduledTaskHandle(ScheduledFuture<?> future, AtomicLong executionCount) {
        this.future = Objects.requireNonNull(future, "future");
        this.executionCount = Objects.requireNonNull(executionCount, "executionCount");
    }

    @Override
    public long executionCount() {
        return executionCount.get();
    }

    @Override
    public boolean isCancelled() {
        return future.isCancelled();
    }

    @Override
    public boolean isDone() {
        return future.isDone();
    }

    @Override
    public boolean cancel() {
        return future.cancel(false);
    }
}
