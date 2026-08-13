package com.dwarfeng.dutil.task.internal.i18n;

import static com.dwarfeng.dutil.task.internal.i18n.TaskMessages.Catalog.IMPL;
import static com.dwarfeng.dutil.task.internal.i18n.TaskMessages.Catalog.STACK;

/**
 * 任务模块私有消息键及资源职责目录。
 *
 * <p>
 * 该枚举只服务于模块内部消息解析，不属于公共 API。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public enum TaskMessageKey {

    EXECUTION_CANCELLED(STACK, "task.execution.cancelled"),
    EXECUTION_TIMED_OUT(IMPL, "task.execution.timed_out"),
    EXECUTOR_CLOSED(IMPL, "task.executor.closed"),
    SCHEDULER_CLOSED(IMPL, "task.scheduler.closed"),
    PROGRESS_FRACTION_INVALID(STACK, "task.progress.fraction_invalid"),
    RESULT_STATE_NOT_TERMINAL(STACK, "task.result.state_not_terminal"),
    RESULT_FINISH_BEFORE_START(STACK, "task.result.finish_before_start"),
    RESULT_FAILURE_REQUIRED(STACK, "task.result.failure_required"),
    RESULT_FAILURE_FORBIDDEN(STACK, "task.result.failure_forbidden"),
    EXECUTION_TIMEOUT_NEGATIVE(STACK, "task.execution.timeout_negative"),
    RETRY_ATTEMPTS_INVALID(STACK, "task.retry.attempts_invalid"),
    RETRY_DELAY_NEGATIVE(STACK, "task.retry.delay_negative"),
    SCHEDULE_DURATION_NEGATIVE(STACK, "task.schedule.duration_negative"),
    SCHEDULE_INTERVAL_NOT_POSITIVE(STACK, "task.schedule.interval_not_positive");

    private final TaskMessages.Catalog catalog;
    private final String key;

    TaskMessageKey(TaskMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    /**
     * 返回消息资源所属的职责目录。
     *
     * @return 消息资源所属的职责目录。
     */
    TaskMessages.Catalog catalog() {
        return catalog;
    }

    /**
     * 返回资源键。
     *
     * @return 资源键。
     */
    public String key() {
        return key;
    }

}
