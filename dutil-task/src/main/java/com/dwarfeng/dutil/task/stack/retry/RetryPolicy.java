package com.dwarfeng.dutil.task.stack.retry;

import com.dwarfeng.dutil.task.internal.i18n.TaskMessageKey;
import com.dwarfeng.dutil.task.internal.i18n.TaskMessages;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 不可变重试策略。
 *
 * @param maxAttempts 最大尝试次数，包含首次执行。
 * @param backoff     退避策略。
 * @param retryOn     可重试失败判定器。
 * @author DwArFeng
 * @since 2.0.0
 */
public record RetryPolicy(int maxAttempts, BackoffStrategy backoff, Predicate<Throwable> retryOn) {

    /**
     * 创建重试策略。
     */
    public RetryPolicy {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException(TaskMessages.message(TaskMessageKey.RETRY_ATTEMPTS_INVALID));
        }
        Objects.requireNonNull(backoff, "backoff");
        Objects.requireNonNull(retryOn, "retryOn");
    }

    /**
     * 返回指定失败后的退避时间。
     */
    public Duration delay(int failedAttempt, Throwable failure) {
        Duration duration = Objects.requireNonNull(backoff.delay(failedAttempt, failure), "backoff delay");
        if (duration.isNegative()) {
            throw new IllegalStateException(TaskMessages.message(TaskMessageKey.RETRY_DELAY_NEGATIVE));
        }
        return duration;
    }
}
