package com.dwarfeng.dutil.task.internal.executor;

import com.dwarfeng.dutil.task.stack.TaskContext;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;

/**
 * 默认任务执行上下文。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class DefaultTaskContext implements TaskContext {

    private final BooleanSupplier cancellationRequested;
    private final BiConsumer<Double, String> progressConsumer;

    public DefaultTaskContext(
            BooleanSupplier cancellationRequested, BiConsumer<Double, String> progressConsumer
    ) {
        this.cancellationRequested = Objects.requireNonNull(cancellationRequested, "cancellationRequested");
        this.progressConsumer = Objects.requireNonNull(progressConsumer, "progressConsumer");
    }

    @Override
    public boolean isCancellationRequested() {
        return cancellationRequested.getAsBoolean();
    }

    @Override
    public void reportProgress(double fraction, String message) {
        progressConsumer.accept(fraction, message);
    }
}
