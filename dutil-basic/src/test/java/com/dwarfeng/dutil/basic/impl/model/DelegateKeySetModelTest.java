package com.dwarfeng.dutil.basic.impl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DelegateKeySetModelTest {

    private final DelegateKeySetModel<String, WithKeyFixture> model = new DelegateKeySetModel<>(new LinkedHashSet<>(),
            Collections.newSetFromMap(new WeakHashMap<>()));
    private final SetObserverFixture<WithKeyFixture> obv = new SetObserverFixture<>();

    @BeforeEach
    public void setUp() {
        model.clearObserver();
        model.clear();
        obv.reset();
        model.add(WithKeyFixture.ELE_1);
        model.add(WithKeyFixture.ELE_2);
        model.add(WithKeyFixture.ELE_3);
        model.add(WithKeyFixture.ELE_4);
        model.add(WithKeyFixture.ELE_5);
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
        assertFalse(model.contains(WithKeyFixture.ELE_2));
        assertEquals(4, model.size());
        assertEquals(WithKeyFixture.ELE_2, obv.removedList.getFirst());
    }

    @Test
    public void testRemoveAllKey() {
        assertTrue(model.removeAllKey(Arrays.asList("B", "C", "D")));
        assertEquals(2, model.size());
        assertEquals(WithKeyFixture.ELE_2, obv.removedList.get(0));
        assertEquals(WithKeyFixture.ELE_3, obv.removedList.get(1));
        assertEquals(WithKeyFixture.ELE_4, obv.removedList.get(2));
    }

    @Test
    public void testRetainAllKey() {
        assertTrue(model.retainAllKey(Arrays.asList("B", "C", "D")));
        assertEquals(3, model.size());
        assertEquals(WithKeyFixture.ELE_1, obv.removedList.get(0));
        assertEquals(WithKeyFixture.ELE_5, obv.removedList.get(1));
    }

    @Test
    public void testHashCode() {
        Set<WithKeyFixture> set = new HashSet<>(Arrays.asList(WithKeyFixture.ELE_1, WithKeyFixture.ELE_2, WithKeyFixture.ELE_3,
                WithKeyFixture.ELE_4, WithKeyFixture.ELE_5));
        assertEquals(set.hashCode(), model.hashCode());
    }

    @Test
    public void testSize() {
        assertEquals(5, model.size());
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testIsEmpty() {
        assertFalse(model.isEmpty());
        model.clear();
        assertTrue(model.isEmpty());
    }

    @Test
    public void testContains() {
        assertTrue(model.contains(WithKeyFixture.ELE_1));
        assertTrue(model.contains(WithKeyFixture.ELE_2));
        assertTrue(model.contains(WithKeyFixture.ELE_3));
        assertTrue(model.contains(WithKeyFixture.ELE_4));
        assertTrue(model.contains(WithKeyFixture.ELE_5));
        assertFalse(model.contains(WithKeyFixture.FAIL_ELE));
    }

    @Test
    public void testIterator() {
        Iterator<WithKeyFixture> i = model.iterator();
        assertEquals(WithKeyFixture.ELE_1, i.next());
        assertEquals(WithKeyFixture.ELE_2, i.next());
        i.remove();
        assertEquals(4, model.size());
        assertFalse(model.containsKey("B"));

        assertEquals(WithKeyFixture.ELE_2, obv.removedList.getFirst());
    }

    @Test
    public void testToArray() {
        assertArrayEquals(new Object[]{WithKeyFixture.ELE_1, WithKeyFixture.ELE_2, WithKeyFixture.ELE_3, WithKeyFixture.ELE_4,
                WithKeyFixture.ELE_5}, model.toArray());
    }

    @Test
    public void testToArrayTArray() {
        assertArrayEquals(new Object[]{WithKeyFixture.ELE_1, WithKeyFixture.ELE_2, WithKeyFixture.ELE_3, WithKeyFixture.ELE_4,
                WithKeyFixture.ELE_5}, model.toArray(new WithKeyFixture[0]));
    }

    @Test
    public void testAdd() {
        assertTrue(model.add(WithKeyFixture.ELE_6));
        assertEquals(WithKeyFixture.ELE_6, obv.addedList.getFirst());
        assertFalse(model.add(WithKeyFixture.FAIL_ELE));
    }

    @Test
    public void testRemove() {
        assertTrue(model.remove(WithKeyFixture.ELE_2));
        assertEquals(WithKeyFixture.ELE_2, obv.removedList.getFirst());
        assertFalse(model.remove(WithKeyFixture.FAIL_ELE));
        assertEquals(4, model.size());
    }

    @Test
    public void testContainsAll() {
        assertTrue(model.containsAll(Arrays.asList(WithKeyFixture.ELE_1, WithKeyFixture.ELE_2, WithKeyFixture.ELE_3,
                WithKeyFixture.ELE_4, WithKeyFixture.ELE_5)));
        assertFalse(model.containsAll(Arrays.asList(WithKeyFixture.ELE_1, WithKeyFixture.ELE_2, WithKeyFixture.ELE_3,
                WithKeyFixture.ELE_4, WithKeyFixture.ELE_5, WithKeyFixture.FAIL_ELE)));
    }

    @Test
    public void testAddAll() {
        assertFalse(model.addAll(Arrays.asList(WithKeyFixture.FAIL_ELE, WithKeyFixture.ELE_2)));
        assertTrue(model.addAll(Arrays.asList(WithKeyFixture.FAIL_ELE, WithKeyFixture.ELE_6, WithKeyFixture.ELE_7)));
        assertEquals(7, model.size());
        assertEquals(WithKeyFixture.ELE_6, obv.addedList.get(0));
        assertEquals(WithKeyFixture.ELE_7, obv.addedList.get(1));
    }

    @SuppressWarnings("SlowAbstractSetRemoveAll")
    @Test
    public void testRemoveAll() {
        assertTrue(model.removeAll(Arrays.asList(WithKeyFixture.ELE_2, WithKeyFixture.ELE_3, WithKeyFixture.ELE_4)));
        assertEquals(2, model.size());
        assertEquals(WithKeyFixture.ELE_2, obv.removedList.get(0));
        assertEquals(WithKeyFixture.ELE_3, obv.removedList.get(1));
        assertEquals(WithKeyFixture.ELE_4, obv.removedList.get(2));
    }

    @Test
    public void testRetainAll() {
        assertTrue(model.retainAll(Arrays.asList(WithKeyFixture.ELE_2, WithKeyFixture.ELE_3, WithKeyFixture.ELE_4)));
        assertEquals(3, model.size());
        assertEquals(WithKeyFixture.ELE_1, obv.removedList.get(0));
        assertEquals(WithKeyFixture.ELE_5, obv.removedList.get(1));
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testClear() {
        model.clear();
        assertEquals(0, model.size());
        assertTrue(model.isEmpty());
        assertEquals(1, obv.cleared);
    }

    @Test
    public void testEqualsObject() {
        Set<WithKeyFixture> set = new HashSet<>(Arrays.asList(WithKeyFixture.ELE_1, WithKeyFixture.ELE_2, WithKeyFixture.ELE_3,
                WithKeyFixture.ELE_4, WithKeyFixture.ELE_5));
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
