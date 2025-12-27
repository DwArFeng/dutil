package com.dwarfeng.dutil.basic.str;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Comparator;
import java.util.Objects;

/**
 * 根据{@linkplain Name} 象的名称属性进行比较的比较器。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 * @deprecated 由于该类不符合命名规范，已经由 {@link NameComparator} 代替。
 */
public final class NameableComparator implements Comparator<Name> {

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Name o1, Name o2) {
        Objects.requireNonNull(o1, DwarfUtil.getExceptionString(ExceptionStringKey.NameableComparator_0));
        Objects.requireNonNull(o2, DwarfUtil.getExceptionString(ExceptionStringKey.NameableComparator_0));
        return o1.getName().compareTo(o2.getName());
    }
}
