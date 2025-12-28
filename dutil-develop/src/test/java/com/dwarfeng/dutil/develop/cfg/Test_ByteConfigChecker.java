package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.develop.cfg.checker.ByteConfigChecker;
import org.junit.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test_ByteConfigChecker {

    private static ByteConfigChecker checker;

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
        checker = new ByteConfigChecker();
    }

    @After
    public void tearDown() {
        checker = null;
    }

    @Test
    public void testIsValid() {
        assertTrue(checker.isValid("0"));
        assertTrue(checker.isValid("-1"));
        assertTrue(checker.isValid("1"));
        assertTrue(checker.isValid("-128"));
        assertTrue(checker.isValid("127"));
        assertFalse(checker.isValid("A"));
        assertFalse(checker.isValid("0xFF"));
        assertFalse(checker.isValid("-129"));
        assertFalse(checker.isValid("0.0"));
        assertFalse(checker.isValid("1.234"));
        assertFalse(checker.isValid("128"));

    }
}
