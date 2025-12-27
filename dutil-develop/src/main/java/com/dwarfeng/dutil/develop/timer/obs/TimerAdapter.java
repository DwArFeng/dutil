package com.dwarfeng.dutil.develop.timer.obs;

import com.dwarfeng.dutil.develop.timer.Plan;

/**
 * 计时器观察器适配器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public abstract class TimerAdapter implements TimerObserver {

    @Override
    public void firePlanScheduled(Plan plan) {
    }

    @Override
    public void firePlanRun(Plan plan, int count, long expectedRumTime, long actualRunTime) {
    }

    @Override
    public void firePlanFinished(Plan plan, int finishedCount, Throwable throwable) {
    }

    @Override
    public void firePlanRemoved(Plan plan) {
    }

    @Override
    public void firePlanCleared() {
    }

    @Override
    public void fireShutDown() {
    }

    @Override
    public void fireTerminated() {
    }
}
