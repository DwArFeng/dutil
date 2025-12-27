package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Objects;

/**
 * 匹配值检查器。
 *
 * <p>
 * 用于检测目标值是否匹配指定的正则表达式。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
@SuppressWarnings("deprecation")
public class MatchConfigChecker implements ConfigChecker {

    private final String regex;

    /**
     * 创建一个匹配任意字符的匹配值检测器。
     *
     * <p>
     * 使用该方法创建的检测器只有当 value 为 <code>null</code>时才返回 <code>false</code>。
     */
    public MatchConfigChecker() {
        this("[\\s\\S]*");
    }

    /**
     * 创建一个拥有指定匹配表达式的匹配值检测器。
     *
     * @param regex 指定的匹配表达式（正则表达式）。
     */
    public MatchConfigChecker(String regex) {
        this.regex = regex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value) {
        if (Objects.isNull(value))
            return false;
        return value.matches(regex);
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
        MatchConfigChecker other = (MatchConfigChecker) obj;
        if (regex == null) {
            return other.regex == null;
        } else return regex.equals(other.regex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((regex == null) ? 0 : regex.hashCode());
        return result;
    }
}
