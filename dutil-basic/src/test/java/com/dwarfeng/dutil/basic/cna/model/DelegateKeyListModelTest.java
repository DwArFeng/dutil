package com.dwarfeng.dutil.basic.cna.model;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DelegateKeyListModelTest {

    private final DelegateKeyListModel<String, TestWithKey> model = new DelegateKeyListModel<>();
    private final TestListObserver<TestWithKey> obv = new TestListObserver<>();

    @Before
    public void setUp() {
        model.clearObserver();
        model.clear();
        model.add(TestWithKey.ELE_1);
        model.add(TestWithKey.ELE_2);
        model.add(TestWithKey.ELE_3);
        model.add(TestWithKey.ELE_4);
        model.add(TestWithKey.ELE_5);
        obv.reset();
        model.addObserver(obv);
    }

    @Test
    public void testContainsKey() {
        assertTrue(model.containsKey("A"));
        assertTrue(model.containsKey("B"));
        assertTrue(model.containsKey("C"));
        assertTrue(model.containsKey("D"));
        assertTrue(model.containsKey("E"));
        assertFalse(model.containsKey("X"));
    }

    @Test
    public void testContainsAllKey() {
        assertTrue(model.containsAllKey(Arrays.asList("A", "B", "C", "D", "E")));
        assertFalse(model.containsAllKey(Arrays.asList("A", "B", "C", "D", "E", "X")));
    }

    @Test
    public void testIndexOfKey() {
        assertTrue(model.add(TestWithKey.ELE_1));
        assertEquals(0, model.indexOfKey("A"));
        assertEquals(1, model.indexOfKey("B"));
        assertEquals(2, model.indexOfKey("C"));
        assertEquals(3, model.indexOfKey("D"));
        assertEquals(4, model.indexOfKey("E"));

    }

    @Test
    public void testLastIndexOfKey() {
        assertTrue(model.add(TestWithKey.ELE_1));
        assertEquals(5, model.lastIndexOfKey("A"));
        assertEquals(1, model.lastIndexOfKey("B"));
        assertEquals(2, model.lastIndexOfKey("C"));
        assertEquals(3, model.lastIndexOfKey("D"));
        assertEquals(4, model.lastIndexOfKey("E"));
    }

    @Test
    public void testRemoveKey() {
        assertFalse(model.removeKey("X"));
        assertTrue(model.removeKey("C"));
        assertEquals(new Integer(2), obv.removeIndexes.get(0));
        assertEquals(TestWithKey.ELE_3, obv.removeElements.get(0));
    }

    @Test
    public void testRemoveAllKey() {
        assertTrue(model.removeAllKey(Arrays.asList("B", "C", "D")));
        assertEquals(2, model.size());

        assertEquals(new Integer(1), obv.removeIndexes.get(0));
        assertEquals(new Integer(1), obv.removeIndexes.get(1));
        assertEquals(new Integer(1), obv.removeIndexes.get(2));

        assertEquals(TestWithKey.ELE_2, obv.removeElements.get(0));
        assertEquals(TestWithKey.ELE_3, obv.removeElements.get(1));
        assertEquals(TestWithKey.ELE_4, obv.removeElements.get(2));
    }

    @Test
    public void testRetainAllKey() {
        assertTrue(model.retainAllKey(Arrays.asList("B", "C", "D")));
        assertEquals(3, model.size());

        assertEquals(new Integer(0), obv.removeIndexes.get(0));
        assertEquals(new Integer(3), obv.removeIndexes.get(1));

        assertEquals(TestWithKey.ELE_1, obv.removeElements.get(0));
        assertEquals(TestWithKey.ELE_5, obv.removeElements.get(1));
    }

    @Test
    public void testHashCode() {
        List<TestWithKey> list = new LinkedList<>(Arrays.asList(TestWithKey.ELE_1, TestWithKey.ELE_2, TestWithKey.ELE_3,
                TestWithKey.ELE_4, TestWithKey.ELE_5));
        assertEquals(model.hashCode(), list.hashCode());
    }

    @Test
    public void testSize() {
        assertEquals(5, model.size());
        model.clear();
        assertEquals(0, model.size());
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
        i.remove();
        assertEquals(4, model.size());
        assertEquals(new Integer(0), obv.removeIndexes.get(0));
        assertEquals(TestWithKey.ELE_1, obv.removeElements.get(0));
        i.next();
        i.next();
        i.next();
        i.next();
        assertFalse(i.hasNext());
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
    public void testAddE() {
        assertTrue(model.add(TestWithKey.FAIL_ELE));
        assertTrue(model.add(TestWithKey.ELE_1));

        assertEquals(new Integer(5), obv.addedIndexes.get(0));
        assertEquals(TestWithKey.FAIL_ELE, obv.addedElements.get(0));

        assertEquals(new Integer(6), obv.addedIndexes.get(1));
        assertEquals(TestWithKey.ELE_1, obv.addedElements.get(1));

        assertEquals(7, model.size());
    }

    @Test
    public void testRemoveObject() {
        assertFalse(model.remove(TestWithKey.FAIL_ELE));
        assertTrue(model.remove(TestWithKey.ELE_3));
        assertEquals(new Integer(2), obv.removeIndexes.get(0));
        assertEquals(TestWithKey.ELE_3, obv.removeElements.get(0));
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
    public void testAddAllCollectionOfQextendsE() {
        assertTrue(model.addAll(Arrays.asList(TestWithKey.ELE_6, TestWithKey.ELE_7, TestWithKey.ELE_8)));

        assertEquals(new Integer(5), obv.addedIndexes.get(0));
        assertEquals(new Integer(6), obv.addedIndexes.get(1));
        assertEquals(new Integer(7), obv.addedIndexes.get(2));

        assertEquals(TestWithKey.ELE_6, obv.addedElements.get(0));
        assertEquals(TestWithKey.ELE_7, obv.addedElements.get(1));
        assertEquals(TestWithKey.ELE_8, obv.addedElements.get(2));
    }

    @Test
    public void testAddAllIntCollectionOfQextendsE() {
        assertTrue(model.addAll(2, Arrays.asList(TestWithKey.ELE_6, TestWithKey.ELE_7, TestWithKey.ELE_8)));

        assertEquals(new Integer(2), obv.addedIndexes.get(0));
        assertEquals(new Integer(3), obv.addedIndexes.get(1));
        assertEquals(new Integer(4), obv.addedIndexes.get(2));

        assertEquals(TestWithKey.ELE_6, obv.addedElements.get(0));
        assertEquals(TestWithKey.ELE_7, obv.addedElements.get(1));
        assertEquals(TestWithKey.ELE_8, obv.addedElements.get(2));
    }

    @Test
    public void testRemoveAll() {
        assertTrue(model.removeAll(Arrays.asList(TestWithKey.ELE_2, TestWithKey.ELE_3, TestWithKey.ELE_4)));
        assertEquals(2, model.size());

        assertEquals(new Integer(1), obv.removeIndexes.get(0));
        assertEquals(new Integer(1), obv.removeIndexes.get(1));
        assertEquals(new Integer(1), obv.removeIndexes.get(2));

        assertEquals(TestWithKey.ELE_2, obv.removeElements.get(0));
        assertEquals(TestWithKey.ELE_3, obv.removeElements.get(1));
        assertEquals(TestWithKey.ELE_4, obv.removeElements.get(2));
    }

    @Test
    public void testRetainAll() {
        assertTrue(model.retainAll(Arrays.asList(TestWithKey.ELE_2, TestWithKey.ELE_3, TestWithKey.ELE_4)));
        assertEquals(3, model.size());

        assertEquals(new Integer(0), obv.removeIndexes.get(0));
        assertEquals(new Integer(3), obv.removeIndexes.get(1));

        assertEquals(TestWithKey.ELE_1, obv.removeElements.get(0));
        assertEquals(TestWithKey.ELE_5, obv.removeElements.get(1));
    }

    @Test
    public void testClear() {
        model.clear();
        assertEquals(0, model.size());
        assertEquals(1, obv.clearedCount);
    }

    @Test
    public void testGet() {
        assertEquals(TestWithKey.ELE_1, model.get(0));
        assertEquals(TestWithKey.ELE_2, model.get(1));
        assertEquals(TestWithKey.ELE_3, model.get(2));
        assertEquals(TestWithKey.ELE_4, model.get(3));
        assertEquals(TestWithKey.ELE_5, model.get(4));

    }

    @Test
    public void testSet() {
        assertEquals(TestWithKey.ELE_1, model.set(0, TestWithKey.FAIL_ELE));
        assertEquals(TestWithKey.ELE_2, model.set(1, TestWithKey.ELE_1));

        assertEquals(new Integer(0), obv.changedIndexes.get(0));
        assertEquals(TestWithKey.ELE_1, obv.changedOldElements.get(0));
        assertEquals(TestWithKey.FAIL_ELE, obv.changedNewElements.get(0));

        assertEquals(new Integer(1), obv.changedIndexes.get(1));
        assertEquals(TestWithKey.ELE_2, obv.changedOldElements.get(1));
        assertEquals(TestWithKey.ELE_1, obv.changedNewElements.get(1));
    }

    @Test
    public void testAddIntE() {
        model.add(2, TestWithKey.FAIL_ELE);
        assertEquals(6, model.size());
        model.add(1, TestWithKey.ELE_3);
        assertEquals(7, model.size());

        assertEquals(new Integer(2), obv.addedIndexes.get(0));
        assertEquals(TestWithKey.FAIL_ELE, obv.addedElements.get(0));

        assertEquals(new Integer(1), obv.addedIndexes.get(1));
        assertEquals(TestWithKey.ELE_3, obv.addedElements.get(1));
    }

    @Test
    public void testRemoveInt() {
        assertEquals(TestWithKey.ELE_3, model.remove(2));
        assertEquals(new Integer(2), obv.removeIndexes.get(0));
        assertEquals(TestWithKey.ELE_3, obv.removeElements.get(0));
    }

    @Test
    public void testIndexOf() {
        assertTrue(model.add(TestWithKey.ELE_1));
        assertEquals(0, model.indexOf(TestWithKey.ELE_1));
    }

    @Test
    public void testLastIndexOf() {
        assertTrue(model.add(TestWithKey.ELE_1));
        assertEquals(5, model.lastIndexOf(TestWithKey.ELE_1));
    }

    @Test
    public void testListIterator() {
        ListIterator<TestWithKey> i = model.listIterator();
        assertEquals(TestWithKey.ELE_1, i.next());
        assertEquals(TestWithKey.ELE_1, i.previous());
        i.add(TestWithKey.ELE_6);

        assertEquals(new Integer(0), obv.addedIndexes.get(0));
        assertEquals(TestWithKey.ELE_6, obv.addedElements.get(0));

        assertEquals(TestWithKey.ELE_6, i.previous());
        i.set(TestWithKey.ELE_7);

        assertEquals(new Integer(0), obv.changedIndexes.get(0));
        assertEquals(TestWithKey.ELE_6, obv.changedOldElements.get(0));
        assertEquals(TestWithKey.ELE_7, obv.changedNewElements.get(0));

        i.remove();
        assertEquals(new Integer(0), obv.removeIndexes.get(0));
        assertEquals(TestWithKey.ELE_7, obv.removeElements.get(0));
    }

    @Test
    public void testListIteratorInt() {
        ListIterator<TestWithKey> i = model.listIterator(2);
        assertEquals(TestWithKey.ELE_3, i.next());
        assertEquals(TestWithKey.ELE_3, i.previous());
        i.add(TestWithKey.ELE_6);

        assertEquals(new Integer(2), obv.addedIndexes.get(0));
        assertEquals(TestWithKey.ELE_6, obv.addedElements.get(0));

        assertEquals(TestWithKey.ELE_6, i.previous());
        i.set(TestWithKey.ELE_7);

        assertEquals(new Integer(2), obv.changedIndexes.get(0));
        assertEquals(TestWithKey.ELE_6, obv.changedOldElements.get(0));
        assertEquals(TestWithKey.ELE_7, obv.changedNewElements.get(0));

        i.remove();
        assertEquals(new Integer(2), obv.removeIndexes.get(0));
        assertEquals(TestWithKey.ELE_7, obv.removeElements.get(0));
    }

    @Test
    public void testEqualsObject() {
        List<TestWithKey> list = new ArrayList<>(Arrays.asList(TestWithKey.ELE_1, TestWithKey.ELE_2, TestWithKey.ELE_3,
                TestWithKey.ELE_4, TestWithKey.ELE_5));
        assertEquals(list, model);
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
}
