package com.dwarfeng.dutil.develop.cfg.obs;

import com.dwarfeng.dutil.basic.prog.Observer;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;

/**
 * 配置观察器。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public interface ConfigObserver extends Observer {

    /**
     * 通知配置模型中指定的配置键的当前值发生了改变。
     *
     * @param configKey  指定的配置键。
     * @param oldValue   配置键的旧值。
     * @param newValue   配置键的新值。
     * @param validValue 配置键当前的有效值。
     */
    void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue, String validValue);

    /**
     * 通知配置模型中的配置键进行了清除。
     */
    void fireConfigKeyCleared();

    /**
     * 通知配置模型中指定的配置键进行了移除。
     *
     * @param configKey 指定的配置键。
     */
    void fireConfigKeyRemoved(ConfigKey configKey);

    /**
     * 通知配置模型中指定的配置键进行了添加。
     *
     * @param configKey 指定的配置键。
     */
    void fireConfigKeyAdded(ConfigKey configKey);

    /**
     * 通知配置模型中指定的配置键的固定属性发生了改变。
     *
     * @param configKey 指定的配置键。
     * @param oldValue  指定配置键的旧的固定属性。
     * @param newValue  指定的配置键的新的固定属性。
     */
    void fireConfigFirmPropsChanged(ConfigKey configKey, ConfigFirmProps oldValue, ConfigFirmProps newValue);
}
