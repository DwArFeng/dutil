package com.dwarfeng.dutil.develop.cfg.parser;

import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.Objects;

/**
 * 浮点数解析器。
 *
 * <p>
 * 该解析器解析的是 <code>float</code> 对象。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class FloatValueParser implements ValueParser {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object parseValue(String value) {
        if (Objects.isNull(value))
            return null;

        try {
            return Float.parseFloat(value);
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
        if (!(object instanceof Float))
            return null;

        return Float.toString((float) object);
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
        return obj.getClass() == FloatValueParser.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return FloatValueParser.class.hashCode() * 17;
    }
}
