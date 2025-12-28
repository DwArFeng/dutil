package com.dwarfeng.dutil.develop.logger;

import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.io.CT.OutputType;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import org.junit.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

import static org.junit.Assert.*;

public class SyncLoggerHandlerImplTest {

    private static Exception exception;

    private static SyncLoggerHandler handler;
    private static StringOutputStream out1;
    private static StringOutputStream out2;
    private static LoggerInfo loggerInfo1;
    private static LoggerInfo loggerInfo2;
    private static TestLoggerObserver obv;

    @BeforeClass
    public static void setUpBeforeClass() {
        CT.setOutputType(OutputType.NO_DATE);
        exception = new Exception("测试用异常");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        CT.setOutputType(OutputType.HALF_DATE);
        exception = null;
    }

    @Before
    public void setUp() {
        handler = LoggerUtil.syncLoggerHandler(new DelegateLoggerHandler());
        obv = new TestLoggerObserver();
        handler.addObserver(obv);
        out1 = new StringOutputStream();
        out2 = new StringOutputStream();
        loggerInfo1 = new TestLoggerInfo("out1", out1);
        loggerInfo2 = new TestLoggerInfo("out2", out2);
    }

    @After
    public void tearDown() throws Exception {
        handler.clearObserver();
        obv = null;
        handler.clear();
        handler = null;
        loggerInfo1 = null;
        loggerInfo2 = null;
        out1.close();
        out1 = null;
        out2.close();
        out2 = null;
    }

    @Test
    public void testHashCode() {
        DelegateLoggerHandler target = new DelegateLoggerHandler();
        assertEquals(target.hashCode(), handler.hashCode());
        target.add(loggerInfo1);
        handler.add(loggerInfo1);
        assertEquals(target.hashCode(), handler.hashCode());
        target.add(loggerInfo2);
        handler.add(loggerInfo2);
        assertEquals(target.hashCode(), handler.hashCode());
    }

    @Test
    public void testAdd() {
        assertTrue(handler.add(loggerInfo1));
        assertTrue(handler.add(loggerInfo2));
        assertFalse(handler.add(loggerInfo1));
        // 根据 DelegateLoggerHandler 默认的代理键值集合的性质，是允许
        // null 元素被添加的，但是注意，use(null)是不被允许的。
        assertTrue(handler.add(null));
        assertArrayEquals(new Object[]{loggerInfo1, loggerInfo2, null}, obv.addedList.toArray());
    }

