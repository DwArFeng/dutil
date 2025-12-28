package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.basic.num.Interval;
import com.dwarfeng.dutil.develop.setting.info.ByteSettingInfo;
import org.junit.*;

import static org.junit.Assert.*;

public class Test_ByteSettingInfo {

    private final static String VALUE_INIT = "12";
    private final static String VALUE_OTHER = "127";
    private final static String VALUE_INVALID = "bba";

    private final static Object VALUE_PARSE_INIT = (byte) 12;
    private final static Object VALUE_PARSE_OTHER = (byte) 127;
    private final static Object VALUE_PARSE_INVALID = 133;

    private static SettingInfo info;

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
        info = new ByteSettingInfo(VALUE_INIT);
    }

    @After
    public void tearDown() {
        info = null;
    }

    @Test
    public final void testHashCode() {
        SettingInfo other = new ByteSettingInfo(VALUE_INIT);
        assertEquals(info.hashCode(), other.hashCode());
        other = new ByteSettingInfo(VALUE_OTHER);
        assertNotEquals(info.hashCode(), other.hashCode());
    }

    @Test
    public final void testEqualsObject() {
        SettingInfo other = new ByteSettingInfo(VALUE_INIT);
        assertEquals(info, other);
        other = new ByteSettingInfo(VALUE_OTHER);
        assertNotEquals(info, other);
    }

    @Test
    public final void testIsValid() {
        assertFalse(info.isValid(null));
        assertFalse(info.isValid(VALUE_INVALID));
        assertTrue(info.isValid(VALUE_INIT));
        assertTrue(info.isValid(VALUE_OTHER));
    }

    @Test
    public final void testNonValid() {
        assertTrue(info.nonValid(null));
        assertTrue(info.nonValid(VALUE_INVALID));
        assertFalse(info.nonValid(VALUE_INIT));
        assertFalse(info.nonValid(VALUE_OTHER));
    }

    @Test
    public final void testParseValue() {
        assertEquals(VALUE_PARSE_INIT, info.parseValue(VALUE_INIT));
        assertEquals(VALUE_PARSE_OTHER, info.parseValue(VALUE_OTHER));
        assertNull(info.parseValue(null));
        assertNull(info.parseValue(VALUE_INVALID));
    }

    @Test
    public final void testParseObject() {
        assertEquals(VALUE_INIT, info.parseObject(VALUE_PARSE_INIT));
        assertEquals(VALUE_OTHER, info.parseObject(VALUE_PARSE_OTHER));
        assertNull(info.parseObject(null));
        assertNull(info.parseObject(VALUE_PARSE_INVALID));
    }

    @Test
    public final void testGetDefaultValue() {
        assertEquals(VALUE_INIT, info.getDefaultValue());
    }

    @Test
    public final void testCheckDefaultValue() {
        new ByteSettingInfo(VALUE_INIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testCheckDefaultValueException() {
        new ByteSettingInfo(VALUE_INVALID);
        fail("没有抛出异常");
    }

    @Test
    public final void testWithInterval() {
        ByteSettingInfo info = new ByteSettingInfo("0", Interval.INTERVAL_NOT_NEGATIVE);
        assertTrue(info.isValid("12"));
        assertFalse(info.isValid("-110"));
    }
}
