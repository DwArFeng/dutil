package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 短整数检查器。
 *
 * <p>
 * 如果指定的值是短整数，且处于指定的范围之内，则能够通过值检查器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
@SuppressWarnings("deprecation")
public class ShortConfigChecker implements ConfigChecker {

    private static final String REGEX_TO_MATCH = "-*[0-9]+";

    private final short minValue;
    private final short maxValue;

    /**
     * 生成一个短整数检查器。
     *
     * <p>
     * 当指定的 value 值是短整数的格式，则该 value 值有效。
     */
    public ShortConfigChecker() {
        this(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    /**
     * 生成一个具有指定最大值和最小值的短整数检查器。
     *
     * <p>
     * 当指定的 value 值是短整数的格式且该数组落在最大值和最小值之间（可以与最大值或最小值相等）， 则该 value 值有效。
     *
     * @param minValue 最小值。
     * @param maxValue 最大值。
     */
    public ShortConfigChecker(short minValue, short maxValue) {
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
            short s = Short.parseShort(value);
            return minValue <= s && s <= maxValue;
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
        ShortConfigChecker other = (ShortConfigChecker) obj;
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
