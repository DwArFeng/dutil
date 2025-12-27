package com.dwarfeng.dutil.basic.cna;

import com.dwarfeng.dutil.basic.str.DefaultName;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttributeComplexTest {

    private static AttributeComplex attributeComplex;
    private static AttributeComplex anotherAttributeComplex;

    @BeforeClass
    public static void setUpBeforeClass() {
        anotherAttributeComplex = AttributeComplex
                .newInstance(new Object[]{"key.a", true, "key.b", false, "key.c", 12450});
    }

    @Before
    public void setUp() {
        attributeComplex = AttributeComplex
                .newInstance(new Object[]{"key.a", true, new DefaultName("key.b"), false, "key.c", 12450});
    }

    @After
    public void tearDown() {
        attributeComplex = null;
    }

    @Test
    public final void testHashCode() {
        assertEquals(attributeComplex.hashCode(), anotherAttributeComplex.hashCode());
    }

    @Test
    public final void testNewInstance() {
        assertEquals(true, attributeComplex.get("key.a"));
        assertEquals(false, attributeComplex.get("key.b"));
        assertEquals(12450, attributeComplex.get("key.c"));
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testNewInstanceException() {
        Object[] objects = new Object[]{"key.a", true, "key.b"};
        AttributeComplex.newInstance(objects);
        fail("没有抛出异常。");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testNewInstanceException1() {
        Object[] objects = new Object[]{null, true, "key.b", false};
        AttributeComplex.newInstance(objects);
        fail("没有抛出异常。");
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testNewInstanceException2() {
        Object[] objects = new Object[]{true, true, "key.b", false};
        AttributeComplex.newInstance(objects);
        fail("没有抛出异常。");
    }

    @Test(expected = NullPointerException.class)
    public final void testNewInstanceException3() {
        AttributeComplex.newInstance(null);
        fail("没有抛出异常。");
    }

    @Test
    public final void testSize() {
        assertEquals(3, attributeComplex.size());
    }

    @Test
    public final void testIsEmpty() {
        assertFalse(attributeComplex.isEmpty());
    }

    @Test
    public final void testContainsKeyString() {
        assertTrue(attributeComplex.containsKey("key.a"));
        assertTrue(attributeComplex.containsKey("key.b"));
        assertTrue(attributeComplex.containsKey("key.c"));
        assertFalse(attributeComplex.containsKey("key.d"));
    }

    @Test
    public final void testContainsKeyName() {
        assertTrue(attributeComplex.containsKey(new DefaultName("key.a")));
        assertTrue(attributeComplex.containsKey(new DefaultName("key.b")));
        assertTrue(attributeComplex.containsKey(new DefaultName("key.c")));
        assertFalse(attributeComplex.containsKey(new DefaultName("key.d")));
    }

    @Test
    public final void testGetString() {
        assertEquals(true, attributeComplex.get("key.a"));
        assertEquals(false, attributeComplex.get("key.b"));
        assertEquals(12450, attributeComplex.get("key.c"));
    }

    @Test
    public final void testGetName() {
        assertEquals(true, attributeComplex.get(new DefaultName("key.a")));
        assertEquals(false, attributeComplex.get(new DefaultName("key.b")));
        assertEquals(12450, attributeComplex.get(new DefaultName("key.c")));
    }

    @Test
    public final void testGetStringClassOfT() {
        assertEquals(true, attributeComplex.get("key.a", Boolean.class));
        assertEquals(false, attributeComplex.get("key.b", Boolean.class));
        assertEquals((Integer) 12450, attributeComplex.get("key.c", Integer.class));
    }

    @Test(expected = ClassCastException.class)
    public final void testGetStringClassOfTException() {
        assertEquals(true, attributeComplex.get("key.a", Integer.class));
        fail("没有抛出异常。");
    }

    @Test
    public final void testGetNameClassOfT() {
        assertEquals(true, attributeComplex.get(new DefaultName("key.a"), Boolean.class));
        assertEquals(false, attributeComplex.get(new DefaultName("key.b"), Boolean.class));
        assertEquals((Integer) 12450, attributeComplex.get(new DefaultName("key.c"), Integer.class));
    }

    @Test
    public final void testEqualsObject() {
        assertEquals(attributeComplex, attributeComplex);
        assertEquals(attributeComplex, anotherAttributeComplex);
        assertEquals(anotherAttributeComplex, attributeComplex);
    }
}
