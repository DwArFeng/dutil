package com.dwarfeng.dutil.basic.sdk.locale;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;

import java.util.IllformedLocaleException;
import java.util.Locale;
import java.util.Objects;

/**
 * 语言环境工具。
 *
 * <p>
 * 该工具接受 BCP 47 语言标签，也兼容使用下划线分隔的传统 Java 语言环境文本。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class LocaleUtil {

    /**
     * 解析语言环境文本。
     *
     * @param source BCP 47 语言标签或下划线分隔文本。
     * @return 解析后的语言环境。
     * @throws NullPointerException     入口参数为 {@code null}。
     * @throws IllegalArgumentException 文本不是合法语言标签。
     */
    public static Locale parse(String source) {
        Objects.requireNonNull(source, BasicMessages.message(BasicMessageKey.LOCALE_SOURCE_REQUIRED));
        try {
            return new Locale.Builder().setLanguageTag(source.replace('_', '-')).build();
        } catch (IllformedLocaleException exception) {
            throw new IllegalArgumentException(BasicMessages.message(BasicMessageKey.LOCALE_SOURCE_INVALID), exception);
        }
    }

    private LocaleUtil() {
        throw new AssertionError("No instances");
    }
}
