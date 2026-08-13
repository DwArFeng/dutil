package com.dwarfeng.dutil.basic.impl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DelegateKeyListModelTest {

    private final DelegateKeyListModel<String, WithKeyFixture> model = new DelegateKeyListModel<>();
    private final ListObserverFixture<WithKeyFixture> obv = new ListObserverFixture<>();

    @BeforeEach
    public void setUp() {
        model.clearObserver();
        model.clear();
        model.add(WithKeyFixture.ELE_1);
        model.add(WithKeyFixture.ELE_2);
        model.add(WithKeyFixture.ELE_3);
        model.add(WithKeyFixture.ELE_4);
        model.add(WithKeyFixture.ELE_5);
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
        assertTrue(model.add(WithKeyFixture.ELE_1));
        assertEquals(0, model.indexOfKey("A"));
        assertEquals(1, model.indexOfKey("B"));
        assertEquals(2, model.indexOfKey("C"));
        assertEquals(3, model.indexOfKey("D"));
        assertEquals(4, model.indexOfKey("E"));
    }

    @Test
    public void testLastIndexOfKey() {
        assertTrue(model.add(WithKeyFixture.ELE_1));
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
        assertEquals(Integer.valueOf(2), obv.removeIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_3, obv.removeElements.getFirst());
    }

    @Test
    public void testRemoveAllKey() {
        assertTrue(model.removeAllKey(Arrays.asList("B", "C", "D")));
        assertEquals(2, model.size());

        assertEquals(Integer.valueOf(1), obv.removeIndexes.get(0));
        assertEquals(Integer.valueOf(1), obv.removeIndexes.get(1));
        assertEquals(Integer.valueOf(1), obv.removeIndexes.get(2));

        assertEquals(WithKeyFixture.ELE_2, obv.removeElements.get(0));
        assertEquals(WithKeyFixture.ELE_3, obv.removeElements.get(1));
        assertEquals(WithKeyFixture.ELE_4, obv.removeElements.get(2));
    }

    @Test
    public void testRetainAllKey() {
        assertTrue(model.retainAllKey(Arrays.asList("B", "C", "D")));
        assertEquals(3, model.size());

        assertEquals(Integer.valueOf(0), obv.removeIndexes.get(0));
        assertEquals(Integer.valueOf(3), obv.removeIndexes.get(1));

        assertEquals(WithKeyFixture.ELE_1, obv.removeElements.get(0));
        assertEquals(WithKeyFixture.ELE_5, obv.removeElements.get(1));
    }

    @Test
    public void testHashCode() {
        List<WithKeyFixture> list = new LinkedList<>(Arrays.asList(
                WithKeyFixture.ELE_1, WithKeyFixture.ELE_2, WithKeyFixture.ELE_3, WithKeyFixture.ELE_4,
                WithKeyFixture.ELE_5
        ));
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
        i.remove();
        assertEquals(4, model.size());
        assertEquals(Integer.valueOf(0), obv.removeIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_1, obv.removeElements.getFirst());
        i.next();
        i.next();
        i.next();
        i.next();
        assertFalse(i.hasNext());
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
    public void testAddE() {
        assertTrue(model.add(WithKeyFixture.FAIL_ELE));
        assertTrue(model.add(WithKeyFixture.ELE_1));

        assertEquals(Integer.valueOf(5), obv.addedIndexes.get(0));
        assertEquals(WithKeyFixture.FAIL_ELE, obv.addedElements.get(0));

        assertEquals(Integer.valueOf(6), obv.addedIndexes.get(1));
        assertEquals(WithKeyFixture.ELE_1, obv.addedElements.get(1));

        assertEquals(7, model.size());
    }

    @Test
    public void testRemoveObject() {
        assertFalse(model.remove(WithKeyFixture.FAIL_ELE));
        assertTrue(model.remove(WithKeyFixture.ELE_3));
        assertEquals(Integer.valueOf(2), obv.removeIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_3, obv.removeElements.getFirst());
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
    public void testAddAllCollectionOfQExtendsE() {
        assertTrue(model.addAll(Arrays.asList(WithKeyFixture.ELE_6, WithKeyFixture.ELE_7, WithKeyFixture.ELE_8)));

        assertEquals(Integer.valueOf(5), obv.addedIndexes.get(0));
        assertEquals(Integer.valueOf(6), obv.addedIndexes.get(1));
        assertEquals(Integer.valueOf(7), obv.addedIndexes.get(2));

        assertEquals(WithKeyFixture.ELE_6, obv.addedElements.get(0));
        assertEquals(WithKeyFixture.ELE_7, obv.addedElements.get(1));
        assertEquals(WithKeyFixture.ELE_8, obv.addedElements.get(2));
    }

    @Test
    public void testAddAllIntCollectionOfQExtendsE() {
        assertTrue(model.addAll(2, Arrays.asList(WithKeyFixture.ELE_6, WithKeyFixture.ELE_7, WithKeyFixture.ELE_8)));

        assertEquals(Integer.valueOf(2), obv.addedIndexes.get(0));
        assertEquals(Integer.valueOf(3), obv.addedIndexes.get(1));
        assertEquals(Integer.valueOf(4), obv.addedIndexes.get(2));

        assertEquals(WithKeyFixture.ELE_6, obv.addedElements.get(0));
        assertEquals(WithKeyFixture.ELE_7, obv.addedElements.get(1));
        assertEquals(WithKeyFixture.ELE_8, obv.addedElements.get(2));
    }

    @Test
    public void testRemoveAll() {
        assertTrue(model.removeAll(Arrays.asList(WithKeyFixture.ELE_2, WithKeyFixture.ELE_3, WithKeyFixture.ELE_4)));
        assertEquals(2, model.size());

        assertEquals(Integer.valueOf(1), obv.removeIndexes.get(0));
        assertEquals(Integer.valueOf(1), obv.removeIndexes.get(1));
        assertEquals(Integer.valueOf(1), obv.removeIndexes.get(2));

        assertEquals(WithKeyFixture.ELE_2, obv.removeElements.get(0));
        assertEquals(WithKeyFixture.ELE_3, obv.removeElements.get(1));
        assertEquals(WithKeyFixture.ELE_4, obv.removeElements.get(2));
    }

    @Test
    public void testRetainAll() {
        assertTrue(model.retainAll(Arrays.asList(WithKeyFixture.ELE_2, WithKeyFixture.ELE_3, WithKeyFixture.ELE_4)));
        assertEquals(3, model.size());

        assertEquals(Integer.valueOf(0), obv.removeIndexes.get(0));
        assertEquals(Integer.valueOf(3), obv.removeIndexes.get(1));

        assertEquals(WithKeyFixture.ELE_1, obv.removeElements.get(0));
        assertEquals(WithKeyFixture.ELE_5, obv.removeElements.get(1));
    }

    @Test
    public void testClear() {
        model.clear();
        assertEquals(0, model.size());
        assertEquals(1, obv.clearedCount);
    }

    @Test
    public void testGet() {
        assertEquals(WithKeyFixture.ELE_1, model.get(0));
        assertEquals(WithKeyFixture.ELE_2, model.get(1));
        assertEquals(WithKeyFixture.ELE_3, model.get(2));
        assertEquals(WithKeyFixture.ELE_4, model.get(3));
        assertEquals(WithKeyFixture.ELE_5, model.get(4));

    }

    @Test
    public void testSet() {
        assertEquals(WithKeyFixture.ELE_1, model.set(0, WithKeyFixture.FAIL_ELE));
        assertEquals(WithKeyFixture.ELE_2, model.set(1, WithKeyFixture.ELE_1));

        assertEquals(Integer.valueOf(0), obv.changedIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_1, obv.changedOldElements.getFirst());
        assertEquals(WithKeyFixture.FAIL_ELE, obv.changedNewElements.getFirst());

        assertEquals(Integer.valueOf(1), obv.changedIndexes.get(1));
        assertEquals(WithKeyFixture.ELE_2, obv.changedOldElements.get(1));
        assertEquals(WithKeyFixture.ELE_1, obv.changedNewElements.get(1));
    }

    @Test
    public void testAddIntE() {
        model.add(2, WithKeyFixture.FAIL_ELE);
        assertEquals(6, model.size());
        model.add(1, WithKeyFixture.ELE_3);
        assertEquals(7, model.size());

        assertEquals(Integer.valueOf(2), obv.addedIndexes.get(0));
        assertEquals(WithKeyFixture.FAIL_ELE, obv.addedElements.get(0));

        assertEquals(Integer.valueOf(1), obv.addedIndexes.get(1));
        assertEquals(WithKeyFixture.ELE_3, obv.addedElements.get(1));
    }

    @Test
    public void testRemoveInt() {
        assertEquals(WithKeyFixture.ELE_3, model.remove(2));
        assertEquals(Integer.valueOf(2), obv.removeIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_3, obv.removeElements.getFirst());
    }

    @Test
    public void testIndexOf() {
        assertTrue(model.add(WithKeyFixture.ELE_1));
        assertEquals(0, model.indexOf(WithKeyFixture.ELE_1));
    }

    @Test
    public void testLastIndexOf() {
        assertTrue(model.add(WithKeyFixture.ELE_1));
        assertEquals(5, model.lastIndexOf(WithKeyFixture.ELE_1));
    }

    @Test
    public void testListIterator() {
        ListIterator<WithKeyFixture> i = model.listIterator();
        assertEquals(WithKeyFixture.ELE_1, i.next());
        assertEquals(WithKeyFixture.ELE_1, i.previous());
        i.add(WithKeyFixture.ELE_6);

        assertEquals(Integer.valueOf(0), obv.addedIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_6, obv.addedElements.getFirst());

        assertEquals(WithKeyFixture.ELE_6, i.previous());
        i.set(WithKeyFixture.ELE_7);

        assertEquals(Integer.valueOf(0), obv.changedIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_6, obv.changedOldElements.getFirst());
        assertEquals(WithKeyFixture.ELE_7, obv.changedNewElements.getFirst());

        i.remove();
        assertEquals(Integer.valueOf(0), obv.removeIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_7, obv.removeElements.getFirst());
    }

    @Test
    public void testListIteratorInt() {
        ListIterator<WithKeyFixture> i = model.listIterator(2);
        assertEquals(WithKeyFixture.ELE_3, i.next());
        assertEquals(WithKeyFixture.ELE_3, i.previous());
        i.add(WithKeyFixture.ELE_6);

        assertEquals(Integer.valueOf(2), obv.addedIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_6, obv.addedElements.getFirst());

        assertEquals(WithKeyFixture.ELE_6, i.previous());
        i.set(WithKeyFixture.ELE_7);

        assertEquals(Integer.valueOf(2), obv.changedIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_6, obv.changedOldElements.getFirst());
        assertEquals(WithKeyFixture.ELE_7, obv.changedNewElements.getFirst());

        i.remove();
        assertEquals(Integer.valueOf(2), obv.removeIndexes.getFirst());
        assertEquals(WithKeyFixture.ELE_7, obv.removeElements.getFirst());
    }

    @Test
    public void testEqualsObject() {
        List<WithKeyFixture> list = new ArrayList<>(Arrays.asList(
                WithKeyFixture.ELE_1, WithKeyFixture.ELE_2, WithKeyFixture.ELE_3, WithKeyFixture.ELE_4,
                WithKeyFixture.ELE_5
        ));
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
