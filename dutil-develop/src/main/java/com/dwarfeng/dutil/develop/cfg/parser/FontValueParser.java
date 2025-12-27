package com.dwarfeng.dutil.develop.cfg.parser;

import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.awt.*;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 字体值转化器。
 *
 * <p>
 * 该解析器解析的是 <b> {@link java.awt.Font}</b> 对象。
 *
 * @author DwArFeng
 * @since 0.1.5-beta
 */
public final class FontValueParser implements ValueParser {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object parseValue(String value) {
        if (Objects.isNull(value))
            return null;

        try {
            StringTokenizer st = new StringTokenizer(value, "-");
            String name = st.nextToken();
            int style = Integer.parseInt(st.nextToken());
            int size = Integer.parseInt(st.nextToken());
            return new Font(name, style, size);
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
        if (!(object instanceof Font))
            return null;

        Font font = (Font) object;
        return String.format("%s-%d-%d", font.getName(), font.getStyle(), font.getSize());
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
        return obj.getClass() == FontValueParser.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return FontValueParser.class.hashCode() * 17;
    }
}
