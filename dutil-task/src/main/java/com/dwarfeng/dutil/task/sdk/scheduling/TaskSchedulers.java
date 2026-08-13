package com.dwarfeng.dutil.task.sdk.scheduling;

import com.dwarfeng.dutil.task.impl.scheduling.DefaultTaskScheduler;
import com.dwarfeng.dutil.task.stack.executor.TaskExecutor;
import com.dwarfeng.dutil.task.stack.scheduling.TaskScheduler;

import java.util.Objects;

/**
 * 任务调度器工厂。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class TaskSchedulers {

    public static TaskScheduler from(TaskExecutor taskExecutor) {
        return new DefaultTaskScheduler(Objects.requireNonNull(taskExecutor, "taskExecutor"));
    }

    private TaskSchedulers() {
        throw new AssertionError("No instances");
    }
}
