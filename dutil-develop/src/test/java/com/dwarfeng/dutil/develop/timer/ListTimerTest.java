package com.dwarfeng.dutil.develop.timer;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import org.junit.*;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ListTimerTest {

    private static ListTimer timer;
    private static TestTimerObserver observer;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        timer = new ListTimer();
        observer = new TestTimerObserver();
        timer.addObserver(observer);
    }

    @After
    public void tearDown() throws Exception {
        timer.shutdown();
        timer.awaitTermination();
        timer.removeObserver(observer);
    }

    @Test
    public final void testSchedule() throws InterruptedException {
        Plan plan_1 = new TestFixTimePlan();
        Plan plan_2 = new TestFixTimePlan();

        assertTrue(timer.schedule(plan_1));
        Thread.sleep(300);
        assertTrue(plan_1.getFinishedCount() >= 3);
        assertTrue(plan_1.getFinishedCount() <= 4);

        assertTrue(timer.schedule(plan_2));
        Thread.sleep(300);
        assertTrue(plan_1.getFinishedCount() >= 6);
        assertTrue(plan_2.getFinishedCount() >= 3);
        assertTrue(plan_1.getFinishedCount() <= 7);
        assertTrue(plan_2.getFinishedCount() <= 4);

        assertFalse(timer.schedule(plan_1));
        assertFalse(timer.schedule(plan_2));

        assertEquals(2, observer.scheduledPlan.size());
        assertEquals(plan_1, observer.scheduledPlan.get(0));
        assertEquals(plan_2, observer.scheduledPlan.get(1));
    }

    @Test
    public final void testRemove() throws InterruptedException {
        Plan plan_1 = new TestFixTimePlan();
        Plan plan_2 = new TestFixTimePlan();
        Plan plan_3 = TimerUtil.dateLimitedPlan(new TestFixTimePlan(), System.currentTimeMillis() + 1000);

        timer.schedule(plan_1);
        timer.schedule(plan_2);
        timer.schedule(plan_3);

        Thread.sleep(10);
        assertTrue(timer.remove(plan_1));
        assertFalse(timer.remove(plan_1));
        Thread.sleep(100);
        assertEquals(2, timer.plans().size());
        assertEquals(1, observer.removedPlan.size());
        assertEquals(plan_1, observer.removedPlan.get(0));
        assertEquals(0, plan_1.getObservers().size());

        Thread.sleep(1500);
        assertEquals(1, timer.plans().size());
        assertEquals(2, observer.removedPlan.size());
        assertEquals(plan_3, observer.removedPlan.get(1));
        assertEquals(0, plan_3.getObservers().size());

        assertTrue(timer.remove(plan_2));
        Thread.sleep(10);
        assertEquals(0, timer.plans().size());
        assertEquals(3, observer.removedPlan.size());
        assertEquals(plan_2, observer.removedPlan.get(2));
        assertEquals(0, plan_2.getObservers().size());

        assertFalse(timer.remove(plan_3));
    }

    @Test
    public final void testClear() throws InterruptedException {
        Plan plan_1 = new TestFixTimePlan();
        Plan plan_2 = new TestFixTimePlan();

        timer.schedule(plan_1);
        timer.schedule(plan_2);

        Thread.sleep(10);

        timer.clear();

        assertTrue(timer.plans().isEmpty());
        assertEquals(1, observer.clearedCount);
    }

    @Test
    public final void testShutdown() {
        timer.schedule(new TestFixTimePlan());
        timer.shutdown();
        assertTrue(timer.isShutdown());
    }

    @Test(expected = IllegalStateException.class)
    public final void testShutdown1() {
        timer.shutdown();
        assertTrue(timer.isShutdown());
        timer.schedule(new TestFixTimePlan());

        fail("没有抛出异常");
    }

    @Test
    public final void testIsShutdown() {
        timer.schedule(new TestFixTimePlan());
        timer.shutdown();
        assertTrue(timer.isShutdown());
    }

    @Test
    public final void testIsTerminated() throws InterruptedException {
        Plan plan_1 = new TestBlockPlan();

        timer.schedule(plan_1);
        Thread.sleep(100);
        timer.shutdown();

        assertTrue(timer.isShutdown());
        assertFalse(timer.isTerminated());

        Thread.sleep(1500);

        assertTrue(timer.isTerminated());
    }

    @Test
    public final void testAwaitTermination() throws InterruptedException {
        Plan plan_1 = new TestBlockPlan(100);
        Plan plan_2 = new TestBlockPlan(100);
        Plan plan_3 = new TestBlockPlan(100);

        TimeMeasurer tm = new TimeMeasurer();
        tm.start();

        timer.schedule(plan_1);
        timer.schedule(plan_2);
        timer.schedule(plan_3);

        Thread.sleep(10);
        timer.shutdown();
        timer.awaitTermination();
        tm.stop();

        // 理论上，计时器只执行完第一个计划，就会进入结束调度。
        assertTrue(tm.getTimeMs() >= 100);
    }

    @Test
    public final void testAwaitTerminationLongTimeUnit() throws InterruptedException {
        Plan plan = new TestBlockPlan(300);

        timer.schedule(plan);
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        Thread.sleep(50);
        timer.shutdown();
        assertFalse(plan.awaitFinish(100, TimeUnit.MILLISECONDS));
        assertFalse(plan.awaitFinish(100, TimeUnit.MILLISECONDS));
        assertTrue(plan.awaitFinish(100, TimeUnit.MILLISECONDS));
        tm.stop();

        // 理论上，计时器只执行完第一个计划，就会进入结束调度。
        assertTrue(tm.getTimeMs() >= 300);
    }

    @Test
    public final void testPlans() {
        Plan plan_1 = new TestFixTimePlan();
        Plan plan_2 = new TestFixTimePlan();
        Plan plan_3 = new TestFixTimePlan();

        assertTrue(timer.schedule(plan_1));
        assertEquals(1, timer.plans().size());
        assertTrue(timer.schedule(plan_2));
        assertTrue(timer.schedule(plan_3));
        assertEquals(3, timer.plans().size());

        Collection<Plan> plans = timer.plans();

        assertEquals(3, plans.size());
        assertTrue(plans.contains(plan_1));
        assertTrue(plans.contains(plan_2));
        assertTrue(plans.contains(plan_3));
    }
}
