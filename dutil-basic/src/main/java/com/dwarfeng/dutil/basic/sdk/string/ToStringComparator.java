package com.dwarfeng.dutil.basic.sdk.string;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;

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
        Objects.requireNonNull(o1, BasicMessages.message(BasicMessageKey.TO_STRING_COMPARATOR_OPERANDS_REQUIRED));
        Objects.requireNonNull(o2, BasicMessages.message(BasicMessageKey.TO_STRING_COMPARATOR_OPERANDS_REQUIRED));
        return o1.toString().compareTo(o2.toString());
    }
}
