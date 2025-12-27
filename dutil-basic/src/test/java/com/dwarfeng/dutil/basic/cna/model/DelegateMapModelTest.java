package com.dwarfeng.dutil.basic.cna.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DelegateMapModelTest {

    private final DelegateMapModel<String, String> model = new DelegateMapModel<>();
    private final TestMapObserver obv = new TestMapObserver();

    @Before
    public void setUp() throws Exception {
        model.clearObserver();
        model.clear();
        model.put("A", "1");
        model.put("B", "2");
        model.put("C", "3");
        model.put("D", "4");
        model.put("E", "5");
        obv.reset();
        model.addObserver(obv);
    }

    @Test
    public void testSize() {
        assertEquals(5, model.size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(model.isEmpty());
        model.clear();
        assertTrue(model.isEmpty());
    }

    @Test
    public void testContainsKey() {
        assertTrue(model.containsKey("A"));
        assertTrue(model.containsKey("B"));
        assertTrue(model.containsKey("C"));
        assertTrue(model.containsKey("D"));
        assertTrue(model.containsKey("E"));
        assertFalse(model.containsKey("1"));

    }

    @Test
    public void testContainsValue() {
        assertTrue(model.containsValue("1"));
        assertTrue(model.containsValue("2"));
        assertTrue(model.containsValue("3"));
        assertTrue(model.containsValue("4"));
        assertTrue(model.containsValue("5"));
        assertFalse(model.containsValue("A"));
    }

    @Test
    public void testGet() {
        assertEquals("1", model.get("A"));
        assertEquals("2", model.get("B"));
        assertEquals("3", model.get("C"));
        assertEquals("4", model.get("D"));
        assertEquals("5", model.get("E"));
        assertNull(model.get("F"));

    }

    @Test
    public void testPut() {
        assertNull(model.put("F", "6"));
        assertEquals("6", model.get("F"));
        assertEquals("F", obv.putKeyList.get(0));
        assertEquals("6", obv.putValueList.get(0));
        assertEquals("1", model.put("A", "7"));
        assertEquals("A", obv.changedKeyList.get(0));
        assertEquals("1", obv.changedOldValueList.get(0));
        assertEquals("7", obv.changedNewValueList.get(0));
    }

    @Test
    public void testRemove() {
        assertNull(model.remove("1"));
        assertEquals("2", model.remove("B"));
        assertEquals("B", obv.removeKeyList.get(0));
        assertEquals("2", obv.removeValueList.get(0));
    }

    @Test
    public void testPutAll() {
        Map<String, String> m = new HashMap<>();
        m.put("A", "1");
        m.put("B", "6");
        m.put("F", "7");
        model.putAll(m);
        assertEquals("A", obv.changedKeyList.get(0));
        assertEquals("1", obv.changedOldValueList.get(0));
        assertEquals("1", obv.changedNewValueList.get(0));
        assertEquals("B", obv.changedKeyList.get(1));
        assertEquals("6", obv.changedNewValueList.get(1));
        assertEquals("2", obv.changedOldValueList.get(1));
        assertEquals("F", obv.putKeyList.get(0));
        assertEquals("7", obv.putValueList.get(0));
    }

    @Test
    public void testClear() {
        model.clear();
        assertTrue(model.isEmpty());
        assertEquals(0, model.size());
        assertEquals(1, obv.cleared);
    }

    @Test
    public void testGetObservers() {
        assertEquals(1, model.getObservers().size());
        assertTrue(model.getObservers().contains(obv));
    }

    @Test
    public void testRemoveObserver() {
        assertTrue(model.removeObserver(obv));
        assertEquals(0, model.getObservers().size());
    }

    @Test
    public void testHashCode() {
        Map<String, String> map = new HashMap<>();
        map.put("A", "1");
        map.put("B", "2");
        map.put("C", "3");
        map.put("D", "4");
        map.put("E", "5");
        assertEquals(model.hashCode(), map.hashCode());
    }

    @Test
    public void testEquals() {
        Map<String, String> map = new HashMap<>();
        map.put("A", "1");
        map.put("B", "2");
        map.put("C", "3");
        map.put("D", "4");
        map.put("E", "5");
        assertEquals(map, model);
        assertEquals(model, map);
    }
}
