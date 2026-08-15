package com.dwarfeng.dutil.basic.impl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DelegateMapModelTest {

    private final DelegateMapModel<String, String> model = new DelegateMapModel<>();
    private final MapObserverFixture obv = new MapObserverFixture();

    @BeforeEach
    public void setUp() {
        model.clearObserver();
        model.clear();
        model.put("A", "1");
        model.put("B", "2");
        model.put("C", "3");
        model.put("D", "4");
        model.put("E", "5");
        obv.reset();
        model.addObserver(obv);
    }

    @Test
    public void testSize() {
        assertEquals(5, model.size());
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testIsEmpty() {
        assertFalse(model.isEmpty());
        model.clear();
        assertTrue(model.isEmpty());
    }

    @Test
    public void testContainsKey() {
        assertTrue(model.containsKey("A"));
        assertTrue(model.containsKey("B"));
        assertTrue(model.containsKey("C"));
        assertTrue(model.containsKey("D"));
        assertTrue(model.containsKey("E"));
        assertFalse(model.containsKey("1"));

    }

    @Test
    public void testContainsValue() {
        assertTrue(model.containsValue("1"));
        assertTrue(model.containsValue("2"));
        assertTrue(model.containsValue("3"));
        assertTrue(model.containsValue("4"));
        assertTrue(model.containsValue("5"));
        assertFalse(model.containsValue("A"));
    }

    @Test
    public void testGet() {
        assertEquals("1", model.get("A"));
        assertEquals("2", model.get("B"));
        assertEquals("3", model.get("C"));
        assertEquals("4", model.get("D"));
        assertEquals("5", model.get("E"));
        assertNull(model.get("F"));

    }

    @Test
    public void testPut() {
        assertNull(model.put("F", "6"));
        assertEquals("6", model.get("F"));
        assertEquals("F", obv.putKeyList.getFirst());
        assertEquals("6", obv.putValueList.getFirst());
        assertEquals("1", model.put("A", "7"));
        assertEquals("A", obv.changedKeyList.getFirst());
        assertEquals("1", obv.changedOldValueList.getFirst());
        assertEquals("7", obv.changedNewValueList.getFirst());
    }

    @Test
    public void testRemove() {
        assertNull(model.remove("1"));
        assertEquals("2", model.remove("B"));
        assertEquals("B", obv.removeKeyList.getFirst());
        assertEquals("2", obv.removeValueList.getFirst());
    }

    @Test
    public void testPutAll() {
        Map<String, String> m = new HashMap<>();
        m.put("A", "1");
        m.put("B", "6");
        m.put("F", "7");
        model.putAll(m);
        assertEquals("A", obv.changedKeyList.getFirst());
        assertEquals("1", obv.changedOldValueList.getFirst());
        assertEquals("1", obv.changedNewValueList.get(0));
        assertEquals("B", obv.changedKeyList.get(1));
        assertEquals("6", obv.changedNewValueList.get(1));
        assertEquals("2", obv.changedOldValueList.get(1));
        assertEquals("F", obv.putKeyList.getFirst());
        assertEquals("7", obv.putValueList.getFirst());
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testClear() {
        model.clear();
        assertTrue(model.isEmpty());
        assertEquals(0, model.size());
        assertEquals(1, obv.cleared);
    }

    @Test
    public void testGetObservers() {
        assertEquals(1, model.getObservers().size());
        assertTrue(model.getObservers().contains(obv));
    }

    @Test
    public void testRemoveObserver() {
        assertTrue(model.removeObserver(obv));
        assertEquals(0, model.getObservers().size());
    }

    @Test
    public void testHashCode() {
        Map<String, String> map = new HashMap<>();
        map.put("A", "1");
        map.put("B", "2");
        map.put("C", "3");
        map.put("D", "4");
        map.put("E", "5");
        assertEquals(model.hashCode(), map.hashCode());
    }

    @Test
    public void testEquals() {
        Map<String, String> map = new HashMap<>();
        map.put("A", "1");
        map.put("B", "2");
        map.put("C", "3");
        map.put("D", "4");
        map.put("E", "5");
        assertEquals(map, model);
        assertEquals(model, map);
    }

    @TestFactory
    public Stream<DynamicTest> testEntrySetView() {
        return Stream.of(
                entrySetTest("entrySetView.testHashCode", EntrySetViewFixture::verifyHashCode),
                entrySetTest("entrySetView.testSize", EntrySetViewFixture::verifySize),
                entrySetTest("entrySetView.testIsEmpty", EntrySetViewFixture::verifyIsEmpty),
                entrySetTest("entrySetView.testContains", EntrySetViewFixture::verifyContains),
                entrySetTest("entrySetView.testIterator", EntrySetViewFixture::verifyIterator),
                entrySetTest("entrySetView.testToArray", EntrySetViewFixture::verifyToArray),
                entrySetTest("entrySetView.testToArrayTArray", EntrySetViewFixture::verifyToArrayTArray),
                entrySetTest("entrySetView.testAdd", EntrySetViewFixture::verifyAdd),
                entrySetTest("entrySetView.testRemove", EntrySetViewFixture::verifyRemove),
                entrySetTest("entrySetView.testContainsAll", EntrySetViewFixture::verifyContainsAll),
                entrySetTest("entrySetView.testAddAll", EntrySetViewFixture::verifyAddAll),
                entrySetTest("entrySetView.testRemoveAll", EntrySetViewFixture::verifyRemoveAll),
                entrySetTest("entrySetView.testRetainAll", EntrySetViewFixture::verifyRetainAll),
                entrySetTest("entrySetView.testClear", EntrySetViewFixture::verifyClear),
                entrySetTest("entrySetView.testEqualsObject", EntrySetViewFixture::verifyEqualsObject)
        );
    }

    @TestFactory
    public Stream<DynamicTest> testKeySetView() {
        return Stream.of(
                keySetTest("keySetView.testSize", KeySetViewFixture::verifySize),
                keySetTest("keySetView.testIsEmpty", KeySetViewFixture::verifyIsEmpty),
                keySetTest("keySetView.testContains", KeySetViewFixture::verifyContains),
                keySetTest("keySetView.testIterator", KeySetViewFixture::verifyIterator),
                keySetTest("keySetView.testToArray", KeySetViewFixture::verifyToArray),
                keySetTest("keySetView.testToArrayTArray", KeySetViewFixture::verifyToArrayTArray),
                keySetTest("keySetView.testAdd", KeySetViewFixture::verifyAdd),
                keySetTest("keySetView.testRemove", KeySetViewFixture::verifyRemove),
                keySetTest("keySetView.testContainsAll", KeySetViewFixture::verifyContainsAll),
                keySetTest("keySetView.testAddAll", KeySetViewFixture::verifyAddAll),
                keySetTest("keySetView.testRemoveAll", KeySetViewFixture::verifyRemoveAll),
                keySetTest("keySetView.testRetainAll", KeySetViewFixture::verifyRetainAll),
                keySetTest("keySetView.testClear", KeySetViewFixture::verifyClear),
                keySetTest("keySetView.testEqualsObject", KeySetViewFixture::verifyEqualsObject)
        );
    }

    @TestFactory
    public Stream<DynamicTest> testValuesView() {
        return Stream.of(
                valuesTest("valuesView.testSize", ValuesViewFixture::verifySize),
                valuesTest("valuesView.testIsEmpty", ValuesViewFixture::verifyIsEmpty),
                valuesTest("valuesView.testContains", ValuesViewFixture::verifyContains),
                valuesTest("valuesView.testIterator", ValuesViewFixture::verifyIterator),
                valuesTest("valuesView.testToArray", ValuesViewFixture::verifyToArray),
                valuesTest("valuesView.testToArrayTArray", ValuesViewFixture::verifyToArrayTArray),
                valuesTest("valuesView.testAdd", ValuesViewFixture::verifyAdd),
                valuesTest("valuesView.testRemove", ValuesViewFixture::verifyRemove),
                valuesTest("valuesView.testContainsAll", ValuesViewFixture::verifyContainsAll),
                valuesTest("valuesView.testAddAll", ValuesViewFixture::verifyAddAll),
                valuesTest("valuesView.testRemoveAll", ValuesViewFixture::verifyRemoveAll),
                valuesTest("valuesView.testRetainAll", ValuesViewFixture::verifyRetainAll),
                valuesTest("valuesView.testClear", ValuesViewFixture::verifyClear)
        );
    }

    private DynamicTest entrySetTest(String name, ViewTest<EntrySetViewFixture> test) {
        return DynamicTest.dynamicTest(name, () -> {
            EntrySetViewFixture view = new EntrySetViewFixture();
            view.prepare();
            test.run(view);
        });
    }

    private DynamicTest keySetTest(String name, ViewTest<KeySetViewFixture> test) {
        return DynamicTest.dynamicTest(name, () -> {
            KeySetViewFixture view = new KeySetViewFixture();
            view.prepare();
            test.run(view);
        });
    }

    private DynamicTest valuesTest(String name, ViewTest<ValuesViewFixture> test) {
        return DynamicTest.dynamicTest(name, () -> {
            ValuesViewFixture view = new ValuesViewFixture();
            view.prepare();
            test.run(view);
        });
    }

    @FunctionalInterface
    private interface ViewTest<T> {

        void run(T view);
    }

    private static final class EntrySetViewFixture {

        private final DelegateMapModel<String, String> viewModel = new DelegateMapModel<>(new HashMap<>(),
                Collections.newSetFromMap(new WeakHashMap<>()));
        private final MapObserverFixture viewObserver = new MapObserverFixture();
        private Set<Map.Entry<String, String>> entrySet;

        void prepare() {
            viewModel.clearObserver();
            viewModel.clear();
            viewModel.put("A", "1");
            viewModel.put("B", "2");
            viewModel.put("C", "3");
            viewModel.put("D", "4");
            viewModel.put("E", "5");
            viewObserver.reset();
            viewModel.addObserver(viewObserver);
            entrySet = viewModel.entrySet();
        }

        void verifyHashCode() {
            Set<Map.Entry<String, String>> set = new HashSet<>(viewModel.entrySet());
            assertEquals(set.hashCode(), entrySet.hashCode());
        }

        @SuppressWarnings("ConstantValue")
        void verifySize() {
            assertEquals(5, entrySet.size());
            entrySet.clear();
            assertEquals(0, entrySet.size());
        }

        @SuppressWarnings("ConstantValue")
        void verifyIsEmpty() {
            assertFalse(entrySet.isEmpty());
            entrySet.clear();
            assertTrue(entrySet.isEmpty());
        }

        void verifyContains() {
            Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
            assertTrue(entrySet.contains(entry1));
            Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "1");
            assertFalse(entrySet.contains(entry2));
        }

        void verifyIterator() {
            Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
            Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
            assertEquals(entry1, iterator.next());
            iterator.remove();
            assertEquals("A", viewObserver.removeKeyList.getFirst());
            assertEquals("1", viewObserver.removeValueList.getFirst());
            Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
            assertEquals(entry2, iterator.next());
            Map.Entry<String, String> entry3 = iterator.next();
            entry3.setValue("0");
            assertEquals("C", viewObserver.changedKeyList.getFirst());
            assertEquals("3", viewObserver.changedOldValueList.getFirst());
            assertEquals("0", viewObserver.changedNewValueList.getFirst());
            iterator.next();
            iterator.next();
            assertFalse(iterator.hasNext());
            assertEquals("0", viewModel.get("C"));
        }

        void verifyToArray() {
            Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
            Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
            Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("C", "3");
            Map.Entry<String, String> entry4 = new AbstractMap.SimpleEntry<>("D", "4");
            Map.Entry<String, String> entry5 = new AbstractMap.SimpleEntry<>("E", "5");
            assertArrayEquals(new Object[]{entry1, entry2, entry3, entry4, entry5}, entrySet.toArray());
        }

        void verifyToArrayTArray() {
            Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
            Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
            Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("C", "3");
            Map.Entry<String, String> entry4 = new AbstractMap.SimpleEntry<>("D", "4");
            Map.Entry<String, String> entry5 = new AbstractMap.SimpleEntry<>("E", "5");
            assertArrayEquals(new Object[]{entry1, entry2, entry3, entry4, entry5}, entrySet.toArray(new Map.Entry[0]));
        }

        void verifyAdd() {
            assertThrows(UnsupportedOperationException.class, () -> entrySet.add(null));
        }

        void verifyRemove() {
            Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
            assertTrue(entrySet.remove(entry1));
            assertFalse(viewModel.containsKey("A"));
            assertEquals("A", viewObserver.removeKeyList.getFirst());
            assertEquals("1", viewObserver.removeValueList.getFirst());
        }

        void verifyContainsAll() {
            Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
            Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
            Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("C", "3");
            Map.Entry<String, String> entry4 = new AbstractMap.SimpleEntry<>("D", "4");
            Map.Entry<String, String> entry5 = new AbstractMap.SimpleEntry<>("E", "5");
            Map.Entry<String, String> entry6 = new AbstractMap.SimpleEntry<>("F", "6");
            assertTrue(entrySet.containsAll(Arrays.asList(entry1, entry2, entry3, entry4, entry5)));
            assertFalse(entrySet.containsAll(Arrays.asList(entry1, entry2, entry3, entry4, entry5, entry6)));
        }

        @SuppressWarnings("DataFlowIssue")
        void verifyAddAll() {
            assertThrows(UnsupportedOperationException.class, () -> entrySet.addAll(null));
        }

        @SuppressWarnings("SlowAbstractSetRemoveAll")
        void verifyRemoveAll() {
            Map.Entry<String, String> entry1 = new AbstractMap.SimpleEntry<>("A", "1");
            Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
            Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("C", "3");
            Map.Entry<String, String> entry4 = new AbstractMap.SimpleEntry<>("D", "4");
            Map.Entry<String, String> entry5 = new AbstractMap.SimpleEntry<>("E", "5");
            assertTrue(entrySet.removeAll(Arrays.asList(entry2, entry3, entry4)));
            assertEquals(2, viewModel.size());
            assertArrayEquals(new Object[]{entry1, entry5}, entrySet.toArray());
            assertEquals("B", viewObserver.removeKeyList.get(0));
            assertEquals("C", viewObserver.removeKeyList.get(1));
            assertEquals("D", viewObserver.removeKeyList.get(2));
            assertEquals("2", viewObserver.removeValueList.get(0));
            assertEquals("3", viewObserver.removeValueList.get(1));
            assertEquals("4", viewObserver.removeValueList.get(2));
        }

        void verifyRetainAll() {
            Map.Entry<String, String> entry2 = new AbstractMap.SimpleEntry<>("B", "2");
            Map.Entry<String, String> entry3 = new AbstractMap.SimpleEntry<>("C", "3");
            Map.Entry<String, String> entry4 = new AbstractMap.SimpleEntry<>("D", "4");
            assertTrue(entrySet.retainAll(Arrays.asList(entry2, entry3, entry4)));
            assertEquals(3, viewModel.size());
            assertArrayEquals(new Object[]{entry2, entry3, entry4}, entrySet.toArray());
            assertEquals("A", viewObserver.removeKeyList.get(0));
            assertEquals("E", viewObserver.removeKeyList.get(1));
            assertEquals("1", viewObserver.removeValueList.get(0));
            assertEquals("5", viewObserver.removeValueList.get(1));
        }

        void verifyClear() {
            entrySet.clear();
            assertEquals(0, viewModel.size());
            assertEquals(1, viewObserver.cleared);
        }

        void verifyEqualsObject() {
            Set<Map.Entry<String, String>> set = new HashSet<>(viewModel.entrySet());
            assertEquals(entrySet, set);
        }
    }

    private static final class KeySetViewFixture {

        private final DelegateMapModel<String, String> viewModel = new DelegateMapModel<>(new HashMap<>(),
                Collections.newSetFromMap(new WeakHashMap<>()));
        private final MapObserverFixture viewObserver = new MapObserverFixture();
        private Set<String> keySet;

        void prepare() {
            viewModel.clearObserver();
            viewModel.clear();
            viewModel.put("A", "1");
            viewModel.put("B", "2");
            viewModel.put("C", "3");
            viewModel.put("D", "4");
            viewModel.put("E", "5");
            viewObserver.reset();
            viewModel.addObserver(viewObserver);
            keySet = viewModel.keySet();
        }

        void verifySize() {
            assertEquals(5, keySet.size());
        }

        @SuppressWarnings("ConstantValue")
        void verifyIsEmpty() {
            assertFalse(keySet.isEmpty());
            keySet.clear();
            assertTrue(keySet.isEmpty());
        }

        void verifyContains() {
            assertTrue(keySet.contains("A"));
            assertTrue(keySet.contains("B"));
            assertTrue(keySet.contains("C"));
            assertTrue(keySet.contains("D"));
            assertTrue(keySet.contains("E"));
            assertFalse(keySet.contains("F"));
        }

        void verifyIterator() {
            Iterator<String> iterator = keySet.iterator();
            assertEquals("A", iterator.next());
            iterator.remove();
            assertEquals("A", viewObserver.removeKeyList.getFirst());
            iterator.next();
            iterator.next();
            iterator.next();
            iterator.next();
            assertFalse(iterator.hasNext());
            assertArrayEquals(new Object[]{"B", "C", "D", "E"}, keySet.toArray());
            assertEquals(4, viewModel.size());
        }

        void verifyToArray() {
            assertArrayEquals(new Object[]{"A", "B", "C", "D", "E"}, keySet.toArray());
        }

        void verifyToArrayTArray() {
            assertArrayEquals(new Object[]{"A", "B", "C", "D", "E"}, keySet.toArray(new String[0]));
        }

        void verifyAdd() {
            assertThrows(UnsupportedOperationException.class, () -> keySet.add("E"));
        }

        void verifyRemove() {
            assertTrue(keySet.remove("A"));
            assertEquals("A", viewObserver.removeKeyList.getFirst());
            assertEquals("1", viewObserver.removeValueList.getFirst());
            assertEquals(4, viewModel.size());
        }

        void verifyContainsAll() {
            assertTrue(keySet.containsAll(Arrays.asList("A", "B", "C", "D", "E")));
            assertFalse(keySet.containsAll(Arrays.asList("A", "B", "C", "D", "E", "F")));
        }

        void verifyAddAll() {
            assertThrows(UnsupportedOperationException.class, () -> keySet.addAll(Arrays.asList("A", "B", "C", "D", "E")));
        }

        @SuppressWarnings("SlowAbstractSetRemoveAll")
        void verifyRemoveAll() {
            assertTrue(keySet.removeAll(Arrays.asList("B", "C", "D")));
            assertArrayEquals(new Object[]{"A", "E"}, keySet.toArray());
            assertEquals("B", viewObserver.removeKeyList.get(0));
            assertEquals("C", viewObserver.removeKeyList.get(1));
            assertEquals("D", viewObserver.removeKeyList.get(2));
            assertEquals("2", viewObserver.removeValueList.get(0));
            assertEquals("3", viewObserver.removeValueList.get(1));
            assertEquals("4", viewObserver.removeValueList.get(2));
        }

        void verifyRetainAll() {
            assertTrue(keySet.retainAll(Arrays.asList("B", "C", "D")));
            assertArrayEquals(new Object[]{"B", "C", "D"}, keySet.toArray());
            assertEquals("A", viewObserver.removeKeyList.get(0));
            assertEquals("E", viewObserver.removeKeyList.get(1));
            assertEquals("1", viewObserver.removeValueList.get(0));
            assertEquals("5", viewObserver.removeValueList.get(1));
        }

        @SuppressWarnings("ConstantValue")
        void verifyClear() {
            keySet.clear();
            assertEquals(0, keySet.size());
            assertEquals(0, viewModel.size());
            assertEquals(1, viewObserver.cleared);
        }

        void verifyEqualsObject() {
            assertEquals(keySet, new HashSet<>(Arrays.asList("A", "B", "D", "C", "E")));
        }
    }

    private static final class ValuesViewFixture {

        private final DelegateMapModel<String, String> viewModel = new DelegateMapModel<>(new LinkedHashMap<>(),
                Collections.newSetFromMap(new WeakHashMap<>()));
        private final MapObserverFixture viewObserver = new MapObserverFixture();
        private Collection<String> values;

        void prepare() {
            viewModel.clearObserver();
            viewModel.clear();
            viewModel.put("A", "1");
            viewModel.put("B", "2");
            viewModel.put("C", "3");
            viewModel.put("D", "4");
            viewModel.put("E", "5");
            viewObserver.reset();
            viewModel.addObserver(viewObserver);
            values = viewModel.values();
        }

        void verifySize() {
            assertEquals(5, values.size());
        }

        @SuppressWarnings("ConstantValue")
        void verifyIsEmpty() {
            assertFalse(values.isEmpty());
            values.clear();
            assertTrue(values.isEmpty());
            assertEquals(0, viewModel.size());
            assertEquals(1, viewObserver.cleared);
        }

        void verifyContains() {
            assertTrue(values.contains("1"));
            assertTrue(values.contains("2"));
            assertTrue(values.contains("3"));
            assertTrue(values.contains("4"));
            assertTrue(values.contains("5"));
            assertFalse(values.contains("6"));
        }

        void verifyIterator() {
            Iterator<String> iterator = values.iterator();
            assertEquals("1", iterator.next());
            iterator.remove();
            assertFalse(viewModel.containsKey("A"));
            assertEquals(4, viewModel.size());
            assertNull(viewModel.get("A"));
            assertEquals("A", viewObserver.removeKeyList.getFirst());
            assertEquals("1", viewObserver.removeValueList.getFirst());
            iterator.next();
            iterator.next();
            assertEquals("4", iterator.next());
            iterator.next();
            assertFalse(iterator.hasNext());
        }

        void verifyToArray() {
            assertArrayEquals(new Object[]{"1", "2", "3", "4", "5"}, values.toArray());
        }

        void verifyToArrayTArray() {
            assertArrayEquals(new Object[]{"1", "2", "3", "4", "5"}, values.toArray(new String[0]));
        }

        void verifyAdd() {
            assertThrows(UnsupportedOperationException.class, () -> values.add("5"));
        }

        void verifyRemove() {
            viewModel.put("F", "2");
            values.remove("2");
            assertEquals(5, viewModel.size());
            assertFalse(viewModel.containsKey("B"));
            assertEquals("B", viewObserver.removeKeyList.getFirst());
            assertEquals("2", viewObserver.removeValueList.getFirst());
            assertTrue(values.contains("2"));
            assertEquals("2", viewModel.get("F"));
        }

        void verifyContainsAll() {
            assertTrue(values.containsAll(Arrays.asList("1", "2", "3", "4", "5")));
            assertFalse(values.containsAll(Arrays.asList("1", "2", "3", "4", "5", "6")));
        }

        void verifyAddAll() {
            assertThrows(UnsupportedOperationException.class, () -> values.addAll(Arrays.asList("6", "7", "8")));
        }

        void verifyRemoveAll() {
            assertTrue(values.removeAll(Arrays.asList("2", "3", "4")));
            assertEquals(2, viewModel.size());
            assertArrayEquals(new Object[]{"1", "5"}, values.toArray());
            assertEquals("B", viewObserver.removeKeyList.get(0));
            assertEquals("C", viewObserver.removeKeyList.get(1));
            assertEquals("D", viewObserver.removeKeyList.get(2));
            assertEquals("2", viewObserver.removeValueList.get(0));
            assertEquals("3", viewObserver.removeValueList.get(1));
            assertEquals("4", viewObserver.removeValueList.get(2));
        }

        void verifyRetainAll() {
            assertTrue(values.retainAll(Arrays.asList("2", "3", "4")));
            assertEquals(3, viewModel.size());
            assertArrayEquals(new Object[]{"2", "3", "4"}, values.toArray());
            assertEquals("A", viewObserver.removeKeyList.get(0));
            assertEquals("E", viewObserver.removeKeyList.get(1));
            assertEquals("1", viewObserver.removeValueList.get(0));
            assertEquals("5", viewObserver.removeValueList.get(1));
        }

        void verifyClear() {
            values.clear();
            assertEquals(0, viewModel.size());
            assertEquals(1, viewObserver.cleared);
        }
    }
}
