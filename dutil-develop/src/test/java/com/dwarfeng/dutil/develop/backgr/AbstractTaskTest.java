package com.dwarfeng.dutil.develop.backgr;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AbstractTaskTest {

    private TestExceptionTask task;
    private TestTaskObserver obv;

    @Before
    public void setUp() {
        obv = new TestTaskObserver();
        task = new TestExceptionTask();
        task.addObserver(obv);
    }

    @After
    public void tearDown() {
        task.clearObserver();
        task = null;
    }

    @Test
    public void testGetLock() {
        task.getLock();
    }

    @Test
    public void testGetObservers() {
        assertEquals(1, task.getObservers().size());
        assertTrue(task.getObservers().contains(obv));
    }

    @Test
    public void testRemoveObserver() {
        assertTrue(task.removeObserver(obv));
        assertEquals(0, task.getObservers().size());
        assertFalse(task.getObservers().contains(obv));
    }

    // 该方法可以测试任务的 isFinished(), isStarted, todo()方法。
    @Test
    public void testState() throws InterruptedException {
        Task task = new TestTask(100);
        TestTaskObserver obv = new TestTaskObserver();
        task.addObserver(obv);

        assertFalse(task.isStarted());
        assertFalse(task.isFinished());

        Thread thread = new Thread(task::run);
        thread.start();

        Thread.sleep(50);
        assertTrue(task.isStarted());
        assertFalse(task.isFinished());
        assertEquals(1, obv.startCount);
        task.awaitFinish();
        assertTrue(task.isStarted());
        assertTrue(task.isFinished());
        assertEquals(1, obv.finishCount);
    }

    @Test
    public void testGetException() throws InterruptedException {
        RuntimeException e = new RuntimeException();
        task.setNextException(e);

        Thread thread = new Thread(() -> task.run());
        thread.start();
        task.awaitFinish();
        assertTrue(task.isStarted());
        assertTrue(task.isFinished());
        assertEquals(1, obv.finishCount);
        assertEquals(e, task.getThrowable());
    }

    @Test
    public void testAwaitFinish() throws InterruptedException {
        TestTask task = new TestTask(100);
        TimeMeasurer tm = new TimeMeasurer();

        tm.start();
        Thread thread = new Thread(task);
        thread.start();
        task.awaitFinish();
        tm.stop();
        assertTrue(tm.getTimeMs() >= 100);
    }

    @Test
    public void testAwaitFinishLongTimeUnit() throws InterruptedException {
        TestTask task = new TestTask(100);
        TimeMeasurer tm = new TimeMeasurer();

        tm.start();
        Thread thread = new Thread(task::run);
        thread.start();
        assertFalse(task.awaitFinish(60, TimeUnit.MILLISECONDS));
        assertTrue(task.awaitFinish(100, TimeUnit.MILLISECONDS));
        tm.stop();

        assertTrue(tm.getTimeMs() >= 100);

    }
}
