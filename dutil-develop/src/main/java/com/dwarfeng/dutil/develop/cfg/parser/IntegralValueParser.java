package com.dwarfeng.dutil.develop.cfg.parser;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

/**
 * 整数值解析器。
 *
 * <p>
 * 该解析器解析的是具有进制的整数对象。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public abstract class IntegralValueParser implements ValueParser {

    /**
     * 该解析器使用的进制。
     */
    protected final int radix;

    /**
     * 生成一个具有指定进制的整数值解析器。
     *
     * @param radix 指定的进制。
     * @throws IllegalArgumentException 进制小于 {@link Character#MIN_RADIX} 或大于
     *                                  {@link Character#MAX_RADIX}。
     */
    protected IntegralValueParser(int radix) {
        if (Character.MIN_RADIX > radix || Character.MAX_RADIX < radix) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.INTEGRALVALUEPARSER_0), radix));
        }
        this.radix = radix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract Object parseValue(String value);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String parseObject(Object object);
}
