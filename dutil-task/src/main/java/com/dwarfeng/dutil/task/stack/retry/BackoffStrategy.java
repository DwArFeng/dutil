package com.dwarfeng.dutil.task.stack.retry;

import java.time.Duration;

/**
 * 重试退避策略。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@FunctionalInterface
public interface BackoffStrategy {

    /**
     * 计算指定失败后的等待时间。
     *
     * @param failedAttempt 已失败的尝试次数，从 1 开始。
     * @param failure       最近一次失败。
     * @return 等待时间。
     */
    Duration delay(int failedAttempt, Throwable failure);
}
