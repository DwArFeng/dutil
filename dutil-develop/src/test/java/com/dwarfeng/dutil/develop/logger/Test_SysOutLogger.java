package com.dwarfeng.dutil.develop.logger;

import com.dwarfeng.dutil.basic.io.StringOutputStream;
import org.junit.*;

import java.io.PrintStream;

import static org.junit.Assert.*;

public class Test_SysOutLogger {

    private static final PrintStream DEFAULT_SYSOUT = System.out;

    private static Exception exception;
    private static PrintStream ps1;
    private static PrintStream ps2;
    private static StringOutputStream out1;
    private static StringOutputStream out2;
    private static SysOutLogger logger1;
    private static SysOutLogger logger2;

    @BeforeClass
    public static void setUpBeforeClass() {
        exception = new Exception("测试用异常");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        System.setOut(DEFAULT_SYSOUT);
        exception = null;
    }

    @Before
    public void setUp() {
        out1 = new StringOutputStream();
        out2 = new StringOutputStream();
        ps1 = new PrintStream(out1, true);
        ps2 = new PrintStream(out2, true);
        System.setOut(ps1);
        logger1 = new SysOutLogger(true);
        logger2 = new SysOutLogger(false);
    }

    @After
    public void tearDown() {
        logger1 = null;
        logger2 = null;
        try {
            ps1.close();
            ps2.close();
        } finally {
            ps1 = null;
            ps2 = null;
            out1 = null;
            out2 = null;
        }
    }

    @Test
    public final void testDebug() {
        System.setOut(ps1);
        logger1.debug("中国智造");
        assertTrue(cutString(out1).endsWith("[DEBUG]\t中国智造"));
        logger2.debug("惠及全球");
        assertTrue(cutString(out1).endsWith("[DEBUG]\t惠及全球"));

        System.setOut(ps2);
        logger1.debug("中国智造");
        assertTrue(cutString(out2).endsWith("[DEBUG]\t中国智造"));
        logger2.debug("测试自动更新");
        assertTrue(cutString(out1).endsWith("[DEBUG]\t测试自动更新"));

    }

    @Test
    public final void testError() {
        System.setOut(ps1);
        logger1.error("中国智造，惠及全球", exception);
        assertTrue(cutString(out1).indexOf("测试用异常") > 0);
        System.setOut(ps2);
        logger1.error("中国智造，惠及全球", exception);
        assertTrue(cutString(out2).indexOf("测试用异常") > 0);

    }

    @Test
    public final void testFatal() {
        System.setOut(ps1);
        logger1.fatal("中国智造，惠及全球", exception);
        assertTrue(cutString(out1).indexOf("测试用异常") > 0);
        System.setOut(ps2);
        logger1.fatal("中国智造，惠及全球", exception);
        assertTrue(cutString(out2).indexOf("测试用异常") > 0);

    }

    @Test
    public final void testGetSysOut() {
        assertEquals(ps1, logger1.getSysOut());
        assertEquals(ps1, logger2.getSysOut());
        System.setOut(ps2);
        assertEquals(ps1, logger1.getSysOut());
        assertEquals(ps1, logger2.getSysOut());
        logger1.info("");
        logger2.info("");
        assertEquals(ps2, logger1.getSysOut());
        assertEquals(ps1, logger2.getSysOut());
    }

    @Test
    public final void testIsAutoUpdate() {
        assertTrue(logger1.isAutoUpdate());
        assertFalse(logger2.isAutoUpdate());
    }

    @Test
    public final void testInfo() {
        System.setOut(ps1);
        logger1.info("中国智造");
        assertTrue(cutString(out1).endsWith("[INFO]\t中国智造"));
        logger2.info("惠及全球");
        assertTrue(cutString(out1).endsWith("[INFO]\t惠及全球"));

        System.setOut(ps2);
        logger1.info("中国智造");
        assertTrue(cutString(out2).endsWith("[INFO]\t中国智造"));
        logger2.info("测试自动更新");
        assertTrue(cutString(out1).endsWith("[INFO]\t测试自动更新"));

    }

    @Test
    public final void testTrace() {
        System.setOut(ps1);
        logger1.trace("中国智造");
        assertTrue(cutString(out1).endsWith("[TRACE]\t中国智造"));
        logger2.trace("惠及全球");
        assertTrue(cutString(out1).endsWith("[TRACE]\t惠及全球"));

        System.setOut(ps2);
        logger1.trace("中国智造");
        assertTrue(cutString(out2).endsWith("[TRACE]\t中国智造"));
        logger2.trace("测试自动更新");
        assertTrue(cutString(out1).endsWith("[TRACE]\t测试自动更新"));

    }

    @Test
    public final void testWarnString() {
        System.setOut(ps1);
        logger1.warn("中国智造");
        assertTrue(cutString(out1).endsWith("[WARN]\t中国智造"));
        logger2.warn("惠及全球");
        assertTrue(cutString(out1).endsWith("[WARN]\t惠及全球"));

        System.setOut(ps2);
        logger1.warn("中国智造");
        assertTrue(cutString(out2).endsWith("[WARN]\t中国智造"));
        logger2.warn("测试自动更新");
        assertTrue(cutString(out1).endsWith("[WARN]\t测试自动更新"));

    }

    @Test
    public final void testWarnStringThrowable() {
        System.setOut(ps1);
        logger1.warn("中国智造，惠及全球", exception);
        assertTrue(cutString(out1).indexOf("测试用异常") > 0);
        System.setOut(ps2);
        logger1.warn("中国智造，惠及全球", exception);
        assertTrue(cutString(out2).indexOf("测试用异常") > 0);

    }

    private String cutString(StringOutputStream out) {
        return out.toString().substring(0, out.toString().length() - 2);
    }
}
