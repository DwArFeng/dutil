package com.dwarfeng.dutil.basic.sdk.locale;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link LocaleUtil} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LocaleUtilTest {

    @Test
    public void testParseLanguageTags() {
        assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleUtil.parse("zh-CN"));
        assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleUtil.parse("zh_CN"));
        assertEquals(Locale.US, LocaleUtil.parse("en_US"));
    }

    @Test
    public void testRejectInvalidTag() {
        assertThrows(IllegalArgumentException.class, () -> LocaleUtil.parse("invalid_locale_tag_"));
    }
}
