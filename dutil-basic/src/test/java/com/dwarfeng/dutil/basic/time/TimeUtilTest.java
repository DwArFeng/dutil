package com.dwarfeng.dutil.basic.time;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static com.dwarfeng.dutil.basic.time.TimeUtil.*;
import static org.junit.Assert.*;

public class TimeUtilTest {

    @Test
    public void testToInstantAndSplit() {
        Date date = new Date(1713340800123L);
        int nanoOffset = 456789;

        Instant instant = toInstant(date, nanoOffset);

        assertEquals(date, toDate(instant));
        assertEquals(nanoOffset, toNanoOffset(instant));
        assertEquals(instant, toInstant(toDate(instant), toNanoOffset(instant)));
    }

    @Test
    public void testToInstantAndSplitWithNegativeEpoch() {
        Instant instant = Instant.ofEpochSecond(-1, 999999999);

        assertEquals(instant, toInstant(toDate(instant), toNanoOffset(instant)));
    }

    @Test
    public void testNanoOffsetBoundary() {
        Date date = new Date(0L);

        Instant minInstant = toInstant(date, MIN_NANO_OFFSET);
        Instant maxInstant = toInstant(date, MAX_NANO_OFFSET);

        assertEquals(0, toNanoOffset(minInstant));
        assertEquals(999999, toNanoOffset(maxInstant));
    }

    @Test
    public void testCompareBoundary() {
        Date date = new Date(123L);
        assertTrue(compare(date, 0, date, 1) < 0);
        assertTrue(compare(new Date(122L), 999999, new Date(123L), 0) < 0);
    }

    @Test
    public void testInstantMillisecondBoundary() {
        Instant instant = Instant.ofEpochSecond(123, 456000000);

        assertEquals(new Date(123456L), toDate(instant));
        assertEquals(0, toNanoOffset(instant));
    }

    @Test
    public void testToInstantNullDate() {
        try {
            toInstant(null, 0);
            fail("No exception throws");
        } catch (NullPointerException e) {
            assertEquals(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEUTIL_0), e.getMessage());
        }
    }

    @Test(expected = NullPointerException.class)
    public void testToDateNullInstant() {
        toDate(null);
    }

    @Test(expected = NullPointerException.class)
    public void testNanoOffsetNullInstant() {
        toNanoOffset(null);
    }

    @Test
    public void testToInstantIllegalNanoOffsetLow() {
        try {
            toInstant(new Date(), -1);
            fail("No exception throws");
        } catch (IllegalArgumentException e) {
            assertEquals(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEUTIL_4), e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToInstantIllegalNanoOffsetHigh() {
        toInstant(new Date(), 1000000);
    }

    @Test
    public void testIsNanoOffsetLegal() {
        assertTrue(isNanoOffsetLegal(0));
        assertTrue(isNanoOffsetLegal(999999));
        assertFalse(isNanoOffsetLegal(-1));
        assertFalse(isNanoOffsetLegal(1000000));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckNanoOffset() {
        checkNanoOffset(1000000);
    }
}
