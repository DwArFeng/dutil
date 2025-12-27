package com.dwarfeng.dutil.basic.str;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Objects;

/**
 * 默认标签。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class DefaultTag implements Tag {

    /**
     * 名称
     */
    protected final String name;
    /**
     * 描述
     */
    protected final String description;

    /**
     * 新建一个具有指定名称，指定描述的默认标签。
     *
     * @param name        指定的名称。
     * @param description 指定的描述。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DefaultTag(String name, String description) {
        Objects.requireNonNull(name, DwarfUtil.getExceptionString(ExceptionStringKey.DefaultTag_0));
        Objects.requireNonNull(name, DwarfUtil.getExceptionString(ExceptionStringKey.DefaultTag_1));
        this.name = name;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return this.description;
    }
}
