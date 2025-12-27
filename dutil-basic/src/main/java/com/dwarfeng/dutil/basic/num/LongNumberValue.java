package com.dwarfeng.dutil.basic.num;

/**
 * Long 数字值。
 *
 * <p>
 * 数字值接口 {@link NumberValue} 的 <code>long</code> 实现。<br>
 * 该实现能够确保在数值转换为浮点数时的精度，但是不能记录浮点数。
 *
 * @author DwArFeng
 * @since 0.1.5-beta
 */
public class LongNumberValue implements NumberValue {

    /**
     * 数字值。
     */
    protected long value;

    /**
     * 生成一个具有指定的数值的 Long 数字值对象。
     *
     * @param value 指定的数字值。
     */
    public LongNumberValue(long value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double doubleValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (value ^ (value >>> 32));
        return result;
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
        if (!(obj instanceof LongNumberValue))
            return false;
        LongNumberValue other = (LongNumberValue) obj;
        return value == other.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LongNumberValue [value=" + value + "]";
    }
}
