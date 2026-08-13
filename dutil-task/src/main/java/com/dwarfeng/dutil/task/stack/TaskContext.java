package com.dwarfeng.dutil.task.stack;

import com.dwarfeng.dutil.task.internal.i18n.TaskMessageKey;
import com.dwarfeng.dutil.task.internal.i18n.TaskMessages;

import java.util.concurrent.CancellationException;

/**
 * 单次任务执行上下文。
 *
 * <p>
 * 任务通过该上下文响应取消请求并报告进度，不应保存上下文供执行结束后继续使用。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface TaskContext {

    /**
     * 返回是否已请求取消。
     *
     * @return 是否已请求取消。
     */
    boolean isCancellationRequested();

    /**
     * 如果已请求取消则抛出取消异常。
     *
     * @throws CancellationException 已请求取消。
     */
    default void throwIfCancellationRequested() {
        if (isCancellationRequested()) {
            throw new CancellationException(TaskMessages.message(TaskMessageKey.EXECUTION_CANCELLED));
        }
    }

    /**
     * 报告任务进度。
     *
     * @param fraction 介于 0 和 1 之间的完成比例。
     * @param message  可为空的进度说明。
     */
    void reportProgress(double fraction, String message);
}
