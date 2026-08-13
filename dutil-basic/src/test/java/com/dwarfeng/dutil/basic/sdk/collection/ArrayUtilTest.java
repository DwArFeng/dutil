package com.dwarfeng.dutil.basic.sdk.collection;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link ArrayUtil} 的单元测试。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class ArrayUtilTest {

    @Test
    public void testReadOnlyArray() {
        String[] strs = new String[]{"1", "2", "3"};
        strs = ArrayUtil.readOnlyArray(strs, string -> string);
        assertArrayEquals(new String[]{"1", "2", "3"}, strs);
    }

    @Test
    public void testBounds() {
        Object[] objects = new Object[2];

        assertTrue(ArrayUtil.checkBounds(objects, 0));
        assertTrue(ArrayUtil.checkBounds(objects, 1));
        assertFalse(ArrayUtil.checkBounds(objects, -1));
        assertFalse(ArrayUtil.checkBounds(objects, 2));
        ArrayUtil.requireInBounds(objects, 0);
        ArrayUtil.requireInBounds(objects, 1, "ignored");
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.requireInBounds(objects, -1));
        IndexOutOfBoundsException exception = assertThrows(
                IndexOutOfBoundsException.class, () -> ArrayUtil.requireInBounds(objects, 2, "out of bounds")
        );
        assertEquals("out of bounds", exception.getMessage());
    }

    @Test
    public void testArrayIterableUsesIteratorContract() {
        Iterator<String> iterator = ArrayUtil.array2Iterable(new String[]{"a"}).iterator();

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
