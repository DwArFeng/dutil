package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 浮点数检查器。
 *
 * <p>
 * 如果指定的值是浮点数，且处于指定的范围之内，则能够通过值检查器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
@SuppressWarnings("deprecation")
public class FloatConfigChecker implements ConfigChecker {

    private static final String REGEX_TO_MATCH = "[-+]?[0-9]*\\.?[0-9]+";

    private final float minValue;
    private final float maxValue;

    /**
     * 生成一个具有指定最大值和最小值的浮点数检查器。
     *
     * <p>
     * 当指定的 value 值是浮点数的格式， 则该 value 值有效。
     */
    public FloatConfigChecker() {
        this(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    /**
     * 生成一个具有指定最大值和最小值的浮点数检查器。
     *
     * <p>
     * 当指定的 value 值是浮点数的格式且该数组落在最大值和最小值之间（可以与最大值或最小值相等）， 则该 value 值有效。
     *
     * @param minValue 最小值。
     * @param maxValue 最大值。
     */
    public FloatConfigChecker(float minValue, float maxValue) {
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
            float f = Float.parseFloat(value);
            return minValue <= f && f <= maxValue;
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
        FloatConfigChecker other = (FloatConfigChecker) obj;
        if (Float.floatToIntBits(maxValue) != Float.floatToIntBits(other.maxValue))
            return false;
        return Float.floatToIntBits(minValue) == Float.floatToIntBits(other.minValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(maxValue);
        result = prime * result + Float.floatToIntBits(minValue);
        return result;
    }
}
