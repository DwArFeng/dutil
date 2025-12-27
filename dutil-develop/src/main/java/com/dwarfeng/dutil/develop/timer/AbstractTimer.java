package com.dwarfeng.dutil.develop.timer;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.timer.obs.TimerObserver;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 抽象计时器。
 *
 * <p>
 * 计时器的抽象实现，提供了锁和观察器的实现。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public abstract class AbstractTimer implements Timer {

    /**
     * 观察器集合
     */
    protected final Set<TimerObserver> observers;
    /**
     * 同步锁
     */
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 生成一个默认的观察器。
     */
    public AbstractTimer() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定的观察器集合的抽象观察器。
     *
     * @param observers 指定的观察器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public AbstractTimer(Set<TimerObserver> observers) {
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTBACKGROUND_0));
        this.observers = observers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadWriteLock getLock() {
        return lock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<TimerObserver> getObservers() {
        lock.readLock().lock();
        try {
            return observers;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addObserver(TimerObserver observer) {
        lock.writeLock().lock();
        try {
            return observers.add(observer);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(TimerObserver observer) {
        lock.writeLock().lock();
        try {
            return observers.remove(observer);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearObserver() {
        lock.writeLock().lock();
        try {
            observers.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 通知观察器指定的计划被安排。
     *
     * @param plan 指定的计划。
     */
    protected void firePlanScheduled(Plan plan) {
        for (TimerObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.firePlanScheduled(plan);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器指定的计划开始。
     *
     * @param plan            指定的计划。
     * @param count           该计划当前的运行次数。
     * @param expectedRumTime 计划运行的理论时间。
     * @param actualRunTime   计划运行的实际时间。
     */
    protected void firePlanRun(Plan plan, int count, long expectedRumTime, long actualRunTime) {
        for (TimerObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.firePlanRun(plan, count, expectedRumTime, actualRunTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器指定的计划结束。
     *
     * @param plan          指定的计划。
     * @param finishedCount 该计划运行完成的次数。
     * @param throwable     本次运行抛出的异常，如没有，则为 <code>null</code>。
     */
    protected void firePlanFinished(Plan plan, int finishedCount, Throwable throwable) {
        for (TimerObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.firePlanFinished(plan, finishedCount, throwable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器指定的计划被移除。
     *
     * @param plan 指定的计划。
     */
    protected void firePlanRemoved(Plan plan) {
        for (TimerObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.firePlanRemoved(plan);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器所有计划被清除。
     */
    protected void firePlanCleared() {
        for (TimerObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.firePlanCleared();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器计时器被关闭。
     */
    protected void fireShutDown() {
        for (TimerObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireShutDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器计时器被终结。
     */
    protected void fireTerminated() {
        for (TimerObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireTerminated();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
