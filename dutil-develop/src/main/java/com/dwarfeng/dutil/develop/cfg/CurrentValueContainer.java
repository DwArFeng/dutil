package com.dwarfeng.dutil.develop.cfg;

import java.util.Map;

/**
 * 当前值容器。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface CurrentValueContainer {

    /**
     * 获取容器中的当前值。
     *
     * <p>
     * 当前值可能合法，也可能不合法。
     *
     * <p>
     * 如果容器中不存在指定的配置键或者入口配置键为 <code>null</code>，则返回 <code>null</code>。
     *
     * @param configKey 指定的配置键。
     * @return 模型中指定配置键对应的当前值。
     */
    String getCurrentValue(ConfigKey configKey);

    /**
     * 获取容器中的配置键与当前值的映射，这个映射包含容器中的所有当前值。
     *
     * <p>
     * 获取的映射是只读的。
     *
     * @return 容器中的配置键与当前值映射。
     */
    Map<ConfigKey, String> getAllCurrentValue();

    /**
     * 设置模型中指定配置键的当前值。
     *
     * <p>
     * 当指定的配置键为 <code>null</code>，或指定的配置键不存在于当前的模型时，不进行任何操作。 <br>
     * 当指定的 currentValue 为 <code>null</code>时，不进行任何操作。
     *
     * @param configKey    指定的配置键。
     * @param currentValue 指定的当前值。
     * @return 该操作是否对模型产生了变更。
     */
    boolean setCurrentValue(ConfigKey configKey, String currentValue);

    /**
     * 设置模型中指定配置键的当前值。
     *
     * <p>
     * 该操作试图将指定的映射中的所有的配置键-当前值设置到模型中。
     *
     * <p>
     * 当指定的配置键为 <code>null</code>，或指定的配置键不存在于当前的模型时，不进行任何操作。
     *
     * @param map 指定的配置键-当前值映射。
     * @return 该操作是否对模型产生了改变。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    boolean setAllCurrentValue(Map<ConfigKey, String> map);
}
