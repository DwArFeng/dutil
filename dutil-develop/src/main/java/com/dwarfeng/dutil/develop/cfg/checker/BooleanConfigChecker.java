package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 布尔值检查器。
 *
 * <p>
 * 只要待检测的文本解析为大写之后，为 <code>TRUE</code> 或者 <code>FALSE</code> 两者之一， 即为有效。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
@SuppressWarnings("deprecation")
public class BooleanConfigChecker implements ConfigChecker {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value) {
        if (Objects.isNull(value))
            return false;
        String str = value.toUpperCase();
        return str.equals("TRUE") || str.equals("FALSE");
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
        return obj.getClass() == BooleanConfigChecker.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return BooleanConfigChecker.class.hashCode() * 17;
    }
}
