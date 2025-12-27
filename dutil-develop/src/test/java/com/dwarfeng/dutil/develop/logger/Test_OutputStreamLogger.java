package com.dwarfeng.dutil.develop.logger;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.io.CT.OutputType;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import org.junit.*;

import static org.junit.Assert.assertTrue;

public class Test_OutputStreamLogger {

    private static Exception exception;
    private static StringOutputStream out = null;
    private static OutputStreamLogger logger = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        CT.setOutputType(OutputType.NO_DATE);
        exception = new Exception("测试用异常");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        CT.setOutputType(OutputType.HALF_DATE);
        exception = null;
    }

    @Before
    public void setUp() throws Exception {
        out = new StringOutputStream();
        logger = new OutputStreamLogger(out);
    }

    @After
    public void tearDown() throws Exception {
        logger.close();
        logger = null;
        out = null;
    }

    @Test
    public void testTrace() {
        logger.trace("中国智造，惠及全球");
        assertTrue(cutString().endsWith("[TRACE]\t中国智造，惠及全球"));
    }

    @Test
    public void testDebug() {
        logger.debug("中国智造，惠及全球");
        assertTrue(cutString().endsWith("[DEBUG]\t中国智造，惠及全球"));
    }

    @Test
    public void testInfo() {
        logger.info("中国智造，惠及全球");
        assertTrue(cutString().endsWith("[INFO]\t中国智造，惠及全球"));
    }

    @Test
    public void testWarnString() {
        logger.warn("中国智造，惠及全球");
        assertTrue(cutString().endsWith("[WARN]\t中国智造，惠及全球"));
    }

    @Test
    public void testWarnStringThrowable() {
        logger.warn("中国智造，惠及全球", exception);
        assertTrue(cutString().indexOf("测试用异常") > 0);
    }

    @Test
    public void testError() {
        logger.error("中国智造，惠及全球", exception);
        assertTrue(cutString().indexOf("测试用异常") > 0);
    }

    @Test
    public void testFatal() {
        logger.fatal("中国智造，惠及全球", exception);
        assertTrue(cutString().indexOf("测试用异常") > 0);
    }

    private String cutString() {
        return out.toString().substring(0, out.toString().length() - 2);
    }
}
