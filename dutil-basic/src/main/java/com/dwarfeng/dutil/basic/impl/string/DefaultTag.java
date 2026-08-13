package com.dwarfeng.dutil.basic.impl.string;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;
import com.dwarfeng.dutil.basic.stack.string.Tag;

import java.util.Objects;

/**
 * 默认标签。
 *
 * @param name        名称
 * @param description 描述
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public record DefaultTag(String name, String description) implements Tag {

    /**
     * 新建一个具有指定名称，指定描述的默认标签。
     *
     * @param name        指定的名称。
     * @param description 指定的描述。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DefaultTag {
        Objects.requireNonNull(name, BasicMessages.message(BasicMessageKey.TAG_NAME_REQUIRED));
        Objects.requireNonNull(name, BasicMessages.message(BasicMessageKey.TAG_DESCRIPTION_REQUIRED));
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
    public String description() {
        return this.description;
    }
}
