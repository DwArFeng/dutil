package com.dwarfeng.dutil.basic.sdk.reflect;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassUtilTest {

    @BeforeAll
    public static void setUpBeforeClass() {
    }

    @AfterAll
    public static void tearDownAfterClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
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
