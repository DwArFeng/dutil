package com.dwarfeng.dutil.basic.cna;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;

public class CollectionUtilTest {

    @Test
    public void test() {
        ArrayList<String> stringList = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        Collection<String> stringCollection = CollectionUtil.readOnlyCollection(stringList, str -> str);

        assertArrayEquals(new String[]{"a", "b", "c", "d"}, stringCollection.toArray());
        assertArrayEquals(new String[]{"a", "b", "c", "d"}, stringCollection.toArray(new String[0]));
        assertArrayEquals(new String[]{"a", "b", "c", "d", null, null, null}, stringCollection.toArray(new String[7]));
    }
}
