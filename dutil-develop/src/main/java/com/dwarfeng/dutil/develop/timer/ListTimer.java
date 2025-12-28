package com.dwarfeng.dutil.develop.timer;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;
import com.dwarfeng.dutil.develop.timer.obs.PlanAdapter;
import com.dwarfeng.dutil.develop.timer.obs.TimerObserver;

import java.util.*;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * 列表计时器。
 *
 * <p>
 * 利用列表实现的计时器。
 *
 * <p>
 * 请不要用任何手段（比如反射）中止该类实例中的线程，因为这样做会引发不可预料的结果。
 *
 * <p>
 * 根据计时器的文档，列表计时器中的任务都是单独的，即使该计时器内部由一个 List 维护，也不能向其中添加已经存在的任务。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class ListTimer extends AbstractTimer {

    /**
     * 计时器后台默认的线程工厂。
     */
    public static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("ListTimer", false,
            Thread.NORM_PRIORITY);
    /**
     * 计划下次运行时间比较器。
     */
    public static final Comparator<Plan> SCHEDULE_PLAN_COMPARATOR = TimerUtil.newScheduleComparator();
    /**
     * 计时器的最小执行间隔。
     */
    public static final Long MIN_RUN_PERIOD = 1L;

    /**
     * 计划列表。
     */
    protected final List<Plan> plans;

    private final Set<Plan> plans2Schedule = new HashSet<>();
    private final Set<Plan> plans2Remove = new HashSet<>();
    private final Thread thread;
    private final Condition condition = lock.writeLock().newCondition();
    private final Map<Plan, PlanInspector> inspecRefs = new HashMap<>();

    private boolean shutdownFlag = false;
    private boolean terminateFlag = false;

    /**
     * 生成一个具有默认维护列表，默认的线程工厂，默认观察器集合的列表计时器。
     */
    public ListTimer() {
        this(new ArrayList<>(), THREAD_FACTORY, Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个指定维护列表，指定的线程工厂，指定观察器集合的列表计时器。
     *
     * @param plans         指定的维护列表。
     * @param threadFactory 指定的线程工厂。
     * @param observers     指定的观察器集合。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public ListTimer(List<Plan> plans, ThreadFactory threadFactory, Set<TimerObserver> observers)
            throws NullPointerException {
        super(observers);

        Objects.requireNonNull(plans, DwarfUtil.getExceptionString(ExceptionStringKey.LISTTIMER_0));
        Objects.requireNonNull(threadFactory, DwarfUtil.getExceptionString(ExceptionStringKey.LISTTIMER_1));

        this.plans = plans;

        thread = threadFactory.newThread(new ThreadRunner());
        thread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean schedule(Plan plan)
            throws IllegalStateException, NullPointerException, UnsupportedOperationException {
        lock.writeLock().lock();
        try {
            // 判断计时器是否已经结束。
            if (isShutdown())
                throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.LISTTIMER_2));

            if (Objects.isNull(plan) || plans.contains(plan) || plan.getNextRunTime() < 0
                    || plans2Schedule.contains(plan))
                return false;

            // 将计划添加到待添加计划集合中，并添加计划观察器。
            plans2Schedule.add(plan);
            PlanInspector inspector = new PlanInspector(plan);
            if (!plan.addObserver(inspector))
                return false;
            inspecRefs.put(plan, inspector);

            firePlanScheduled(plan);
            signalCondition();
            return true;

        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Plan plan) throws UnsupportedOperationException {
        lock.writeLock().lock();
        try {
            if (Objects.isNull(plan) || !(plans.contains(plan) || plans2Schedule.contains(plan))
                    || plans2Remove.contains(plan))
                return false;

            plans2Remove.add(plan);
            firePlanRemoved(plan);
            signalCondition();
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() throws UnsupportedOperationException {
        lock.writeLock().lock();
        try {
            plans2Remove.addAll(plans2Schedule);
            plans2Remove.addAll(plans);
            firePlanCleared();
            // 唤醒计时器线程，检查当前队列。
            signalCondition();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdown() {
        lock.writeLock().lock();
        try {
            shutdownFlag = true;
            // 唤醒计时器线程，检查当前队列。
            signalCondition();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShutdown() {
        lock.readLock().lock();
        try {
            return shutdownFlag;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTerminated() {
        lock.readLock().lock();
        try {
            return terminateFlag;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void awaitTermination() throws InterruptedException {
        lock.writeLock().lock();
        try {
            while (!terminateFlag) {
                condition.await();
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        lock.writeLock().lock();
        try {
            long nanosTimeout = unit.toNanos(timeout);
            while (!terminateFlag) {
                if (nanosTimeout > 0)
                    nanosTimeout = condition.awaitNanos(nanosTimeout);
                else
                    return false;
            }
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Plan> plans() {
        lock.readLock().lock();
        try {
            Set<Plan> plans = new HashSet<>();
            plans.addAll(this.plans);
            plans.addAll(plans2Schedule);
            plans.removeAll(plans2Remove);
            return Collections.unmodifiableCollection(plans);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 唤醒计时器线程。
     */
    private void signalCondition() {
        lock.writeLock().lock();
        try {
            condition.signalAll();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private class PlanInspector extends PlanAdapter {

        private final Plan plan;

        public PlanInspector(Plan plan) {
            this.plan = plan;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void fireRun(int count, long expectedRumTime, long actualRunTime) {
            lock.readLock().lock();
            try {
                firePlanRun(plan, count, expectedRumTime, actualRunTime);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void fireFinished(int finishedCount, Throwable throwable) {
            lock.readLock().lock();
            try {
                firePlanFinished(plan, finishedCount, throwable);
            } finally {
                lock.readLock().unlock();
            }
        }

    }

    private final class ThreadRunner implements Runnable {

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            try {
                // 计时器没有关闭的情况下，一直运行主循环。
                while (!isShutdown()) {
                    mainLoop();
                }

                // 代码运行到此处，意味着计时器已经被关闭了。
                shutdownMethod();

            } catch (InterruptedException ignore) {
                // 抛异常也要按照基本法。
            }
        }

        /**
         * 主循环。
         */
        private void mainLoop() throws InterruptedException {
            // 定义变量
            Plan aimPlan;
            long aimPlanRunTime;
            long systemTime;

            lock.writeLock().lock();
            try {
                // 对 shutdownFlag 进行双重检查。
                if (shutdownFlag) {
                    return;
                }

                // 遍历所有待移除的计划，将待移除的计划全部移除。
                for (Plan plan : plans2Remove) {
                    removePlan(plan, false);
                }
                plans2Remove.clear();

                // 遍历所有待添加的计划，将计划按照下一次执行的时间排序。
                for (Plan plan : plans2Schedule) {
                    CollectionUtil.insertByOrder(plans, plan, SCHEDULE_PLAN_COMPARATOR);
                }
                plans2Schedule.clear();

                // 检查计划列表是否为空，如果列表为空，则线程等待。
                if (plans.isEmpty()) {
                    condition.await();
                    return;
                }

                // 取出计划列表中距离执行时间最近的一个计划（第 0 个计划）
                aimPlan = plans.get(0);
            } finally {
                lock.writeLock().unlock();
            }

            // 获取目标计划的下一个执行时间和当前的系统时间。
            aimPlanRunTime = aimPlan.getNextRunTime();
            systemTime = System.currentTimeMillis();

            // 判断系统时间是否大于其下一个执行时间。
            if (systemTime >= aimPlanRunTime) {
                // 执行当前计划。
                // 注：根据 Plan 的协议，在 Plan 运行完毕后会通知观察器。而 ListTimer 中的侦听
                // PlanInspect 负责判断该计划的进一步动作，是继续运行还是被移除。
                try {
                    aimPlan.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                lock.writeLock().lock();
                try {
                    // 查看计时器是否被关闭。
                    if (shutdownFlag) {
                        // 移除计划，并查看该计划移除后是否是最后一个计划。
                        removePlan(aimPlan, true);
                        if (plans.isEmpty()) {
                            // 将终结标识置为 true，通知观察器，并唤醒计时器线程，通知 awaitTerminal 方法。
                            terminateFlag = true;
                            fireTerminated();
                            signalCondition();
                        }
                    } else {
                        // 判断 nextRunTime 是否小于 0
                        if (aimPlan.getNextRunTime() < 0) {
                            // 移除计划。
                            removePlan(aimPlan, true);
                        } else {
                            // 移除该计划，并且按照执行的先后顺序插入该计划。
                            plans.remove(0);
                            CollectionUtil.insertByOrder(plans, aimPlan, SCHEDULE_PLAN_COMPARATOR);
                        }
                    }
                    aimPlan = null;

                    // 此处休眠，保障计时器的最小运行间隔。
                    condition.await(MIN_RUN_PERIOD, TimeUnit.MICROSECONDS);
                } finally {
                    lock.writeLock().unlock();
                }

            } else {
                lock.writeLock().lock();
                try {
                    // 线程休眠 : 下一个执行时间-系统时间
                    condition.await(aimPlanRunTime - systemTime, TimeUnit.MILLISECONDS);
                } finally {
                    lock.writeLock().unlock();
                }
            }

        }

        private void shutdownMethod() {
            lock.writeLock().lock();
            try {
                // 检查队列是否为空。
                if (plans.isEmpty()) {
                    // 将终结标识置为 true，通知观察器，并唤醒计时器线程，通知 awaitTerminal 方法。
                    terminateFlag = true;
                    fireTerminated();
                    signalCondition();
                    return;
                }
                // 判断第 0 个元素是否正在执行
                if (!plans.get(0).isRunning()) {
                    // 如果不在执行，则直接清除队列中的所有元素。
                    plans.clear();
                    firePlanCleared();
                    // 将终结标识置为 true，通知观察器，并唤醒计时器线程，通知 awaitTerminal 方法。
                    terminateFlag = true;
                    fireTerminated();
                    signalCondition();
                } else {
                    // 删除除了第 0 个元素之外的所有元素（除了第 0 个元素之外，剩下的元素一定没有执行。）;
                    if (plans.size() >= 2) {
                        for (int i = plans.size() - 1; i > 0; i--) {
                            Plan plan = plans.get(i);
                            plans.remove(i);
                            firePlanRemoved(plan);
                        }
                    }
                }
            } finally {
                lock.writeLock().unlock();
            }

        }

        private void removePlan(Plan plan, boolean fireFlag) {
            // 注意：下面 if 中的表达式不能换成等价的非-短路或。
            if (!(plans.remove(plan) || plans2Schedule.remove(plan))) {
                // 该异常不应该抛出，不对外开放，故不设置国际化接口。
                new IllegalStateException("An exception occurred while the plan was being removed.").printStackTrace();
            }

            if (!plan.removeObserver(inspecRefs.remove(plan))) {
                // 该异常不应该抛出，不对外开放，故不设置国际化接口。
                new IllegalStateException("The listener was not properly removed.").printStackTrace();
            }

            if (fireFlag)
                firePlanRemoved(plan);
        }
    }
}
