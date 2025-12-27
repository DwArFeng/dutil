package com.dwarfeng.dutil.develop.backgr;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.backgr.obs.TaskObserver;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * 抽象任务。
 *
 * <p>
 * 任务接口的抽象实现。
 *
 * <p>
 * 该类良好地定义了 <code>run</code>方法，并且在<code>run</code> 方法中执行<code>todo</code>
 * 方法，<code>todo</code>方法中填写需要实现的具体任务。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public abstract class AbstractTask implements Task {

    /**
     * 观察器集合。
     */
    protected final Set<TaskObserver> observers;
    /**
     * 同步读写锁
     */
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock runningLock = new ReentrantLock();
    private final Condition runningCondition = runningLock.newCondition();

    private boolean finishedFlag = false;
    private boolean startedFlag = false;
    private Throwable throwable = null;

    /**
     * 生成一个默认的抽象任务。
     */
    public AbstractTask() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定观察器集合的抽象任务。
     *
     * @param observers 指定的观察器集合。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public AbstractTask(Set<TaskObserver> observers) throws NullPointerException {
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTTASK_0));
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
    public Set<TaskObserver> getObservers() {
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
    public boolean addObserver(TaskObserver observer) {
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
    public boolean removeObserver(TaskObserver observer) {
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
     * {@inheritDoc}
     */
    @Override
    public boolean isStarted() {
        lock.readLock().lock();
        try {
            return startedFlag;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinished() {
        lock.readLock().lock();
        try {
            return finishedFlag;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    public Exception getException() {
        lock.readLock().lock();
        try {
            if (throwable instanceof Exception) {
                return (Exception) throwable;
            } else {
                return new Exception(throwable);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Throwable getThrowable() {
        lock.readLock().lock();
        try {
            return throwable;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void awaitFinish() throws InterruptedException {
        runningLock.lock();
        try {
            // TODO 此处将 finishedFlag 换成了 isFinished() 方法，在将来的实际运行中确认这样做是否会产生死锁。
            while (!isFinished()) {
                runningCondition.await();
            }
        } finally {
            runningLock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean awaitFinish(long timeout, TimeUnit unit) throws InterruptedException {
        runningLock.lock();
        try {
            long nanosTimeout = unit.toNanos(timeout);
            // TODO 此处将 finishedFlag 换成了 isFinished() 方法，在将来的实际运行中确认这样做是否会产生死锁。
            while (!isFinished()) {
                if (nanosTimeout > 0)
                    nanosTimeout = runningCondition.awaitNanos(nanosTimeout);
                else
                    return false;
            }
            return true;
        } finally {
            runningLock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        // 置位开始标志，并且通知观察器。
        lock.writeLock().lock();
        try {
            startedFlag = true;
        } finally {
            lock.writeLock().unlock();
        }
        fireStarted();
        try {
            // 运行准备方法。
            beforeTodo();
            // 运行 todo 方法。
            todo();
            // 运行结束方法。
            afterTodo();
        } catch (Throwable e) {
            throwable = e;
        }
        // 置位结束标志，并且通知观察器。
        lock.writeLock().lock();
        try {
            finishedFlag = true;
        } finally {
            lock.writeLock().unlock();
        }
        fireFinished();
        // 唤醒等待线程
        runningLock.lock();
        try {
            runningCondition.signalAll();
        } finally {
            runningLock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AbstractTask [finishFlag=" + finishedFlag + ", startFlag=" + startedFlag + ", exception=" + throwable
                + "]";
    }

    /**
     * 通知观察器任务结束。
     */
    protected void fireFinished() {
        for (TaskObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireFinished();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器任务开始。
     */
    protected void fireStarted() {
        for (TaskObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireStarted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 在调用 {@link #todo()} 之前调用，默认不执行任何操作。
     *
     * @throws Exception 抛出的异常。
     */
    protected void beforeTodo() throws Exception {
        // 默认不执行任何操作。
    }

    /**
     * 在调用 {@link #todo()} 之后调用，默认不执行任何操作。
     *
     * @throws Exception 抛出的异常。
     */
    protected void afterTodo() throws Exception {
        // 默认不执行任何操作。
    }

    /**
     * 抽象任务需要实现的具体任务。
     *
     * <p>
     * 该方法允许抛出异常，如果抛出异常，任务则会终止，并且调用 {@link AbstractTask#getThrowable()}
     * 方法会返回抛出的异常。
     *
     * @throws Exception 抛出的异常。
     */
    protected abstract void todo() throws Exception;
}
