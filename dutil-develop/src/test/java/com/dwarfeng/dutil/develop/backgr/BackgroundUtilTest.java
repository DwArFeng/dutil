package com.dwarfeng.dutil.develop.backgr;

import org.junit.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BackgroundUtilTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testBlockedTask() throws InterruptedException {
        Task task_1 = new TestTask(100);
        Task task_2 = BackgroundUtil.blockedTask(new TestTask(1), new Task[]{task_1});

        Thread thread_1 = new Thread(() -> {
            task_1.run();
        });
        Thread thread_2 = new Thread(() -> {
            task_2.run();
        });

        thread_2.start();
        thread_1.start();

        assertFalse(task_2.awaitFinish(50, TimeUnit.MILLISECONDS));
        assertTrue(task_2.isStarted());
        assertFalse(task_2.awaitFinish(40, TimeUnit.MILLISECONDS));
        assertTrue(task_2.isStarted());
        assertTrue(task_2.awaitFinish(40, TimeUnit.MILLISECONDS));
    }
}
