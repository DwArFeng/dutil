package com.dwarfeng.dutil.task.stack.event;

import com.dwarfeng.dutil.task.stack.TaskProgress;
import com.dwarfeng.dutil.task.stack.TaskResult;
import com.dwarfeng.dutil.task.stack.TaskState;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * 任务生命周期事件。
 *
 * @param type       事件类型。
 * @param state      事件发生后的状态。
 * @param progress   当前进度。
 * @param result     可选终态结果。
 * @param occurredAt 发生时间。
 * @param <T>        任务返回值类型。
 * @author DwArFeng
 * @since 2.0.0
 */
public record TaskEvent<T>(Type type, TaskState state, TaskProgress progress, TaskResult<T> result,
                           Instant occurredAt) {

    /**
     * 任务事件类型。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public enum Type {
        STARTED,
        PROGRESS_CHANGED,
        COMPLETED
    }

    /**
     * 创建任务事件。
     */
    public TaskEvent {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(progress, "progress");
        Objects.requireNonNull(occurredAt, "occurredAt");
    }

    /**
     * 返回可选终态结果。
     *
     * @return 终态结果。
     */
    public Optional<TaskResult<T>> optionalResult() {
        return Optional.ofNullable(result);
    }
}