    @Test
    public void testAddAll() {
        assertTrue(handler.addAll(Arrays.asList(loggerInfo1, loggerInfo1, loggerInfo1)));
        assertTrue(handler.add(null));
        assertTrue(handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null)));
        assertFalse(handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null)));
        assertArrayEquals(new Object[]{loggerInfo1, null, loggerInfo2}, obv.addedList.toArray());
    }

    @Test
    public void testClear() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.clear();
        assertEquals(0, handler.size());
        assertTrue(handler.isEmpty());
        assertTrue(obv.clearedFlag.get());
    }

    @Test
    public void testContains() {
        handler.addAll(Arrays.asList(loggerInfo1, null));
        assertTrue(handler.contains(loggerInfo1));
        assertFalse(handler.contains(loggerInfo2));
        assertTrue(handler.contains(null));
    }

    @Test
    public void testContainsAll() {
        handler.addAll(Arrays.asList(loggerInfo1, null));
        assertTrue(handler.contains(loggerInfo1));
        assertTrue(handler.contains(null));
        assertTrue(handler.containsAll(Arrays.asList(loggerInfo1, null)));
        assertFalse(handler.containsAll(Arrays.asList(loggerInfo1, loggerInfo2)));
        assertFalse(handler.containsAll(Arrays.asList(loggerInfo2, null)));
        assertFalse(handler.containsAll(Arrays.asList(loggerInfo1, loggerInfo2, null)));
    }

    @Test
    public void testContainsAllKey() {
        handler.addAll(Arrays.asList(loggerInfo1, null));
        assertTrue(handler.containsAllKey(Collections.singletonList("out1")));
        assertTrue(handler.containsAllKey(Collections.singletonList((LoggerInfo) null)));
        assertTrue(handler.containsAllKey(Arrays.asList("out1", null)));
        assertFalse(handler.containsAllKey(Arrays.asList("out1", "out2")));
        assertFalse(handler.containsAllKey(Arrays.asList("out2", null)));
        assertFalse(handler.containsAllKey(Arrays.asList("out1", "out2", null)));
    }

    @Test
    public void testContainsKey() {
        handler.addAll(Arrays.asList(loggerInfo1, null));
        assertTrue(handler.containsKey("out1"));
        assertFalse(handler.containsKey("out2"));
        assertTrue(handler.containsKey(null));
    }

    @Test
    public void testDebug() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        handler.debug("中国智造，惠及全球");
        assertTrue(cutString(out1).endsWith("[DEBUG]\t中国智造，惠及全球"));
        assertTrue(cutString(out2).endsWith("[DEBUG]\t中国智造，惠及全球"));
    }

    @Test
    public void testEqualsObject() {
        DelegateLoggerHandler target = new DelegateLoggerHandler();
        assertEquals(target, handler);
        target.add(loggerInfo1);
        handler.add(loggerInfo1);
        assertEquals(target, handler);
        target.add(loggerInfo2);
        handler.add(loggerInfo2);
        assertEquals(target, handler);
    }

    @Test
    public void testError() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        handler.error("中国智造，惠及全球", exception);
        assertTrue(cutString(out1).indexOf("测试用异常") > 0);
        assertTrue(cutString(out2).indexOf("测试用异常") > 0);
    }

    @Test
    public void testFatal() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        handler.fatal("中国智造，惠及全球", exception);
        assertTrue(cutString(out1).indexOf("测试用异常") > 0);
        assertTrue(cutString(out2).indexOf("测试用异常") > 0);
    }

    @Test
    public void testGet() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        assertNull(handler.get(null));
        assertEquals(handler.get("out1"), loggerInfo1);
        assertEquals(handler.get("out2"), loggerInfo2);
    }

    @Test
    public void testInfo() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        handler.info("中国智造，惠及全球");
        assertTrue(cutString(out1).endsWith("[INFO]\t中国智造，惠及全球"));
        assertTrue(cutString(out2).endsWith("[INFO]\t中国智造，惠及全球"));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(handler.isEmpty());
        handler.add(loggerInfo1);
        assertFalse(handler.isEmpty());
    }

    @Test
    public void testIterator() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2));
        Iterator<LoggerInfo> iterator = handler.iterator();
        Object obj1 = iterator.next();
        Object obj2 = iterator.next();
        assertFalse(iterator.hasNext());
        if (Objects.equals(obj1, loggerInfo1)) {
            assertEquals(obj2, loggerInfo2);
        } else if (Objects.equals(obj1, loggerInfo2)) {
            assertEquals(obj2, loggerInfo1);
        } else {
            fail("testIterator 方法异常");
        }
    }

    @Test
    public void testRemove() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.use(loggerInfo1);
        assertTrue(handler.remove(loggerInfo1));
        assertFalse(handler.remove(loggerInfo1));
        assertTrue(handler.remove(null));
        assertTrue(handler.remove(loggerInfo2));
        assertTrue(handler.isEmpty());
        assertArrayEquals(new Object[]{loggerInfo1, null, loggerInfo2}, obv.removedList.toArray());
        assertArrayEquals(new Object[]{"out1"}, obv.unusedKeyList.toArray());
    }

    @Test
    public void testRemoveAll() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        assertTrue(handler.removeAll(Arrays.asList(loggerInfo1, null)));
        assertTrue(handler.removeAll(Arrays.asList(loggerInfo1, loggerInfo2)));
        assertFalse(handler.removeAll(Arrays.asList(loggerInfo1, loggerInfo2, null)));
        assertTrue(handler.isEmpty());
        assertTrue(obv.removedList.containsAll(Arrays.asList(loggerInfo1, loggerInfo2, null)));
        assertTrue(obv.unusedKeyList.contains("out1"));
        assertTrue(obv.unusedKeyList.contains("out2"));
    }

    @Test
    public void testRemoveAllKey() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        assertTrue(handler.removeAllKey(Collections.singletonList("out2")));
        assertFalse(handler.removeAllKey(Collections.singletonList("out2")));
        assertEquals(1, handler.usedLoggers().size());
        assertTrue(handler.removeAllKey(Collections.singletonList("out1")));
        assertFalse(handler.removeAllKey(Collections.singletonList("out1")));
        assertEquals(0, handler.usedLoggers().size());
        assertEquals(1, handler.size());
        assertTrue(obv.removedList.contains(loggerInfo1));
        assertTrue(obv.removedList.contains(loggerInfo2));
        assertFalse(obv.removedList.contains(null));
    }

    @Test
    public void testRemoveKey() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        assertTrue(handler.removeKey("out2"));
        assertFalse(handler.removeKey("out2"));
        assertEquals(1, handler.usedLoggers().size());
        assertTrue(handler.removeKey("out1"));
        assertFalse(handler.removeKey("out1"));
        assertEquals(0, handler.usedLoggers().size());
        assertEquals(1, handler.size());
        assertTrue(obv.removedList.contains(loggerInfo1));
        assertTrue(obv.removedList.contains(loggerInfo2));
        assertFalse(obv.removedList.contains(null));
    }

    @Test
    public void testRetainAll() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        assertTrue(handler.retainAll(Arrays.asList(loggerInfo1, loggerInfo2)));
        assertFalse(handler.contains(null));
        assertTrue(handler.contains(loggerInfo1));
        assertTrue(handler.contains(loggerInfo2));
        assertTrue(handler.retainAll(Collections.singletonList(loggerInfo1)));
        assertFalse(handler.retainAll(Collections.singletonList(loggerInfo1)));
        assertTrue(handler.contains(loggerInfo1));
        assertEquals(1, handler.size());
        assertArrayEquals(new Object[]{null, loggerInfo2}, obv.removedList.toArray());
    }

    @Test
    public void testRetainAllKey() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        assertTrue(handler.retainAllKey(Arrays.asList("out1", "out2")));
        assertFalse(handler.contains(null));
        assertTrue(handler.contains(loggerInfo1));
        assertTrue(handler.contains(loggerInfo2));
        assertTrue(handler.retainAllKey(Collections.singletonList("out1")));
        assertFalse(handler.retainAllKey(Collections.singletonList("out1")));
        assertTrue(handler.contains(loggerInfo1));
        assertEquals(1, handler.size());
        assertArrayEquals(new Object[]{null, loggerInfo2}, obv.removedList.toArray());
    }

    @Test
    public void testToArray() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        assertTrue(ArrayUtil.containsAll(handler.toArray(), new Object[]{loggerInfo1, loggerInfo2, null}));
        assertEquals(3, handler.toArray().length);
    }

    @Test
    public void testToArrayTArray() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        assertTrue(ArrayUtil.containsAll(handler.toArray(new LoggerInfo[0]),
                new Object[]{loggerInfo1, loggerInfo2, null}));
        assertEquals(3, handler.toArray(new LoggerInfo[0]).length);
        assertEquals(5, handler.toArray(new LoggerInfo[5]).length);
    }

    @Test(expected = ArrayStoreException.class)
    public void testToArrayTArray1() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.toArray(new Logger[5]);
    }

    @Test
    public void testTrace() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        handler.trace("中国智造，惠及全球");
        assertTrue(cutString(out1).endsWith("[TRACE]\t中国智造，惠及全球"));
        assertTrue(cutString(out2).endsWith("[TRACE]\t中国智造，惠及全球"));
    }

    @Test
    public void testUsedLoggers() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        assertTrue(handler.usedLoggers().isEmpty());
        handler.useAll();
        assertEquals(2, handler.usedLoggers().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUsedLoggers1() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        handler.usedLoggers().clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUsedLoggers2() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        Iterator<Logger> i = handler.usedLoggers().iterator();
        i.next();
        i.remove();
    }

    @Test
    public void testWarnString() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        handler.warn("中国智造，惠及全球");
        assertTrue(cutString(out1).endsWith("[WARN]\t中国智造，惠及全球"));
        assertTrue(cutString(out2).endsWith("[WARN]\t中国智造，惠及全球"));
    }

    @Test
    public void testWarnStringThrowable() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        handler.warn("中国智造，惠及全球", exception);
        assertTrue(cutString(out1).indexOf("测试用异常") > 0);
        assertTrue(cutString(out2).indexOf("测试用异常") > 0);
    }

    @Test
    public void testUse() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        assertFalse(handler.use(null));
        assertTrue(handler.use(loggerInfo1));
        assertTrue(handler.use(loggerInfo2));
        handler.info("中国智造，惠及全球");
        assertTrue(cutString(out1).endsWith("[INFO]\t中国智造，惠及全球"));
        assertTrue(cutString(out2).endsWith("[INFO]\t中国智造，惠及全球"));
        assertArrayEquals(new Object[]{"out1", "out2"}, obv.usedKeyList.toArray());
    }

    @Test
    public void testUnuse() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        assertEquals(2, handler.usedLoggers().size());
        handler.info("中国智造，惠及全球");
        assertTrue(cutString(out1).endsWith("[INFO]\t中国智造，惠及全球"));
        assertTrue(cutString(out2).endsWith("[INFO]\t中国智造，惠及全球"));
        assertTrue(handler.unuse(loggerInfo1));
        assertEquals(1, handler.usedLoggers().size());
        assertFalse(handler.unuse(loggerInfo1));
        assertFalse(handler.unuse(null));
        handler.trace("中国智造，惠及全球");
        assertTrue(cutString(out1).endsWith("[INFO]\t中国智造，惠及全球"));
        assertTrue(cutString(out2).endsWith("[TRACE]\t中国智造，惠及全球"));
        assertTrue(handler.unuse(loggerInfo2));
        assertEquals(0, handler.usedLoggers().size());
        assertArrayEquals(new Object[]{"out1", "out2"}, obv.unusedKeyList.toArray());
        handler.unuse(loggerInfo1);
    }

    @Test
    public void testUseAll() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        assertEquals(2, handler.usedLoggers().size());
        assertTrue(obv.usedKeyList.contains("out1"));
        assertTrue(obv.usedKeyList.contains("out2"));
    }

    @Test
    public void testUnuseAll() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        handler.unuseAll();
        assertEquals(0, handler.usedLoggers().size());
        assertTrue(obv.unusedKeyList.contains("out1"));
        assertTrue(obv.unusedKeyList.contains("out2"));
    }

    @Test
    public void testUseKey() {
        handler.add(loggerInfo1);
        assertTrue(handler.useKey("out1"));
        assertFalse(handler.useKey("out1"));
        assertFalse(handler.useKey(null));
        handler.add(null);
        assertFalse(handler.useKey(null));
        assertFalse(handler.useKey("out2"));
        handler.add(loggerInfo2);
        assertTrue(handler.useKey("out2"));
        assertFalse(handler.useKey("out2"));
    }

    @Test
    public void testUseAllCollectionOfLoggerInfo() {
        assertFalse(handler.useAll(Arrays.asList(loggerInfo1, loggerInfo2)));
        handler.add(loggerInfo2);
        assertTrue(handler.useAll(Arrays.asList(loggerInfo1, loggerInfo2)));
        handler.add(loggerInfo1);
        assertTrue(handler.useAll(Arrays.asList(loggerInfo1, loggerInfo2)));
        assertFalse(handler.useAll(Arrays.asList(loggerInfo1, loggerInfo2)));
        assertArrayEquals(new Object[]{"out2", "out1"}, obv.usedKeyList.toArray());
    }

    @Test
    public void testUnuseAllCollectionOfLoggerInfo() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        assertTrue(handler.unuseAll(Collections.singletonList(loggerInfo2)));
        assertFalse(handler.unuseAll(Collections.singletonList(loggerInfo2)));
        assertTrue(handler.unuseAll(Collections.singletonList(loggerInfo1)));
        assertFalse(handler.unuseAll(Collections.singletonList(loggerInfo1)));
        assertArrayEquals(new Object[]{"out2", "out1"}, obv.unusedKeyList.toArray());
    }

    @Test
    public void testuseAllKeyKey() {
        assertFalse(handler.useAllKey(Arrays.asList("out1", "out2")));
        handler.add(loggerInfo2);
        assertTrue(handler.useAllKey(Arrays.asList("out1", "out2")));
        handler.add(loggerInfo1);
        assertTrue(handler.useAllKey(Arrays.asList("out1", "out2")));
        assertFalse(handler.useAllKey(Arrays.asList("out1", "out2")));
        assertArrayEquals(new Object[]{"out2", "out1"}, obv.usedKeyList.toArray());
    }

    @Test
    public void testUnuseAllKey() {
        handler.addAll(Arrays.asList(loggerInfo1, loggerInfo2, null));
        handler.useAll();
        assertTrue(handler.unuseAllKey(Collections.singletonList("out2")));
        assertFalse(handler.unuseAllKey(Collections.singletonList("out2")));
        assertTrue(handler.unuseAllKey(Collections.singletonList("out1")));
        assertFalse(handler.unuseAllKey(Collections.singletonList("out1")));
        assertArrayEquals(new Object[]{"out2", "out1"}, obv.unusedKeyList.toArray());
    }

    private String cutString(StringOutputStream out) {
        return out.toString().substring(0, out.toString().length() - 2);
    }
}
