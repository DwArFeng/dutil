package com.dwarfeng.dutil.develop.backgr;

import org.junit.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BackgroundUtilTest {

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBlockedTask() throws InterruptedException {
        Task task_1 = new TestTask(100);
        Task task_2 = BackgroundUtil.blockedTask(new TestTask(1), new Task[]{task_1});

        Thread thread_1 = new Thread(task_1::run);
        Thread thread_2 = new Thread(task_2::run);

        thread_2.start();
        thread_1.start();

        assertFalse(task_2.awaitFinish(50, TimeUnit.MILLISECONDS));
        assertTrue(task_2.isStarted());
        assertFalse(task_2.awaitFinish(40, TimeUnit.MILLISECONDS));
        assertTrue(task_2.isStarted());
        assertTrue(task_2.awaitFinish(40, TimeUnit.MILLISECONDS));
    }
}
