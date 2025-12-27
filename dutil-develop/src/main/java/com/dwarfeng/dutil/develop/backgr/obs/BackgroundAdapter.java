package com.dwarfeng.dutil.develop.backgr.obs;

import com.dwarfeng.dutil.develop.backgr.Task;

/**
 * 后台观察器适配器。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public abstract class BackgroundAdapter implements BackgroundObserver {

    @Override
    public void fireTaskSubmitted(Task task) {
    }

    @Override
    public void fireTaskStarted(Task task) {
    }

    @Override
    public void fireTaskFinished(Task task) {
    }

    @Override
    public void fireTaskRemoved(Task task) {
    }

    @Override
    public void fireShutDown() {
    }

    @Override
    public void fireTerminated() {
    }
}
