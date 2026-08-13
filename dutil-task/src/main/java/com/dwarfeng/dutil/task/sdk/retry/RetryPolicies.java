package com.dwarfeng.dutil.task.sdk.retry;

import com.dwarfeng.dutil.task.stack.retry.RetryPolicy;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 常用重试策略工厂。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class RetryPolicies {

    public static RetryPolicy noDelay(int maxAttempts) {
        return new RetryPolicy(maxAttempts, (attempt, failure) -> Duration.ZERO, failure -> true);
    }

    public static RetryPolicy fixed(int maxAttempts, Duration delay) {
        Objects.requireNonNull(delay, "delay");
        return new RetryPolicy(maxAttempts, (attempt, failure) -> delay, failure -> true);
    }

    public static RetryPolicy exponential(int maxAttempts, Duration initialDelay, Duration maximumDelay) {
        return exponential(maxAttempts, initialDelay, maximumDelay, failure -> true);
    }

    public static RetryPolicy exponential(
            int maxAttempts, Duration initialDelay, Duration maximumDelay, Predicate<Throwable> retryOn
    ) {
        Objects.requireNonNull(initialDelay, "initialDelay");
        Objects.requireNonNull(maximumDelay, "maximumDelay");
        return new RetryPolicy(maxAttempts, (attempt, failure) -> {
            int shift = Math.min(attempt - 1, 62);
            long multiplier = 1L << shift;
            try {
                Duration candidate = initialDelay.multipliedBy(multiplier);
                return candidate.compareTo(maximumDelay) > 0 ? maximumDelay : candidate;
            } catch (ArithmeticException exception) {
                return maximumDelay;
            }
        }, retryOn);
    }

    private RetryPolicies() {
        throw new AssertionError("No instances");
    }
}
