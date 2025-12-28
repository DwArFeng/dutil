package com.dwarfeng.dutil.develop.cfg.parser;

import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.Locale;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 地区值解析器。
 *
 * @author DwArFeng
 * @since 0.1.2-beta
 */
public class LocaleValueParser implements ValueParser {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public Object parseValue(String value) {
        if (Objects.isNull(value))
            return null;
        if (!value.matches("[a-z]+(_[A-Z]+(_[a-zA-Z]+)?)?"))
            return null;

        StringTokenizer tokenizer = new StringTokenizer(value, "_");
        String language = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        String country = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        String variant = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

        return new Locale(language, country, variant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String parseObject(Object object) {
        if (Objects.isNull(object))
            return null;
        if (!(object instanceof Locale))
            return null;

        Locale locale = (Locale) object;
        return String.format("%s_%s_%s", locale.getLanguage(), locale.getCountry(), locale.getVariant());
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
        return obj.getClass() == LocaleValueParser.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return LocaleValueParser.class.hashCode() * 17;
    }
}
