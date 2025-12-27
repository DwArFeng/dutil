package com.dwarfeng.dutil.basic.num;

import java.math.BigDecimal;

/**
 * 默认数字值。
 *
 * <p>
 * 数字值接口 {@link NumberValue} 的 BigDecimal 实现。<br>
 * 该实现在数字类型转换，尤其是转换到整形数的时候，可能会引发精度方面的误差。
 *
 * <p>
 * 当不希望发生转换误差时，有以下方法可以提供参考：<br>
 * <blockquote>1、当您希望只使用该接口的整数功能时，可以使用用 {@link IntNumberValue} 或者 {@link LongNumberValue} 这些类使用
 * 整型数来存储数值，它不能记录浮点数，但是在转换成浮点数时，可以保证精度。<br>
 * 2、当您希望该接口能够存储浮点数，同时希望浮点数在转换成整型数时保持精度时，可以使用 {@link BigDecimalNumberValue} 它用
 * {@link BigDecimal} 记录数值，可以保证转换时的精度， 但是它的内存开销要高很多。</blockquote>
 *
 * @author DwArFeng
 * @since 0.1.5-beta
 */
public class DefaultNumberValue implements NumberValue {

    public static final DefaultNumberValue ZERO = new DefaultNumberValue();
    public static final DefaultNumberValue ONE = new DefaultNumberValue();

    /**
     * 该多台值接口的值
     */
    protected final double val;

    /**
     * 生成一个大小为 0 的快速值接口。
     *
     * <p>
     * 该构造器方法的优先度小于静态字段 {@link DefaultNumberValue#ZERO}。
     */
    public DefaultNumberValue() {
        this(0);
    }

    /**
     * 生成一个具有指定值的快速多态值接口对象。
     *
     * @param val 指定的值。
     */
    public DefaultNumberValue(double val) {
        this.val = val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double doubleValue() {
        return val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof DefaultNumberValue))
            return false;
        DefaultNumberValue v = (DefaultNumberValue) obj;
        return v.val == val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Double.hashCode(val) * 17;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DefaultNumberValue [val=" + val + "]";
    }
}
