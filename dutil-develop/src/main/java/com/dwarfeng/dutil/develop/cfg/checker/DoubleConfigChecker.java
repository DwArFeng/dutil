package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 双精度浮点检查器。
 *
 * <p>
 * 如果指定的值是双精度浮点，且处于指定的范围之内，则能够通过值检查器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
@SuppressWarnings("deprecation")
public class DoubleConfigChecker implements ConfigChecker {

    private static final String REGEX_TO_MATCH = "[-+]?[0-9]*\\.?[0-9]+";

    private final double minValue;
    private final double maxValue;

    /**
     * 生成一个双精度浮点检查器。
     *
     * <p>
     * 当指定的 value 值是双精度浮点的格式， 则该 value 值有效。
     *
     */
    public DoubleConfigChecker() {
        this(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    /**
     * 生成一个双精度浮点检查器。
     *
     * <p>
     * 当指定的 value 值是双精度浮点的格式且该数组落在最大值和最小值之间（可以与最大值或最小值相等）， 则该 value 值有效。
     *
     * @param minValue 最小值。
     * @param maxValue 最大值。
     */
    public DoubleConfigChecker(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value) {
        if (Objects.isNull(value))
            return false;
        if (!value.matches(REGEX_TO_MATCH))
            return false;
        try {
            double d = Double.parseDouble(value);
            return minValue <= d && d <= maxValue;
        } catch (NumberFormatException e) {
            return false;
        }
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
        DoubleConfigChecker other = (DoubleConfigChecker) obj;
        if (Double.doubleToLongBits(maxValue) != Double.doubleToLongBits(other.maxValue))
            return false;
        return Double.doubleToLongBits(minValue) == Double.doubleToLongBits(other.minValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(maxValue);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(minValue);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
