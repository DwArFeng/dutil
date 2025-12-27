package com.dwarfeng.dutil.develop.timer;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.timer.obs.PlanObserver;
import com.dwarfeng.dutil.develop.timer.obs.TimerObserver;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 有关计时器的工具包。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public final class TimerUtil {

    /**
     * 生成一个新的计划比较器。
     *
     * <p>
     * 计划比较器用于比较两个计划的执行时间。 该比较器的规则是：
     *
     * <pre>
     * 1. 较早执行的计划小于较晚执行的计划。
     * 2. 永远不会执行的计划大于会在某时某刻执行的计划。
     * 3. 两个永远不会指定的互相相等。
     * </pre>
     *
     * @return 生成的新的计划比较器。
     */
    public static Comparator<Plan> newScheduleComparator() {
        return new SchedulePlanComparator();
    }

    private static final class SchedulePlanComparator implements Comparator<Plan> {

        /**
         * {@inheritDoc}
         */
        // 为了保证阅读性，有些代码不做简化。
        @SuppressWarnings("ConstantConditions")
        @Override
        public int compare(Plan o1, Plan o2) {
            Objects.requireNonNull(o1, DwarfUtil.getExceptionString(ExceptionStringKey.TIMERUTIL_0));
            Objects.requireNonNull(o2, DwarfUtil.getExceptionString(ExceptionStringKey.TIMERUTIL_1));

            long l1 = o1.getNextRunTime();
            long l2 = o2.getNextRunTime();

            if (l1 < 0 && l2 >= 0)
                return 1;

            if (l1 >= 0 && l2 < 0)
                return -1;

            if (l1 >= 0 && l2 >= 0)
                return Long.compare(l1, l2);

            return 0;
        }

    }

    /**
     * 根据指定的计时器生成一个不可变更的计时器。
     *
     * @param timer 指定的计时器。
     * @return 根据指定的计时器生成的不可变更的计时器。
     */
    public static final Timer unmodifiableTimer(Timer timer) {
        Objects.requireNonNull(timer, DwarfUtil.getExceptionString(ExceptionStringKey.TIMERUTIL_2));
        return new UnmodifiableTimer(timer);
    }

    private static final class UnmodifiableTimer implements Timer {

        private final Timer delegate;

        public UnmodifiableTimer(Timer delegate) {
            this.delegate = delegate;
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
        public Set<TimerObserver> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(TimerObserver observer) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(TimerObserver observer) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("clearObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean schedule(Plan plan)
                throws IllegalStateException, NullPointerException, UnsupportedOperationException {
            throw new UnsupportedOperationException("schedule");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Plan plan) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("clear");
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
        public void awaitTermination() throws InterruptedException {
            throw new UnsupportedOperationException("awaitTermination");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            throw new UnsupportedOperationException("awaitTermination");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<Plan> plans() {
            return Collections.unmodifiableCollection(delegate.plans());
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
            if (obj == this)
                return true;
            if (obj == delegate)
                return true;

            return delegate.equals(obj);
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
     * 根据指定的计划生成一个具有执行期限的计划。
     *
     * <p>
     * 生成的计划执在指定的执行期限后便停止执行。
     *
     * @param plan        指定的计划。
     * @param limitedDate 指定的执行期限限制，其值为 1970 年 1 月 1 日到计划执行期限包含的毫秒数。
     * @return 根据指定的计划生成的具有执行期限的计划。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static final Plan dateLimitedPlan(Plan plan, long limitedDate) {
        Objects.requireNonNull(plan, DwarfUtil.getExceptionString(ExceptionStringKey.TIMERUTIL_3));
        return new DateLimitedPlan(plan, limitedDate);
    }

    private static final class DateLimitedPlan implements Plan {

        private final Plan delegate;
        private final long limitedDate;

        public DateLimitedPlan(Plan delegate, long limitedDate) {
            this.delegate = delegate;
            this.limitedDate = limitedDate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            delegate.run();
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
        public Set<PlanObserver> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(PlanObserver observer) throws UnsupportedOperationException {
            return delegate.addObserver(observer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(PlanObserver observer) throws UnsupportedOperationException {
            return delegate.removeObserver(observer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() throws UnsupportedOperationException {
            delegate.clearObserver();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isRunning() {
            return delegate.isRunning();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getExpectedRunTime() {
            return delegate.getExpectedRunTime();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getActualRunTime() {
            return delegate.getActualRunTime();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getNextRunTime() {
            long delegateNextRunTime = delegate.getNextRunTime();

            if (delegateNextRunTime < 0 || delegateNextRunTime > limitedDate)
                return -1;

            return delegateNextRunTime;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getFinishedCount() {
            return delegate.getFinishedCount();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Throwable getLastThrowable() {
            return delegate.getLastThrowable();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getLastThrowableCount() {
            return delegate.getLastThrowableCount();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void awaitFinish() throws InterruptedException {
            delegate.awaitFinish();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean awaitFinish(long timeout, TimeUnit unit) throws InterruptedException {
            return delegate.awaitFinish(timeout, unit);
        }

    }

    /**
     * 根据指定的计划生成一个具有执行次数限制的计划。
     *
     * <p>
     * 生成的计划在执行指定的次数后便停止执行。
     *
     * @param plan         指定的计划。
     * @param limitedTimes 指定的执行次数限制。
     * @return 根据指定的计划生成的具有执行次数限制的计划。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static final Plan timesLimitedPlan(Plan plan, int limitedTimes) {
        Objects.requireNonNull(plan, DwarfUtil.getExceptionString(ExceptionStringKey.TIMERUTIL_3));
        return new TimesLimitedPlan(plan, limitedTimes);
    }

    private static final class TimesLimitedPlan implements Plan {

        private final Plan delegate;
        private final int limitedTimes;

        public TimesLimitedPlan(Plan delegate, int limitedTimes) {
            this.delegate = delegate;
            this.limitedTimes = limitedTimes;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            delegate.run();
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
        public Set<PlanObserver> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(PlanObserver observer) throws UnsupportedOperationException {
            return delegate.addObserver(observer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(PlanObserver observer) throws UnsupportedOperationException {
            return delegate.removeObserver(observer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() throws UnsupportedOperationException {
            delegate.clearObserver();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isRunning() {
            return delegate.isRunning();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getExpectedRunTime() {
            return delegate.getExpectedRunTime();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getActualRunTime() {
            return delegate.getActualRunTime();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getNextRunTime() {
            long delegateNextRunTime = delegate.getNextRunTime();

            if (delegateNextRunTime < 0)
                return -1;

            int finishedCount = delegate.getFinishedCount();

            if (finishedCount >= limitedTimes)
                return -1;

            return delegateNextRunTime;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getFinishedCount() {
            return delegate.getFinishedCount();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Throwable getLastThrowable() {
            return delegate.getLastThrowable();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getLastThrowableCount() {
            return delegate.getLastThrowableCount();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void awaitFinish() throws InterruptedException {
            delegate.awaitFinish();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean awaitFinish(long timeout, TimeUnit unit) throws InterruptedException {
            return delegate.awaitFinish(timeout, unit);
        }

    }

    // 禁止外部实例化。
    private TimerUtil() {
    }
}
