package com.dwarfeng.dutil.develop.cfg.parser;

import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.Objects;

/**
 * 地区值解析器。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 * @deprecated 该类由于命名不规范，已经被 {@link LocaleValueParser} 代替。
 */
@Deprecated
public class LocaleParser implements ValueParser {

    private final LocaleValueParser parser = new LocaleValueParser();

    /**
     * {@inheritDoc}
     */
    @Override
    public Object parseValue(String value) {
        return parser.parseValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String parseObject(Object object) {
        return parser.parseObject(object);
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
        return obj.getClass() == LocaleParser.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return LocaleParser.class.hashCode() * 17;
    }
}
