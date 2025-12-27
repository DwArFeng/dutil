package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 恒假配置值检查器。
 *
 * <p>
 * 该配置值检查器对于任意的值，均返回 <code>false</code>，换句话说， 该配置值检查器拒绝一切值。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
@SuppressWarnings("deprecation")
public class FalseConfigChecker implements ConfigChecker {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value) {
        return false;
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
        return obj.getClass() == FalseConfigChecker.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return FalseConfigChecker.class.hashCode() * 17;
    }
}
