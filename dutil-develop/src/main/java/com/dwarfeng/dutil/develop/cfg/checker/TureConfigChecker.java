package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 恒真式配置值检查器。
 *
 * <p>
 * 该检查器对一个配置中的一切值的检查恒返回 <code>true</code>，换句话说， 该配置值检查器允许一切值。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 * @deprecated 该检查器的定义与 {@link NonNullConfigChecker} 重复，不推荐使用此方法。
 */
@Deprecated
public class TureConfigChecker implements ConfigChecker {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value) {
        return true;
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
        return obj.getClass() == TureConfigChecker.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return TureConfigChecker.class.hashCode() * 17;
    }
}
