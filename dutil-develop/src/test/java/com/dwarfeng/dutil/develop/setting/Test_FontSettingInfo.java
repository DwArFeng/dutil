package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.develop.setting.info.FontSettingInfo;
import org.junit.*;

import java.awt.*;

import static org.junit.Assert.*;

public class Test_FontSettingInfo {

    private final static String VALUE_INIT = Font.SANS_SERIF + "-" + Font.PLAIN + "-12";
    private final static String VALUE_OTHER = Font.SANS_SERIF + "-" + Font.BOLD + "-10";
    private final static String VALUE_INVALID = "foo-plain-10";

    private final static Object VALUE_PARSE_INIT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    private final static Object VALUE_PARSE_OTHER = new Font(Font.SANS_SERIF, Font.BOLD, 10);
    private final static Object VALUE_PARSE_INVALID = true;

    private static SettingInfo info;

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
        info = new FontSettingInfo(VALUE_INIT);
    }

    @After
    public void tearDown() {
        info = null;
    }

    @Test
    public final void testHashCode() {
        SettingInfo other = new FontSettingInfo(VALUE_INIT);
        assertEquals(info.hashCode(), other.hashCode());
        other = new FontSettingInfo(VALUE_OTHER);
        assertNotEquals(info.hashCode(), other.hashCode());
    }

    @Test
    public final void testEqualsObject() {
        SettingInfo other = new FontSettingInfo(VALUE_INIT);
        assertEquals(info, other);
        other = new FontSettingInfo(VALUE_OTHER);
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
        new FontSettingInfo(VALUE_INIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testCheckDefaultValueException() {
        new FontSettingInfo(VALUE_INVALID);
        fail("没有抛出异常");
    }
}
