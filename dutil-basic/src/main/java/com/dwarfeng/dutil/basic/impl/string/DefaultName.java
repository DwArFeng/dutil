package com.dwarfeng.dutil.basic.impl.string;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;
import com.dwarfeng.dutil.basic.stack.string.Name;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 默认名称。
 *
 * <p>
 * 名称接口的默认实现。
 *
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public record DefaultName(String name) implements Name {

    /**
     * 生成一个具有指定名称的默认名称接口。
     *
     * @param name 指定的名称。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public DefaultName {
        Objects.requireNonNull(name, BasicMessages.message(BasicMessageKey.NAME_VALUE_REQUIRED));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj))
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof DefaultName(String name1)))
            return false;
        return name1.equals(this.name());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String toString() {
        return this.name;
    }
}
