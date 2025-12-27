package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.basic.prog.ObserverSet;
import com.dwarfeng.dutil.develop.cfg.obs.ExconfigObserver;
import com.dwarfeng.dutil.develop.cfg.struct.ExconfigEntry;
import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.Collection;
import java.util.Set;

/**
 * Ex 配置模型。
 *
 * <p>
 * 该配置模型与 {@link ConfigModel} 相比， 增加了对指定的配置键对应的值直接解析的支持。 同时， 模型的入口将更加的合理。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface ExconfigModel extends CurrentValueContainer, ObserverSet<ExconfigObserver> {

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
     * 当指定的配置入口不为 <code>null</code>，且入口中的所有值都不为
     * <code>null</code>并有效，且该配置入口中的配置键不存在于该模型时，可进行添加操作; 否则不进行任何操作。
     *
     * @param exconfigEntry 指定的配置入口。
     * @return 该操作是否对模型产生了变更。
     * @throws UnsupportedOperationException 如果配置模型不支持该操作。
     */
    boolean add(ExconfigEntry exconfigEntry);

    /**
     * 向该模型中添加指定的配置入口（可选操作）。
     *
     * <p>
     * 当指定的配置入口不为 <code>null</code>，且入口中的所有值都不为
     * <code>null</code>并有效且该配置入口中的配置键不存在于该模型时，可进行添加操作; 否则不进行任何操作。
     *
     * <p>
     * 该方法会试图添加 Collection 中所有的配置入口。
     *
     * @param exconfigEntries 指定的配置入口 Collection。
     * @return 该操作是否对模型产生了变更。
     * @throws UnsupportedOperationException 如果配置模型不支持该操作。
     * @throws NullPointerException          入口参数为 <code>null</code>。
     */
    boolean addAll(Collection<ExconfigEntry> exconfigEntries);

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

    /**
     * 获取配置模型中的值解析器。
     *
     * <p>
     * 如果配置模型中不存在指定的配置键或者入口配置键为 <code>null</code>，则返回 <code>null</code>。
     *
     * @param configKey 指定的配置键。
     * @return 指定的配置键对应的值解析器。
     */
    ValueParser getValueParser(ConfigKey configKey);

    /**
     * 设置模型中指定配置键的值解析器（可选操作）。
     *
     * <p>
     * 当指定的配置键为 <code>null</code>，或指定的配置键不存在于当前的模型时，不进行任何操作。 <br>
     * 当指定的 valueParser 为 <code>null</code>时，不进行任何操作。
     *
     * @param configKey   指定的配置键。
     * @param valueParser 指定的值解析器。
     * @return 该操作是否对模型造成了改变。
     */
    boolean setValueParser(ConfigKey configKey, ValueParser valueParser);

    /**
     * 获取模型中指定配置键的对应的有效值的解析值。
     *
     * @param configKey 指定配置键。
     * @return 指定的配置键对应的有效值的解析值。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 指定的配置键对应的实际值无法被解析为对象。
     */
    Object getParsedValue(ConfigKey configKey);

    /**
     * 获取模型中指定配置键的对应的有效值的解析值。
     *
     * <p>
     * 该解析值将被转换为指定的类型。
     *
     * @param configKey 指定的配置键。
     * @param clas      指定的类型。
     * @param <T>       值需要被转换成的类型。
     * @return 被转换成指定类型的指定的配置键对应的有效值的解析值。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws ClassCastException       类型转换异常。
     * @throws IllegalArgumentException 指定的配置键对应的实际值无法被解析为对象。
     */
    <T> T getParsedValue(ConfigKey configKey, Class<T> clas);

    /**
     * 设置模型中对应的配置键的对应的有效值。
     *
     * <p>
     * 该方法使用解析器将对象解析为字符串，并将得到的字符串设置为当前值。
     *
     * @param configKey 指定的配置键。
     * @param obj       指定的对象。
     * @return 是否设置成功。
     * @throws IllegalArgumentException 指定的对象无法被解析器解析为字符串。
     */
    boolean setParsedValue(ConfigKey configKey, Object obj);
}
