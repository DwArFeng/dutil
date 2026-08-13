package com.dwarfeng.dutil.task.stack.scheduling;

import com.dwarfeng.dutil.task.stack.Task;
import com.dwarfeng.dutil.task.stack.TaskResult;

import java.util.function.Consumer;

/**
 * 轻量任务调度器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface TaskScheduler extends AutoCloseable {

    <T> ScheduledTaskHandle schedule(Task<T> task, TaskSchedule schedule, Consumer<TaskResult<T>> resultConsumer);

    @Override
    void close();
}
