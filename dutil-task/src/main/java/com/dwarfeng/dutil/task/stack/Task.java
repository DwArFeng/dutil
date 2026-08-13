package com.dwarfeng.dutil.task.stack;

/**
 * 可重复执行的任务定义。
 *
 * <p>
 * 任务对象只描述工作逻辑，不保存某次执行的状态。同一个定义可以安全地产生多次独立执行。
 *
 * @param <T> 任务返回值类型。
 * @author DwArFeng
 * @since 2.0.0
 */
@FunctionalInterface
public interface Task<T> {

    /**
     * 执行任务逻辑。
     *
     * @param context 本次执行上下文。
     * @return 执行结果值。
     * @throws Exception 执行失败时抛出的异常。
     */
    T execute(TaskContext context) throws Exception;
}
