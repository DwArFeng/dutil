package com.dwarfeng.dutil.basic.cna.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultReferenceModelTest {

    private final ReferenceModel<String> model = new DefaultReferenceModel<>();
    private final TestReferenceObserver<String> observer = new TestReferenceObserver<>();

    @Before
    public void setUp() throws Exception {
        model.clearObserver();
        model.clear();
        model.set("A");
        observer.reset();
        model.addObserver(observer);
    }

    @Test
    public void testGet() {
        assertEquals("A", model.get());
    }

    @Test
    public void testSet() {
        assertEquals("A", model.set("B"));
        assertEquals("A", observer.getOldValue());
        assertEquals("B", observer.getNewValue());
        assertFalse(observer.isClearFlag());
    }

    @Test
    public void testClear() {
        model.clear();
        assertTrue(model.isEmpty());
        assertTrue(observer.isClearFlag());
        assertNull(observer.getOldValue());
        assertNull(observer.getNewValue());
        assertNull(model.get());
    }

    @Test
    public void testGetObservers1() {
        assertEquals(1, model.getObservers().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetObservers2() {
        model.getObservers().add(new TestReferenceObserver<>());
    }
}
