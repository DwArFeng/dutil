package com.dwarfeng.dutil.develop.logger;

import org.junit.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test_SysOutLoggerInfo {

    private static SysOutLoggerInfo info1;
    private static SysOutLoggerInfo info2;

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
        info1 = new SysOutLoggerInfo("info1", true);
        info2 = new SysOutLoggerInfo("info2", false);
    }

    @After
    public void tearDown() {
        info1 = null;
        info2 = null;
    }

    @Test
    public final void testNewLogger() throws Exception {
        SysOutLogger logger1 = (SysOutLogger) info1.newLogger();
        SysOutLogger logger2 = (SysOutLogger) info2.newLogger();
        assertTrue(logger1.isAutoUpdate());
        assertFalse(logger2.isAutoUpdate());
    }

    @Test
    public final void testIsAutoUpdate() {
        assertTrue(info1.isAutoUpdate());
        assertFalse(info2.isAutoUpdate());
    }
}
