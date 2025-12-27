package com.dwarfeng.dutil.develop.cfg.struct;

import com.dwarfeng.dutil.develop.cfg.ConfigEntry;

/**
 * Ex 配置入口。
 *
 * <p>
 * 在原有的配置入口的基础上，增加了值解析器。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface ExconfigEntry extends ConfigEntry {

    /**
     * 获取该配置入口的值解析器。
     *
     * @return 该配置入口的值解析器。
     */
    ValueParser getValueParser();

    /**
     * 获取该配置入口的当前值。
     *
     * @return 该配置入口的当前值。
     */
    String getCurrentValue();
}
