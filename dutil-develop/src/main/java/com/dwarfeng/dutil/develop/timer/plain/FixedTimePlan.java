package com.dwarfeng.dutil.develop.timer.plan;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.timer.AbstractPlan;
import com.dwarfeng.dutil.develop.timer.obs.PlanObserver;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 定时运行计划。
 *
 * <p>
 * 定时运行计划是指计划执行的间隔是一定的，该类计划的下一次执行时间等于本次实际运行时间加计划的运行间隔。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public abstract class FixedTimePlan extends AbstractPlan {

    /**
     * 该计划的运行间隔。
     */
    protected final long period;

    /**
     * 生成一个指定运行周期，指定运行偏置的定时运行计划。
     *
     * <p>
     * 计划的运行周期必须大于等于 0。
     *
     * <p>
     * 所谓的运行偏置是指该计划的首次运行时间与当前系统时间的差值，以毫秒为单位，且不得小于 0。
     *
     * @param period        指定的运行周期。
     * @param nextRunOffset 指定的运行偏置。
     * @throws IllegalArgumentException 参数 <code>period</code> 小于等于 0 或者参数 <code>nextRunOffset</code>
     *                                  小于 0。
     */
    public FixedTimePlan(long period, long nextRunOffset) {
        this(period, nextRunOffset, Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个指定运行周期，指定运行偏置，指定观察器集合的定时运行计划。
     *
     * <p>
     * 计划的运行周期必须大于等于 0。
     *
     * <p>
     * 所谓的运行偏置是指该计划的首次运行时间与当前系统时间的差值，以毫秒为单位，且不得小于 0。
     *
     * @param period        指定的运行周期。
     * @param nextRunOffset 指定的运行偏置。
     * @param observers     指定的观察器集合。
     * @throws IllegalArgumentException 参数 <code>period</code> 小于等于 0 或者参数 <code>nextRunOffset</code>
     *                                  小于 0。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     */
    public FixedTimePlan(long period, long nextRunOffset, Set<PlanObserver> observers) {
        super(nextRunOffset, observers);
        Objects.requireNonNull(period, DwarfUtil.getExceptionString(ExceptionStringKey.FIXEDTIMEPLAN_0));

        this.period = period;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FixedTimePlan [period=" + period + ", isRunning()=" + isRunning() + ", getExpectedRunTime()="
                + getExpectedRunTime() + ", getActualRunTime()=" + getActualRunTime() + ", getFinishedCount()="
                + getFinishedCount() + ", getLastThrowable()=" + getLastThrowable() + ", getLastThrowableCount()="
                + getLastThrowableCount() + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected long updateNextRunTime() {
        long l = getActualRunTime();
        return l < 0 ? period : l + period;
    }
}
