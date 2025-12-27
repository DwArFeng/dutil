package com.dwarfeng.dutil.develop.timer;

import com.dwarfeng.dutil.develop.timer.obs.TimerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestTimerObserver extends TimerAdapter {

    final List<Plan> scheduledPlan = new ArrayList<>();
    final List<Plan> runPlan = new ArrayList<>();
    final List<Integer> runCount = new ArrayList<>();
    final List<Long> runExpectedTime = new ArrayList<>();
    final List<Long> runActualTime = new ArrayList<>();
    final List<Plan> finishedPlan = new ArrayList<>();
    final List<Integer> finishedCount = new ArrayList<>();
    final List<Throwable> finishedThrowable = new ArrayList<>();
    final List<Plan> removedPlan = new ArrayList<>();
    int clearedCount = 0;
    boolean shutdownFlag = false;
    boolean terminatedFlag = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void firePlanScheduled(Plan plan) {
        scheduledPlan.add(plan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void firePlanRun(Plan plan, int count, long expectedRumTime, long actualRunTime) {
        runPlan.add(plan);
        runCount.add(count);
        runExpectedTime.add(expectedRumTime);
        runActualTime.add(actualRunTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void firePlanFinished(Plan plan, int finishedCount, Throwable throwable) {
        finishedPlan.add(plan);
        this.finishedCount.add(finishedCount);
        finishedThrowable.add(throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void firePlanRemoved(Plan plan) {
        removedPlan.add(plan);
    }

    @Override
    public void firePlanCleared() {
        clearedCount++;
    }

    @Override
    public void fireShutDown() {
        shutdownFlag = true;
    }

    @Override
    public void fireTerminated() {
        terminatedFlag = true;
    }
}
