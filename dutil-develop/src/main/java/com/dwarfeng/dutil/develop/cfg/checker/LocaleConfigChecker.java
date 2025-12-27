package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

/**
 * 国家/地区配置值检查器。
 *
 * <p>
 * 检查一个值是否符合标准的国家/地区规范。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
@SuppressWarnings("deprecation")
public class LocaleConfigChecker implements ConfigChecker {

    private final MatchConfigChecker delegate = new MatchConfigChecker("[a-z]+(_[A-Z]+(_[a-zA-Z]+)?)?");

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value) {
        return delegate.isValid(value);
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
        LocaleConfigChecker other = (LocaleConfigChecker) obj;
        if (delegate == null) {
            return other.delegate == null;
        } else return delegate.equals(other.delegate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((delegate == null) ? 0 : delegate.hashCode());
        return result;
    }
}
