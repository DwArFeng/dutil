package com.dwarfeng.dutil.basic.cna.model;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DelegateMapModelEntrySetTest {

    private final DelegateMapModel<String, String> model = new DelegateMapModel<>(new HashMap<>(),
            Collections.newSetFromMap(new WeakHashMap<>()));
    private final TestMapObserver obv = new TestMapObserver();
    private Set<Map.Entry<String, String>> entrySet;

    @Before
    public void setUp() {
        model.clearObserver();
        model.clear();
        model.put("A", "1");
        model.put("B", "2");
        model.put("C", "3");
        model.put("D", "4");
        model.put("E", "5");
        obv.reset();
        model.addObserver(obv);
        entrySet = model.entrySet();
    }

    @Test
    public void testHashCode() {
        Set<Map.Entry<String, String>> set = new HashSet<>(model.entrySet());
        assertEquals(set.hashCode(), entrySet.hashCode());
    }

    @Test
    public void testSize() {
        assertEquals(5, entrySet.size());
        entrySet.clear();
        assertEquals(0, entrySet.size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(entrySet.isEmpty());
        entrySet.clear();
        assertTrue(entrySet.isEmpty());
    }

    @Test
    public void testContains() {
        Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
        assertTrue(entrySet.contains(entry1));
        Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "1");
        assertFalse(entrySet.contains(entry2));
    }

    @Test
    public void testIterator() {
        Iterator<Map.Entry<String, String>> i = entrySet.iterator();
        Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
        assertEquals(entry1, i.next());
        i.remove();
        assertEquals("A", obv.removeKeyList.get(0));
        assertEquals("1", obv.removeValueList.get(0));
        Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
        assertEquals(entry2, i.next());
        Map.Entry<String, String> entry3 = i.next();
        entry3.setValue("0");
        assertEquals("C", obv.changedKeyList.get(0));
        assertEquals("3", obv.changedOldValueList.get(0));
        assertEquals("0", obv.changedNewValueList.get(0));
        i.next();
        i.next();
        assertFalse(i.hasNext());
        assertEquals("0", model.get("C"));
    }

    @Test
    public void testToArray() {
        Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
        Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
        Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("C", "3");
        Map.Entry<String, String> entry4 = new AbstractMap.SimpleEntry<>("D", "4");
        Map.Entry<String, String> entry5 = new AbstractMap.SimpleEntry<>("E", "5");
        assertArrayEquals(new Object[]{entry1, entry2, entry3, entry4, entry5}, entrySet.toArray());
    }

    @Test
    public void testToArrayTArray() {
        Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
        Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
        Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("C", "3");
        Map.Entry<String, String> entry4 = new AbstractMap.SimpleEntry<>("D", "4");
        Map.Entry<String, String> entry5 = new AbstractMap.SimpleEntry<>("E", "5");
        assertArrayEquals(new Object[]{entry1, entry2, entry3, entry4, entry5}, entrySet.toArray(new Map.Entry[0]));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        entrySet.add(null);
    }

    @Test
    public void testRemove() {
        Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
        assertTrue(entrySet.remove(entry1));
        assertFalse(model.containsKey("A"));
        assertEquals("A", obv.removeKeyList.get(0));
        assertEquals("1", obv.removeValueList.get(0));
    }

    @Test
    public void testContainsAll() {
        Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
        Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
        Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("C", "3");
        Map.Entry<String, String> entry4 = new AbstractMap.SimpleEntry<>("D", "4");
        Map.Entry<String, String> entry5 = new AbstractMap.SimpleEntry<>("E", "5");
        Map.Entry<String, String> entry6 = new AbstractMap.SimpleEntry<>("F", "6");

        assertTrue(entrySet.containsAll(Arrays.asList(entry1, entry2, entry3, entry4, entry5)));
        assertFalse(entrySet.containsAll(Arrays.asList(entry1, entry2, entry3, entry4, entry5, entry6)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        entrySet.addAll(null);
    }

    @Test
    public void testRemoveAll() {
        Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
        Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
        Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("C", "3");
        Map.Entry<String, String> entry4 = new AbstractMap.SimpleEntry<>("D", "4");
        Map.Entry<String, String> entry5 = new AbstractMap.SimpleEntry<>("E", "5");

        assertTrue(entrySet.removeAll(Arrays.asList(entry2, entry3, entry4)));

        assertEquals(2, model.size());

        assertArrayEquals(new Object[]{entry1, entry5}, entrySet.toArray());

        assertEquals("B", obv.removeKeyList.get(0));
        assertEquals("C", obv.removeKeyList.get(1));
        assertEquals("D", obv.removeKeyList.get(2));

        assertEquals("2", obv.removeValueList.get(0));
        assertEquals("3", obv.removeValueList.get(1));
        assertEquals("4", obv.removeValueList.get(2));
    }

    @Test
    public void testRetainAll() {
        Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
        Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("C", "3");
        Map.Entry<String, String> entry4 = new AbstractMap.SimpleEntry<>("D", "4");
        assertTrue(entrySet.retainAll(Arrays.asList(entry2, entry3, entry4)));

        assertEquals(3, model.size());

        assertArrayEquals(new Object[]{entry2, entry3, entry4}, entrySet.toArray());

        assertEquals("A", obv.removeKeyList.get(0));
        assertEquals("E", obv.removeKeyList.get(1));

        assertEquals("1", obv.removeValueList.get(0));
        assertEquals("5", obv.removeValueList.get(1));
    }

    @Test
    public void testClear() {
        entrySet.clear();
        assertEquals(0, model.size());
        assertEquals(1, obv.cleared);
    }

    @Test
    public void testEqualsObject() {
        Set<Map.Entry<String, String>> set = new HashSet<>(model.entrySet());
        assertEquals(entrySet, set);
    }
}
