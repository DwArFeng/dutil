package com.dwarfeng.dutil.develop.setting.info;

import com.dwarfeng.dutil.develop.setting.AbstractSettingInfo;

import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 国家/地区配置信息。
 *
 * <p>
 * 提供合标准的国家/地区格式检查与值转换。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class LocaleSettingInfo extends AbstractSettingInfo {

    private static final String DELIM = "_";
    private static final String MATCH_REGEX = "[a-z]+((_[A-Z]+(_[a-zA-Z]+)?)|(_[A-Z]?(_[a-zA-Z]+)+))?";

    /**
     * 生成一个新的国家/地区配置信息。
     *
     * @param defaultValue 指定的默认值。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 指定的默认值不能通过自身检查。
     */
    public LocaleSettingInfo(String defaultValue) throws NullPointerException, IllegalArgumentException {
        super(defaultValue);
        checkDefaultValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return LocaleSettingInfo.class.hashCode() * 61 + defaultValue.hashCode() * 17;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (Objects.isNull(obj))
            return false;
        if (!(obj.getClass() == LocaleSettingInfo.class))
            return false;

        LocaleSettingInfo that = (LocaleSettingInfo) obj;
        return Objects.equals(this.defaultValue, that.defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LocaleSettingInfo [defaultValue=" + defaultValue + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isNonNullValid(String value) {
        return value.matches(MATCH_REGEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object parseValidValue(String value) {
        StringTokenizer tokenizer = new StringTokenizer(value, DELIM);

        String language = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        String country = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        String variant = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

        return new Locale(language, country, variant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String parseNonNullObject(Object object) {
        if (!(object instanceof Locale))
            return null;

        Locale locale = (Locale) object;

        String language = locale.getLanguage();
        if (language.isEmpty())
            return null;

        String country = locale.getCountry();
        String variant = locale.getVariant();

        StringBuilder sb = new StringBuilder();
        sb.append(language);

        if (country.isEmpty() && variant.isEmpty()) {
            return sb.toString();
        } else if (country.isEmpty() && !variant.isEmpty()) {
            sb.append(DELIM);
            sb.append(DELIM);
            sb.append(variant);
            return sb.toString();
        } else if (!country.isEmpty() && variant.isEmpty()) {
            sb.append(DELIM);
            sb.append(country);
            return sb.toString();
        } else {
            sb.append(DELIM);
            sb.append(country);
            sb.append(DELIM);
            sb.append(variant);
            return sb.toString();
        }

    }
}
