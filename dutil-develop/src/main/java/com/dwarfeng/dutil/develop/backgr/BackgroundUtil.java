package com.dwarfeng.dutil.develop.backgr;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.dutil.basic.prog.ReadOnlyGenerator;
import com.dwarfeng.dutil.basic.threads.ThreadUtil;
import com.dwarfeng.dutil.develop.backgr.obs.BackgroundObserver;
import com.dwarfeng.dutil.develop.backgr.obs.TaskObserver;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 有关后台的工具包。
 *
 * <p>
 * 该包中包含后台的常用方法。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public final class BackgroundUtil {

    /**
     * 从指定的 {@link Runnable} 中生成一个新的任务。
     *
     * @param runnable 指定的 {@link Runnable}。
     * @return 从指定的 {@link Runnable} 中生成的新任务。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Task newTaskFromRunnable(Runnable runnable) throws NullPointerException {
        return newTaskFromRunnable(runnable, Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 从指定的 {@link Runnable} 中生成一个新的任务。
     *
     * @param runnable  指定的 {@link Runnable}。
     * @param observers 指定的观察器集合。
     * @return 从指定的 {@link Runnable} 中生成的新任务。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Task newTaskFromRunnable(Runnable runnable, Set<TaskObserver> observers) throws NullPointerException {
        Objects.requireNonNull(runnable, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_0));
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_4));

        return new RunnableTask(runnable, observers);
    }

    private final static class RunnableTask extends AbstractTask {

        private final Runnable runnable;

        public RunnableTask(Runnable runnable, Set<TaskObserver> observers) throws NullPointerException {
            super(observers);
            this.runnable = runnable;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void todo() {
            runnable.run();
        }

    }

    /**
     * 通过指定的 {@link Callable} 生成一个新的任务。
     *
     * @param callable 指定的 {@link Callable}。
     * @return 从指定的 {@link Callable} 生成的新任务。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static <V> ResultTask<V> newTaskFromCallable(Callable<V> callable) throws NullPointerException {
        return newTaskFromCallable(callable, Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 通过指定的 {@link Callable} 生成一个新的任务。
     *
     * @param callable  指定的 {@link Callable}。
     * @param observers 指定的观察器集合。
     * @return 从指定的 {@link Callable} 生成的新任务。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static <V> ResultTask<V> newTaskFromCallable(Callable<V> callable, Set<TaskObserver> observers)
            throws NullPointerException {
        Objects.requireNonNull(callable, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_5));
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_4));

        return new CallableTask<>(callable, observers);
    }

    private static final class CallableTask<V> extends ResultTask<V> {

        private final Callable<V> callable;

        public CallableTask(Callable<V> callable, Set<TaskObserver> observers) throws NullPointerException {
            super(observers);
            this.callable = callable;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void todo() throws Exception {
            setResult(callable.call());
        }

    }

    /**
     * 通过指定的任务和指定的阻塞任务数组生成一个阻塞任务。
     *
     * <p>
     * 生成的任务会代理指定任务的 {@link Task#getException()} 和 {@link Task#getThrowable()} 结果性质的方法。
     *
     * <p>
     * 关于任务的阻塞<br>
     * 在该任务执行时，只有所有参与阻塞的任务完成后，才开始执行任务中的功能语句。 <br>
     * 阻塞任务可以被应用在数个任务需要按照先后关系依次执行的情形中，特别是在并发线程中先后执行指定的数个任务。
     * 只要将先执行的任务作为后执行任务的阻塞任务，即可以完成任务的先后执行。 <br>
     * 当阻塞任务具有至少一个阻塞任务时，如果在等待阻塞任务时阻塞任务被中断（抛出 {@link InterruptedException} 异常），
     * 该任务将会终止执行，并且调用 {@link Task#getThrowable()} 会返回相应的异常。
     *
     * @param task       指定的任务。
     * @param blockTasks 指定的阻塞任务数组。
     * @return 通过指定的任务和指定的阻塞任务数组生成的阻塞任务。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static Task blockedTask(Task task, Task[] blockTasks) throws NullPointerException, IllegalStateException {
        Objects.requireNonNull(task, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_1));
        Objects.requireNonNull(blockTasks, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_6));

        return new BlockedTask(task, ArrayUtil.getNonNull(blockTasks), Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 通过指定的任务和指定的阻塞任务数组生成一个阻塞任务。
     *
     * <p>
     * 生成的任务会代理指定任务的 {@link Task#getException()} 和 {@link Task#getThrowable()} 结果性质的方法。
     *
     * <p>
     * 关于任务的阻塞<br>
     * 在该任务执行时，只有所有参与阻塞的任务完成后，才开始执行任务中的功能语句。 <br>
     * 阻塞任务可以被应用在数个任务需要按照先后关系依次执行的情形中，特别是在并发线程中先后执行指定的数个任务。
     * 只要将先执行的任务作为后执行任务的阻塞任务，即可以完成任务的先后执行。 <br>
     * 当阻塞任务具有至少一个阻塞任务时，如果在等待阻塞任务时阻塞任务被中断（抛出 {@link InterruptedException} 异常），
     * 该任务将会终止执行，并且调用 {@link Task#getThrowable()} 会返回相应的异常。
     *
     * @param task       指定的任务。
     * @param blockTasks 指定的阻塞任务数组。
     * @param observers  指定的观察器集合。
     * @return 通过指定的任务和指定的阻塞任务数组生成的阻塞任务。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static Task blockedTask(Task task, Task[] blockTasks, Set<TaskObserver> observers)
            throws NullPointerException, IllegalStateException {
        Objects.requireNonNull(task, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_1));
        Objects.requireNonNull(blockTasks, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_6));
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_4));

        return new BlockedTask(task, ArrayUtil.getNonNull(blockTasks), observers);
    }

    private static final class BlockedTask extends AbstractTask {

        private final Task task;
        private final Task[] blockTasks;

        public BlockedTask(Task task, Task[] blockTasks, Set<TaskObserver> observers) throws NullPointerException {
            super(observers);
            this.task = task;
            this.blockTasks = blockTasks;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @Deprecated
        public Exception getException() {
            lock.readLock().lock();
            try {
                return task.getException();

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
                return task.getThrowable();

            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void beforeTodo() throws Exception {
            for (Task blockTask : blockTasks)
                blockTask.awaitFinish();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void todo() {
            task.run();
        }

    }

    /**
     * 由指定的任务生成一个不可编辑的任务。
     *
     * @param task 指定的任务。
     * @return 由指定的任务生成的不可编辑的任务。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Task unmodifiableTask(Task task) {
        Objects.requireNonNull(task, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_1));
        return new UnmodifiableTask(task);
    }

    private static final class UnmodifiableTask implements Task {

        private final Task delegate;

        public UnmodifiableTask(Task delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            throw new UnsupportedOperationException("run");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<TaskObserver> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(TaskObserver observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(TaskObserver observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return ThreadUtil.unmodifiableReadWriteLock(delegate.getLock());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isStarted() {
            return delegate.isStarted();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isFinished() {
            return delegate.isFinished();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @Deprecated
        public Exception getException() {
            return delegate.getException();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Throwable getThrowable() {
            return delegate.getThrowable();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void awaitFinish() {
            throw new UnsupportedOperationException("awaitFinish");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean awaitFinish(long timeout, TimeUnit unit) {
            throw new UnsupportedOperationException("awaitFinish");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return delegate.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (delegate.equals(obj))
                return true;
            return super.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegate.toString();
        }

    }

    /**
     * 根据指定的后台生成一个不可编辑的后台。
     *
     * @param background 指定的后台。
     * @return 由指定的后台生成的不可编辑的后台。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Background unmodifiableBackground(Background background) {
        Objects.requireNonNull(background, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_2));
        return new UnmodifiableBackground(background);
    }

    private static final class UnmodifiableBackground implements Background {

        private final Background delegate;

        public UnmodifiableBackground(Background delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<BackgroundObserver> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(BackgroundObserver observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(BackgroundObserver observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return ThreadUtil.unmodifiableReadWriteLock(delegate.getLock());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean submit(Task task) {
            throw new UnsupportedOperationException("submit");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean submitAll(Collection<? extends Task> c) {
            throw new UnsupportedOperationException("submitAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void shutdown() {
            throw new UnsupportedOperationException("shutdown");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isShutdown() {
            return delegate.isShutdown();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isTerminated() {
            return delegate.isTerminated();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void awaitTermination() {
            throw new UnsupportedOperationException("awaitTermination");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) {
            throw new UnsupportedOperationException("awaitTermination");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<Task> tasks() {
            return delegate.tasks();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return delegate.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (delegate.equals(obj))
                return true;
            return super.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegate.toString();
        }

    }

    /**
     * 由指定的后台和指定的只读生成器生成一个只读后台。
     *
     * @param background 指定的后台。
     * @param generator  指定的只读生成器。
     * @return 由指定的后台和指定的只读生成器生成的只读后台。
     */
    public static Background readOnlyBackground(Background background, ReadOnlyGenerator<Task> generator) {
        Objects.requireNonNull(background, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_2));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_3));

        return new ReadOnlyBackground(background, generator);
    }

    /**
     * 根据指定的后台生成一个只读的后台。
     *
     * @param background 指定的后台。
     * @return 由指定的后台生成的只读后台。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Background readOnlyBackground(Background background) {
        Objects.requireNonNull(background, DwarfUtil.getExceptionString(ExceptionStringKey.BACKGROUNDUTIL_2));

        return new ReadOnlyBackground(background, task -> {
            return unmodifiableTask(task);
        });
    }

    private static final class ReadOnlyBackground implements Background {

        private final Background delegate;
        private final ReadOnlyGenerator<Task> generator;

        public ReadOnlyBackground(Background delegate, ReadOnlyGenerator<Task> generator) {
            this.delegate = delegate;
            this.generator = generator;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return delegate.getLock();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<BackgroundObserver> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(BackgroundObserver observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(BackgroundObserver observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean submit(Task task) {
            throw new UnsupportedOperationException("submit");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean submitAll(Collection<? extends Task> c) {
            throw new UnsupportedOperationException("submitAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void shutdown() {
            throw new UnsupportedOperationException("shutdown");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isShutdown() {
            return delegate.isShutdown();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isTerminated() {
            return delegate.isTerminated();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void awaitTermination() {
            throw new UnsupportedOperationException("awaitTermination");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) {
            throw new UnsupportedOperationException("awaitTermination");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<Task> tasks() {
            return CollectionUtil.readOnlySet(delegate.tasks(), generator);
        }

    }

    private BackgroundUtil() {
    }
}
