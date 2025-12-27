package com.dwarfeng.dutil.basic.str;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Comparator;
import java.util.Objects;

/**
 * 对象的 toString 比较器，以两个对象的<code>toString()</code>方法比较其大小。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class ToStringComparator<T> implements Comparator<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(T o1, T o2) {
        Objects.requireNonNull(o1, DwarfUtil.getExceptionString(ExceptionStringKey.TOSTRINGCOMPARATOR_0));
        Objects.requireNonNull(o2, DwarfUtil.getExceptionString(ExceptionStringKey.TOSTRINGCOMPARATOR_0));
        return o1.toString().compareTo(o2.toString());
    }
}
