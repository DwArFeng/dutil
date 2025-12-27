package com.dwarfeng.dutil.basic.prog;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Objects;

/**
 * 默认标签过滤器。
 *
 * <p>
 * 标签过滤器接口的默认实现。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class DefaultTagFilter<T> extends DefaultNameFilter<T> implements TagFilter<T> {

    /**
     * 过滤器的描述。
     */
    protected final String description;

    /**
     * 根据指定的名称，指定的描述，指定的代理过滤器生成一个新的默认标签过滤器。
     *
     * @param name           指定的名称。
     * @param description    指定的描述。
     * @param delegateFilter 指定的代理过滤器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DefaultTagFilter(String name, String description, Filter<? super T> delegateFilter) {
        super(name, delegateFilter);

        Objects.requireNonNull(description, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTTAGFILTER_0));
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof DefaultTagFilter))
            return false;
        DefaultTagFilter<?> other = (DefaultTagFilter<?>) obj;
        if (description == null) {
            return other.description == null;
        } else return description.equals(other.description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DefaultTagFilter [description=" + description + ", name=" + name + ", delegateFilter=" + delegateFilter
                + "]";
    }
}
