package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.develop.setting.info.LocaleSettingInfo;
import org.junit.*;

import java.util.Locale;

import static org.junit.Assert.*;

public class Test_ValueFilteredSettingInfo {

    private static SettingInfo settingInfo;

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
        settingInfo = SettingUtil.valueFilteredSettingInfo(new LocaleSettingInfo("zh_CN"), value -> value.startsWith("zh"));
    }

    @After
    public void tearDown() {
        settingInfo = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testFilteredSettingInfo() {
        SettingUtil.valueFilteredSettingInfo(new LocaleSettingInfo("en_US"), value -> value.startsWith("zh"));
        fail("没有抛出指定的异常");
    }

    @Test
    public final void testIsValid() {
        assertTrue(settingInfo.isValid("zh_CN"));
        assertTrue(settingInfo.isValid("zh_TW"));
        assertTrue(settingInfo.isValid("zh__foo"));

        assertFalse(settingInfo.isValid("en_US"));
        assertFalse(settingInfo.isValid("zh_"));
        assertFalse(settingInfo.isValid("zh__"));
        assertFalse(settingInfo.isValid("zh_CN_"));
        assertFalse(settingInfo.isValid("zh_CN_jinan_"));
    }

    @Test
    public final void testNonValid() {
        assertFalse(settingInfo.nonValid("zh_CN"));
        assertFalse(settingInfo.nonValid("zh_TW"));
        assertFalse(settingInfo.nonValid("zh__foo"));

        assertTrue(settingInfo.nonValid("en_US"));
        assertTrue(settingInfo.nonValid("zh_"));
        assertTrue(settingInfo.nonValid("zh__"));
        assertTrue(settingInfo.nonValid("zh_CN_"));
        assertTrue(settingInfo.nonValid("zh_CN_jinan_"));
    }

    @Test
    public final void testParseValue() {
        assertEquals(Locale.CHINA, settingInfo.parseValue("zh_CN"));
        assertEquals(Locale.TAIWAN, settingInfo.parseValue("zh_TW"));
        assertNull(settingInfo.parseValue("en_US"));
    }

    @Test
    public final void testParseObject() {
        assertEquals("zh_CN", settingInfo.parseObject(Locale.CHINA));
        assertEquals("zh_TW", settingInfo.parseObject(Locale.TAIWAN));
        assertNull(settingInfo.parseObject(Locale.ENGLISH));
    }

    @Test
    public final void testGetDefaultValue() {
        assertEquals("zh_CN", settingInfo.getDefaultValue());
    }
}
