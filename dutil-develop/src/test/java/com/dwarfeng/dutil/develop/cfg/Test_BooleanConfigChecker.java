package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.develop.cfg.checker.BooleanConfigChecker;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Test_BooleanConfigChecker {

    @Test
    public void testHashCode() {
        BooleanConfigChecker A = new BooleanConfigChecker(), B = new BooleanConfigChecker();

        assertEquals(A.hashCode(), B.hashCode());
    }

    @Test
    public void testEqualsObject() {
        BooleanConfigChecker A = new BooleanConfigChecker(), B = new BooleanConfigChecker();

        assertNotEquals(null, A);
        assertEquals(A, B);
    }
}
