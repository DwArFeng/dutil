package com.dwarfeng.dutil.task.sdk.composition;

import com.dwarfeng.dutil.task.stack.Task;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutor;
import com.dwarfeng.dutil.task.stack.executor.TaskHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * 批量任务和任务组合工具。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class TaskCompositions {

    public static <T> Task<List<T>> sequence(List<? extends Task<? extends T>> tasks) {
        List<? extends Task<? extends T>> definitions = List.copyOf(tasks);
        return context -> {
            List<T> results = new ArrayList<>(definitions.size());
            for (Task<? extends T> task : definitions) {
                context.throwIfCancellationRequested();
                results.add(task.execute(context));
            }
            return List.copyOf(results);
        };
    }

    public static <T> Task<List<T>> parallel(
            List<? extends Task<? extends T>> tasks, TaskExecutor executor
    ) {
        List<? extends Task<? extends T>> definitions = List.copyOf(tasks);
        Objects.requireNonNull(executor, "executor");
        return context -> {
            List<TaskHandle<? extends T>> handles = new ArrayList<>(definitions.size());
            for (Task<? extends T> definition : definitions) {
                handles.add(executor.submit(definition));
            }
            List<T> results = new ArrayList<>(handles.size());
            try {
                for (TaskHandle<? extends T> handle : handles) {
                    context.throwIfCancellationRequested();
                    results.add(handle.get());
                }
                return List.copyOf(results);
            } catch (CancellationException exception) {
                handles.forEach(handle -> handle.cancel(true));
                throw exception;
            } catch (InterruptedException exception) {
                handles.forEach(handle -> handle.cancel(true));
                Thread.currentThread().interrupt();
                throw exception;
            } catch (ExecutionException exception) {
                handles.forEach(handle -> handle.cancel(true));
                Throwable cause = exception.getCause();
                if (cause instanceof Exception checkedException) {
                    throw checkedException;
                }
                throw new IllegalStateException(cause);
            }
        };
    }

    private TaskCompositions() {
        throw new AssertionError("No instances");
    }
}
