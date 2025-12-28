package com.dwarfeng.dutil.develop.resource;

import com.dwarfeng.dutil.basic.cna.model.DelegateKeySetModel;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class Test_UnmodifiableResourceHandler {

    private ResourceHandler handler = null;
    private TestSetObserver<Resource> observer = null;

    @Before
    public void setUp() {
        handler = new DelegateResourceHandler(
                new DelegateKeySetModel<>(new LinkedHashSet<>(), Collections.newSetFromMap(new WeakHashMap<>())));
        observer = new TestSetObserver<>();
        handler.add(TestResource.A);
        handler.add(TestResource.B);
        handler.add(TestResource.C);
        handler.addObserver(observer);
        handler = ResourceUtil.unmodifiableResourceHandler(handler);
    }

    @Test
    public void testContainsKey() {
        assertTrue(handler.containsKey("A"));
        assertTrue(handler.containsKey("B"));
        assertTrue(handler.containsKey("C"));
        assertFalse(handler.containsKey("D"));
    }

    @Test
    public void testContainsAllKey() {
        assertTrue(handler.containsAllKey(Arrays.asList("A", "B", "C")));
        assertFalse(handler.containsAllKey(Arrays.asList("A", "B", "C", "D")));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveKey() {
        handler.removeKey("A");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAllKey() {
        handler.removeAllKey(Arrays.asList("A", "B"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAllKey() {
        handler.retainAllKey(Arrays.asList("A", "B"));
    }

    @Test
    public void testSize() {
        assertEquals(3, handler.size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(handler.isEmpty());
    }

    @Test
    public void testContains() {
        assertTrue(handler.contains(TestResource.A));
        assertTrue(handler.contains(TestResource.B));
        assertTrue(handler.contains(TestResource.C));
        assertFalse(handler.contains(null));
    }

    @Test
    public void testIterator() {
        Iterator<Resource> i = handler.iterator();
        i.next();
        i.next();
        i.next();
        assertFalse(i.hasNext());
    }

    @Test
    public void testToArray() {
        assertArrayEquals(new Resource[]{TestResource.A, TestResource.B, TestResource.C}, handler.toArray());
    }

    @Test
    public void testToArrayTArray() {
        assertArrayEquals(new Resource[]{TestResource.A, TestResource.B, TestResource.C},
                handler.toArray(new Resource[0]));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        handler.add(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove() {
        handler.remove(null);
    }

    @Test
    public void testContainsAll() {
        assertTrue(handler.containsAll(Arrays.asList(TestResource.A, TestResource.B, TestResource.C)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        handler.addAll(Collections.singletonList(TestResource.B));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        handler.retainAll(Collections.singletonList(TestResource.B));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        handler.removeAll(Collections.singletonList(TestResource.B));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        handler.clear();
    }

    @Test
    public void testGetObservers() {
        assertEquals(1, handler.getObservers().size());
        assertTrue(handler.getObservers().contains(observer));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddObserver() {
        handler.getObservers().add(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveObserver() {
        handler.getObservers().remove(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClearObserver() {
        handler.getObservers().clear();
    }

    @Test
    public void testHashCode() {
        Set<Resource> set = new HashSet<>();
        set.add(TestResource.A);
        set.add(TestResource.B);
        set.add(TestResource.C);
        assertEquals(set.hashCode(), handler.hashCode());
    }

    @Test
    public void testEquals() {
        Set<Resource> set = new HashSet<>();
        set.add(TestResource.A);
        set.add(TestResource.B);
        set.add(TestResource.C);
        assertEquals(set, handler);
    }
}
