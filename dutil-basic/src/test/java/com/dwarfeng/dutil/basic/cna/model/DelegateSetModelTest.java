package com.dwarfeng.dutil.basic.cna.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DelegateSetModelTest {

    private final SetModel<String> model = new DelegateSetModel<>(new LinkedHashSet<>(),
            Collections.newSetFromMap(new WeakHashMap<>()));
    private final TestSetObserver<String> obv = new TestSetObserver<>();

    @Before
    public void setUp() {
        model.clearObserver();
        model.clear();
        obv.reset();
        model.add("0");
        model.add("1");
        model.add("2");
        model.add("3");
        model.addObserver(obv);
    }

    @Test
    public final void testSize() {
        assertEquals(4, model.size());
    }

    @Test
    public final void testIsEmpty() {
        assertFalse(model.isEmpty());
        model.clear();
        assertTrue(model.isEmpty());
    }

    @Test
    public final void testContains() {
        assertTrue(model.contains("0"));
        assertTrue(model.contains("1"));
        assertTrue(model.contains("2"));
        assertTrue(model.contains("3"));
        assertFalse(model.contains("4"));

    }

    @Test
    public final void testIterator() {
        Iterator<String> i = model.iterator();
        assertEquals("0", i.next());
        assertEquals("1", i.next());
        i.remove();
        assertEquals("1", obv.removedList.get(0));
        i.next();
        i.next();
        assertFalse(i.hasNext());
    }

    @Test
    public final void testToArray() {
        assertArrayEquals(new Object[]{"0", "1", "2", "3"}, model.toArray());
    }

    @Test
    public final void testToArrayTArray() {
        assertArrayEquals(new String[]{"0", "1", "2", "3"}, model.toArray(new String[0]));
    }

    @Test
    public final void testAdd() {
        model.add("5");
        assertArrayEquals(new String[]{"0", "1", "2", "3", "5"}, model.toArray(new String[0]));
        assertEquals("5", obv.addedList.get(0));
    }

    @Test
    public final void testRemove() {
        model.remove("1");
        assertArrayEquals(new String[]{"0", "2", "3"}, model.toArray(new String[0]));
        assertEquals("1", obv.removedList.get(0));
    }

    @Test
    public final void testContainsAll() {
        assertTrue(model.containsAll(Arrays.asList("0", "1", "2", "3")));
        assertFalse(model.containsAll(Arrays.asList("0", "1", "2", "4")));
    }

    @Test(expected = NullPointerException.class)
    public final void testContainsAllWithException() {
        model.containsAll(null);
    }

    @Test
    public final void testAddAll() {
        model.addAll(Arrays.asList("0", "1", "4", "5"));
        assertArrayEquals(new String[]{"0", "1", "2", "3", "4", "5"}, model.toArray(new String[0]));
    }

    @Test(expected = NullPointerException.class)
    public final void testAddAllWithException() {
        model.addAll(null);
    }

    @Test
    public final void testRemoveAll() {
        model.removeAll(Arrays.asList("1", "2"));
        assertArrayEquals(new String[]{"0", "3"}, model.toArray(new String[0]));
        assertEquals("1", obv.removedList.get(0));
        assertEquals("2", obv.removedList.get(1));
    }

    @Test(expected = NullPointerException.class)
    public final void testRemoveAllWithException() {
        model.removeAll(null);
    }

    @Test
    public final void testRetainAll() {
        model.retainAll(Arrays.asList("1", "2"));
        assertArrayEquals(new String[]{"1", "2"}, model.toArray(new String[0]));
        assertEquals("0", obv.removedList.get(0));
        assertEquals("3", obv.removedList.get(1));
    }

    @Test(expected = NullPointerException.class)
    public final void testRetainAllWithException() {
        model.retainAll(null);
    }

    @Test
    public final void testClear() {
        model.clear();
        assertArrayEquals(new String[]{}, model.toArray(new String[0]));
        assertEquals(1, obv.cleared);
    }

    @Test
    public final void testGetObservers() {
        assertEquals(1, model.getObservers().size());
    }

    @Ignore
    @Test
    public final void testRemoveObserver() {
        model.removeObserver(obv);
        assertEquals(0, model.getObservers().size());
    }

    @Test
    public final void testEquals() {
        Set<String> set = new HashSet<>(Arrays.asList("0", "1", "2", "3"));
        assertEquals(model, set);
    }

    @Ignore
    @Test
    public final void testHashCode() {
        Set<String> set = new HashSet<>(Arrays.asList("0", "1", "2", "3"));
        assertEquals(model.hashCode(), set.hashCode());
    }
}
