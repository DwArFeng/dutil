package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 整数检查器。
 *
 * <p>
 * 如果指定的值是整数，且处于指定的范围之内，则能够通过值检查器。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
@SuppressWarnings("deprecation")
public class IntegerConfigChecker implements ConfigChecker {

    private static final String REGEX_TO_MATCH = "-*[0-9]+";

    private final int minValue;
    private final int maxValue;

    /**
     * 生成一个整数检查器。
     *
     * <p>
     * 当指定的 value 值是整数的格式，则该 value 值有效。
     */
    public IntegerConfigChecker() {
        this(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * 生成一个具有指定最大值和最小值的整数检查器。
     *
     * <p>
     * 当指定的 value 值是整数的格式且该数组落在最大值和最小值之间（可以与最大值或最小值相等）， 则该 value 值有效。
     *
     * @param minValue 最小值。
     * @param maxValue 最大值。
     */
    public IntegerConfigChecker(int minValue, int maxValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
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
            int i = Integer.parseInt(value);
            return minValue <= i && i <= maxValue;
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
        IntegerConfigChecker other = (IntegerConfigChecker) obj;
        if (maxValue != other.maxValue)
            return false;
        return minValue == other.minValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + maxValue;
        result = prime * result + minValue;
        return result;
    }
}
