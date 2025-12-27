package com.dwarfeng.dutil.basic.threads;

import com.dwarfeng.dutil.basic.str.Name;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 内部线程类。
 *
 * <p>
 * 该类在最大程度上封装了在其内部具有线程的对象。<br>
 * 该类中定义了一个内部线程，并且可以对其线程调用其中的方法。<br>
 * 该类中可以对其中的内部线程定义启动方法、循环方法、结束方法，并可以返回线程的状态，以及返回线程本身。<br>
 * 该类的有关线程的操作方法均为线程不安全，如有必要，需要在外部进行同步方法编写。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 * @deprecated <code>java.util.concurrent</code>
 * 包中拥有远比此完善的工具类，该工具类在以后的版本中不再开发，并且不应该继续使用该工具类。
 */
public abstract class InnerThread implements Name {

    protected Thread t;
    protected boolean runFlag;
    protected boolean isDaemon;
    protected final String name;
    protected final Lock lock;
    protected final Condition condition;
    private boolean runCheck;

    /**
     * 生成一个默认的内部线程类。该内部线程不是守护线程，且线程名为空。
     */
    public InnerThread() {
        this(null, false);
    }

    /**
     * 生成一个内部线程类，其内部线程是否为守护线程由构造器指定。
     *
     * @param isDaemon 该内部线程是否为守护线程。
     */
    public InnerThread(boolean isDaemon) {
        this(null, isDaemon);
    }

    public InnerThread(String name) {
        this(name, false);
    }

    /**
     * 生成一个具有指定名称的内部线程，其线程是否为守护线程有构造器指定。
     *
     * @param isDaemon 内部线程是否为守护线程。
     * @param name     该内部线程的名字。
     */
    public InnerThread(String name, boolean isDaemon) {
        this.runFlag = false;
        this.isDaemon = isDaemon;
        this.name = name;
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.runCheck = true;
    }

    /**
     * 返回该内部线程类的内部线程是否为守护线程。
     *
     * @return 该内部线程类的内部线程是否为守护线程。
     */
    public boolean isDaemon() {
        return this.isDaemon;
    }

    /**
     * 指定该内部线程类的内部线程是否为守护线程。
     *
     * <p>
     * 如果该内部线程已经被开启，则线程在关闭后的下一次开启才会设置为该设置。
     *
     * @param isDaemon 该内部线程类的内部线程是否为守护线程。
     */
    public void setDaemon(boolean isDaemon) {
        this.isDaemon = isDaemon;
    }

    /**
     * 返回该内部线程类的线程是否为活动的。
     *
     * @return 该内部线程类的线程是否为活动的。
     */
    public boolean isAlive() {
        if (t == null)
            return false;
        return t.isAlive();
    }

    /**
     * 启动线程。
     *
     * <p>
     * 如果在此之前，内部线程已经被启动，且仍未停止，则该方法无效。
     *
     * <p>
     * 该方法会丢弃旧的线程，指定新的线程。
     */
    public void start() {
        if (isAlive())
            return;
        t = createThread();
        runFlag = true;
        runCheck = true;
        t.start();
    }

    /**
     * 停止线程。
     *
     * <p>
     * 该方法会尽量的结束当前的线程，如果该线程在当时处于睡眠状态，则该方法会
     * 尝试唤醒该线程，并且将其的运行标志设为<code>false</code>。该线程会在执行最后 一次循环后停止。<br>
     * 如果在此之前，线程还未启动，则该方法无效。<br>
     * 一定不要在该类中的内部线程调用此方法，即不要在重写的
     * {@link InnerThread#threadStartMethod()}、{@link InnerThread#threadRunMethod()}、{@link InnerThread#threadStopMethod()}
     * 中调用此方法，否则可能会引起死锁。
     */
    public void stop() {
        this.runFlag = false;
        if (t != null)
            t.interrupt();
    }

    /**
     * 停止线程并阻塞。
     *
     * <p>
     * 该方法会尝试结束当前线程，并且在该线程的 {@link InnerThread#threadStopMethod()} 停止之前会一直阻塞该方法。
     * <br>
     * 如果在停止之前，线程还未启动，则该方法无效。<br>
     * 一定不要在该类中的内部线程调用此方法，即不要在重写的
     * {@link InnerThread#threadStartMethod()}、{@link InnerThread#threadRunMethod()}、{@link InnerThread#threadStopMethod()}
     * 中调用此方法，否则可能会引起死锁。
     */
    public void stopAndBlock() {
        this.stop();
        lock.lock();
        try {
            while (runCheck) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    // DO NOTHING
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * 线程的启动方法。
     *
     * <p>
     * 该方法会在该内部线程类执行<code>start()</code>方法之后首先进行调用。在 线程的整个生命周期内，该方法仅被调用一次。
     */
    protected abstract void threadStartMethod();

    /**
     * 线程的循环方法。
     *
     * <p>
     * 该方法会在该内部线程类执行<code>start()</code>方法之后，在执行完
     * {@link InnerThread#threadStartMethod()} 法之后循环调用。<br>
     * 当该内部线程类执行<code>stop()</code>方法之后，会在结束完当前的循环放方法（如果
     * 此时的方法没有执行完毕时）停止循环，并在其后执行 {@link InnerThread#threadStopMethod()} 法。
     */
    protected abstract void threadRunMethod();

    /**
     * 线程的停止方法。
     *
     * <p>
     * 该方法会在该内部线程类执行<code>stop()</code>方法之后，在该内部线程执行完
     * 之后作为最后一个被执行的方法执行。该程序在整个程序的生命周期内仅仅被执行一次。
     */
    protected abstract void threadStopMethod();

    /*
     * 创造一个新的线程。
     */
    private Thread createThread() {
        t = new Thread(new Runnable() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void run() {
                try {
                    // 启动线程
                    threadStartMethod();
                    while (runFlag) {
                        // 循环执行
                        threadRunMethod();
                    }
                    // 停止
                    threadStopMethod();
                } finally {
                    runCheck = false;
                    // 唤醒由于 stopAndBlock()方法阻塞的线程（如果存在）。
                    lock.lock();
                    try {
                        condition.signalAll();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });
        if (getName() != null && getName().length() > 0)
            t.setName(getName());
        t.setDaemon(isDaemon);
        return t;
    }
}
