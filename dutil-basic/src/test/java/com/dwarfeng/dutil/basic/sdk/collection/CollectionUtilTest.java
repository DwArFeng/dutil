package com.dwarfeng.dutil.basic.sdk.collection;

import com.dwarfeng.dutil.basic.stack.function.ReadOnlyGenerator;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.Map.Entry;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link CollectionUtil} 的单元测试。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class CollectionUtilTest {

    // region nonNullSet

    @Test
    public void testNonNullSetBasic() {
        Set<String> backingSet = new HashSet<>();
        Set<String> set = CollectionUtil.nonNullSet(backingSet);

        assertTrue(set.isEmpty());
        assertTrue(set.add("a"));
        assertTrue(set.add("b"));
        assertFalse(set.add("a"));
        assertEquals(2, set.size());
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
    }

    @Test
    public void testNonNullSetNullParameter() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.nonNullSet(null);
        });
    }

    @Test
    public void testNonNullSetNonEmptyParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            Set<String> set = new HashSet<>();
            set.add("a");
            CollectionUtil.nonNullSet(set);
        });
    }

    @Test
    public void testNonNullSetAddNull() {
        assertThrows(NullPointerException.class, () -> {
            Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
            set.add(null);
        });
    }

    @Test
    public void testNonNullSetAddAllWithNull() {
        assertThrows(NullPointerException.class, () -> {
            Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
            set.addAll(Arrays.asList("a", null, "b"));
        });
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testNonNullSetAddAllNullCollection() {
        assertThrows(NullPointerException.class, () -> {
            Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
            set.addAll(null);
        });
    }

    @Test
    public void testNonNullSetAddAllEmptyCollection() {
        Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
        set.add("a");
        assertFalse(set.addAll(Collections.emptyList()));
        assertEquals(1, set.size());
    }

    @Test
    public void testNonNullSetRemove() {
        Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
        set.add("a");
        set.add("b");
        assertTrue(set.remove("a"));
        assertFalse(set.contains("a"));
        assertEquals(1, set.size());
    }

    @Test
    public void testNonNullSetRemoveAll() {
        Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
        set.add("a");
        set.add("b");
        set.add("c");
        assertTrue(set.removeAll(Arrays.asList("a", "b")));
        assertEquals(1, set.size());
        assertTrue(set.contains("c"));
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void testNonNullSetRetainAll() {
        Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
        set.add("a");
        set.add("b");
        set.add("c");
        assertTrue(set.retainAll(Arrays.asList("a")));
        assertEquals(1, set.size());
        assertTrue(set.contains("a"));
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testNonNullSetClear() {
        Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
        set.add("a");
        set.add("b");
        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.size());
    }

    @Test
    public void testNonNullSetContainsAll() {
        Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
        set.add("a");
        set.add("b");
        set.add("c");
        assertTrue(set.containsAll(Arrays.asList("a", "b")));
        assertFalse(set.containsAll(Arrays.asList("a", "d")));
    }

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    @Test
    public void testNonNullSetToArray() {
        Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
        set.add("a");
        set.add("b");
        Object[] arr = set.toArray();
        assertEquals(2, arr.length);
        assertTrue(Arrays.asList(arr).contains("a"));
        assertTrue(Arrays.asList(arr).contains("b"));
        String[] strArr = set.toArray(new String[0]);
        assertEquals(2, strArr.length);
    }

    @SuppressWarnings("UseBulkOperation")
    @Test
    public void testNonNullSetIterator() {
        Set<String> set = CollectionUtil.nonNullSet(new HashSet<>());
        set.add("a");
        set.add("b");
        List<String> collected = new ArrayList<>();
        for (String s : set) {
            collected.add(s);
        }
        assertEquals(2, collected.size());
        assertTrue(collected.contains("a"));
        assertTrue(collected.contains("b"));
    }

    // endregion

    // region nonNullList

    @Test
    public void testNonNullListBasic() {
        List<String> backingList = new ArrayList<>();
        List<String> list = CollectionUtil.nonNullList(backingList);

        assertTrue(list.isEmpty());
        assertTrue(list.add("a"));
        assertTrue(list.add("b"));
        assertEquals(2, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
    }

    @Test
    public void testNonNullListNullParameter() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.nonNullList(null);
        });
    }

    @Test
    public void testNonNullListNonEmptyParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<String> list = new ArrayList<>();
            list.add("a");
            CollectionUtil.nonNullList(list);
        });
    }

    @Test
    public void testNonNullListAddNull() {
        assertThrows(NullPointerException.class, () -> {
            List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
            list.add(null);
        });
    }

    @Test
    public void testNonNullListSetNull() {
        assertThrows(NullPointerException.class, () -> {
            List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
            list.add("a");
            list.set(0, null);
        });
    }

    @Test
    public void testNonNullListSubList() {
        List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
        list.add("a");
        list.add("b");
        list.add("c");
        List<String> subList = list.subList(1, 3);
        assertEquals(2, subList.size());
        assertEquals("b", subList.get(0));
        assertEquals("c", subList.get(1));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testNonNullListAddAllNullCollection() {
        assertThrows(NullPointerException.class, () -> {
            List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
            list.addAll(null);
        });
    }

    @Test
    public void testNonNullListAddAllWithNull() {
        assertThrows(NullPointerException.class, () -> {
            List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
            list.addAll(Arrays.asList("a", null, "b"));
        });
    }

    @Test
    public void testNonNullListAddAllAtIndexWithNull() {
        assertThrows(NullPointerException.class, () -> {
            List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
            list.add("a");
            list.addAll(0, Arrays.asList("x", null));
        });
    }

    @Test
    public void testNonNullListAddAtIndexNull() {
        assertThrows(NullPointerException.class, () -> {
            List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
            list.add("a");
            list.addFirst(null);
        });
    }

    @Test
    public void testNonNullListRemove() {
        List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
        list.add("a");
        list.add("b");
        list.add("c");
        assertTrue(list.remove("a"));
        assertEquals(Arrays.asList("b", "c"), list);
        assertEquals("b", list.removeFirst());
        assertEquals(Collections.singletonList("c"), list);
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void testNonNullListRemoveAll() {
        List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
        list.add("a");
        list.add("b");
        list.add("c");
        assertTrue(list.removeAll(Arrays.asList("a")));
        assertEquals(Arrays.asList("b", "c"), list);
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void testNonNullListRetainAll() {
        List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
        list.add("a");
        list.add("b");
        list.add("c");
        assertTrue(list.retainAll(Arrays.asList("a")));
        assertEquals(Collections.singletonList("a"), list);
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testNonNullListClear() {
        List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
        list.add("a");
        list.add("b");
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testNonNullListContainsAll() {
        List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
        list.add("a");
        list.add("b");
        list.add("c");
        assertTrue(list.containsAll(Arrays.asList("a", "b")));
        assertFalse(list.containsAll(Arrays.asList("a", "d")));
    }

    @Test
    public void testNonNullListIndexOf() {
        List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
        list.add("a");
        list.add("b");
        list.add("b");
        list.add("c");
        assertEquals(1, list.indexOf("b"));
        assertEquals(2, list.lastIndexOf("b"));
    }

    @Test
    public void testNonNullListListIterator() {
        List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
        list.add("a");
        list.add("b");
        list.add("c");
        ListIterator<String> it = list.listIterator();
        assertEquals("a", it.next());
        assertEquals("b", it.next());
        ListIterator<String> it2 = list.listIterator(1);
        assertEquals("b", it2.next());
    }

    @Test
    public void testNonNullListToArray() {
        List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
        list.add("a");
        list.add("b");
        assertArrayEquals(new Object[]{"a", "b"}, list.toArray());
        assertArrayEquals(new String[]{"a", "b"}, list.toArray(new String[0]));
    }

    @SuppressWarnings("UseBulkOperation")
    @Test
    public void testNonNullListIterator() {
        List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
        list.add("a");
        list.add("b");
        List<String> collected = new ArrayList<>();
        for (String s : list) {
            collected.add(s);
        }
        assertEquals(Arrays.asList("a", "b"), collected);
    }

    @Test
    public void testNonNullListSubListAddNull() {
        assertThrows(NullPointerException.class, () -> {
            List<String> list = CollectionUtil.nonNullList(new ArrayList<>());
            list.add("a");
            list.add("b");
            list.add("c");
            List<String> subList = list.subList(1, 3);
            subList.add(null);
        });
    }

    // endregion

    // region nonNullMap

    @Test
    public void testNonNullMapBasic() {
        Map<String, Integer> backingMap = new HashMap<>();
        Map<String, Integer> map = CollectionUtil.nonNullMap(backingMap);

        assertTrue(map.isEmpty());
        assertNull(map.put("a", 1));
        assertEquals(Integer.valueOf(1), map.put("a", 2));
        assertEquals(1, map.size());
        assertEquals(Integer.valueOf(2), map.get("a"));
    }

    @Test
    public void testNonNullMapNullParameter() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.nonNullMap(null);
        });
    }

    @Test
    public void testNonNullMapNonEmptyParameter() {
        assertThrows(IllegalArgumentException.class, () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("a", 1);
            CollectionUtil.nonNullMap(map);
        });
    }

    @Test
    public void testNonNullMapPutNullKey() {
        assertThrows(NullPointerException.class, () -> {
            Map<String, Integer> map = CollectionUtil.nonNullMap(new HashMap<>());
            map.put(null, 1);
        });
    }

    @Test
    public void testNonNullMapPutAllWithNullKey() {
        assertThrows(NullPointerException.class, () -> {
            Map<String, Integer> map = CollectionUtil.nonNullMap(new HashMap<>());
            Map<String, Integer> other = new HashMap<>();
            other.put("a", 1);
            other.put(null, 2);
            map.putAll(other);
        });
    }

    @Test
    public void testNonNullMapPutAllValid() {
        Map<String, Integer> map = CollectionUtil.nonNullMap(new HashMap<>());
        Map<String, Integer> other = new HashMap<>();
        other.put("a", 1);
        other.put("b", 2);
        map.putAll(other);
        assertEquals(2, map.size());
        assertEquals(Integer.valueOf(1), map.get("a"));
        assertEquals(Integer.valueOf(2), map.get("b"));
    }

    @Test
    public void testNonNullMapContainsKey() {
        Map<String, Integer> map = CollectionUtil.nonNullMap(new HashMap<>());
        map.put("a", 1);
        assertTrue(map.containsKey("a"));
        assertFalse(map.containsKey("b"));
    }

    @Test
    public void testNonNullMapContainsValue() {
        Map<String, Integer> map = CollectionUtil.nonNullMap(new HashMap<>());
        map.put("a", 1);
        assertTrue(map.containsValue(1));
        assertFalse(map.containsValue(2));
    }

    @Test
    public void testNonNullMapRemove() {
        Map<String, Integer> map = CollectionUtil.nonNullMap(new HashMap<>());
        map.put("a", 1);
        map.put("b", 2);
        assertEquals(Integer.valueOf(1), map.remove("a"));
        assertEquals(1, map.size());
        assertNull(map.remove("c"));
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testNonNullMapClear() {
        Map<String, Integer> map = CollectionUtil.nonNullMap(new HashMap<>());
        map.put("a", 1);
        map.put("b", 2);
        map.clear();
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

    @SuppressWarnings("RedundantCollectionOperation")
    @Test
    public void testNonNullMapKeySetValuesEntrySet() {
        Map<String, Integer> map = CollectionUtil.nonNullMap(new HashMap<>());
        map.put("a", 1);
        map.put("b", 2);
        assertEquals(2, map.keySet().size());
        assertTrue(map.keySet().contains("a"));
        assertEquals(2, map.values().size());
        assertTrue(map.values().contains(1));
        assertEquals(2, map.entrySet().size());
    }

    // endregion

    // region containsNull

    @Test
    public void testContainsNull() {
        assertFalse(CollectionUtil.containsNull(Arrays.asList("a", "b", "c")));
        assertTrue(CollectionUtil.containsNull(Arrays.asList("a", null, "c")));
        assertTrue(CollectionUtil.containsNull(Collections.singletonList(null)));
        assertFalse(CollectionUtil.containsNull(Collections.emptyList()));
    }

    @Test
    public void testContainsNullNullParameter() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.containsNull(null);
        });
    }

    // endregion

    // region requireNotContainsNull

    @Test
    public void testRequireNotContainsNull() {
        CollectionUtil.requireNotContainsNull(Arrays.asList("a", "b", "c"));
        CollectionUtil.requireNotContainsNull(Collections.emptyList());
    }

    @Test
    public void testRequireNotContainsNullWithNull() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.requireNotContainsNull(Arrays.asList("a", null, "c"));
        });
    }

    @Test
    public void testRequireNotContainsNullNullParameter() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.requireNotContainsNull(null);
        });
    }

    @Test
    public void testRequireNotContainsNullWithMessage() {
        CollectionUtil.requireNotContainsNull(Arrays.asList("a", "b"), "message");
    }

    @Test
    public void testRequireNotContainsNullWithMessageAndNull() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.requireNotContainsNull(Arrays.asList("a", null), "custom message");
        });
    }

    @Test
    public void testRequireNotContainsNullMessagePropagation() {
        try {
            CollectionUtil.requireNotContainsNull(Arrays.asList("a", null), "custom message");
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            assertEquals("custom message", e.getMessage());
        }
    }

    // endregion

    // region enumeration2Iterator

    @Test
    public void testEnumeration2Iterator() {
        Vector<String> vector = new Vector<>(Arrays.asList("a", "b", "c"));
        Iterator<String> iterator = CollectionUtil.enumeration2Iterator(vector.elements());

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertEquals("c", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testEnumeration2IteratorNullParameter() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.enumeration2Iterator(null);
        });
    }

    // endregion

    // region iterator2Enumeration

    @Test
    public void testIterator2Enumeration() {
        List<String> list = Arrays.asList("a", "b", "c");
        Enumeration<String> enumeration = CollectionUtil.iterator2Enumeration(list.iterator());

        assertTrue(enumeration.hasMoreElements());
        assertEquals("a", enumeration.nextElement());
        assertEquals("b", enumeration.nextElement());
        assertEquals("c", enumeration.nextElement());
        assertFalse(enumeration.hasMoreElements());
    }

    @Test
    public void testIterator2EnumerationNullParameter() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.iterator2Enumeration(null);
        });
    }

    // endregion

    // region contactIterator

    @Test
    public void testContactIterator() {
        Iterator<String> first = Arrays.asList("a", "b").iterator();
        Iterator<String> second = Arrays.asList("c", "d").iterator();
        Iterator<String> joint = CollectionUtil.contactIterator(first, second);

        // 必须在使用 next() 前调用 hasNext()，以便在第一个迭代器耗尽时正确切换到第二个
        assertTrue(joint.hasNext());
        assertEquals("a", joint.next());
        assertTrue(joint.hasNext());
        assertEquals("b", joint.next());
        assertTrue(joint.hasNext());
        assertEquals("c", joint.next());
        assertTrue(joint.hasNext());
        assertEquals("d", joint.next());
        assertFalse(joint.hasNext());
    }

    @Test
    public void testContactIteratorFirstNull() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.contactIterator(null, Collections.singletonList("a").iterator());
        });
    }

    @Test
    public void testContactIteratorSecondNull() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.contactIterator(Collections.singletonList("a").iterator(), null);
        });
    }

    @SuppressWarnings("RedundantCollectionOperation")
    @Test
    public void testContactIteratorFirstEmpty() {
        Iterator<String> first = Collections.<String>emptyList().iterator();
        Iterator<String> second = Arrays.asList("c", "d").iterator();
        Iterator<String> joint = CollectionUtil.contactIterator(first, second);
        assertTrue(joint.hasNext());
        assertEquals("c", joint.next());
        assertEquals("d", joint.next());
        assertFalse(joint.hasNext());
    }

    @SuppressWarnings("RedundantCollectionOperation")
    @Test
    public void testContactIteratorSecondEmpty() {
        Iterator<String> first = Arrays.asList("a", "b").iterator();
        Iterator<String> second = Collections.<String>emptyList().iterator();
        Iterator<String> joint = CollectionUtil.contactIterator(first, second);
        assertTrue(joint.hasNext());
        assertEquals("a", joint.next());
        assertEquals("b", joint.next());
        assertFalse(joint.hasNext());
    }

    @SuppressWarnings("RedundantCollectionOperation")
    @Test
    public void testContactIteratorBothEmpty() {
        Iterator<String> first = Collections.<String>emptyList().iterator();
        Iterator<String> second = Collections.<String>emptyList().iterator();
        Iterator<String> joint = CollectionUtil.contactIterator(first, second);
        assertFalse(joint.hasNext());
    }

    // endregion

    // region insertByOrder

    @Test
    public void testInsertByOrder() {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 3, 5, 7));
        Comparator<Integer> comparator = Integer::compareTo;

        int index = CollectionUtil.insertByOrder(list, 4, comparator);
        assertEquals(2, index);
        assertEquals(Arrays.asList(1, 3, 4, 5, 7), list);

        index = CollectionUtil.insertByOrder(list, 0, comparator);
        assertEquals(0, index);
        assertEquals(Arrays.asList(0, 1, 3, 4, 5, 7), list);

        index = CollectionUtil.insertByOrder(list, 9, comparator);
        assertEquals(6, index);
        assertEquals(Arrays.asList(0, 1, 3, 4, 5, 7, 9), list);

        // 插入 4 到 [0, 1, 3, 4, 5, 7, 9]，第一个 >=4 的元素在索引 3，插入位置为 3
        index = CollectionUtil.insertByOrder(list, 4, comparator);
        assertEquals(3, index);
        assertEquals(Arrays.asList(0, 1, 3, 4, 4, 5, 7, 9), list);
    }

    @Test
    public void testInsertByOrderEmptyList() {
        List<Integer> list = new ArrayList<>();
        int index = CollectionUtil.insertByOrder(list, 5, Integer::compareTo);
        assertEquals(0, index);
        assertEquals(Collections.singletonList(5), list);
    }

    @Test
    public void testInsertByOrderWithSearcher() {
        List<Integer> list = new ArrayList<>(Arrays.asList(2, 4, 6));
        int index = CollectionUtil.insertByOrder(
                list, 5, Integer::compareTo, CollectionUtil.BINARY_ORDERED_INSERTION_SEARCHER
        );
        assertEquals(2, index);
        assertEquals(Arrays.asList(2, 4, 5, 6), list);
    }

    @Test
    public void testInsertByOrderNullList() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.insertByOrder(null, 1, Integer::compareTo);
        });
    }

    @Test
    public void testInsertByOrderNullComparator() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.insertByOrder(new ArrayList<>(), 1, null);
        });
    }

    @Test
    public void testInsertByOrderWithSequentialSearcher() {
        List<Integer> list = new LinkedList<>(Arrays.asList(2, 4, 6));
        int index = CollectionUtil.insertByOrder(
                list, 5, Integer::compareTo, CollectionUtil.SEQUENTIAL_ORDERED_INSERTION_SEARCHER
        );
        assertEquals(2, index);
        assertEquals(Arrays.asList(2, 4, 5, 6), list);
    }

    @Test
    public void testInsertByOrderWithAdaptiveSearcherArrayList() {
        List<Integer> list = new ArrayList<>(Arrays.asList(2, 4, 6));
        int index = CollectionUtil.insertByOrder(
                list, 5, Integer::compareTo, CollectionUtil.ADAPTIVE_ORDERED_INSERTION_SEARCHER
        );
        assertEquals(2, index);
        assertEquals(Arrays.asList(2, 4, 5, 6), list);
    }

    @Test
    public void testInsertByOrderWithAdaptiveSearcherLinkedList() {
        List<Integer> list = new LinkedList<>(Arrays.asList(2, 4, 6));
        int index = CollectionUtil.insertByOrder(
                list, 5, Integer::compareTo, CollectionUtil.ADAPTIVE_ORDERED_INSERTION_SEARCHER
        );
        assertEquals(2, index);
        assertEquals(Arrays.asList(2, 4, 5, 6), list);
    }

    @Test
    public void testInsertByOrderNullSearcher() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.insertByOrder(
                    new ArrayList<>(Arrays.asList(1, 2, 3)), 2, Integer::compareTo, null
            );
        });
    }

    @Test
    public void testInsertByOrderEdgeCases() {
        List<Integer> single = new ArrayList<>(Collections.singletonList(5));
        int idx = CollectionUtil.insertByOrder(single, 3, Integer::compareTo);
        assertEquals(0, idx);
        assertEquals(Arrays.asList(3, 5), single);

        idx = CollectionUtil.insertByOrder(single, 7, Integer::compareTo);
        assertEquals(2, idx);
        assertEquals(Arrays.asList(3, 5, 7), single);

        idx = CollectionUtil.insertByOrder(single, 5, Integer::compareTo);
        assertEquals(1, idx);
        assertEquals(Arrays.asList(3, 5, 5, 7), single);
    }

    // endregion

    // region unmodifiableIterator

    @Test
    public void testUnmodifiableIterator() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        Iterator<String> iterator = CollectionUtil.unmodifiableIterator(list.iterator());

        assertTrue(iterator.hasNext());
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertEquals("c", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testUnmodifiableIteratorRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Iterator<String> iterator = CollectionUtil.unmodifiableIterator(new ArrayList<>(Arrays.asList("a", "b"))
                    .iterator());
            iterator.next();
            iterator.remove();
        });
    }

    @Test
    public void testUnmodifiableIteratorNullParameter() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.unmodifiableIterator(null);
        });
    }

    // endregion

    // region readOnlyIterator

    @Test
    public void testReadOnlyIterator() {
        List<String> list = Arrays.asList("a", "b", "c");
        ReadOnlyGenerator<String> generator = String::toUpperCase;
        Iterator<String> iterator = CollectionUtil.readOnlyIterator(list.iterator(), generator);

        assertEquals("A", iterator.next());
        assertEquals("B", iterator.next());
        assertEquals("C", iterator.next());
    }

    @Test
    public void testReadOnlyIteratorNullIterator() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlyIterator(null, str -> str);
        });
    }

    @Test
    public void testReadOnlyIteratorRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Iterator<String> iterator = CollectionUtil.readOnlyIterator(
                    new ArrayList<>(Arrays.asList("a", "b")).iterator(), str -> str);
            iterator.next();
            iterator.remove();
        });
    }

    @Test
    public void testReadOnlyIteratorNullGenerator() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlyIterator(Collections.singletonList("a").iterator(), null);
        });
    }

    // endregion

    // region unmodifiableListIterator

    @Test
    public void testUnmodifiableListIterator() {
        List<String> list = Arrays.asList("a", "b", "c");
        ListIterator<String> listIterator = CollectionUtil.unmodifiableListIterator(list.listIterator());

        assertTrue(listIterator.hasNext());
        assertEquals("a", listIterator.next());
        assertEquals(1, listIterator.nextIndex());
    }

    @Test
    public void testUnmodifiableListIteratorRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            ListIterator<String> listIterator = CollectionUtil.unmodifiableListIterator(
                    new ArrayList<>(Arrays.asList("a", "b")).listIterator());
            listIterator.next();
            listIterator.remove();
        });
    }

    @Test
    public void testUnmodifiableListIteratorSet() {
        assertThrows(UnsupportedOperationException.class, () -> {
            ListIterator<String> listIterator = CollectionUtil.unmodifiableListIterator(
                    new ArrayList<>(Arrays.asList("a", "b")).listIterator());
            listIterator.next();
            listIterator.set("x");
        });
    }

    @Test
    public void testUnmodifiableListIteratorAdd() {
        assertThrows(UnsupportedOperationException.class, () -> {
            ListIterator<String> listIterator = CollectionUtil.unmodifiableListIterator(
                    new ArrayList<>(Arrays.asList("a", "b")).listIterator());
            listIterator.add("x");
        });
    }

    @Test
    public void testUnmodifiableListIteratorNullParameter() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.unmodifiableListIterator(null);
        });
    }

    @Test
    public void testUnmodifiableListIteratorPrevious() {
        List<String> list = Arrays.asList("a", "b", "c");
        ListIterator<String> listIterator = CollectionUtil.unmodifiableListIterator(list.listIterator());
        listIterator.next();
        listIterator.next();
        assertTrue(listIterator.hasPrevious());
        assertEquals(1, listIterator.previousIndex());
        assertEquals("b", listIterator.previous());
        assertEquals(0, listIterator.previousIndex());
        assertEquals("a", listIterator.previous());
    }

    // endregion

    // region readOnlyListIterator

    @Test
    public void testReadOnlyListIterator() {
        List<String> list = Arrays.asList("a", "b");
        ReadOnlyGenerator<String> generator = String::toUpperCase;
        ListIterator<String> listIterator = CollectionUtil.readOnlyListIterator(list.listIterator(), generator);

        assertEquals("A", listIterator.next());
        assertEquals("B", listIterator.next());
    }

    @Test
    public void testReadOnlyListIteratorRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            ListIterator<String> listIterator = CollectionUtil.readOnlyListIterator(
                    new ArrayList<>(Arrays.asList("a", "b")).listIterator(), str -> str);
            listIterator.next();
            listIterator.remove();
        });
    }

    @Test
    public void testReadOnlyListIteratorSet() {
        assertThrows(UnsupportedOperationException.class, () -> {
            ListIterator<String> listIterator = CollectionUtil.readOnlyListIterator(
                    new ArrayList<>(Arrays.asList("a", "b")).listIterator(), str -> str);
            listIterator.next();
            listIterator.set("x");
        });
    }

    @Test
    public void testReadOnlyListIteratorAdd() {
        assertThrows(UnsupportedOperationException.class, () -> {
            ListIterator<String> listIterator = CollectionUtil.readOnlyListIterator(
                    new ArrayList<>(Arrays.asList("a", "b")).listIterator(), str -> str);
            listIterator.add("x");
        });
    }

    @Test
    public void testReadOnlyListIteratorNullListIterator() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlyListIterator(null, str -> str);
        });
    }

    @Test
    public void testReadOnlyListIteratorNullGenerator() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlyListIterator(
                    new ArrayList<>(Arrays.asList("a", "b")).listIterator(), null);
        });
    }

    @Test
    public void testReadOnlyListIteratorPrevious() {
        List<String> list = Arrays.asList("a", "b", "c");
        ReadOnlyGenerator<String> generator = String::toUpperCase;
        ListIterator<String> listIterator = CollectionUtil.readOnlyListIterator(list.listIterator(), generator);
        listIterator.next();
        listIterator.next();
        assertTrue(listIterator.hasPrevious());
        assertEquals("B", listIterator.previous());
        assertEquals("A", listIterator.previous());
    }

    // endregion

    // region readOnlyCollection

    @Test
    public void testReadOnlyCollection() {
        ArrayList<String> stringList = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        Collection<String> stringCollection = CollectionUtil.readOnlyCollection(stringList, str -> str);

        assertArrayEquals(new String[]{"a", "b", "c", "d"}, stringCollection.toArray());
        assertArrayEquals(new String[]{"a", "b", "c", "d"}, stringCollection.toArray(new String[0]));
        assertArrayEquals(new String[]{"a", "b", "c", "d", null, null, null}, stringCollection.toArray(new String[7]));
        assertEquals(4, stringCollection.size());
        assertTrue(stringCollection.contains("a"));
    }

    @Test
    public void testReadOnlyCollectionAdd() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Collection<String> collection = CollectionUtil.readOnlyCollection(new ArrayList<>(), str -> str);
            collection.add("a");
        });
    }

    @Test
    public void testReadOnlyCollectionRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Collection<String> collection = CollectionUtil.readOnlyCollection(
                    new ArrayList<>(Collections.singletonList("a")), str -> str
            );
            collection.remove("a");
        });
    }

    @Test
    public void testReadOnlyCollectionAddAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Collection<String> collection = CollectionUtil.readOnlyCollection(new ArrayList<>(), str -> str);
            collection.addAll(Arrays.asList("a", "b"));
        });
    }

    @Test
    public void testReadOnlyCollectionRemoveAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Collection<String> collection = CollectionUtil.readOnlyCollection(
                    new ArrayList<>(Arrays.asList("a", "b")), str -> str);
            collection.removeAll(Collections.singleton("a"));
        });
    }

    @Test
    public void testReadOnlyCollectionRetainAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Collection<String> collection = CollectionUtil.readOnlyCollection(
                    new ArrayList<>(Arrays.asList("a", "b")), str -> str);
            collection.retainAll(Collections.singleton("a"));
        });
    }

    @Test
    public void testReadOnlyCollectionClear() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Collection<String> collection = CollectionUtil.readOnlyCollection(
                    new ArrayList<>(Arrays.asList("a", "b")), str -> str);
            collection.clear();
        });
    }

    @Test
    public void testReadOnlyCollectionNullCollection() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlyCollection(null, str -> str);
        });
    }

    @Test
    public void testReadOnlyCollectionNullGenerator() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlyCollection(new ArrayList<>(), null);
        });
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void testReadOnlyCollectionIsEmpty() {
        Collection<String> empty = CollectionUtil.readOnlyCollection(new ArrayList<>(), str -> str);
        assertTrue(empty.isEmpty());
        Collection<String> nonEmpty = CollectionUtil.readOnlyCollection(
                new ArrayList<>(Arrays.asList("a")), str -> str);
        assertFalse(nonEmpty.isEmpty());
    }

    @Test
    public void testReadOnlyCollectionContainsAll() {
        Collection<String> col = CollectionUtil.readOnlyCollection(
                new ArrayList<>(Arrays.asList("a", "b", "c")), str -> str);
        assertTrue(col.containsAll(Arrays.asList("a", "b")));
        assertFalse(col.containsAll(Arrays.asList("a", "d")));
    }

    @Test
    public void testReadOnlyCollectionIteratorWithGenerator() {
        Collection<String> col = CollectionUtil.readOnlyCollection(
                new ArrayList<>(Arrays.asList("a", "b")), String::toUpperCase);
        Iterator<String> it = col.iterator();
        assertEquals("A", it.next());
        assertEquals("B", it.next());
    }

    // endregion

    // region readOnlySet

    @Test
    public void testReadOnlySet() {
        Set<String> set = new HashSet<>(Arrays.asList("a", "b", "c"));
        Set<String> readOnlySet = CollectionUtil.readOnlySet(set, str -> str);

        assertEquals(3, readOnlySet.size());
        assertTrue(readOnlySet.contains("a"));
        assertTrue(readOnlySet.contains("b"));
        assertTrue(readOnlySet.contains("c"));
    }

    @Test
    public void testReadOnlySetAdd() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Set<String> set = CollectionUtil.readOnlySet(new HashSet<>(), str -> str);
            set.add("a");
        });
    }

    @Test
    public void testReadOnlySetRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Set<String> set = CollectionUtil.readOnlySet(new HashSet<>(Arrays.asList("a", "b")), str -> str);
            set.remove("a");
        });
    }

    @Test
    public void testReadOnlySetAddAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Set<String> set = CollectionUtil.readOnlySet(new HashSet<>(), str -> str);
            set.addAll(Arrays.asList("a", "b"));
        });
    }

    @Test
    public void testReadOnlySetRemoveAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Set<String> set = CollectionUtil.readOnlySet(new HashSet<>(Arrays.asList("a", "b")), str -> str);
            set.removeAll(Collections.singleton("a"));
        });
    }

    @Test
    public void testReadOnlySetRetainAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Set<String> set = CollectionUtil.readOnlySet(new HashSet<>(Arrays.asList("a", "b")), str -> str);
            set.retainAll(Collections.singleton("a"));
        });
    }

    @Test
    public void testReadOnlySetClear() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Set<String> set = CollectionUtil.readOnlySet(new HashSet<>(Arrays.asList("a", "b")), str -> str);
            set.clear();
        });
    }

    @Test
    public void testReadOnlySetNullSet() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlySet(null, str -> str);
        });
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void testReadOnlySetNullGenerator() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlySet(new HashSet<>(Arrays.asList("a")), null);
        });
    }

    @Test
    public void testReadOnlySetHashCode() {
        Set<String> delegate = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> readOnlySet = CollectionUtil.readOnlySet(delegate, str -> str);
        assertEquals(delegate.hashCode(), readOnlySet.hashCode());
    }

    @SuppressWarnings({"EqualsWithItself", "SimplifiableAssertion"})
    @Test
    public void testReadOnlySetEquals() {
        Set<String> delegate = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> readOnlySet = CollectionUtil.readOnlySet(delegate, str -> str);
        assertTrue(readOnlySet.equals(delegate));
        assertTrue(readOnlySet.equals(readOnlySet));
    }

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    @Test
    public void testReadOnlySetToArray() {
        Set<String> delegate = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> readOnlySet = CollectionUtil.readOnlySet(delegate, String::toUpperCase);
        Object[] arr = readOnlySet.toArray();
        assertEquals(2, arr.length);
        assertTrue(Arrays.asList(arr).contains("A"));
        assertTrue(Arrays.asList(arr).contains("B"));
        String[] strArr = readOnlySet.toArray(new String[0]);
        assertEquals(2, strArr.length);
    }

    @SuppressWarnings("UseBulkOperation")
    @Test
    public void testReadOnlySetIterator() {
        Set<String> delegate = new HashSet<>(Arrays.asList("a", "b"));
        Set<String> readOnlySet = CollectionUtil.readOnlySet(delegate, String::toUpperCase);
        List<String> collected = new ArrayList<>();
        for (String s : readOnlySet) {
            collected.add(s);
        }
        assertEquals(2, collected.size());
        assertTrue(collected.contains("A"));
        assertTrue(collected.contains("B"));
    }

    // endregion

    // region readOnlyList

    @Test
    public void testReadOnlyList() {
        List<String> list = Arrays.asList("a", "b", "c");
        List<String> readOnlyList = CollectionUtil.readOnlyList(new ArrayList<>(list), str -> str);

        assertEquals(3, readOnlyList.size());
        assertEquals("a", readOnlyList.get(0));
        assertEquals("b", readOnlyList.get(1));
    }

    @Test
    public void testReadOnlyListAdd() {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> list = CollectionUtil.readOnlyList(new ArrayList<>(), str -> str);
            list.add("a");
        });
    }

    @Test
    public void testReadOnlyListSet() {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> list = CollectionUtil.readOnlyList(new ArrayList<>(Collections.singletonList("a")), str -> str);
            list.set(0, "b");
        });
    }

    @Test
    public void testReadOnlyListRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> list = CollectionUtil.readOnlyList(new ArrayList<>(Arrays.asList("a", "b")), str -> str);
            list.remove("a");
        });
    }

    @Test
    public void testReadOnlyListRemoveByIndex() {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> list = CollectionUtil.readOnlyList(new ArrayList<>(Arrays.asList("a", "b")), str -> str);
            list.removeFirst();
        });
    }

    @Test
    public void testReadOnlyListAddAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> list = CollectionUtil.readOnlyList(new ArrayList<>(), str -> str);
            list.addAll(Arrays.asList("a", "b"));
        });
    }

    @Test
    public void testReadOnlyListAddAllAtIndex() {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> list = CollectionUtil.readOnlyList(new ArrayList<>(Collections.singletonList("a")), str -> str);
            list.addAll(0, Arrays.asList("x", "y"));
        });
    }

    @Test
    public void testReadOnlyListAddAtIndex() {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> list = CollectionUtil.readOnlyList(new ArrayList<>(Arrays.asList("a", "b")), str -> str);
            list.addFirst("x");
        });
    }

    @Test
    public void testReadOnlyListRemoveAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> list = CollectionUtil.readOnlyList(new ArrayList<>(Arrays.asList("a", "b")), str -> str);
            list.removeAll(Collections.singleton("a"));
        });
    }

    @Test
    public void testReadOnlyListRetainAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> list = CollectionUtil.readOnlyList(new ArrayList<>(Arrays.asList("a", "b")), str -> str);
            list.retainAll(Collections.singleton("a"));
        });
    }

    @Test
    public void testReadOnlyListClear() {
        assertThrows(UnsupportedOperationException.class, () -> {
            List<String> list = CollectionUtil.readOnlyList(new ArrayList<>(Arrays.asList("a", "b")), str -> str);
            list.clear();
        });
    }

    @Test
    public void testReadOnlyListNullList() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlyList(null, str -> str);
        });
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void testReadOnlyListNullGenerator() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlyList(new ArrayList<>(Arrays.asList("a")), null);
        });
    }

    @Test
    public void testReadOnlyListSubList() {
        List<String> list = CollectionUtil.readOnlyList(
                new ArrayList<>(Arrays.asList("a", "b", "c", "d")), String::toUpperCase);
        List<String> subList = list.subList(1, 3);
        assertEquals(2, subList.size());
        assertEquals("B", subList.get(0));
        assertEquals("C", subList.get(1));
    }

    @Test
    public void testReadOnlyListListIterator() {
        List<String> list = CollectionUtil.readOnlyList(
                new ArrayList<>(Arrays.asList("a", "b", "c")), str -> str);
        ListIterator<String> it = list.listIterator();
        assertEquals("a", it.next());
        assertEquals("b", it.next());
        ListIterator<String> it2 = list.listIterator(1);
        assertEquals("b", it2.next());
    }

    @Test
    public void testReadOnlyListIndexOf() {
        List<String> list = CollectionUtil.readOnlyList(
                new ArrayList<>(Arrays.asList("a", "b", "c")), str -> str);
        assertEquals(0, list.indexOf("a"));
        assertEquals(2, list.indexOf("c"));
    }

    @Test
    public void testReadOnlyListLastIndexOf() {
        List<String> list = CollectionUtil.readOnlyList(
                new ArrayList<>(Arrays.asList("a", "b", "b", "c")), str -> str);
        assertEquals(2, list.lastIndexOf("b"));
        assertEquals(0, list.lastIndexOf("a"));
    }

    @Test
    public void testReadOnlyListHashCode() {
        List<String> delegate = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> readOnlyList = CollectionUtil.readOnlyList(delegate, str -> str);
        assertEquals(delegate.hashCode(), readOnlyList.hashCode());
    }

    @SuppressWarnings({"EqualsWithItself", "SimplifiableAssertion"})
    @Test
    public void testReadOnlyListEquals() {
        List<String> delegate = new ArrayList<>(Arrays.asList("a", "b"));
        List<String> readOnlyList = CollectionUtil.readOnlyList(delegate, str -> str);
        assertTrue(readOnlyList.equals(delegate));
        assertTrue(readOnlyList.equals(readOnlyList));
    }

    // endregion

    // region readOnlyMapEntry

    @Test
    public void testReadOnlyMapEntry() {
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 1);
        Entry<String, Integer> entry = map.entrySet().iterator().next();
        Entry<String, Integer> readOnlyEntry = CollectionUtil.readOnlyMapEntry(entry, String::toUpperCase, v -> v + 10);

        assertEquals("KEY", readOnlyEntry.getKey());
        assertEquals(Integer.valueOf(11), readOnlyEntry.getValue());
    }

    @Test
    public void testReadOnlyMapEntrySetValue() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("key", 1);
            Entry<String, Integer> entry = map.entrySet().iterator().next();
            Entry<String, Integer> readOnlyEntry = CollectionUtil.readOnlyMapEntry(entry, k -> k, v -> v);
            readOnlyEntry.setValue(2);
        });
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    @Test
    public void testReadOnlyMapEntryNullEntry() {
        assertThrows(NullPointerException.class, () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("k", 1);
            CollectionUtil.readOnlyMapEntry(null, k -> k, v -> v);
        });
    }

    @Test
    public void testReadOnlyMapEntryNullKeyGen() {
        assertThrows(NullPointerException.class, () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("k", 1);
            Entry<String, Integer> entry = map.entrySet().iterator().next();
            CollectionUtil.readOnlyMapEntry(entry, null, v -> v);
        });
    }

    @Test
    public void testReadOnlyMapEntryNullValueGen() {
        assertThrows(NullPointerException.class, () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("k", 1);
            Entry<String, Integer> entry = map.entrySet().iterator().next();
            CollectionUtil.readOnlyMapEntry(entry, k -> k, null);
        });
    }

    @Test
    public void testReadOnlyMapEntryHashCode() {
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 1);
        Entry<String, Integer> entry = map.entrySet().iterator().next();
        Entry<String, Integer> readOnlyEntry = CollectionUtil.readOnlyMapEntry(entry, k -> k, v -> v);
        assertEquals(entry.hashCode(), readOnlyEntry.hashCode());
    }

    @SuppressWarnings({"EqualsWithItself", "SimplifiableAssertion"})
    @Test
    public void testReadOnlyMapEntryEquals() {
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 1);
        Entry<String, Integer> entry = map.entrySet().iterator().next();
        Entry<String, Integer> readOnlyEntry = CollectionUtil.readOnlyMapEntry(entry, k -> k, v -> v);
        assertTrue(readOnlyEntry.equals(entry));
        assertTrue(readOnlyEntry.equals(readOnlyEntry));
    }

    // endregion

    // region readOnlyMap

    @Test
    public void testReadOnlyMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Map<String, Integer> readOnlyMap = CollectionUtil.readOnlyMap(map, String::toUpperCase, v -> v * 10);

        assertEquals(2, readOnlyMap.size());
        assertEquals(Integer.valueOf(10), readOnlyMap.get("a"));
        assertEquals(Integer.valueOf(20), readOnlyMap.get("b"));
    }

    @Test
    public void testReadOnlyMapPut() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Map<String, Integer> map = CollectionUtil.readOnlyMap(new HashMap<>(), k -> k, v -> v);
            map.put("a", 1);
        });
    }

    @Test
    public void testReadOnlyMapRemove() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Map<String, Integer> backing = new HashMap<>();
            backing.put("a", 1);
            Map<String, Integer> map = CollectionUtil.readOnlyMap(backing, k -> k, v -> v);
            map.remove("a");
        });
    }

    @Test
    public void testReadOnlyMapPutAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Map<String, Integer> map = CollectionUtil.readOnlyMap(new HashMap<>(), k -> k, v -> v);
            Map<String, Integer> other = new HashMap<>();
            other.put("a", 1);
            other.put("b", 2);
            map.putAll(other);
        });
    }

    @Test
    public void testReadOnlyMapClear() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Map<String, Integer> backing = new HashMap<>();
            backing.put("a", 1);
            Map<String, Integer> map = CollectionUtil.readOnlyMap(backing, k -> k, v -> v);
            map.clear();
        });
    }

    @Test
    public void testReadOnlyMapNullMap() {
        assertThrows(NullPointerException.class, () -> {
            CollectionUtil.readOnlyMap(null, k -> k, v -> v);
        });
    }

    @Test
    public void testReadOnlyMapNullKeyGen() {
        assertThrows(NullPointerException.class, () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("a", 1);
            CollectionUtil.readOnlyMap(map, null, v -> v);
        });
    }

    @Test
    public void testReadOnlyMapNullValueGen() {
        assertThrows(NullPointerException.class, () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("a", 1);
            CollectionUtil.readOnlyMap(map, k -> k, null);
        });
    }

    @SuppressWarnings("UseBulkOperation")
    @Test
    public void testReadOnlyMapKeySet() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Map<String, Integer> readOnlyMap = CollectionUtil.readOnlyMap(map, String::toUpperCase, v -> v);
        Set<String> keySet = readOnlyMap.keySet();
        assertEquals(2, keySet.size());
        List<String> keys = new ArrayList<>();
        for (String k : keySet) {
            keys.add(k);
        }
        assertTrue(keys.contains("A"));
        assertTrue(keys.contains("B"));
    }

    @SuppressWarnings("UseBulkOperation")
    @Test
    public void testReadOnlyMapValues() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Map<String, Integer> readOnlyMap = CollectionUtil.readOnlyMap(map, k -> k, v -> v * 10);
        Collection<Integer> values = readOnlyMap.values();
        assertEquals(2, values.size());
        List<Integer> valueList = new ArrayList<>();
        for (Integer v : values) {
            valueList.add(v);
        }
        assertTrue(valueList.contains(10));
        assertTrue(valueList.contains(20));
    }

    @Test
    public void testReadOnlyMapEntrySet() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Map<String, Integer> readOnlyMap = CollectionUtil.readOnlyMap(
                map, String::toUpperCase, v -> v * 10);
        Set<Entry<String, Integer>> entrySet = readOnlyMap.entrySet();
        assertEquals(2, entrySet.size());
        for (Entry<String, Integer> e : entrySet) {
            assertTrue(e.getKey().equals("A") || e.getKey().equals("B"));
            assertTrue(e.getValue() == 10 || e.getValue() == 20);
        }
    }

    @Test
    public void testReadOnlyMapContainsKey() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        Map<String, Integer> readOnlyMap = CollectionUtil.readOnlyMap(map, k -> k, v -> v);
        assertTrue(readOnlyMap.containsKey("a"));
        assertFalse(readOnlyMap.containsKey("b"));
    }

    @Test
    public void testReadOnlyMapContainsValue() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        Map<String, Integer> readOnlyMap = CollectionUtil.readOnlyMap(map, k -> k, v -> v);
        assertTrue(readOnlyMap.containsValue(1));
        assertFalse(readOnlyMap.containsValue(2));
    }

    @Test
    public void testReadOnlyMapIsEmpty() {
        Map<String, Integer> emptyMap = new HashMap<>();
        Map<String, Integer> readOnlyEmpty = CollectionUtil.readOnlyMap(emptyMap, k -> k, v -> v);
        assertTrue(readOnlyEmpty.isEmpty());
        Map<String, Integer> nonEmpty = new HashMap<>();
        nonEmpty.put("a", 1);
        Map<String, Integer> readOnlyNonEmpty = CollectionUtil.readOnlyMap(nonEmpty, k -> k, v -> v);
        assertFalse(readOnlyNonEmpty.isEmpty());
    }

    @Test
    public void testReadOnlyMapHashCode() {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("a", 1);
        delegate.put("b", 2);
        Map<String, Integer> readOnlyMap = CollectionUtil.readOnlyMap(delegate, k -> k, v -> v);
        assertEquals(delegate.hashCode(), readOnlyMap.hashCode());
    }

    @SuppressWarnings({"EqualsWithItself", "SimplifiableAssertion"})
    @Test
    public void testReadOnlyMapEquals() {
        Map<String, Integer> delegate = new HashMap<>();
        delegate.put("a", 1);
        delegate.put("b", 2);
        Map<String, Integer> readOnlyMap = CollectionUtil.readOnlyMap(delegate, k -> k, v -> v);
        assertTrue(readOnlyMap.equals(delegate));
        assertTrue(readOnlyMap.equals(readOnlyMap));
    }

    // endregion
}
