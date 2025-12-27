package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.cfg.struct.ConfigChecker;

import java.util.*;

/**
 * 默认配置模型。
 *
 * <p>
 * 配置模型的默认实现。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class DefaultConfigModel extends AbstractConfigModel {

    /**
     * 配置模型的当前值映射
     */
    protected final Map<ConfigKey, String> currentValueMap;

    /**
     * 配置模型的固定属性映射
     */
    protected final Map<ConfigKey, ConfigFirmProps> firmPropsMap;

    /**
     * 生成一个默认的，不包含任何配置条目的默认配置模型。
     */
    public DefaultConfigModel() {
        this(new ArrayList<>(), new HashMap<>(), new HashMap<>());
    }

    /**
     * 生成一个由指定配置条目数组成的默认配置模型。
     *
     * <p>
     * 生成的模型中，每个条目的现有值和默认值相等。
     *
     * @param configEntries 指定的配置条目数组。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 配置入口集合中至少一个入口无效。
     */
    public DefaultConfigModel(ConfigEntry[] configEntries) {
        this(Arrays.asList(configEntries), new HashMap<>(), new HashMap<>());
    }

    /**
     * 生成一个由指定配置条目集合组成的默认配置模型。
     *
     * <p>
     * 生成的模型中，每个条目的现有值和默认值相等。
     *
     * @param configEntries 指定的配置条目集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DefaultConfigModel(Collection<ConfigEntry> configEntries) {
        this(configEntries, new HashMap<>(), new HashMap<>());
    }

    /**
     * 生成一个由指定的配置条目，指定的当前值映射代理，指定的固定属性映射代理组合成的默认配置模型。
     *
     * <p>
     * 映射代理决定了配置以何种形式存储，如指定代理为 {@link LinkedHashMap} 可以保证配置模型拥有固定的迭代顺序。
     *
     * <p>
     * 需要注意的是，入口的代理映射应该是空的，如果映射代理是非空的，那它们会在存储配置数据之前先清空。
     *
     * @param configEntries   指定的配置条目集合。
     * @param currentValueMap 指定的当前值映射代理。
     * @param firmPropsMap    指定的固定属性映射代理。
     * @throws NullPointerException     入口参数 为 <code>null</code>。
     * @throws IllegalArgumentException 至少一个配置入口无效。
     */
    public DefaultConfigModel(Collection<ConfigEntry> configEntries, Map<ConfigKey, String> currentValueMap,
                              Map<ConfigKey, ConfigFirmProps> firmPropsMap) {
        Objects.requireNonNull(configEntries, DwarfUtil.getExceptionString(ExceptionStringKey.DefaultConfigModel_0));
        Objects.requireNonNull(currentValueMap, DwarfUtil.getExceptionString(ExceptionStringKey.DefaultConfigModel_4));
        Objects.requireNonNull(firmPropsMap, DwarfUtil.getExceptionString(ExceptionStringKey.DefaultConfigModel_5));

        this.currentValueMap = currentValueMap;
        this.firmPropsMap = firmPropsMap;

        for (ConfigEntry configEntry : configEntries) {
            if (ConfigUtil.nonValid(configEntry)) {
                throw new IllegalArgumentException(
                        DwarfUtil.getExceptionString(ExceptionStringKey.DefaultConfigModel_2));
            }
        }
        for (ConfigEntry configEntry : configEntries) {
            firmPropsMap.put(configEntry.getConfigKey(), configEntry.getConfigFirmProps());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        this.currentValueMap.clear();
        this.firmPropsMap.clear();
        fireConfigKeyCleared();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(ConfigKey configKey) {
        return firmPropsMap.containsKey(configKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentValue(ConfigKey configKey) {
        if (currentValueMap.containsKey(configKey)) {
            return currentValueMap.get(configKey);
        } else {
            String result = firmPropsMap.get(configKey).getDefaultValue();
            currentValueMap.put(configKey, result);
            return result;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<ConfigKey, String> getAllCurrentValue() {
        return Collections.unmodifiableMap(currentValueMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return firmPropsMap.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ConfigKey> keySet() {
        return Collections.unmodifiableSet(firmPropsMap.keySet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(ConfigEntry configEntry) {
        if (ConfigUtil.nonValid(configEntry))
            return false;

        if (firmPropsMap.containsKey(configEntry.getConfigKey())) {
            return false;
        }

        ConfigKey configKey = configEntry.getConfigKey();
        firmPropsMap.put(configKey, configEntry.getConfigFirmProps());
        currentValueMap.remove(configKey);

        fireConfigKeyAdded(configKey);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<ConfigEntry> configEntries) {
        Objects.requireNonNull(configEntries, DwarfUtil.getExceptionString(ExceptionStringKey.DefaultConfigModel_0));

        boolean aFlag = false;

        for (ConfigEntry configEntry : configEntries) {
            if (add(configEntry))
                aFlag = true;
        }

        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(ConfigKey configKey) {
        if (!firmPropsMap.containsKey(configKey))
            return false;

        firmPropsMap.remove(configKey);

        fireConfigKeyRemoved(configKey);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<ConfigKey> configKeys) {
        Objects.requireNonNull(configKeys, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTEXCONFIGMODEL_2));
        return batchRemove(configKeys, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<ConfigKey> configKeys) {
        Objects.requireNonNull(configKeys, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTEXCONFIGMODEL_2));
        return batchRemove(configKeys, false);
    }

    private boolean batchRemove(Collection<ConfigKey> configKeys, boolean aFlag) {
        List<ConfigKey> key2Remove = new ArrayList<>();
        boolean result = false;

        for (ConfigKey key : firmPropsMap.keySet()) {
            if (configKeys.contains(key) == aFlag) {
                key2Remove.add(key);
            }
        }

        for (ConfigKey configKey : key2Remove) {
            if (remove(configKey))
                result = true;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return firmPropsMap.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValueValid(ConfigKey configKey, String value) {
        if (Objects.isNull(configKey))
            return false;
        if (Objects.isNull(value))
            return false;

        if (!firmPropsMap.containsKey(configKey))
            return false;

        String currentValue = getCurrentValue(configKey);
        ConfigChecker configChecker = getConfigFirmProps(configKey).getConfigChecker();

        return configChecker.isValid(currentValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValidValue(ConfigKey configKey) {
        String defaultValue = getConfigFirmProps(configKey).getDefaultValue();
        String currentValue = getCurrentValue(configKey);

        return isValueValid(configKey, currentValue) ? currentValue : defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigFirmProps getConfigFirmProps(ConfigKey configKey) {
        return firmPropsMap.get(configKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setConfigFirmProps(ConfigKey configKey, ConfigFirmProps configFirmProps) {
        if (ConfigUtil.nonValid(configFirmProps))
            return false;
        if (!containsKey(configKey))
            return false;

        ConfigFirmProps oldOne = firmPropsMap.get(configKey);

        firmPropsMap.put(configKey, configFirmProps);

        fireConfigFirmPropsChanged(configKey, oldOne, configFirmProps);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setCurrentValue(ConfigKey configKey, String currentValue) {
        if (Objects.isNull(configKey))
            return false;
        if (Objects.isNull(currentValue))
            return false;

        if (!containsKey(configKey))
            return false;

        String oldOne = currentValueMap.get(configKey);

        currentValueMap.put(configKey, currentValue);

        fireCurrentValueChanged(configKey, oldOne, currentValue, getValidValue(configKey));

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setAllCurrentValue(Map<ConfigKey, String> map) {
        Objects.requireNonNull(map, DwarfUtil.getExceptionString(ExceptionStringKey.DefaultConfigModel_3));

        boolean aFlag = false;

        for (Map.Entry<ConfigKey, String> entry : map.entrySet()) {
            if (setCurrentValue(entry.getKey(), entry.getValue()))
                aFlag = true;
        }

        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetCurrentValue(ConfigKey configKey) {
        return setCurrentValue(configKey, getConfigFirmProps(configKey).getDefaultValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetAllCurrentValue() {
        boolean aFlag = false;

        for (ConfigKey configKey : keySet()) {
            if (resetCurrentValue(configKey))
                aFlag = true;
        }

        return aFlag;
    }
}
