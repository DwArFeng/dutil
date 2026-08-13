package com.dwarfeng.dutil.task.sdk.scheduling;

import com.dwarfeng.dutil.task.stack.scheduling.TaskSchedule;

import java.time.Duration;

/**
 * 常用任务调度计划工厂。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class TaskSchedules {

    public static TaskSchedule once(Duration delay) {
        return new TaskSchedule(TaskSchedule.Mode.ONCE, delay, Duration.ZERO);
    }

    public static TaskSchedule fixedDelay(Duration initialDelay, Duration delay) {
        return new TaskSchedule(TaskSchedule.Mode.FIXED_DELAY, initialDelay, delay);
    }

    public static TaskSchedule fixedRate(Duration initialDelay, Duration period) {
        return new TaskSchedule(TaskSchedule.Mode.FIXED_RATE, initialDelay, period);
    }

    private TaskSchedules() {
        throw new AssertionError("No instances");
    }
}
