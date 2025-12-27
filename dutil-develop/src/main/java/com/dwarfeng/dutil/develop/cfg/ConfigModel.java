package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.basic.prog.ObserverSet;
import com.dwarfeng.dutil.develop.cfg.obs.ConfigObserver;

import java.util.Collection;
import java.util.Set;

/**
 * 配置模型。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface ConfigModel extends CurrentValueContainer, ObserverSet<ConfigObserver> {

    /**
     * 清空配置模型中的所有记录（可选操作）。
     *
     * @throws UnsupportedOperationException 如果该配置模型不支持此操作。
     */
    void clear();

    /**
     * 如果此模型包含指定配置键，则返回 <code>true</code>。
     *
     * @param configKey 指定的配置键。
     * @return 此配置模型是否包含指定的配置键。
     */
    boolean containsKey(ConfigKey configKey);

    /**
     * 如果该配置模型中不包含任何记录，则返回 <code>true</code>。
     *
     * @return 该配置模型中是否不包含任何记录。
     */
    boolean isEmpty();

    /**
     * 返回包含此模型配置键的<code>Set</code>视图。
     *
     * <p>
     * 该 <code>Set</code>视图受模型支持，所以模型中所作的更改均可以在此视图中反应出来。
     * 该<code>Set</code>视图是只读的，调用其编辑方法会抛出 {@link UnsupportedOperationException}。
     *
     * @return 包含此模型配置键的<code>Set</code>视图。
     */
    Set<ConfigKey> keySet();

    /**
     * 向该模型中添加指定的配置入口（可选操作）。
     *
     * <p>
     * 当指定的配置入口不为 <code>null</code>且该配置入口中的配置键不存在于该模型时，可进行添加操作; 否则不进行任何操作。
     *
     * @param configEntry 指定的配置入口。
     * @return 该操作是否对模型产生了变更。
     * @throws UnsupportedOperationException 如果配置模型不支持该操作。
     */
    boolean add(ConfigEntry configEntry);

    /**
     * 向该模型中添加指定的配置入口（可选操作）。
     *
     * <p>
     * 当指定的配置入口不为 <code>null</code>且该配置入口中的配置键不存在于该模型时，可进行添加操作; 否则不进行任何操作。
     *
     * <p>
     * 该方法会试图添加 Collection 中所有的配置入口。
     *
     * @param configEntries 指定的配置入口 Collection。
     * @return 该操作是否对模型产生了变更。
     * @throws UnsupportedOperationException 如果配置模型不支持该操作。
     * @throws NullPointerException          入口参数为 <code>null</code>。
     */
    boolean addAll(Collection<ConfigEntry> configEntries);

    /**
     * 移除该模型中指定的配置键（可选操作）。
     *
     * <p>
     * 如果指定的配置键为 <code>null</code>，或者不在该模型中，则不进行任何操作。
     *
     * @param configKey 指定的配置键。
     * @return 该操作是否对模型产生了变更。
     * @throws UnsupportedOperationException 如果配置模型不支持该操作。
     */
    boolean remove(ConfigKey configKey);

    /**
     * 移除该模型中的配置键（可选操作）。
     *
     * <p>
     * 该方法会试图移除指定配置键 Collection 中的所有配置键。
     *
     * <p>
     * 如果指定的配置键不为 <code>null</code>，且不在该模型中，则进行移除操作。
     *
     * @param configKeys 指定的配置键 Collection。
     * @return 该操作是否对模型产生了变。
     * @throws UnsupportedOperationException 如果配置模型不支持该操作。
     * @throws NullPointerException          入口参数为 <code>null</code>。
     */
    boolean removeAll(Collection<ConfigKey> configKeys);

    /**
     * 仅保留此 collection 中那些也包含在指定 collection 的元素（可选操作）。 换句话说，移除此 collection
     * 中未包含在指定 collection 中的所有元素。
     *
     * @param configKeys 包含保留在此 collection 中的元素的 collection。
     * @return 该操作是否对模型产生了变更。
     * @throws UnsupportedOperationException 如果配置模型不支持该操作。
     * @throws NullPointerException          入口参数为 <code>null</code>。
     */
    boolean retainAll(Collection<ConfigKey> configKeys);

    /**
     * 返回该模型中的记录的配置的个数。
     *
     * @return 该模型中记录的配置的个数。
     */
    int size();

    /**
     * 判断一个值对于该模型来说是否合法。
     *
     * <p>
     * 如果指定的键为 <code>null</code>，或者该模型中不存在指定的配置键，则返回 <code>false</code>。 <br>
     * 如果指定的 value 为 <code>null</code>，则返回 <code>false</code>。
     *
     * @param configKey 指定的配置键。
     * @param value     指定的值。
     * @return 指定的值是否合适指定的配置键。
     */
    boolean isValueValid(ConfigKey configKey, String value);

    /**
     * 获取一个配置键的合法的值。
     *
     * <p>
     * 如果指定的配置键在该模型中存在，则查看该配置键的当前值是否合法， 如果合法，则返回当前值；如果不合法，则返回默认值。
     *
     * <p>
     * 如果指定的配置键在该模型中不存在，则返回 <code>null</code>
     *
     * @param configKey 指定的配置键。
     * @return 该配置键的合法的值。
     */
    String getValidValue(ConfigKey configKey);

    /**
     * 获取一个配置键对应的固定属性。
     *
     * <p>
     * 如果该配置键不存在或者为 <code>null</code>，则返回 <code>null</code>。
     *
     * @param configKey 指定的配置键。
     * @return 指定的配置键对应的固定属性。
     */
    ConfigFirmProps getConfigFirmProps(ConfigKey configKey);

    /**
     * 设置模型中指定配置键对应的固定属性（可选操作）。
     *
     * <p>
     * 当指定的配置键不为 <code>null</code>，且存在于该配置模型中时，即可更改其固定属性。 <br>
     * 如果配置属性更改，虽然配置键对应的当前值不会发生变更，但由于配置值检查器的更改，该值的有效性仍然有可能发生变化。
     *
     * <p>
     * 虽然配置模型中拥有该方法，但不推荐使用，因为，配置固定属性本身就应该是不可变的。
     *
     * @param configKey       指定的配置键。
     * @param configFirmProps 指定的配置固定属性。
     * @return 该操作是否对模型产生了变更。
     * @throws UnsupportedOperationException 如果模型不支持该操作。
     */
    boolean setConfigFirmProps(ConfigKey configKey, ConfigFirmProps configFirmProps);

    /**
     * 将一个指定的配置键对应的当前值重置为默认值。
     *
     * <p>
     * 如果指定的配置键不存在或者为 <code>null</code>，则不进行任何操作。
     *
     * @param configKey 指定的配置键。
     * @return 该方法是否对模型产生了变更。
     */
    boolean resetCurrentValue(ConfigKey configKey);

    /**
     * 将模型中的所有配置键对应的当前值重置为默认值。
     *
     * @return 该方法是否对模型产生了变更。
     */
    boolean resetAllCurrentValue();
}
