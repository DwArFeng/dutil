package com.dwarfeng.dutil.develop.cfg.obs;

import com.dwarfeng.dutil.basic.prog.Observer;
import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

/**
 * Ex 配置模型观察器。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface ExconfigObserver extends Observer {

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
     * @param configKey       指定的配置键。
     * @param configFirmProps 指定的配置键对应的固定属性。
     * @param valueParser     指定的配置键对应的值解析器。
     * @param currentValue    指定的配置键对应的当前值。
     */
    void fireConfigKeyRemoved(
            ConfigKey configKey, ConfigFirmProps configFirmProps, ValueParser valueParser, String currentValue
    );

    /**
     * 通知配置模型中指定的配置键进行了添加。
     *
     * @param configKey       指定的配置键。
     * @param configFirmProps 指定的配置键对应的固定属性。
     * @param valueParser     指定的配置键对应的值解析器。
     * @param currentValue    指定的配置键对应的当前值。
     */
    void fireConfigKeyAdded(
            ConfigKey configKey, ConfigFirmProps configFirmProps, ValueParser valueParser, String currentValue
    );

    /**
     * 通知配置模型中指定的配置键的固定属性发生了改变。
     *
     * @param configKey 指定的配置键。
     * @param oldValue  指定配置键的旧的固定属性。
     * @param newValue  指定的配置键的新的固定属性。
     */
    void fireConfigFirmPropsChanged(ConfigKey configKey, ConfigFirmProps oldValue, ConfigFirmProps newValue);

    /**
     * 通知配置模型中指定的配置键的值解析器发生了改变。
     *
     * @param configKey 指定的配置键。
     * @param oldValue  指定的配置键对应的旧的值解析器。
     * @param newValue  指定的配置键对应的新的值解析器。
     */
    void fireValueParserChanged(ConfigKey configKey, ValueParser oldValue, ValueParser newValue);
}
