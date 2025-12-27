package com.dwarfeng.dutil.develop.cfg.parser;

import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.Objects;

/**
 * 整型数值解析器。
 *
 * <p>
 * 该解析器解析的是 <code>int</code> 对象。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class IntegerValueParser extends IntegralValueParser implements ValueParser {

    /**
     * 生成一个十进制的整型数值解析器。
     */
    public IntegerValueParser() {
        this(10);
    }

    /**
     * 生成一个进制为指定值的整型数值解析器。
     *
     * @param radix 指定的进制。
     * @throws IllegalArgumentException 进制小于 {@link Character#MIN_RADIX} 或大于
     *                                  {@link Character#MAX_RADIX}。
     */
    public IntegerValueParser(int radix) {
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
            return Integer.parseInt(value, radix);
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
        if (!(object instanceof Integer))
            return null;
        return Integer.toString((int) object, radix);
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
        IntegerValueParser other = (IntegerValueParser) obj;
        return radix == other.radix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + radix;
        return result;
    }
}
