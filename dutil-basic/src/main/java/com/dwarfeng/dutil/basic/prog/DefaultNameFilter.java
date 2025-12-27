package com.dwarfeng.dutil.basic.prog;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Objects;

/**
 * 默认名称过滤器。
 *
 * <p>
 * 默认过滤器的抽象实现。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class DefaultNameFilter<T> implements NameFilter<T> {

    /**
     * 过滤器的名称
     */
    protected final String name;
    /**
     * 过滤器的代理过滤器
     */
    protected final Filter<? super T> delegateFilter;

    /**
     * 使用指定的名称和指定的代理过滤器构建一个新的抽象名称过滤器。
     *
     * @param name           指定的名称。
     * @param delegateFilter 指定的代理过滤器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DefaultNameFilter(String name, Filter<? super T> delegateFilter) {
        Objects.requireNonNull(name, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTNAMEFILTER_0));
        Objects.requireNonNull(delegateFilter, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTNAMEFILTER_1));

        this.name = name;
        this.delegateFilter = delegateFilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(T t) {
        return delegateFilter.accept(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((delegateFilter == null) ? 0 : delegateFilter.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
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
        if (!(obj instanceof DefaultNameFilter))
            return false;
        DefaultNameFilter<?> other = (DefaultNameFilter<?>) obj;
        if (delegateFilter == null) {
            if (other.delegateFilter != null)
                return false;
        } else if (!delegateFilter.equals(other.delegateFilter))
            return false;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DefaultNameFilter [name=" + name + ", delegateFilter=" + delegateFilter + "]";
    }
}
