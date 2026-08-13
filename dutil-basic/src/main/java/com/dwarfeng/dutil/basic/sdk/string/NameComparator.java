package com.dwarfeng.dutil.basic.sdk.string;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;
import com.dwarfeng.dutil.basic.stack.string.Name;

import java.util.Comparator;
import java.util.Objects;

/**
 * 根据{@linkplain Name} 象的名称属性进行比较的比较器。
 *
 * @author DwArFeng
 * @since 0.1.3-beta
 */
public final class NameComparator implements Comparator<Name> {

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Name o1, Name o2) {
        Objects.requireNonNull(o1, BasicMessages.message(BasicMessageKey.NAME_COMPARATOR_OPERANDS_REQUIRED));
        Objects.requireNonNull(o2, BasicMessages.message(BasicMessageKey.NAME_COMPARATOR_OPERANDS_REQUIRED));
        return o1.name().compareTo(o2.name());
    }
}
