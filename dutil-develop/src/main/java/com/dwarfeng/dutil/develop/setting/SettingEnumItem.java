package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 配置枚举条目。
 *
 * <p>
 * 该条目专门用于存放配置条目的枚举类。该类可以使枚举中的配置条目快速的添加到配置处理器中。
 *
 * <p>
 * 同时，该条目实现 <code>Name</code> 接口，方便使用枚举条目进行查询。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface SettingEnumItem extends Name {

    /**
     * 获取条目中的名称。
     *
     * <p>
     * 该名称作为配置处理器的键值。也可以用于配置处理器的查询。
     *
     * @return 条目中的名称。
     */
    @Override
    String getName();

    /**
     * 获取条目中的配置信息。
     *
     * <p>
     * 该配置信息中的默认值同时用作配置处理器的当前值。
     *
     * @return 条目中的配置信息。
     */
    SettingInfo getSettingInfo();
}
