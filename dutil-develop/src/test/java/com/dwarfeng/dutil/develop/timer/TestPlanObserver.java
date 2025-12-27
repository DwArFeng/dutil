package com.dwarfeng.dutil.develop.timer;

import com.dwarfeng.dutil.develop.timer.obs.PlanAdapter;

class TestPlanObserver extends PlanAdapter {

    private boolean runningFlag = false;
    private int finishedCount = -1;
    private Throwable throwable = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireRun(int count, long expectedRumTime, long actualRunTime) {
        runningFlag = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireFinished(int finishedCount, Throwable throwable) {
        this.finishedCount = finishedCount;
        this.throwable = throwable;
    }

    public boolean isRunningFlag() {
        return runningFlag;
    }

    public int getFinishedCount() {
        return finishedCount;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
