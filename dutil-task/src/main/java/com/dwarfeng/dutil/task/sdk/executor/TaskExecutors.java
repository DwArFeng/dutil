package com.dwarfeng.dutil.task.sdk.executor;

import com.dwarfeng.dutil.task.impl.executor.DefaultTaskExecutor;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutor;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * 任务执行器工厂。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class TaskExecutors {

    public static TaskExecutor virtualThreads() {
        return new DefaultTaskExecutor();
    }

    public static TaskExecutor from(ExecutorService executorService) {
        return new DefaultTaskExecutor(Objects.requireNonNull(executorService, "executorService"));
    }

    private TaskExecutors() {
        throw new AssertionError("No instances");
    }
}
