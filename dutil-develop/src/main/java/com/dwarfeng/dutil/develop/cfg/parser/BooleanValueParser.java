package com.dwarfeng.dutil.develop.cfg.parser;

import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.Objects;

/**
 * 布尔值解析器。
 *
 * <p>
 * 该解析器获得的是 <code>boolean</code> 对象。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class BooleanValueParser implements ValueParser {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object parseValue(String value) {
        if (Objects.isNull(value))
            return null;

        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String parseObject(Object object) {
        if (Objects.isNull(object))
            return null;
        if (!(object instanceof Boolean))
            return null;

        return Boolean.toString((boolean) object);
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
        return obj.getClass() == BooleanValueParser.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return BooleanValueParser.class.hashCode() * 17;
    }
}
