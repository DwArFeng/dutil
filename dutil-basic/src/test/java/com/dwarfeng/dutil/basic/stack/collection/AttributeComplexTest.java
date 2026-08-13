package com.dwarfeng.dutil.basic.stack.collection;

import com.dwarfeng.dutil.base.sdk.i18n.MessageContext;
import com.dwarfeng.dutil.basic.impl.string.DefaultName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class AttributeComplexTest {

    private static AttributeComplex attributeComplex;
    private static AttributeComplex anotherAttributeComplex;

    @BeforeAll
    public static void setUpBeforeClass() {
        anotherAttributeComplex = AttributeComplex
                .newInstance(new Object[]{"key.a", true, "key.b", false, "key.c", 12450});
    }

    @BeforeEach
    public void setUp() {
        attributeComplex = AttributeComplex
                .newInstance(new Object[]{"key.a", true, new DefaultName("key.b"), false, "key.c", 12450});
    }

    @AfterEach
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

    @Test
    public final void testNewInstanceException() {
        IllegalArgumentException exception = MessageContext.call(
                Locale.SIMPLIFIED_CHINESE,
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> AttributeComplex.newInstance(new Object[]{"key.a", true, "key.b"})
                )
        );

        assertEquals("数组 \"objects\" 中的元素个数必须是偶数。", exception.getMessage());
    }

    @Test
    public final void testNewInstanceException1() {
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] objects = new Object[]{null, true, "key.b", false};
            AttributeComplex.newInstance(objects);
            fail("没有抛出异常。");
        });
    }

    @Test
    public final void testNewInstanceException2() {
        assertThrows(IllegalArgumentException.class, () -> {
            Object[] objects = new Object[]{true, true, "key.b", false};
            AttributeComplex.newInstance(objects);
            fail("没有抛出异常。");
        });
    }

    @Test
    public final void testNewInstanceException3() {
        assertThrows(NullPointerException.class, () -> {
            AttributeComplex.newInstance(null);
            fail("没有抛出异常。");
        });
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

    // 由于该测试本身就是测试类型转换异常，因此忽略相关警告。
    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    @Test
    public final void testGetStringClassOfTException() {
        assertThrows(ClassCastException.class, () -> {
            assertEquals(true, attributeComplex.get("key.a", Integer.class));
            fail("没有抛出异常。");
        });
    }

    @Test
    public final void testGetNameClassOfT() {
        assertEquals(true, attributeComplex.get(new DefaultName("key.a"), Boolean.class));
        assertEquals(false, attributeComplex.get(new DefaultName("key.b"), Boolean.class));
        assertEquals((Integer) 12450, attributeComplex.get(new DefaultName("key.c"), Integer.class));
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public final void testEqualsObject() {
        assertEquals(attributeComplex, attributeComplex);
        assertEquals(attributeComplex, anotherAttributeComplex);
        assertEquals(anotherAttributeComplex, attributeComplex);
    }
}
