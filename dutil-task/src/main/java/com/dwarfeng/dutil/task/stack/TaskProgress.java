package com.dwarfeng.dutil.task.stack;

import com.dwarfeng.dutil.task.internal.i18n.TaskMessageKey;
import com.dwarfeng.dutil.task.internal.i18n.TaskMessages;

import java.time.Instant;
import java.util.Objects;

/**
 * 不可变任务进度。
 *
 * @param fraction  完成比例。
 * @param message   进度说明。
 * @param updatedAt 更新时间。
 * @author DwArFeng
 * @since 2.0.0
 */
public record TaskProgress(double fraction, String message, Instant updatedAt) {

    /**
     * 创建任务进度。
     */
    public TaskProgress {
        if (!Double.isFinite(fraction) || fraction < 0.0 || fraction > 1.0) {
            throw new IllegalArgumentException(TaskMessages.message(TaskMessageKey.PROGRESS_FRACTION_INVALID));
        }
        message = Objects.requireNonNullElse(message, "");
        Objects.requireNonNull(updatedAt, "updatedAt");
    }

    /**
     * 返回尚未开始的进度。
     *
     * @return 初始进度。
     */
    public static TaskProgress initial() {
        return new TaskProgress(0.0, "", Instant.EPOCH);
    }
}
