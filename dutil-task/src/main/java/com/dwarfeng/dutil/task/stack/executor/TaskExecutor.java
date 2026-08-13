package com.dwarfeng.dutil.task.stack.executor;

import com.dwarfeng.dutil.task.stack.Task;

/**
 * 任务执行器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface TaskExecutor extends AutoCloseable {

    /**
     * 提交任务。
     *
     * @param task    任务定义。
     * @param options 执行选项。
     * @param <T>     返回值类型。
     * @return 单次执行句柄。
     */
    <T> TaskHandle<T> submit(Task<T> task, TaskExecutionOptions options);

    /**
     * 使用默认选项提交任务。
     */
    default <T> TaskHandle<T> submit(Task<T> task) {
        return submit(task, TaskExecutionOptions.DEFAULT);
    }

    /**
     * 关闭执行器。
     */
    @Override
    void close();
}
