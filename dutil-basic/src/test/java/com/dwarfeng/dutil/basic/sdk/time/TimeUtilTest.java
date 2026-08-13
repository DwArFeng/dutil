package com.dwarfeng.dutil.basic.sdk.time;

import com.dwarfeng.dutil.base.sdk.i18n.MessageContext;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import static com.dwarfeng.dutil.basic.sdk.time.TimeUtil.*;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(NullPointerException.class, () -> toInstant(null, 0));
    }

    @Test
    public void testToDateNullInstant() {
        assertThrows(NullPointerException.class, () -> toDate(null));
    }

    @Test
    public void testNanoOffsetNullInstant() {
        assertThrows(NullPointerException.class, () -> toNanoOffset(null));
    }

    @Test
    public void testToInstantIllegalNanoOffsetLow() {
        assertThrows(IllegalArgumentException.class, () -> toInstant(new Date(), -1));
    }

    @Test
    public void testToInstantIllegalNanoOffsetMessageUsesScopedLocale() {
        IllegalArgumentException english = MessageContext.call(
                Locale.ENGLISH,
                () -> assertThrows(IllegalArgumentException.class, () -> toInstant(new Date(), -1))
        );
        IllegalArgumentException chinese = MessageContext.call(
                Locale.SIMPLIFIED_CHINESE,
                () -> assertThrows(IllegalArgumentException.class, () -> toInstant(new Date(), -1))
        );

        assertEquals("The entrance param \"nanoOffset\" is not a legal nano offset in milli.", english.getMessage());
        assertEquals("入口参数\"nanoOffset\"不是合法的毫秒内纳秒偏移量。", chinese.getMessage());
    }

    @Test
    public void testToInstantIllegalNanoOffsetHigh() {
        assertThrows(IllegalArgumentException.class, () -> toInstant(new Date(), 1000000));
    }

    @Test
    public void testIsNanoOffsetLegal() {
        assertTrue(isNanoOffsetLegal(0));
        assertTrue(isNanoOffsetLegal(999999));
        assertFalse(isNanoOffsetLegal(-1));
        assertFalse(isNanoOffsetLegal(1000000));
    }

    @Test
    public void testCheckNanoOffset() {
        assertThrows(IllegalArgumentException.class, () -> checkNanoOffset(1000000));
    }
}
