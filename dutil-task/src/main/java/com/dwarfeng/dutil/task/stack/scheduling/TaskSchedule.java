package com.dwarfeng.dutil.task.stack.scheduling;

import com.dwarfeng.dutil.task.internal.i18n.TaskMessageKey;
import com.dwarfeng.dutil.task.internal.i18n.TaskMessages;

import java.time.Duration;
import java.util.Objects;

/**
 * 轻量任务调度计划。
 *
 * @param mode         调度模式。
 * @param initialDelay 首次执行延迟。
 * @param interval     重复执行间隔；单次执行时为零。
 * @author DwArFeng
 * @since 2.0.0
 */
public record TaskSchedule(Mode mode, Duration initialDelay, Duration interval) {

    /**
     * 调度模式。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    public enum Mode {
        ONCE,
        FIXED_DELAY,
        FIXED_RATE
    }

    /**
     * 创建调度计划。
     */
    public TaskSchedule {
        Objects.requireNonNull(mode, "mode");
        Objects.requireNonNull(initialDelay, "initialDelay");
        Objects.requireNonNull(interval, "interval");
        if (initialDelay.isNegative() || interval.isNegative()) {
            throw new IllegalArgumentException(TaskMessages.message(TaskMessageKey.SCHEDULE_DURATION_NEGATIVE));
        }
        if (mode != Mode.ONCE && interval.isZero()) {
            throw new IllegalArgumentException(TaskMessages.message(TaskMessageKey.SCHEDULE_INTERVAL_NOT_POSITIVE));
        }
    }
}
