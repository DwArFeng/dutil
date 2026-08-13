package com.dwarfeng.dutil.basic.stack.number;

import com.dwarfeng.dutil.basic.sdk.io.CT;
import com.dwarfeng.dutil.basic.stack.number.Interval.BoundaryType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class IntervalTest {

    @Test
    public void testHashCode() {
        Interval interval0 = new Interval(BoundaryType.CLOSED, BoundaryType.OPENED, new BigDecimal(12450),
                new BigDecimal("12450.12"));
        Interval interval1 = new Interval(BoundaryType.CLOSED, BoundaryType.OPENED, new BigDecimal(12450),
                new BigDecimal("12450.12"));
        assertEquals(interval0.hashCode(), interval1.hashCode());
    }

    @Test
    public void testParseInterval0() {
        Interval interval = Interval.parseInterval("[ 0.12 , 12450.12 )");
        assertSame(interval.leftBoundaryType(), BoundaryType.CLOSED);
        assertSame(interval.rightBoundaryType(), BoundaryType.OPENED);
        assertEquals(interval.leftValue(), new BigDecimal("0.12"));
        assertEquals(interval.rightValue(), new BigDecimal("12450.12"));
    }

    @Test
    public void testParseInterval1() {
        assertThrows(IllegalArgumentException.class, () -> {
            Interval interval = Interval.parseInterval("[infinity , 12450.12 )");
            assertSame(interval.leftBoundaryType(), BoundaryType.CLOSED);
            assertSame(interval.rightBoundaryType(), BoundaryType.OPENED);
            assertEquals(interval.leftValue(), new BigDecimal("0.12"));
            assertEquals(interval.rightValue(), new BigDecimal("12450.12"));
        });
    }

    @Test
    public void testParseInterval2() {
        Interval interval = Interval.parseInterval("[ -infinity , 12450.12 )");
        assertSame(interval.leftBoundaryType(), BoundaryType.CLOSED);
        assertSame(interval.rightBoundaryType(), BoundaryType.OPENED);
        assertNull(interval.leftValue());
        assertEquals(new BigDecimal("12450.12"), interval.rightValue());
    }

    @Test
    public void testParseInterval3() {
        Interval interval = Interval.parseInterval("[ 12450.12 , infinity )");
        assertSame(interval.leftBoundaryType(), BoundaryType.CLOSED);
        assertSame(interval.rightBoundaryType(), BoundaryType.OPENED);
        assertNull(interval.rightValue());
        assertEquals(new BigDecimal("12450.12"), interval.leftValue());
    }

    @Test
    public void testParseInterval4() {
        assertThrows(IllegalArgumentException.class, () -> {
            Interval.parseInterval("( 12450.12 , 12450 )");
        });
    }

    @Test
    public void testInterval() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Interval(BoundaryType.CLOSED, BoundaryType.CLOSED, new BigDecimal("12450.12"), new BigDecimal(12450));
        });
    }

    @Test
    public void testContainsBigDecimal() {
        Interval interval = new Interval(BoundaryType.CLOSED, BoundaryType.OPENED, new BigDecimal(12450),
                new BigDecimal("12450.12"));
        assertTrue(interval.contains(new BigDecimal("12450.11")));
        assertFalse(interval.contains(12450.12));
        assertFalse(interval.contains(new BigDecimal("12450.13")));
    }

    @Test
    public void testContainsDouble() {
        Interval interval = new Interval(BoundaryType.CLOSED, BoundaryType.OPENED, new BigDecimal(12450),
                new BigDecimal("12450.12"));
        assertTrue(interval.contains(12450.11));
        assertFalse(interval.contains(12450.12));
        assertFalse(interval.contains(12450.13));
    }

    @Test
    public void testContainsInt() {
        Interval interval = new Interval(BoundaryType.CLOSED, BoundaryType.OPENED, new BigDecimal(12450),
                new BigDecimal("12450.12"));
        assertTrue(interval.contains(12450));
        assertFalse(interval.contains(12451));
    }

    @Disabled
    public void testContainsLong() {
        Interval interval = new Interval(BoundaryType.CLOSED, BoundaryType.OPENED, new BigDecimal(12450),
                new BigDecimal("12450.12"));
        assertTrue(interval.contains((long) 12450));
        assertFalse(interval.contains((long) 12451));
    }

    @Test
    public void testEqualsObject() {
        Interval interval0 = new Interval(BoundaryType.CLOSED, BoundaryType.OPENED, new BigDecimal(12450),
                new BigDecimal("12450.12"));
        Interval interval1 = new Interval(BoundaryType.CLOSED, BoundaryType.OPENED, new BigDecimal(12450),
                new BigDecimal("12450.12"));
        assertNotSame(interval0, interval1);
        assertEquals(interval0, interval1);
    }

    @Test
    public void testToString() {
        CT.trace(new Interval(BoundaryType.CLOSED, BoundaryType.OPENED, new BigDecimal(12450),
                new BigDecimal("12450.12")));
    }

    @Test
    public void testToStringIntRoundingMode() {
        CT.trace(new Interval(
                BoundaryType.CLOSED, BoundaryType.OPENED,
                new BigDecimal(12450), new BigDecimal("12450.12")
        ).toString(2, RoundingMode.HALF_DOWN));
    }
}
