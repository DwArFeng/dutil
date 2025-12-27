package com.dwarfeng.dutil.develop.timer;

import com.dwarfeng.dutil.basic.prog.ObserverSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.dutil.develop.timer.obs.TimerObserver;

import java.util.Collection;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 计时器。
 *
 * <p>
 * 计时器主要用于周期性的执行计划（{@link Plan}）。 与系统自带的计时器（{@link java.util.Timer} 相比，
 * 该计时器最大的好处是可以独立的控制每一个计划的停止与启动。同时，该计时器是一个计时器观察器集合， 可以为注册的计时器观察器提供通知。
 *
 * <p>
 * 计时器是一个容器，在其中存放多个计划。并且根据每个计划的参数相应的周期性地执行任务，从这一点上，很像 {@link TimerTask}。
 * 与传统的计时器相比，该计时器在计划的取消以及移除机制上做出了改进。 传统计时器被计划的任务无法主动取出，即使调用其中的
 * <code>cancel()</code> 指令以后，也要通过调用计时器的 <code>purge()</code>指令间接的移除。
 * 相较而言该计时器在移除机制上则合理许多：
 *
 * <pre>
 * 1、{@link Plan} 相比较 {@link TimerTask} 来说，没有取消机制——它没有取消方法。
 * 2、该计时器将计划的取消与计划的移除统一起来，也就是说，当计划从计时器中移除时，它随即取消。
 * 3、被移除（取消）的计划本质上与没被取消的计划没有任何区别，当它被重新安排到计时器中，随时可以继续执行。
 * </pre>
 *
 * <p>
 * 计时器在没有任何计划时，不会主动停止。计时器的停止依靠其 {@link #shutdown()} 方法，
 * 该方法会停止计时器的内部计时线程，并且移除所有的计划。
 *
 * <p>
 * 一般来说， 计时器会使用一个专用的线程来周期性地执行计划， 正是由于这个性质， 计时器是线程安全的， 并且实现了外部读写安全接口。
 *
 * <p>
 * 计时器中的所有任务都是单独的，也就是说，不能向一个计时器中添加两个相等的任务。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface Timer extends ExternalReadWriteThreadSafe, ObserverSet<TimerObserver> {

    /**
     * 向该计时器中添加一个计划（可选操作）。
     *
     * @param plan 指定的计划。
     * @return 该操作是否改变了计时器本身。
     * @throws IllegalStateException         该计时器已经被取消。
     * @throws NullPointerException          入口参数为 <code>null</code>。
     * @throws UnsupportedOperationException 不支持该操作。
     * @throws IllegalStateException         计时器已经关闭。
     */
    boolean schedule(Plan plan) throws IllegalStateException, NullPointerException, UnsupportedOperationException;

    /**
     * 从该计时器中移除指定的计划（可选操作）。
     *
     * @param plan 指定的计划。
     * @return 该操作是否改变了计时器本身。
     * @throws UnsupportedOperationException 不支持该操作。
     */
    boolean remove(Plan plan) throws UnsupportedOperationException;

    /**
     * 从该计时器中清除所有的计划（可选操作）。
     *
     * @throws UnsupportedOperationException 不支持该操作。
     */
    void clear() throws UnsupportedOperationException;

    /**
     * 关闭该计时器。
     *
     * <p>
     * 该动作会停止当前计时器的计时线程，阻止后续计划的执行。 如果关闭该计时器时正好有计划在执行， 那么正在执行的计划不受影响。
     * 所有的计划执行完毕之后，计时器会停止线程并会清除所有的计划。在此之后，如果继续尝试向该计时器中添加计划， 则会报出
     * {@link IllegalStateException}。
     */
    void shutdown();

    /**
     * 返回该计时器是否被关闭。
     *
     * @return 该计时器是否被关闭。
     */
    boolean isShutdown();

    /**
     * 如果关闭后所有任务都已完成，则返回 <code>true</code>。注意，除非首先调用 <code>shutdown</code>，否则
     * <code>isTerminated</code> 永不为 true。
     *
     * @return 如果关闭后所有任务都已完成，则返回 <code>true</code>。
     */
    boolean isTerminated();

    /**
     * 阻塞调用线程，直到该后台关闭（可选方法）。
     *
     * @throws InterruptedException          线程在阻塞时被别的线程中断。
     * @throws UnsupportedOperationException 不支持该操作。
     */
    void awaitTermination() throws InterruptedException, UnsupportedOperationException;

    /**
     * 阻塞调用线程，直至该后台关闭或者阻塞超过指定的时间（可选方法）。
     *
     * @param timeout 指定的时间大小。
     * @param unit    指定的时间单位。
     * @return 如果该方法是由于超时而返回的，则返回 <code>false</code>，否则返回 <code>true</code>。
     * @throws InterruptedException          线程在阻塞时被别的线程中断。
     * @throws UnsupportedOperationException 不支持该操作。
     */
    boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException, UnsupportedOperationException;

    /**
     * 获取该计时器中所有的计划组成的集合。
     *
     * <p>
     * 该集合不可调用添加方法，如果在该集合中移除某个计划， 也会在计时器中移除该计划。
     *
     * @return 该计时器中所有的计划组成的集合。
     */
    Collection<Plan> plans();
}
