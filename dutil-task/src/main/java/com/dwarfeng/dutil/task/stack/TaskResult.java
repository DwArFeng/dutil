package com.dwarfeng.dutil.task.stack;

import com.dwarfeng.dutil.task.internal.i18n.TaskMessageKey;
import com.dwarfeng.dutil.task.internal.i18n.TaskMessages;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * 单次任务执行的终态结果。
 *
 * @param state      终态。
 * @param value      成功结果值。
 * @param failure    失败原因。
 * @param startedAt  开始时间。
 * @param finishedAt 结束时间。
 * @param <T>        结果值类型。
 * @author DwArFeng
 * @since 2.0.0
 */
public record TaskResult<T>(TaskState state, T value, Throwable failure, Instant startedAt, Instant finishedAt) {

    /**
     * 创建终态结果。
     */
    public TaskResult {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(startedAt, "startedAt");
        Objects.requireNonNull(finishedAt, "finishedAt");
        if (!state.isTerminal()) {
            throw new IllegalArgumentException(TaskMessages.message(TaskMessageKey.RESULT_STATE_NOT_TERMINAL));
        }
        if (finishedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException(TaskMessages.message(TaskMessageKey.RESULT_FINISH_BEFORE_START));
        }
        boolean failureState = state == TaskState.FAILED || state == TaskState.TIMED_OUT;
        if (failureState && failure == null) {
            throw new IllegalArgumentException(TaskMessages.message(TaskMessageKey.RESULT_FAILURE_REQUIRED));
        }
        if (!failureState && failure != null) {
            throw new IllegalArgumentException(TaskMessages.message(TaskMessageKey.RESULT_FAILURE_FORBIDDEN));
        }
    }

    /**
     * 返回执行耗时。
     *
     * @return 执行耗时。
     */
    public Duration duration() {
        return Duration.between(startedAt, finishedAt);
    }

    /**
     * 返回可选失败原因。
     *
     * @return 失败原因。
     */
    public Optional<Throwable> optionalFailure() {
        return Optional.ofNullable(failure);
    }
}
