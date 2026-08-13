package com.dwarfeng.dutil.task.stack.event;

/**
 * 任务监听订阅。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@FunctionalInterface
public interface TaskSubscription extends AutoCloseable {

    /**
     * 取消订阅。
     */
    @Override
    void close();
}
