package com.dwarfeng.dutil.develop.backgr;

import com.dwarfeng.dutil.basic.prog.ObserverSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.dutil.develop.backgr.obs.TaskObserver;

import java.util.concurrent.TimeUnit;

/**
 * 任务接口。
 *
 * <p>
 * 任务接口用于定义一项任务，该任务可用于在后台中执行。
 *
 * <p>
 * 任务的主要功能由 {@link #run()} 实现，需要注意的是，<b>只有按照规定的要求去实现 <code>run</code>
 * 方法，后台才有可能会正常工作</b>。 因为有的后台依赖这些规则判断任务是否开始和结束，如果不按照要求的话，
 * 这类后台的行为将是不可预料的，甚至会导致后台永远无法被终止、任务用于无法被移除。
 *
 * <p>
 * 任务接口的抽象实现 {@link AbstractTask} 完全按照规则编写了 <code>run</code>方法。在正常的情况下，
 * 用户只需要继承该抽象类便可以正确的实现该接口，并不需要对该接口直接进行实现。
 *
 * <p>
 * 如果一定要直接实现该接口，应该注意的是：<code>run</code>方法在运行的开始，一定要通知所有的观察器该任务已经开始运行；
 * 并且在运行结束的时候，一定要通知所有的观察器任务已经结束运行。如果 <code>run</code>方法中的代码有可能会抛出任何异常，
 * 那么一定要建立完善的异常判断机制，只要抛出异常，就要停止下一步的动作，并且要使
 * <code>getException</code>方法返回的异常等于抛出的这个异常。
 *
 * @author DwArFeng
 * @see AbstractTask
 * @since 0.1.0-beta
 */
public interface Task extends Runnable, ExternalReadWriteThreadSafe, ObserverSet<TaskObserver> {

    /**
     * 获取任务是否已经开始执行。
     *
     * @return 任务是否已经开始执行。
     */
    boolean isStarted();

    /**
     * 任务是否执行完毕。
     *
     * @return 是否执行完毕。
     */
    boolean isFinished();

    /**
     * 获取任务的异常。
     *
     * <p>
     * 如果没有异常，则返回 <code>null</code>。
     *
     * @return 任务的异常，如果没有，则返回 <code>null</code>。
     * @deprecated 该方法已经被更全面的 {@link #getThrowable()} 取代。
     */
    @Deprecated
    Exception getException();

    /**
     * 获取任务的可抛出对象。
     *
     * <p>
     * 如果任务执行一切顺利，没有异常或者错误，则返回 <code>null</code>。
     *
     * @return 任务的可抛出对象，如果没有，则返回 <code>null</code>。
     */
    Throwable getThrowable();

    /**
     * 等待该过程执行完毕。
     *
     * <p>
     * 调用该方法的线程会在过程执行完毕之前一直阻塞。
     *
     * @throws InterruptedException 线程在等待的时候被中断。
     */
    void awaitFinish() throws InterruptedException;

    /**
     * 阻塞调用线程，直到任务执行完毕或阻塞时间超过指定时间。
     *
     * @param timeout 指定的时间数值。
     * @param unit    指定的时间单位。
     * @return 如果线程是因为任务执行完毕而终止等待，则返回 <code>true</code>；如果是应为超时而结束等待则返回
     * <code>false</code>。
     * @throws InterruptedException 线程在阻塞的时候被中断。
     */
    boolean awaitFinish(long timeout, TimeUnit unit) throws InterruptedException;
}
