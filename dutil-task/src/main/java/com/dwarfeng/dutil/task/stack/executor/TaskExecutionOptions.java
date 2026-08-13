package com.dwarfeng.dutil.task.stack.executor;

import com.dwarfeng.dutil.task.internal.i18n.TaskMessageKey;
import com.dwarfeng.dutil.task.internal.i18n.TaskMessages;

import java.time.Duration;
import java.util.Objects;

/**
 * 单次任务执行选项。
 *
 * @param name    执行名称。
 * @param timeout 超时时间，零表示不设置超时。
 * @author DwArFeng
 * @since 2.0.0
 */
public record TaskExecutionOptions(String name, Duration timeout) {

    public static final TaskExecutionOptions DEFAULT = new TaskExecutionOptions("task", Duration.ZERO);

    /**
     * 创建执行选项。
     */
    public TaskExecutionOptions {
        name = Objects.requireNonNullElse(name, "task");
        Objects.requireNonNull(timeout, "timeout");
        if (timeout.isNegative()) {
            throw new IllegalArgumentException(TaskMessages.message(TaskMessageKey.EXECUTION_TIMEOUT_NEGATIVE));
        }
    }

    /**
     * 返回是否设置了超时。
     *
     * @return 是否设置超时。
     */
    public boolean hasTimeout() {
        return !timeout.isZero();
    }
}
