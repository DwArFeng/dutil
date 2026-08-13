package com.dwarfeng.dutil.task.stack;

/**
 * 单次任务执行状态。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public enum TaskState {
    CREATED,
    RUNNING,
    SUCCEEDED,
    FAILED,
    CANCELLED,
    TIMED_OUT;

    /**
     * 返回状态是否已经终止。
     *
     * @return 是否终止。
     */
    public boolean isTerminal() {
        return this == SUCCEEDED || this == FAILED || this == CANCELLED || this == TIMED_OUT;
    }
}
