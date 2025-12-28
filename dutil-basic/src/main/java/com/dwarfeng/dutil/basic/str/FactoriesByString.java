package com.dwarfeng.dutil.basic.str;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 提供一些使用 String 作为入口参数的工厂方法。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class FactoriesByString {

    /**
     * 通过指定的字符串构建国家/地区。
     *
     * <p>
     * 指定的字符串必须符合标准国家/地区代码格式。
     *
     * @param string 指定的字符串。
     * @return 由字符串构建的国家/地区。
     * @throws NullPointerException     入口参数为 <code>null</code>
     * @throws IllegalArgumentException 入口参数不是标准的国家/地区代码格式。
     */
    @SuppressWarnings("DuplicatedCode")
    public static Locale newLocale(String string) {
        Objects.requireNonNull(string, DwarfUtil.getExceptionString(ExceptionStringKey.FactoriesByString_0));
        if (!string.matches("[a-z]+(_[A-Z]+(_[a-zA-Z]+)?)?")) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.FactoriesByString_1));
        }

        StringTokenizer tokenizer = new StringTokenizer(string, "_");
        String language = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        String country = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        String variant = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

        return new Locale(language, country, variant);
    }

    // 禁止外部实例化。
    private FactoriesByString() {
    }
}
