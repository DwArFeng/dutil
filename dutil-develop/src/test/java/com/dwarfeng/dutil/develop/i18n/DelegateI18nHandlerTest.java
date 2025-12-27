package com.dwarfeng.dutil.develop.i18n;

import com.dwarfeng.dutil.basic.cna.model.DelegateKeySetModel;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class DelegateI18nHandlerTest {

    private static I18nHandler handler = null;
    private static TestI18NObserver observer = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        handler = new DelegateI18nHandler(
                new DelegateKeySetModel<>(new LinkedHashSet<>(), Collections.newSetFromMap(new WeakHashMap<>())));
        handler.add(TestI18nInfo.DEFAULT);
        handler.add(TestI18nInfo.CHINESE);
        handler.add(TestI18nInfo.ENGLISH);
        handler.add(TestI18nInfo.JAPANESE);
        observer = new TestI18NObserver();
        handler.addObserver(observer);
    }

    @After
    public void tearDown() {
        handler = null;
        observer = null;
    }

    @Test
    public void testSetCurrentLocale() {
        assertFalse(handler.setCurrentLocale(Locale.CANADA));
        assertTrue(handler.setCurrentLocale(Locale.CHINA));
        assertNull(observer.changedLocaleOldValue.get(0));
        assertEquals(Locale.CHINA, observer.changedLocaleNewValue.get(0));
    }

    @Test
    public void testGetCurrentLocale() {
        assertNull(handler.getCurrentLocale());
        handler.setCurrentLocale(Locale.CANADA);
        assertNull(handler.getCurrentLocale());
        handler.setCurrentLocale(Locale.JAPANESE);
        assertEquals(Locale.JAPANESE, handler.getCurrentLocale());
    }

    @Test
    public void testGetCurrentI18n() {
        assertNull(handler.getCurrentI18n());
        handler.setCurrentLocale(Locale.CANADA);
        assertNull(handler.getCurrentI18n());
        assertTrue(handler.setCurrentLocale(Locale.CHINA));
        assertTrue(Objects.nonNull(handler.getCurrentI18n()));
        assertEquals("你好", handler.getCurrentI18n().getString("hello"));
        assertTrue(handler.setCurrentLocale(Locale.ENGLISH));
        assertEquals("hello", handler.getCurrentI18n().getString("hello"));
        assertTrue(handler.setCurrentLocale(Locale.JAPANESE));
        assertEquals("今日は", handler.getCurrentI18n().getString("hello"));
    }

    @Test
    public void testRemoveKey() {
        assertTrue(handler.setCurrentLocale(Locale.ENGLISH));
        assertTrue(handler.removeKey(Locale.ENGLISH));
        assertEquals(Locale.ENGLISH, observer.changedLocaleOldValue.get(1));
        assertNull(handler.getCurrentLocale());
        assertNull(observer.changedLocaleNewValue.get(1));
        assertEquals("你好", handler.getCurrentI18n().getString("hello"));
    }

    @Test
    public void testRemoveKeyNull() {
        assertTrue(handler.removeKey(null));
        assertNull(observer.changedLocaleOldValue.get(0));
        assertNull(observer.changedLocaleNewValue.get(0));
        assertNull(handler.getCurrentI18n());
    }

    @Test
    public void testRemoveKeyDefault() {
        assertTrue(handler.removeKey(null));
        assertNull(observer.changedLocaleOldValue.get(0));
        assertNull(observer.changedLocaleNewValue.get(0));
        assertNull(handler.getCurrentI18n());
    }

    @Test
    public void testRemoveAllKey() {
        assertTrue(handler.removeAllKey(Arrays.asList(Locale.ENGLISH, Locale.JAPAN, null)));
        assertNull(observer.changedLocaleOldValue.get(0));
        assertNull(observer.changedLocaleNewValue.get(0));
        assertNull(handler.getCurrentI18n());
    }

    @Test
    public void testRetainAllKey() {
        assertTrue(handler.retainAllKey(Arrays.asList(null, Locale.CHINA, Locale.ENGLISH)));
        assertEquals(0, observer.changedLocaleNewValue.size());
        assertTrue(handler.retainAllKey(Collections.singletonList(Locale.CHINA)));
        assertNull(observer.changedLocaleOldValue.get(0));
        assertNull(observer.changedLocaleNewValue.get(0));
        assertNull(handler.getCurrentI18n());
    }

    @Test
    public void testIterator() {
        assertTrue(handler.setCurrentLocale(Locale.CHINA));
        Iterator<I18nInfo> i = handler.iterator();
        assertTrue(i.hasNext());
        i.next();
        i.next();
        i.remove();
        assertEquals(Locale.CHINA, observer.changedLocaleOldValue.get(1));
        assertNull(observer.changedLocaleNewValue.get(1));
    }

    @Test
    public void testRemove() {
        assertTrue(handler.setCurrentLocale(Locale.ENGLISH));
        assertTrue(handler.remove(TestI18nInfo.ENGLISH));
        assertEquals(Locale.ENGLISH, observer.changedLocaleOldValue.get(1));
        assertNull(observer.changedLocaleNewValue.get(1));
    }

    @Test
    public void testRemoveDefault() {
        assertTrue(handler.remove(TestI18nInfo.DEFAULT));
        assertNull(observer.changedLocaleOldValue.get(0));
        assertNull(observer.changedLocaleNewValue.get(0));
        assertNull(handler.getCurrentI18n());
    }

    @Test
    public void testRetainAll() {
        assertTrue(handler.retainAll(Arrays.asList(TestI18nInfo.DEFAULT, TestI18nInfo.CHINESE, TestI18nInfo.ENGLISH)));
        assertEquals(0, observer.changedLocaleNewValue.size());
        assertTrue(handler.retainAll(Collections.singletonList(TestI18nInfo.CHINESE)));
        assertNull(observer.changedLocaleOldValue.get(0));
        assertNull(observer.changedLocaleNewValue.get(0));
        assertNull(handler.getCurrentI18n());
    }

    @Test
    public void testRemoveAll() {
        assertTrue(handler.removeAll(Arrays.asList(TestI18nInfo.ENGLISH, TestI18nInfo.JAPANESE, TestI18nInfo.DEFAULT)));
        assertNull(observer.changedLocaleOldValue.get(0));
        assertNull(observer.changedLocaleNewValue.get(0));
        assertNull(handler.getCurrentI18n());
    }

    @Test
    public void testClear() {
        assertTrue(handler.setCurrentLocale(Locale.CHINA));
        handler.clear();
        assertEquals(Locale.CHINA, observer.changedLocaleOldValue.get(1));
        assertNull(observer.changedLocaleNewValue.get(1));
    }
}
