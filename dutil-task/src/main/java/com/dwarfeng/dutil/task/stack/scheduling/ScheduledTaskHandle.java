package com.dwarfeng.dutil.task.stack.scheduling;

/**
 * 调度任务句柄。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ScheduledTaskHandle extends AutoCloseable {

    long executionCount();

    boolean isCancelled();

    boolean isDone();

    boolean cancel();

    @Override
    default void close() {
        cancel();
    }
}
