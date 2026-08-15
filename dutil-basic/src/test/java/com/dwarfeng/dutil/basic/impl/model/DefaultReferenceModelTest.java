package com.dwarfeng.dutil.basic.impl.model;

import com.dwarfeng.dutil.basic.stack.model.ReferenceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultReferenceModelTest {

    private final ReferenceModel<String> model = new DefaultReferenceModel<>();
    private final ReferenceObserverFixture<String> observer = new ReferenceObserverFixture<>();

    @BeforeEach
    public void setUp() {
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

    @Test
    public void testGetObservers2() {
        assertThrows(UnsupportedOperationException.class, () -> model.getObservers().add(new ReferenceObserverFixture<>()));
    }
}
