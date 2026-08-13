package com.dwarfeng.dutil.task.sdk.retry;

import com.dwarfeng.dutil.task.stack.Task;
import com.dwarfeng.dutil.task.stack.retry.RetryPolicy;

import java.time.Duration;
import java.util.Objects;

/**
 * 重试任务适配工具。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class RetryingTasks {

    public static <T> Task<T> retry(Task<T> task, RetryPolicy policy) {
        Objects.requireNonNull(task, "task");
        Objects.requireNonNull(policy, "policy");
        return context -> {
            for (int attempt = 1; ; attempt++) {
                context.throwIfCancellationRequested();
                try {
                    return task.execute(context);
                } catch (Exception exception) {
                    if (attempt >= policy.maxAttempts() || !policy.retryOn().test(exception)) {
                        throw exception;
                    }
                    Duration delay = policy.delay(attempt, exception);
                    if (!delay.isZero()) {
                        Thread.sleep(delay);
                    }
                }
            }
        };
    }

    private RetryingTasks() {
        throw new AssertionError("No instances");
    }
}
