package com.dwarfeng.dutil.develop.reuse;

import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

public class Test_MapReusePool {

    private static final class TestCondition implements Condition {

        public static final Object INCREASE = new Object();
        public static final Object RESET = new Object();

        public final int aimCount;
        public int currentCount;

        public TestCondition(int aimCount) {
            this.aimCount = aimCount;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isReuseSatisfy() {
            return !isReuseUnsatisfy();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isReuseUnsatisfy() {
            return currentCount >= aimCount;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void update(Object updateObject) throws IllegalArgumentException {
            if (Objects.equals(updateObject, INCREASE)) {
                currentCount++;
            }

            if (Objects.equals(updateObject, RESET)) {
                currentCount = 0;
            }
        }

    }

    private static MapReusePool<String> pool;

    private final static String ELEMENT_A = "A";
    private final static String ELEMENT_B = "B";
    private final static String ELEMENT_C = "C";
    private final static String ELEMENT_D = "D";
    private final static String ELEMENT_E = "E";

    private static TestCondition CONDITION_A = new TestCondition(2);
    private static TestCondition CONDITION_B = new TestCondition(2);
    private static TestCondition CONDITION_C = new TestCondition(3);
    private static TestCondition CONDITION_D = new TestCondition(3);
    private static TestCondition CONDITION_E = new TestCondition(4);

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
        CONDITION_A = new TestCondition(2);
        CONDITION_B = new TestCondition(2);
        CONDITION_C = new TestCondition(3);
        CONDITION_D = new TestCondition(3);
        CONDITION_E = new TestCondition(4);

        pool = new MapReusePool<>(new LinkedHashMap<>());
        pool.put(ELEMENT_A, CONDITION_A);
        pool.put(ELEMENT_B, CONDITION_B);
        pool.put(ELEMENT_C, CONDITION_C);
        pool.put(ELEMENT_D, CONDITION_D);
    }

    @After
    public void tearDown() {
        pool = null;

        CONDITION_A = null;
        CONDITION_B = null;
        CONDITION_C = null;
        CONDITION_D = null;
        CONDITION_E = null;
    }

    @Test
    public final void testIsEmpty() {
        assertFalse(pool.isEmpty());
        pool.clear();
        assertTrue(pool.isEmpty());
    }

    @Test
    public final void testContains() {
        assertTrue(pool.contains(ELEMENT_A));
        assertTrue(pool.contains(ELEMENT_B));
        assertTrue(pool.contains(ELEMENT_C));
        assertTrue(pool.contains(ELEMENT_D));
        assertFalse(pool.contains(ELEMENT_E));
    }

    @Test
    public final void testContainsAll() {
        assertTrue(pool.containsAll(Arrays.asList(ELEMENT_A, ELEMENT_B, ELEMENT_C, ELEMENT_D)));
        assertFalse(pool.containsAll(Arrays.asList(ELEMENT_A, ELEMENT_B, ELEMENT_C, ELEMENT_D, ELEMENT_E)));
    }

    @Test
    public final void testRemove() {
        assertFalse(pool.remove(ELEMENT_E));
        assertTrue(pool.remove(ELEMENT_A));
        assertEquals(3, pool.size());
    }

    @Test
    public final void testRemoveAll() {
        assertTrue(pool.removeAll(Arrays.asList(ELEMENT_B, ELEMENT_C, ELEMENT_D, ELEMENT_E)));
        assertEquals(1, pool.size());
    }

    @Test
    public final void testRetainAll() {
        assertTrue(pool.retainAll(Arrays.asList(ELEMENT_B, ELEMENT_C, ELEMENT_D, ELEMENT_E)));
        assertEquals(3, pool.size());
    }

    @Test
    public final void testClear() {
        pool.clear();
        assertTrue(pool.isEmpty());
        assertEquals(0, pool.size());
    }

    @Test
    public final void testIterator() {
        Iterator<String> iterator = pool.iterator();
        String next = iterator.next();
        assertEquals(ELEMENT_A, next);
        next = iterator.next();
        assertEquals(ELEMENT_B, next);
        iterator.remove();
        assertEquals(3, pool.size());
        assertTrue(pool.contains(ELEMENT_A));
        assertFalse(pool.contains(ELEMENT_B));
        assertTrue(pool.contains(ELEMENT_C));
        assertTrue(pool.contains(ELEMENT_D));
    }

    @Test
    public final void testSize() {
        assertEquals(4, pool.size());
        pool.put(ELEMENT_E, CONDITION_E);
        assertEquals(5, pool.size());
        pool.clear();
        assertEquals(0, pool.size());
    }

    @Test
    public final void testGetCondition() {
        assertEquals(CONDITION_A, pool.getCondition(ELEMENT_A));
        assertEquals(CONDITION_B, pool.getCondition(ELEMENT_B));
        assertEquals(CONDITION_C, pool.getCondition(ELEMENT_C));
        assertEquals(CONDITION_D, pool.getCondition(ELEMENT_D));
    }

    @Test
    public final void testPut() {
        pool.put(ELEMENT_E, CONDITION_E);
        assertEquals(5, pool.size());
        assertEquals(CONDITION_E, pool.getCondition(ELEMENT_E));
    }

    @Test
    public final void testBatchOperator_1() {
        pool.batchOperator().update(ELEMENT_A, TestCondition.RESET).updateRemain(TestCondition.INCREASE);
        assertEquals(0, CONDITION_A.currentCount);
        assertEquals(1, CONDITION_B.currentCount);
        assertEquals(1, CONDITION_C.currentCount);
        assertEquals(1, CONDITION_D.currentCount);
    }

    @Test
    public final void testBatchOperator_2() {
        pool.batchOperator().updateAll(e -> ((TestCondition) pool.getCondition(e)).aimCount > 2, TestCondition.RESET)
                .updateRemain(TestCondition.INCREASE);
        assertEquals(1, CONDITION_A.currentCount);
        assertEquals(1, CONDITION_B.currentCount);
        assertEquals(0, CONDITION_C.currentCount);
        assertEquals(0, CONDITION_D.currentCount);
    }

    @Test
    public final void testBatchOperator_3() {
        pool.batchOperator().updateAll(Arrays.asList(ELEMENT_A, ELEMENT_D, ELEMENT_E), TestCondition.RESET)
                .updateRemain(TestCondition.INCREASE);
        assertEquals(0, CONDITION_A.currentCount);
        assertEquals(1, CONDITION_B.currentCount);
        assertEquals(1, CONDITION_C.currentCount);
        assertEquals(0, CONDITION_D.currentCount);
    }

    @Test
    public final void testBatchOperator_4() {
        pool.batchOperator().updateAll(Arrays.asList(ELEMENT_A, ELEMENT_D, ELEMENT_E), TestCondition.RESET)
                .updateRemain(TestCondition.INCREASE);
        assertEquals(0, CONDITION_A.currentCount);
        assertEquals(1, CONDITION_B.currentCount);
        assertEquals(1, CONDITION_C.currentCount);
        assertEquals(0, CONDITION_D.currentCount);

        Collection<String> unsatisfyElements = pool.batchOperator()
                .updateAll(Arrays.asList(ELEMENT_A, ELEMENT_D, ELEMENT_E), TestCondition.RESET)
                .updateRemain(TestCondition.INCREASE).getUnsatisfyElements();
        assertEquals(1, unsatisfyElements.size());
        assertTrue(unsatisfyElements.contains(ELEMENT_B));

        unsatisfyElements = pool.batchOperator()
                .updateAll(Arrays.asList(ELEMENT_A, ELEMENT_D, ELEMENT_E), TestCondition.RESET)
                .updateRemain(TestCondition.INCREASE).removeUnsatisfyElements();
        assertEquals(2, unsatisfyElements.size());
        assertTrue(unsatisfyElements.containsAll(Arrays.asList(ELEMENT_B, ELEMENT_C)));
        assertEquals(2, pool.size());
        assertTrue(pool.containsAll(Arrays.asList(ELEMENT_A, ELEMENT_D)));
    }
}
