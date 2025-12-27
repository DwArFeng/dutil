package com.dwarfeng.dutil.develop.i18n;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Locale;
import java.util.Objects;

/**
 * 抽象国际化信息。
 *
 * <p>
 * 国际化信息的抽象实现。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public abstract class AbstractI18nInfo implements I18nInfo {

    /**
     * 该国际化信息的键。
     */
    protected final Locale key;
    /**
     * 该国际化信息的名称。
     */
    protected final String name;

    /**
     * 生成一个具有指定的键，指定的名称的国际化信息。
     *
     * @param key  指定的键。
     * @param name 指定的名称。
     * @throws NullPointerException 入口参数 <code>name</code> 为 <code>null</code>。
     */
    public AbstractI18nInfo(Locale key, String name) {
        Objects.requireNonNull(name, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTI18NINFO_1));

        this.key = key;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Locale getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }
}
