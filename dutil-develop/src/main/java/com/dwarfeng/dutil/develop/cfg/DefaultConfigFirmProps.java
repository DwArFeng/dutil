package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.cfg.struct.ConfigChecker;

import java.util.Objects;

/**
 * 默认配置固定属性。
 *
 * <p>
 * 配置固定属性接口的默认实现。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class DefaultConfigFirmProps implements ConfigFirmProps {

    private final ConfigChecker ConfigChecker;
    private final String defaultValue;

    /**
     * 生成一个新的默认配置固定属性实例。
     *
     * @param configChecker 指定的配置值检查器。
     * @param defaultValue  指定的默认配置值。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 指定的默认值无法通过指定的配置值检查器的检验。
     */
    public DefaultConfigFirmProps(ConfigChecker configChecker, String defaultValue) {
        Objects.requireNonNull(configChecker,
                DwarfUtil.getExceptionString(ExceptionStringKey.DefaultConfigFirmProps_0));
        Objects.requireNonNull(defaultValue, DwarfUtil.getExceptionString(ExceptionStringKey.DefaultConfigFirmProps_1));

        if (configChecker.nonValid(defaultValue)) {
            throw new IllegalArgumentException(
                    DwarfUtil.getExceptionString(ExceptionStringKey.DefaultConfigFirmProps_2));
        }

        this.ConfigChecker = configChecker;
        this.defaultValue = defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigChecker getConfigChecker() {
        return ConfigChecker;
    }
}
