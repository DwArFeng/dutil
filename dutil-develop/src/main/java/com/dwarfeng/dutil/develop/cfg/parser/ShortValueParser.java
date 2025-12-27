package com.dwarfeng.dutil.develop.cfg.parser;

import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.Objects;

/**
 * 短整型数值解析器。
 *
 * <p>
 * 该解析器解析的是 <code>short</code> 对象。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class ShortValueParser extends IntegralValueParser implements ValueParser {

    /**
     * 生成一个十进制的短整型数解析器。
     */
    public ShortValueParser() {
        this(10);
    }

    /**
     * 生成一个具有指定进制的短整数解析器。
     *
     * @param radix 指定的进制。
     * @throws IllegalArgumentException 进制小于 {@link Character#MIN_RADIX} 或大于
     *                                  {@link Character#MAX_RADIX}。
     */
    protected ShortValueParser(int radix) {
        super(radix);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object parseValue(String value) {
        if (Objects.isNull(value))
            return null;
        try {
            return Short.parseShort(value, radix);
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
        if (!(object instanceof Short))
            return null;

        return Integer.toString((Short) object, radix);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShortValueParser other = (ShortValueParser) obj;
        return radix == other.radix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 41;
        int result = 1;
        result = prime * result + radix;
        return result;
    }
}
