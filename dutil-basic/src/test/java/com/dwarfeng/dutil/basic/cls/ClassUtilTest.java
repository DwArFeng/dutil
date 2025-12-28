package com.dwarfeng.dutil.basic.cls;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class ClassUtilTest {

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public final void testGetPackedClass() {
        assertEquals(Object.class, ClassUtil.getPackedClass(Object.class));
        assertEquals(Byte.class, ClassUtil.getPackedClass(byte.class));
        assertEquals(Short.class, ClassUtil.getPackedClass(short.class));
        assertEquals(Integer.class, ClassUtil.getPackedClass(int.class));
        assertEquals(Long.class, ClassUtil.getPackedClass(long.class));
        assertEquals(Float.class, ClassUtil.getPackedClass(float.class));
        assertEquals(Double.class, ClassUtil.getPackedClass(double.class));
        assertEquals(Character.class, ClassUtil.getPackedClass(char.class));
        assertEquals(Boolean.class, ClassUtil.getPackedClass(boolean.class));
        assertEquals(Void.class, ClassUtil.getPackedClass(void.class));
    }
}
