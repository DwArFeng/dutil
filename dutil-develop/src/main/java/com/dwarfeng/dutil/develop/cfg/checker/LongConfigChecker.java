package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 长整数检查器。
 *
 * <p>
 * 如果指定的值是长整数，且处于指定的范围之内，则能够通过值检查器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
@SuppressWarnings("deprecation")
public class LongConfigChecker implements ConfigChecker {

    private static final String REGEX_TO_MATCH = "-*[0-9]+";

    private final long minValue;
    private final long maxValue;

    /**
     * 生成一个长整数检查器。
     *
     * <p>
     * 当指定的 value 值是长整数的格式，则该 value 值有效。
     */
    public LongConfigChecker() {
        this(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /**
     * 生成一个具有指定最大值和最小值的长整数检查器。
     *
     * <p>
     * 当指定的 value 值是长整数的格式且该数组落在最大值和最小值之间（可以与最大值或最小值相等）， 则该 value 值有效。
     *
     * @param minValue 指定的最小值。
     * @param maxValue 指定的最大值。
     */
    public LongConfigChecker(long minValue, long maxValue) {
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
            long l = Long.parseLong(value);
            return minValue <= l && l <= maxValue;
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
        LongConfigChecker other = (LongConfigChecker) obj;
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
        result = prime * result + (int) (maxValue ^ (maxValue >>> 32));
        result = prime * result + (int) (minValue ^ (minValue >>> 32));
        return result;
    }
}
