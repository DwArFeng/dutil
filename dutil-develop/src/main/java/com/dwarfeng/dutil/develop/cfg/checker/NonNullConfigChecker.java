package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 非空检查器。
 *
 * <p>
 * 只要文本不是 <code>null</code>，就是有效的。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
@SuppressWarnings("deprecation")
public class NonNullConfigChecker implements ConfigChecker {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value) {
        return Objects.nonNull(value);
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
        return obj.getClass() == NonNullConfigChecker.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return NonNullConfigChecker.class.hashCode() * 17;
    }
}
