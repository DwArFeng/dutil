package com.dwarfeng.dutil.basic.cna.model;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DelegateListModelTest {

    private final DelegateListModel<String> model = new DelegateListModel<>();
    private final TestListObserver<String> obv = new TestListObserver<>();

    @Before
    public void setUp() throws Exception {
        model.clearObserver();
        model.clear();
        model.add("0");
        model.add("1");
        model.add("2");
        model.add("3");
        obv.reset();
        model.addObserver(obv);
    }

    @Test
    public void testHashCode() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("0", "1", "2", "3"));
        assertEquals(true, model.hashCode() == list.hashCode());
    }

    @Test
    public void testSize() {
        assertEquals(4, model.size());
        model.add("4");
        assertEquals(5, model.size());
        model.clear();
        assertEquals(0, model.size());
    }

    @Test
    public void testIsEmpty() {
        assertEquals(false, model.isEmpty());
        model.clear();
        assertEquals(true, model.isEmpty());
    }

    @Test
    public void testContains() {
        assertEquals(true, model.contains("0"));
        assertEquals(true, model.contains("1"));
        assertEquals(true, model.contains("2"));
        assertEquals(true, model.contains("3"));
        assertEquals(false, model.contains("4"));
    }

    @Test
    public void testIterator() {
        Iterator<String> i = model.iterator();
        i.next();
        i.remove();
        assertEquals(3, model.size());
        assertEquals(new Integer(0), obv.removeIndexes.get(0));
        assertEquals("0", obv.removeElements.get(0));
        i.next();
        i.next();
        i.remove();
        assertEquals(2, model.size());
        assertEquals(new Integer(1), obv.removeIndexes.get(1));
        assertEquals("2", obv.removeElements.get(1));
        i.next();
        i.remove();
        assertEquals(1, model.size());
        assertEquals(new Integer(1), obv.removeIndexes.get(2));
        assertEquals("3", obv.removeElements.get(2));
        assertEquals(false, i.hasNext());
    }

    @Test
    public void testToArray() {
        assertArrayEquals(new Object[]{"0", "1", "2", "3"}, model.toArray());
    }

    @Test
    public void testToArrayTArray() {
        assertArrayEquals(new String[]{"0", "1", "2", "3"}, model.toArray(new String[0]));
    }

    @Test
    public void testAddE() {
        model.add("4");
        assertEquals(new Integer(4), obv.addedIndexes.get(0));
        assertEquals("4", obv.addedElements.get(0));
        assertEquals(5, model.size());
        assertEquals("4", model.get(4));
    }

    @Test
    public void testRemoveObject() {
        assertEquals(true, model.remove("1"));
        assertEquals(new Integer(1), obv.removeIndexes.get(0));
        assertEquals(true, model.containsAll(Arrays.asList("0", "2", "3")));
    }

    @Test
    public void testContainsAll() {
        assertEquals(true, model.containsAll(Arrays.asList("0", "1", "2", "3")));
        assertEquals(false, model.containsAll(Arrays.asList("0", "1", "2", "4")));
    }

    @Test
    public void testAddAllCollection() {
        model.addAll(Arrays.asList("4", "5", "6"));
        assertEquals(7, model.size());
        assertArrayEquals(new String[]{"0", "1", "2", "3", "4", "5", "6"}, model.toArray(new String[0]));
        assertEquals(3, obv.addedIndexes.size());
        assertArrayEquals(new Object[]{4, 5, 6}, obv.addedIndexes.toArray());
        assertArrayEquals(new Object[]{"4", "5", "6"}, obv.addedElements.toArray());
    }

    @Test
    public void testAddAllIntCollection() {
        model.addAll(1, Arrays.asList("4", "5", "6"));
        assertEquals(7, model.size());
        assertArrayEquals(new String[]{"0", "4", "5", "6", "1", "2", "3"}, model.toArray(new String[0]));
        assertEquals(3, obv.addedIndexes.size());
        assertArrayEquals(new Object[]{1, 2, 3}, obv.addedIndexes.toArray());
        assertArrayEquals(new Object[]{"4", "5", "6"}, obv.addedElements.toArray());
    }

    @Test
    public void testRemoveAll() {
        model.removeAll(Arrays.asList("1", "2"));
        assertEquals(2, model.size());
        assertArrayEquals(new String[]{"0", "3"}, model.toArray(new String[0]));
        assertEquals(2, obv.removeIndexes.size());
        assertArrayEquals(new Object[]{1, 1}, obv.removeIndexes.toArray());
    }

    @Test
    public void testRetainAll() {
        model.retainAll(Arrays.asList("1", "2"));
        assertEquals(2, model.size());
        assertArrayEquals(new String[]{"1", "2"}, model.toArray(new String[0]));
        assertEquals(2, obv.removeIndexes.size());
        assertArrayEquals(new Object[]{0, 2}, obv.removeIndexes.toArray());
    }

    @Test
    public void testClear() {
        model.clear();
        assertEquals(0, model.size());
        assertEquals(1, obv.clearedCount);
    }

    @Test
    public void testGet() {
        assertEquals("0", model.get(0));
        assertEquals("1", model.get(1));
        assertEquals("2", model.get(2));
        assertEquals("3", model.get(3));
    }

    @Test
    public void testSet() {
        model.set(0, "4");
        assertArrayEquals(new String[]{"4", "1", "2", "3"}, model.toArray(new String[0]));
        assertArrayEquals(new Object[]{0}, obv.changedIndexes.toArray());
        assertArrayEquals(new Object[]{"4"}, obv.changedNewElements.toArray());
        assertArrayEquals(new Object[]{"0"}, obv.changedOldElements.toArray());
    }

    @Test
    public void testAddIntE() {
        model.add(1, "4");
        assertArrayEquals(new String[]{"0", "4", "1", "2", "3"}, model.toArray(new String[0]));
        assertArrayEquals(new Object[]{1}, obv.addedIndexes.toArray());
        assertArrayEquals(new Object[]{"4"}, obv.addedElements.toArray());
    }

    @Test
    public void testRemoveInt() {
        model.remove(1);
        assertArrayEquals(new String[]{"0", "2", "3"}, model.toArray(new String[0]));
        assertArrayEquals(new Object[]{1}, obv.removeIndexes.toArray());
        assertArrayEquals(new Object[]{"1"}, obv.removeElements.toArray());
    }

    @Test
    public void testIndexOf() {
        model.add("0");
        assertEquals(0, model.indexOf("0"));
        assertEquals(2, model.indexOf("2"));
    }

    @Test
    public void testLastIndexOf() {
        model.add("0");
        assertEquals(4, model.lastIndexOf("0"));
        assertEquals(2, model.lastIndexOf("2"));
    }

    @Test
    public void testListIterator() {
        ListIterator<String> i = model.listIterator();
        assertEquals("0", i.next());
        assertEquals("0", i.previous());
        assertEquals("0", i.next());
        i.remove();
        assertEquals(new Integer(0), obv.removeIndexes.get(0));

        assertEquals("1", i.next());
        assertEquals("1", i.previous());

        i.add("0");
        assertEquals(new Integer(0), obv.addedIndexes.get(0));

        i.next();
        i.remove();
        i.next();
        i.remove();

        assertArrayEquals(new String[]{"0", "3"}, model.toArray(new String[0]));
        obv.reset();

        assertEquals("0", i.previous());
        assertEquals("0", i.next());

        // 两个方向的添加测试
        i.add("2");
        assertArrayEquals(new String[]{"0", "2", "3"}, model.toArray(new String[0]));
        assertEquals(new Integer(1), obv.addedIndexes.get(0));
        assertEquals("2", i.previous());

        i.add("1");
        assertArrayEquals(new String[]{"0", "1", "2", "3"}, model.toArray(new String[0]));
        assertEquals(new Integer(1), obv.addedIndexes.get(1));
        assertEquals("1", obv.addedElements.get(1));

        assertEquals("2", i.next());
        assertEquals("3", i.next());

        i.set("4");
        assertArrayEquals(new String[]{"0", "1", "2", "4"}, model.toArray(new String[0]));
        assertEquals(new Integer(3), obv.changedIndexes.get(0));
        assertEquals("3", obv.changedOldElements.get(0));
        assertEquals("4", obv.changedNewElements.get(0));
    }

    @Test
    public void testListIteratorInt() {
        ListIterator<String> i = model.listIterator(2);

        i.add("4");
        assertArrayEquals(new String[]{"0", "1", "4", "2", "3"}, model.toArray(new String[0]));
        assertEquals(new Integer(2), obv.addedIndexes.get(0));
    }

    @Test
    public void testEqualsObject() {
        List<String> list = new ArrayList<>(Arrays.asList("0", "1", "2", "3"));
        assertEquals(true, model.equals(list));
        assertEquals(true, list.equals(model));
        list.add("1");
        assertEquals(false, model.equals(list));
        assertEquals(false, list.equals(model));
    }

    @Test
    public void testGetObservers() {
        assertEquals(true, model.getObservers().contains(obv));
    }

    @Test
    public void testRemoveObserver() {
        model.removeObserver(obv);
        assertEquals(0, model.getObservers().size());
    }
}
