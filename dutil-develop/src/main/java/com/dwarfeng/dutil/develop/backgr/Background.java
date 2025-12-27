package com.dwarfeng.dutil.develop.backgr;

import com.dwarfeng.dutil.basic.prog.ObserverSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.dutil.develop.backgr.obs.BackgroundObserver;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 后台接口。
 *
 * <p>
 * 后台用于处理向其中提交的任务，通常来讲，后台拥有自己的线程，因此后台的方法可以与程序前台的方法同步执行。
 * 正是由于后台的多线程性质，所有的后台均是线程安全的，并且实现了外部读写安全接口。
 *
 * <p>
 * 不同的后台对向其提交的任务有不同的规则，比如： 有的后台会根据提交的先后关系一一运行被提交的任务； 有的后台会开辟不同的线程同时执行不同的任务。
 * 当任务被提交的时候，则该规则便立即适用。
 *
 * <p>
 * 当提交的任务完成后，后台会立即将该任务移除，这个过程是自动的。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface Background extends ExternalReadWriteThreadSafe, ObserverSet<BackgroundObserver> {

    /**
     * 向后台中提交指定的任务。
     *
     * <p>
     * 只有非 <code>null</code> 且还未开始的任务才可提交成功。
     *
     * <p>
     * 试图向正在关闭的后台中提交任务会抛出异常。
     *
     * @param task 指定的任务。
     * @return 该任务是否被提交。
     * @throws IllegalStateException 试图向正在关闭的后台中提交任务。
     */
    boolean submit(Task task);

    /**
     * 向后台中提交指定 <code>collection</code> 中的所有任务。
     *
     * <p>
     * 只有非 <code>null</code> 且还未开始的任务才可提交成功。
     *
     * <p>
     * 试图向正在关闭的后台中提交任务会抛出异常。
     *
     * @param c 指定的<code>collection</code>。
     * @return 如果至少一个任务被提交，则返回 <code>true</code>。
     * @throws NullPointerException  入口参数为 <code>null</code>。
     * @throws IllegalStateException 试图向正在关闭的后台中提交任务。
     */
    boolean submitAll(Collection<? extends Task> c);

    /**
     * 关闭后台。
     *
     * <p>
     * 当后台关闭时，后台会拒绝后续的任务提交，已提交的任务则不受影响。
     *
     * <p>
     * 当已提交的所有任务执行完毕后，后台则会被终结。
     */
    void shutdown();

    /**
     * 如果此后台已关闭，则返回 <code>true</code>。
     *
     * @return 该后台是否已经关闭。
     */
    boolean isShutdown();

    /**
     * 如果关闭后所有任务都已完成，则返回 <code>true</code>。注意，除非首先调用 <code>shutdown</code> 或
     * <code>shutdownNow</code>，否则 <code>isTerminated</code> 永不为 true。
     *
     * @return 如果关闭后所有任务都已完成，则返回 <code>true</code>。
     */
    boolean isTerminated();

    /**
     * 阻塞调用线程，直到该后台关闭。
     *
     * @throws InterruptedException 线程在阻塞时被别的线程中断。
     */
    void awaitTermination() throws InterruptedException;

    /**
     * 阻塞调用线程，直至该后台关闭或者阻塞超过指定的时间。
     *
     * @param timeout 指定的时间大小。
     * @param unit    指定的时间单位。
     * @return 如果该方法是由于超时而返回的，则返回 <code>false</code>，否则返回 <code>true</code>。
     * @throws InterruptedException 线程在阻塞时被别的线程中断。
     */
    boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;

    /**
     * 获取由后台中所有的任务组成的集合。
     *
     * <p>
     * 该集合是只读的。
     *
     * @return 由后台中的所有任务组成的集合。
     */
    Set<Task> tasks();
}
