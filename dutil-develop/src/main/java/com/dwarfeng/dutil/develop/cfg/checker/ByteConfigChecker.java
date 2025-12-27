package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 字节检查器。
 *
 * <p>
 * 如果指定的值是字节，则能够通过值检查器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
@SuppressWarnings("deprecation")
public class ByteConfigChecker implements ConfigChecker {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value) {
        if (Objects.isNull(value))
            return false;
        if (!value.matches("-*[0-9]+"))
            return false;
        try {
            Long l = Long.parseLong(value);
            return Byte.MIN_VALUE <= l && l <= Byte.MAX_VALUE;
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
        if (Objects.isNull(obj))
            return false;
        return obj.getClass() == ByteConfigChecker.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return ByteConfigChecker.class.hashCode() * 17;
    }
}
