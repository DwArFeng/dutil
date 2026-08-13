package com.dwarfeng.dutil.task.sdk;

import com.dwarfeng.dutil.task.stack.Task;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 任务定义工厂和转换工具。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class Tasks {

    public static <T> Task<T> fromCallable(Callable<? extends T> callable) {
        Objects.requireNonNull(callable, "callable");
        return context -> {
            context.throwIfCancellationRequested();
            return callable.call();
        };
    }

    public static <T> Task<T> fromSupplier(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier, "supplier");
        return context -> {
            context.throwIfCancellationRequested();
            return supplier.get();
        };
    }

    public static Task<Void> fromRunnable(Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable");
        return context -> {
            context.throwIfCancellationRequested();
            runnable.run();
            return null;
        };
    }

    public static <T, R> Task<R> map(Task<T> task, Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(task, "task");
        Objects.requireNonNull(mapper, "mapper");
        return context -> mapper.apply(task.execute(context));
    }

    public static <T, R> Task<R> flatMap(Task<T> task, Function<? super T, ? extends Task<R>> mapper) {
        Objects.requireNonNull(task, "task");
        Objects.requireNonNull(mapper, "mapper");
        return context -> Objects.requireNonNull(mapper.apply(task.execute(context)), "mapped task").execute(context);
    }

    private Tasks() {
        throw new AssertionError("No instances");
    }
}
