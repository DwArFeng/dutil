package com.dwarfeng.dutil.task.stack.event;

/**
 * 任务事件监听器。
 *
 * @param <T> 任务返回值类型。
 * @author DwArFeng
 * @since 2.0.0
 */
@FunctionalInterface
public interface TaskListener<T> {

    /**
     * 接收任务事件。
     *
     * @param event 任务事件。
     */
    void onEvent(TaskEvent<T> event);
}
