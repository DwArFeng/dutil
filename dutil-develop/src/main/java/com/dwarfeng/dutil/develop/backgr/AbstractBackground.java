package com.dwarfeng.dutil.develop.backgr;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.backgr.obs.BackgroundObserver;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 抽象后台。
 *
 * <p>
 * 后台的抽象实现，提供了锁和观察器的实现。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public abstract class AbstractBackground implements Background {

    /**
     * 观察器集合
     */
    protected final Set<BackgroundObserver> observers;
    /**
     * 同步锁
     */
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 生成一个默认的后台。
     */
    public AbstractBackground() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定的观察器集合的抽象后台。
     *
     * @param observers 指定的观察器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public AbstractBackground(Set<BackgroundObserver> observers) {
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
    public Set<BackgroundObserver> getObservers() {
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
    public boolean addObserver(BackgroundObserver observer) {
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
    public boolean removeObserver(BackgroundObserver observer) {
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
     * 通知观察器指定的任务被提交。
     *
     * @param task 指定的任务。
     */
    protected void fireTaskSubmitted(Task task) {
        for (BackgroundObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireTaskSubmitted(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器指定的任务开始。
     *
     * @param task 指定的任务。
     */
    protected void fireTaskStarted(Task task) {
        for (BackgroundObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireTaskStarted(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器指定的任务结束。
     *
     * @param task 指定的任务。
     */
    protected void fireTaskFinished(Task task) {
        for (BackgroundObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireTaskFinished(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器指定的任务被移除。
     *
     * @param task 指定的任务。
     */
    protected void fireTaskRemoved(Task task) {
        for (BackgroundObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireTaskRemoved(task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器后台被关闭。
     */
    protected void fireShutDown() {
        for (BackgroundObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireShutDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器后台被终结。
     */
    protected void fireTerminated() {
        for (BackgroundObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireTerminated();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
