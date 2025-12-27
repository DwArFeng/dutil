package com.dwarfeng.dutil.develop.timer;

import com.dwarfeng.dutil.basic.prog.ObserverSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.dutil.develop.timer.obs.PlanObserver;

import java.util.concurrent.TimeUnit;

/**
 * 计划接口。
 *
 * <p>
 * 计划接口用于定义一项计划，该计划可以在计时器中周期性地执行。
 *
 * <p>
 * 计划的主要功能由 {@link #run()} 实现，需要注意的是，<b>只有按照规定的要求去实现 <code>run</code>
 * 方法，计时器才有可能会正常工作</b>。 因为有的计时器依赖这些规则判断计划是否开始和结束，如果不按照要求的话，
 * 这类计时器的行为将是不可预料的，甚至会导致计时器永远无法被终止、计划用于无法被移除。
 *
 * <p>
 * TODO 完善计划接口的规则。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface Plan extends Runnable, ExternalReadWriteThreadSafe, ObserverSet<PlanObserver> {

    /**
     * 获取该计划是否正在执行中。
     *
     * @return 该计划是否正在执行中。
     */
    boolean isRunning();

    /**
     * 获取该计划本次运行的期望运行时间。
     *
     * @return 该计划本次运行的期望运行时间。
     */
    long getExpectedRunTime();

    /**
     * 获取该计划本次运行的实际运行时间。
     *
     * <p>
     * 如果该计划从来没有运行过，则返回 <code>-1</code>。
     *
     * @return 该计划本次的实际运行时间。
     */
    long getActualRunTime();

    /**
     * 获取该计划的下次运行时间。
     *
     * <p>
     * 注意：在一个运行周期内，反复调用该方法，得到的值应当一致。
     *
     * <p>
     * 如果返回的时间大于等于 0 且小于当前的系统时间，那么代表该计划将会立即运行。
     *
     * <p>
     * 如果返回的时间小于 0，那么代表该计划不需要再次运行。
     *
     * @return 该计划的下次运行时间。
     */
    long getNextRunTime();

    /**
     * 获取计划已经执行完毕的次数。
     *
     * @return 计划已经执行完毕的次数。
     */
    int getFinishedCount();

    /**
     * 获取计划发生的最后一个异常。
     *
     * <p>
     * 如果没有异常，则返回 <code>null</code>。
     *
     * @return 计划发生的最后一个异常。
     */
    Throwable getLastThrowable();

    /**
     * 获取计划发生最后一个异常的周期。
     *
     * <p>
     * 当返回值等于 <code>-1</code> 时，代表该计划没有任何异常发生。
     *
     * @return 计划发生的最后一个异常的周期。
     */
    int getLastThrowableCount();

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
