package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.develop.cfg.checker.ClassConfigChecker;
import org.junit.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test_ClassConfigChecker {

    private static ClassConfigChecker checker;

    @BeforeClass
    public static void setUpBeforeClass() {
        checker = new ClassConfigChecker();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        checker = null;
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public final void testIsValid() {
        assertTrue(checker.isValid("a123"));
        assertTrue(checker.isValid("com.dwarfeng.Checker"));
        assertTrue(checker.isValid("com.dwarfeng.Checker1"));
        assertFalse(checker.isValid("123"));
        assertFalse(checker.isValid("com."));
        assertFalse(checker.isValid("包.类"));
        assertFalse(checker.isValid("com.123"));
        assertFalse(checker.isValid(null));
    }
}
