package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.basic.str.DefaultName;
import com.dwarfeng.dutil.develop.setting.info.BooleanSettingInfo;
import com.dwarfeng.dutil.develop.setting.obs.SettingObserver;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class DefaultSettingHandlerTest {

    private static DefaultSettingHandler handler;
    private static TestSettingObserver observer;

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
        handler = new DefaultSettingHandler(new LinkedHashMap<>(), new LinkedHashMap<>(),
                Collections.newSetFromMap(new WeakHashMap<>()));
        SettingUtil.putEnumItems(new SettingEnumItem[]{Test_SettingEnumItem.ENTRY_1, Test_SettingEnumItem.ENTRY_2,
                Test_SettingEnumItem.ENTRY_3, Test_SettingEnumItem.ENTRY_4}, handler);
        observer = new TestSettingObserver();
        handler.addObserver(observer);
    }

    @After
    public void tearDown() {
        handler.clearObserver();
        handler = null;
        observer = null;
    }

    @Test
    public void testPutStringSettingInfoString() {
        assertTrue(handler.put("entry.5", new BooleanSettingInfo("TRUE"), "TRUE"));
        assertFalse(handler.put("entry.5", new BooleanSettingInfo("TRUE"), "TRUE"));
        assertEquals(5, handler.size());
        assertEquals(1, observer.putList.size());
        assertEquals("entry.5", observer.putList.get(0));
        assertTrue(handler.put("entry.1", new BooleanSettingInfo("TRUE"), "FALSE"));
        assertEquals(1, observer.currentValueChangedKey.size());
        assertEquals("entry.1", observer.currentValueChangedKey.get(0));
        assertTrue(handler.put("entry.2", new BooleanSettingInfo("TRUE"), "TRUE"));
        assertEquals(1, observer.settingInfoChangedKey.size());
        assertEquals("entry.2", observer.settingInfoChangedKey.get(0));
        assertEquals(2, observer.currentValueChangedKey.size());
        assertEquals("entry.2", observer.currentValueChangedKey.get(1));
    }

    @Test
    public void testRemoveKey() {
        assertFalse(handler.removeKey("entry.5"));
        assertTrue(handler.removeKey("entry.3"));
        assertEquals(3, handler.size());
        assertEquals("entry.3", observer.removedKey.get(0));
        assertEquals(1, observer.removedKey.size());
    }

    @Test
    public void testRetainAllKey() {
        assertFalse(handler.retainAllKey(Arrays.asList("entry.1", "entry.2", "entry.3", "entry.4")));
        assertTrue(handler.retainAllKey(Arrays.asList("entry.2", "entry.3")));
        assertEquals(2, observer.removedKey.size());
        assertTrue(observer.removedKey.contains("entry.1"));
        assertTrue(observer.removedKey.contains("entry.4"));
    }

    @Test
    public void testSetSettingInfoStringSettingInfo() {
        assertFalse(handler.setSettingInfo("entry.1", new BooleanSettingInfo("TRUE")));
        assertTrue(handler.setSettingInfo("entry.1", new BooleanSettingInfo("FALSE")));
        assertEquals(1, observer.settingInfoChangedKey.size());
        assertEquals("entry.1", observer.settingInfoChangedKey.get(0));
    }

    @Test
    public void testSetCurrentValueStringString() {
        assertFalse(handler.setCurrentValue("entry.5", "TRUE"));
        assertTrue(handler.setCurrentValue("entry.1", "FALSE"));
        assertEquals("FALSE", handler.getCurrentValue("entry.1"));
        assertEquals("entry.1", observer.currentValueChangedKey.get(0));
        assertEquals(1, observer.currentValueChangedKey.size());
        assertTrue(handler.setCurrentValue("entry.1", "ABC"));
        assertEquals("entry.1", observer.currentValueChangedKey.get(1));
        assertEquals("ABC", handler.getCurrentValue("entry.1"));
        assertEquals(2, observer.currentValueChangedKey.size());
    }

    @Test
    public void testResetCurrentValueString() {
        handler.setCurrentValue("entry.1", "ABC");
        assertTrue(handler.resetCurrentValue("entry.1"));
        assertEquals("TRUE", handler.getCurrentValue("entry.1"));
        assertEquals(2, observer.currentValueChangedKey.size());
        assertEquals("entry.1", observer.currentValueChangedKey.get(0));
        assertEquals("entry.1", observer.currentValueChangedKey.get(1));
    }

    @Test
    public void testSetParsedValueStringObject() {
        assertTrue(handler.getParsedValue("entry.1") instanceof Boolean);
        assertEquals(true, handler.getParsedValue("entry.1"));
    }

    @Test
    public void testSize() {
        assertEquals(4, handler.size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(handler.isEmpty());
        handler.clear();
        assertTrue(handler.isEmpty());
    }

    @Test
    public void testClear() {
        handler.clear();
        assertTrue(handler.isEmpty());
        assertEquals(0, handler.size());
        assertEquals(1, observer.clearCount);
    }

    @Test
    public void testContainsKey() {
        assertTrue(handler.containsKey("entry.1"));
        assertTrue(handler.containsKey("entry.2"));
        assertTrue(handler.containsKey("entry.3"));
        assertTrue(handler.containsKey("entry.4"));
        assertFalse(handler.containsKey("entry.5"));
    }

    @Test
    public void testRemoveAllKey() {
        assertTrue(handler.removeAllKey(Arrays.asList("entry.2", "entry.3")));
        assertEquals(2, observer.removedKey.size());
        assertEquals("entry.2", observer.removedKey.get(0));
        assertEquals("entry.3", observer.removedKey.get(1));
        assertEquals(2, handler.size());
        assertFalse(handler.removeAllKey(Arrays.asList("entry.2", "entry.3")));
        assertTrue(handler.removeAllKey(Arrays.asList("entry.2", "entry.3", "entry.4")));
        assertEquals(1, handler.size());
    }

    @Test
    public void testGetSettingInfoString() {
        assertEquals("TRUE", handler.getSettingInfo("entry.1").getDefaultValue());
        assertEquals("FALSE", handler.getSettingInfo("entry.2").getDefaultValue());
    }

    @Test
    public void testGetCurrentValueString() {
        assertEquals("TRUE", handler.getCurrentValue("entry.1"));
        assertEquals("FALSE", handler.getCurrentValue("entry.2"));
    }

    @Test
    public void testHashCode() {
        DefaultSettingHandler anotherHandler = new DefaultSettingHandler(new LinkedHashMap<>(), new LinkedHashMap<>(),
                Collections.newSetFromMap(new WeakHashMap<>()));
        assertEquals(0, anotherHandler.hashCode());

        SettingUtil.putEnumItems(new SettingEnumItem[]{Test_SettingEnumItem.ENTRY_1, Test_SettingEnumItem.ENTRY_2,
                Test_SettingEnumItem.ENTRY_3, Test_SettingEnumItem.ENTRY_4}, anotherHandler);
        assertEquals(handler.hashCode(), anotherHandler.hashCode());
    }

    @Test
    public void testGetObservers() {
        Set<SettingObserver> observers = handler.getObservers();
        assertEquals(1, observers.size());
        assertTrue(observers.contains(observer));
    }

    @Test
    public void testRemoveObserver() {
        assertTrue(handler.removeObserver(observer));
        assertTrue(handler.getObservers().isEmpty());
    }

    @Test
    public void testClearObserver() {
        handler.clearObserver();
        assertTrue(handler.getObservers().isEmpty());
    }

    @Test
    public void testPutNameSettingInfoString() {
        assertTrue(handler.put(new DefaultName("entry.5"), new BooleanSettingInfo("TRUE"), "TRUE"));
        assertFalse(handler.put(new DefaultName("entry.5"), new BooleanSettingInfo("TRUE"), "TRUE"));
        assertEquals(5, handler.size());
        assertEquals(1, observer.putList.size());
        assertEquals("entry.5", observer.putList.get(0));
        assertTrue(handler.put(Test_SettingEnumItem.ENTRY_1, new BooleanSettingInfo("TRUE"), "FALSE"));
        assertEquals(1, observer.currentValueChangedKey.size());
        assertEquals("entry.1", observer.currentValueChangedKey.get(0));
    }

    @Test
    public void testPutAll() {
        SettingHandler anotherHandler = new DefaultSettingHandler();
        SettingUtil.putEnumItems(new SettingEnumItem[]{Test_SettingEnumItem.ENTRY_1, Test_SettingEnumItem.ENTRY_2,
                Test_SettingEnumItem.ENTRY_3, Test_SettingEnumItem.ENTRY_4}, anotherHandler);
        assertFalse(handler.putAll(anotherHandler));
        anotherHandler.clear();
        SettingUtil.putEnumItems(new SettingEnumItem[]{Test_SettingEnumItem.ENTRY_5, Test_SettingEnumItem.ENTRY_6,
                Test_SettingEnumItem.ENTRY_7, Test_SettingEnumItem.ENTRY_8}, anotherHandler);
        assertTrue(handler.putAll(anotherHandler));
        assertEquals(4, observer.putList.size());
        assertTrue(observer.putList.contains("entry.5"));
        assertTrue(observer.putList.contains("entry.6"));
        assertTrue(observer.putList.contains("entry.7"));
        assertTrue(observer.putList.contains("entry.8"));
    }

    @Test
    public void testContainsAllKey() {
        assertTrue(handler.containsAllKey(Arrays.asList("entry.1", "entry.2", "entry.3", "entry.4")));
        assertFalse(handler.containsAllKey(Arrays.asList("entry.1", "entry.2", "entry.3", "entry.4", "entry.5")));
        assertFalse(handler.containsAllKey(Collections.singletonList("entry.5")));
        assertTrue(handler.containsAllKey(Arrays.asList("entry.2", "entry.3")));
    }

    @Test
    public void testGetSettingInfoName() {
        assertEquals("TRUE", handler.getSettingInfo(Test_SettingEnumItem.ENTRY_1).getDefaultValue());
        assertEquals("FALSE", handler.getSettingInfo(Test_SettingEnumItem.ENTRY_2).getDefaultValue());
        assertNull(handler.getSettingInfo(new DefaultName("entry.5")));
    }

    @Test
    public void testSetSettingInfoNameSettingInfo() {
        assertFalse(handler.setSettingInfo(Test_SettingEnumItem.ENTRY_1, new BooleanSettingInfo("TRUE")));
        assertTrue(handler.setSettingInfo(Test_SettingEnumItem.ENTRY_1, new BooleanSettingInfo("FALSE")));
        assertEquals(1, observer.settingInfoChangedKey.size());
        assertEquals("entry.1", observer.settingInfoChangedKey.get(0));
    }

    @Test
    public void testIsValueValidStringString() {
        assertTrue(handler.isValueValid("entry.1", "TRUE"));
        assertTrue(handler.isValueValid("entry.1", "FALSE"));
        assertTrue(handler.isValueValid("entry.1", "true"));
        assertTrue(handler.isValueValid("entry.1", "false"));
        assertFalse(handler.isValueValid("entry.1", "233"));
        assertFalse(handler.isValueValid("entry.1", "ture"));
    }

    @Test
    public void testIsValueValidNameString() {
        assertTrue(handler.isValueValid(Test_SettingEnumItem.ENTRY_1, "TRUE"));
        assertTrue(handler.isValueValid(Test_SettingEnumItem.ENTRY_1, "FALSE"));
        assertTrue(handler.isValueValid(Test_SettingEnumItem.ENTRY_1, "true"));
        assertTrue(handler.isValueValid(Test_SettingEnumItem.ENTRY_1, "false"));
        assertFalse(handler.isValueValid(Test_SettingEnumItem.ENTRY_1, "233"));
        assertFalse(handler.isValueValid(Test_SettingEnumItem.ENTRY_1, "ture"));
    }

    @Test
    public void testGetValidValueString() {
        assertEquals("TRUE", handler.getValidValue("entry.1"));
        handler.setCurrentValue("entry.1", "FALSE");
        assertEquals("FALSE", handler.getValidValue("entry.1"));
        handler.setCurrentValue("entry.1", "ABC");
        assertEquals("TRUE", handler.getValidValue("entry.1"));
    }

    @Test
    public void testGetCurrentValueName() {
        assertEquals("TRUE", handler.getCurrentValue(Test_SettingEnumItem.ENTRY_1));
        assertEquals("FALSE", handler.getCurrentValue(Test_SettingEnumItem.ENTRY_2));
    }

    @Test
    public void testGetValidValueName() {
        assertEquals("TRUE", handler.getValidValue(Test_SettingEnumItem.ENTRY_1));
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_1, "FALSE");
        assertEquals("FALSE", handler.getValidValue(Test_SettingEnumItem.ENTRY_1));
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_1, "ABC");
        assertEquals("TRUE", handler.getValidValue(Test_SettingEnumItem.ENTRY_1));
    }

    @Test
    public void testSetCurrentValueNameString() {
        assertFalse(handler.setCurrentValue(new DefaultName("entry.5"), "TRUE"));
        assertTrue(handler.setCurrentValue(Test_SettingEnumItem.ENTRY_1, "FALSE"));
        assertEquals("FALSE", handler.getCurrentValue(Test_SettingEnumItem.ENTRY_1));
        assertEquals("entry.1", observer.currentValueChangedKey.get(0));
        assertEquals(1, observer.currentValueChangedKey.size());
        assertTrue(handler.setCurrentValue(Test_SettingEnumItem.ENTRY_1, "ABC"));
        assertEquals("entry.1", observer.currentValueChangedKey.get(1));
        assertEquals("ABC", handler.getCurrentValue(Test_SettingEnumItem.ENTRY_1));
        assertEquals(2, observer.currentValueChangedKey.size());
    }

    @Test
    public void testSetAllCurrentValue() {
        Map<String, String> m;
        m = new HashMap<>();
        m.put("entry.1", "FALSE");
        m.put("entry.2", "TRUE");
        m.put("entry.3", "450");
        m.put("entry.4", "12");
        assertTrue(handler.setAllCurrentValue(m));
        assertFalse(handler.setAllCurrentValue(m));
        assertEquals("FALSE", handler.getCurrentValue("entry.1"));
        assertEquals("TRUE", handler.getCurrentValue("entry.2"));
        assertEquals("450", handler.getCurrentValue("entry.3"));
        assertEquals("12", handler.getCurrentValue("entry.4"));
        m = new HashMap<>();
        m.put("entry.1", "TRUE");
        m.put("entry.2", "FALSE");
        assertTrue(handler.setAllCurrentValue(m));
        assertEquals("TRUE", handler.getCurrentValue("entry.1"));
        assertEquals("FALSE", handler.getCurrentValue("entry.2"));
    }

    @Test
    public void testResetCurrentValueName() {
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_1, "ABC");
        assertTrue(handler.resetCurrentValue(Test_SettingEnumItem.ENTRY_1));
        assertEquals("TRUE", handler.getCurrentValue(Test_SettingEnumItem.ENTRY_1));
        assertEquals(2, observer.currentValueChangedKey.size());
        assertEquals("entry.1", observer.currentValueChangedKey.get(0));
        assertEquals("entry.1", observer.currentValueChangedKey.get(1));
    }

    @Test
    public void testResetAllCurrentValueCollectionOfString() {
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_1, "");
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_2, "");
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_3, "");
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_4, "");

    }

    @Test
    public void testResetAllCurrentValue() {
        assertFalse(handler.resetAllCurrentValue());
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_1, "");
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_2, "");
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_3, "");
        handler.setCurrentValue(Test_SettingEnumItem.ENTRY_4, "");
        assertTrue(handler.resetAllCurrentValue());
        assertEquals(8, observer.currentValueChangedKey.size());

        List<String> subList = observer.currentValueChangedKey.subList(3, 7);

        assertTrue(subList.contains("entry.1"));
        assertTrue(subList.contains("entry.2"));
        assertTrue(subList.contains("entry.3"));
        assertTrue(subList.contains("entry.4"));
    }

    @Test
    public void testGetParsedValueString() {
        assertEquals(true, handler.getParsedValue("entry.1"));
        assertEquals(false, handler.getParsedValue("entry.2"));
        assertEquals(12, handler.getParsedValue("entry.3"));
        assertEquals(450, handler.getParsedValue("entry.4"));
    }

    @Test
    public void testGetParsedValueName() {
        assertEquals(true, handler.getParsedValue(Test_SettingEnumItem.ENTRY_1));
        assertEquals(false, handler.getParsedValue(Test_SettingEnumItem.ENTRY_2));
        assertEquals(12, handler.getParsedValue(Test_SettingEnumItem.ENTRY_3));
        assertEquals(450, handler.getParsedValue(Test_SettingEnumItem.ENTRY_4));
    }

    @Test
    public void testGetParsedValueStringClassOfT() {
        assertEquals(true, handler.getParsedValue("entry.1", Boolean.class));
        assertEquals(false, handler.getParsedValue("entry.2", Boolean.class));
        assertEquals(12, (Object) handler.getParsedValue("entry.3", Integer.class));
        assertEquals(450, (Object) handler.getParsedValue("entry.4", Integer.class));
    }

    @Test(expected = ClassCastException.class)
    public void testGetParsedValueStringClassOfTException() {
        handler.getParsedValue("entry.1", Integer.class);
        fail("没有抛出异常。");
    }

    @Test
    public void testGetParsedValueNameClassOfT() {
        assertEquals(true, handler.getParsedValue(Test_SettingEnumItem.ENTRY_1, Boolean.class));
        assertEquals(false, handler.getParsedValue(Test_SettingEnumItem.ENTRY_2, Boolean.class));
        assertEquals(12, (Object) handler.getParsedValue(Test_SettingEnumItem.ENTRY_3, Integer.class));
        assertEquals(450, (Object) handler.getParsedValue(Test_SettingEnumItem.ENTRY_4, Integer.class));
    }

    @Test
    public void testGetParsedValidValueString() {
        assertEquals(true, handler.getParsedValidValue("entry.1"));
        assertEquals(false, handler.getParsedValidValue("entry.2"));
        assertEquals(12, handler.getParsedValidValue("entry.3"));
        assertEquals(450, handler.getParsedValidValue("entry.4"));
        assertNull(handler.getParsedValidValue("entry.5"));
    }

    @Test
    public void testGetParsedValidValueName() {
        assertEquals(true, handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_1));
        assertEquals(false, handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_2));
        assertEquals(12, handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_3));
        assertEquals(450, handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_4));
        assertNull(handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_5));
    }

    @Test
    public void testGetParsedValidValueStringClassOfT() {
        assertEquals(true, handler.getParsedValidValue("entry.1", Boolean.class));
        assertEquals(false, handler.getParsedValidValue("entry.2", Boolean.class));
        assertEquals(12, (int) handler.getParsedValidValue("entry.3", Integer.class));
        assertEquals(450, (int) handler.getParsedValidValue("entry.4", Integer.class));
        assertNull(handler.getParsedValidValue("entry.5", Boolean.class));
    }

    @Test(expected = ClassCastException.class)
    public void testGetParsedValidValueStringClassOfTException() {
        handler.getParsedValidValue("entry.1", String.class);
        fail("没有抛出异常。");
    }

    @Test
    public void testGetParsedValidValueNameClassOfT() {
        assertEquals(true, handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_1, Boolean.class));
        assertEquals(false, handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_2, Boolean.class));
        assertEquals(12, (int) handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_3, Integer.class));
        assertEquals(450, (int) handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_4, Integer.class));
        assertNull(handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_5, Boolean.class));
    }

    @Test(expected = ClassCastException.class)
    public void testGetParsedValidValueNameClassOfTException() {
        handler.getParsedValidValue(Test_SettingEnumItem.ENTRY_1, String.class);
        fail("没有抛出异常。");
    }

    @Test
    public void testSetParsedValueNameObject() {
        assertEquals(true, handler.getParsedValue(Test_SettingEnumItem.ENTRY_1));
        assertEquals(false, handler.getParsedValue(Test_SettingEnumItem.ENTRY_2));
        assertEquals(12, handler.getParsedValue(Test_SettingEnumItem.ENTRY_3));
        assertEquals(450, handler.getParsedValue(Test_SettingEnumItem.ENTRY_4));
    }

    @Test
    public void testEqualsObject() {
        DefaultSettingHandler anotherHandler = new DefaultSettingHandler(new LinkedHashMap<>(), new LinkedHashMap<>(),
                Collections.newSetFromMap(new WeakHashMap<>()));

        SettingUtil.putEnumItems(new SettingEnumItem[]{Test_SettingEnumItem.ENTRY_1, Test_SettingEnumItem.ENTRY_2,
                Test_SettingEnumItem.ENTRY_3, Test_SettingEnumItem.ENTRY_4}, anotherHandler);
        assertEquals(handler, anotherHandler);
    }
}
