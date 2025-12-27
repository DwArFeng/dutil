package com.dwarfeng.dutil.develop.cfg.parser;

import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.io.File;
import java.util.Objects;

/**
 * 文件值转化器。
 *
 * <p>
 * 该解析器解析的是 <b> {@link java.io.File}</b> 对象。
 *
 * @author DwArFeng
 * @since 0.1.5-beta
 */
public class FileValueParser implements ValueParser {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object parseValue(String value) {
        if (Objects.isNull(value))
            return null;

        try {
            return new File(value);
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
        if (!(object instanceof File))
            return null;

        return ((File) object).getAbsolutePath();
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
        return obj.getClass() == FileValueParser.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return FileValueParser.class.hashCode() * 17;
    }
}
