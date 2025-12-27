package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 类检查器。
 *
 * <p>
 * 如果指定的值是一个规范的公共的类的名称，则返回 <code>true</code>;
 *
 * <p>
 * 注意，该检查器只适用于检测纯英文字符的包，带有其它字符的包该检查器不支持，验证时一律返回 <code>false</code>。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
@SuppressWarnings("deprecation")
public class ClassConfigChecker implements ConfigChecker {

    private static final String REGEX_TO_MATCH = "^[a-z,A-Z][a-z,A-Z,0-9]*(\\.[a-z,A-Z][a-z,A-Z,0-9]*)*$";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value) {
        if (Objects.isNull(value)) {
            return false;
        }
        return value.matches(REGEX_TO_MATCH);
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
        return obj.getClass() == ClassConfigChecker.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return ClassConfigChecker.class.hashCode() * 17;
    }
}
