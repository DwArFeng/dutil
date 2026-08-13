package com.dwarfeng.dutil.task.stack.executor;

import com.dwarfeng.dutil.task.stack.TaskProgress;
import com.dwarfeng.dutil.task.stack.TaskResult;
import com.dwarfeng.dutil.task.stack.TaskState;
import com.dwarfeng.dutil.task.stack.event.TaskListener;
import com.dwarfeng.dutil.task.stack.event.TaskSubscription;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

/**
 * 单次任务执行句柄。
 *
 * @param <T> 任务返回值类型。
 * @author DwArFeng
 * @since 2.0.0
 */
public interface TaskHandle<T> extends Future<T> {

    /**
     * 返回本次执行名称。
     *
     * @return 执行名称。
     */
    String name();

    /**
     * 返回 dutil 任务执行状态。
     *
     * <p>
     * {@link Future#state()} 保留 JDK 原生状态语义；该方法额外区分尚未开始的执行。
     *
     * @return dutil 任务执行状态。
     */
    TaskState taskState();

    /**
     * 返回最近一次进度。
     *
     * @return 最近一次进度。
     */
    TaskProgress progress();

    /**
     * 返回已经产生的终态结果。
     *
     * @return 可选终态结果。
     */
    Optional<TaskResult<T>> result();

    /**
     * 返回终态结果完成阶段。
     *
     * @return 终态结果完成阶段。
     */
    CompletionStage<TaskResult<T>> completion();

    /**
     * 订阅执行事件。
     *
     * @param listener 监听器。
     * @return 可关闭的订阅。
     */
    TaskSubscription subscribe(TaskListener<T> listener);
}
