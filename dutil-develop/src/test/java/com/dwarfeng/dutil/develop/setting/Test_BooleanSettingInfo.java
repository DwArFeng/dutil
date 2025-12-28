package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.develop.setting.info.BooleanSettingInfo;
import org.junit.*;

import static org.junit.Assert.*;

public class Test_BooleanSettingInfo {

    private final static String VALUE_INIT = "FALSE";
    private final static String VALUE_OTHER = "TRUE";
    private final static String VALUE_INVALID = "TTTT";

    private final static Object VALUE_PARSE_INIT = false;
    private final static Object VALUE_PARSE_OTHER = true;
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
        info = new BooleanSettingInfo(VALUE_INIT);
    }

    @After
    public void tearDown() {
        info = null;
    }

    @Test
    public final void testHashCode() {
        SettingInfo other = new BooleanSettingInfo(VALUE_INIT);
        assertEquals(info.hashCode(), other.hashCode());
        other = new BooleanSettingInfo(VALUE_OTHER);
        assertNotEquals(info.hashCode(), other.hashCode());
    }

    @Test
    public final void testEqualsObject() {
        SettingInfo other = new BooleanSettingInfo(VALUE_INIT);
        assertEquals(info, other);
        other = new BooleanSettingInfo(VALUE_OTHER);
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
        new BooleanSettingInfo(VALUE_INIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testCheckDefaultValueException() {
        new BooleanSettingInfo(VALUE_INVALID);
        fail("没有抛出异常");
    }
}
