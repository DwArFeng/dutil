package com.dwarfeng.dutil.develop.cfg.parser;

import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.Objects;

/**
 * 双精度浮点数解析器。
 *
 * <p>
 * 该解析器解析的是 <code>double</code> 对象。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class DoubleValueParser implements ValueParser {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object parseValue(String value) {
        if (Objects.isNull(value))
            return false;

        try {
            return Double.parseDouble(value);
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
        if (!(object instanceof Double))
            return null;

        return Double.toString((double) object);
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
        return obj.getClass() == DoubleValueParser.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return DoubleValueParser.class.hashCode() * 17;
    }
}
