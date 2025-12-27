package com.dwarfeng.dutil.develop.cfg.obs;

import com.dwarfeng.dutil.develop.cfg.ConfigFirmProps;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

/**
 * Ex 配置模型适配器。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public abstract class ExconfigAdapter implements ExconfigObserver {

    @Override
    public void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue, String validValue) {
    }

    @Override
    public void fireConfigKeyCleared() {
    }

    @Override
    public void fireConfigKeyRemoved(
            ConfigKey configKey, ConfigFirmProps configFirmProps, ValueParser valueParser, String currentValue
    ) {
    }

    @Override
    public void fireConfigKeyAdded(
            ConfigKey configKey, ConfigFirmProps configFirmProps, ValueParser valueParser, String currentValue
    ) {
    }

    @Override
    public void fireConfigFirmPropsChanged(
            ConfigKey configKey, ConfigFirmProps oldValue, ConfigFirmProps newValue
    ) {
    }

    @Override
    public void fireValueParserChanged(
            ConfigKey configKey, ValueParser oldValue, ValueParser newValue
    ) {
    }
}
