package com.dwarfeng.dutil.basic.cna.model;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DelegateKeySetModelTest {

    private final DelegateKeySetModel<String, TestWithKey> model = new DelegateKeySetModel<>(new LinkedHashSet<>(),
            Collections.newSetFromMap(new WeakHashMap<>()));
    private final TestSetObserver<TestWithKey> obv = new TestSetObserver<>();

    @Before
    public void setUp() {
        model.clearObserver();
        model.clear();
        obv.reset();
        model.add(TestWithKey.ELE_1);
        model.add(TestWithKey.ELE_2);
        model.add(TestWithKey.ELE_3);
        model.add(TestWithKey.ELE_4);
        model.add(TestWithKey.ELE_5);
        model.addObserver(obv);
    }

    @Test
    public void testContainsKey() {
        assertTrue(model.containsKey("A"));
        assertTrue(model.containsKey("B"));
        assertTrue(model.containsKey("C"));
        assertTrue(model.containsKey("D"));
        assertTrue(model.containsKey("E"));
        assertFalse(model.containsKey("F"));

    }

    @Test
    public void testContainsAllKey() {
        assertTrue(model.containsAllKey(Arrays.asList("A", "B", "C", "D", "E")));
        assertFalse(model.containsAllKey(Arrays.asList("A", "B", "C", "D", "E", "F")));
    }

    @Test
    public void testRemoveKey() {
        assertTrue(model.removeKey("B"));
        assertFalse(model.contains(TestWithKey.ELE_2));
        assertEquals(4, model.size());
        assertEquals(TestWithKey.ELE_2, obv.removedList.get(0));
    }

    @Test
    public void testRemoveAllKey() {
        assertTrue(model.removeAllKey(Arrays.asList("B", "C", "D")));
        assertEquals(2, model.size());
        assertEquals(TestWithKey.ELE_2, obv.removedList.get(0));
        assertEquals(TestWithKey.ELE_3, obv.removedList.get(1));
        assertEquals(TestWithKey.ELE_4, obv.removedList.get(2));
    }

    @Test
    public void testRetainAllKey() {
        assertTrue(model.retainAllKey(Arrays.asList("B", "C", "D")));
        assertEquals(3, model.size());
        assertEquals(TestWithKey.ELE_1, obv.removedList.get(0));
        assertEquals(TestWithKey.ELE_5, obv.removedList.get(1));
    }

    @Test
    public void testHashCode() {
        Set<TestWithKey> set = new HashSet<>(Arrays.asList(TestWithKey.ELE_1, TestWithKey.ELE_2, TestWithKey.ELE_3,
                TestWithKey.ELE_4, TestWithKey.ELE_5));
        assertEquals(set.hashCode(), model.hashCode());
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
    public void testContains() {
        assertTrue(model.contains(TestWithKey.ELE_1));
        assertTrue(model.contains(TestWithKey.ELE_2));
        assertTrue(model.contains(TestWithKey.ELE_3));
        assertTrue(model.contains(TestWithKey.ELE_4));
        assertTrue(model.contains(TestWithKey.ELE_5));
        assertFalse(model.contains(TestWithKey.FAIL_ELE));
    }

    @Test
    public void testIterator() {
        Iterator<TestWithKey> i = model.iterator();
        assertEquals(TestWithKey.ELE_1, i.next());
        assertEquals(TestWithKey.ELE_2, i.next());
        i.remove();
        assertEquals(4, model.size());
        assertFalse(model.containsKey("B"));

        assertEquals(TestWithKey.ELE_2, obv.removedList.get(0));
    }

    @Test
    public void testToArray() {
        assertArrayEquals(new Object[]{TestWithKey.ELE_1, TestWithKey.ELE_2, TestWithKey.ELE_3, TestWithKey.ELE_4,
                TestWithKey.ELE_5}, model.toArray());
    }

    @Test
    public void testToArrayTArray() {
        assertArrayEquals(new Object[]{TestWithKey.ELE_1, TestWithKey.ELE_2, TestWithKey.ELE_3, TestWithKey.ELE_4,
                TestWithKey.ELE_5}, model.toArray(new TestWithKey[0]));
    }

    @Test
    public void testAdd() {
        assertTrue(model.add(TestWithKey.ELE_6));
        assertEquals(TestWithKey.ELE_6, obv.addedList.get(0));
        assertFalse(model.add(TestWithKey.FAIL_ELE));
    }

    @Test
    public void testRemove() {
        assertTrue(model.remove(TestWithKey.ELE_2));
        assertEquals(TestWithKey.ELE_2, obv.removedList.get(0));
        assertFalse(model.remove(TestWithKey.FAIL_ELE));
        assertEquals(4, model.size());
    }

    @Test
    public void testContainsAll() {
        assertTrue(model.containsAll(Arrays.asList(TestWithKey.ELE_1, TestWithKey.ELE_2, TestWithKey.ELE_3,
                TestWithKey.ELE_4, TestWithKey.ELE_5)));
        assertFalse(model.containsAll(Arrays.asList(TestWithKey.ELE_1, TestWithKey.ELE_2, TestWithKey.ELE_3,
                TestWithKey.ELE_4, TestWithKey.ELE_5, TestWithKey.FAIL_ELE)));
    }

    @Test
    public void testAddAll() {
        assertFalse(model.addAll(Arrays.asList(TestWithKey.FAIL_ELE, TestWithKey.ELE_2)));
        assertTrue(model.addAll(Arrays.asList(TestWithKey.FAIL_ELE, TestWithKey.ELE_6, TestWithKey.ELE_7)));
        assertEquals(7, model.size());
        assertEquals(TestWithKey.ELE_6, obv.addedList.get(0));
        assertEquals(TestWithKey.ELE_7, obv.addedList.get(1));
    }

    @Test
    public void testRemoveAll() {
        assertTrue(model.removeAll(Arrays.asList(TestWithKey.ELE_2, TestWithKey.ELE_3, TestWithKey.ELE_4)));
        assertEquals(2, model.size());
        assertEquals(TestWithKey.ELE_2, obv.removedList.get(0));
        assertEquals(TestWithKey.ELE_3, obv.removedList.get(1));
        assertEquals(TestWithKey.ELE_4, obv.removedList.get(2));
    }

    @Test
    public void testRetainAll() {
        assertTrue(model.retainAll(Arrays.asList(TestWithKey.ELE_2, TestWithKey.ELE_3, TestWithKey.ELE_4)));
        assertEquals(3, model.size());
        assertEquals(TestWithKey.ELE_1, obv.removedList.get(0));
        assertEquals(TestWithKey.ELE_5, obv.removedList.get(1));
    }

    @Test
    public void testClear() {
        model.clear();
        assertEquals(0, model.size());
        assertTrue(model.isEmpty());
        assertEquals(1, obv.cleared);
    }

    @Test
    public void testEqualsObject() {
        Set<TestWithKey> set = new HashSet<>(Arrays.asList(TestWithKey.ELE_1, TestWithKey.ELE_2, TestWithKey.ELE_3,
                TestWithKey.ELE_4, TestWithKey.ELE_5));
        assertEquals(set.hashCode(), model.hashCode());
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
        assertTrue(model.getObservers().isEmpty());
    }
}
