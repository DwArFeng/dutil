package com.dwarfeng.dutil.basic.num;

/**
 * Integer 数字值。
 *
 * <p>
 * 数字值接口 {@link NumberValue} 的 <code>integer</code> 实现。<br>
 * 该实现能够确保在数值转换为浮点数时的精度，但是不能记录浮点数。
 *
 * @author DwArFeng
 * @since 0.1.5-beta
 */
public class IntNumberValue implements NumberValue {

    /**
     * 数字值。
     */
    protected int value;

    /**
     * 生成一个具有指定的数值的 Integer 数字值对象。
     *
     * @param value 指定的数字值。
     */
    public IntNumberValue(int value) {
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
        result = prime * result + value;
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
        if (!(obj instanceof IntNumberValue))
            return false;
        IntNumberValue other = (IntNumberValue) obj;
        return value == other.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IntNumberValue [value=" + value + "]";
    }
}
