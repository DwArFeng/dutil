package com.dwarfeng.dutil.develop.setting;

/**
 * 配置信息。
 *
 * <p>
 * 配置信息用于在配置处理器中保存一个配置键所对应的配置的判定以及转换依据。
 * 配置处信息可以检查某个配置值是否合法，将合法的配置文本值转化成一个对象，或者将指定的对象转换成合法的配置值。
 *
 * <p>
 * 配置信息应该遵循如下的约定：
 *
 * <pre>
 * 1.  多次调用{@link #getDefaultValue()} 返回的值应该始终不变，而且返回的值必须能通过自身检查。
 * 2. 对于任何值<code>value</code>（包括 <code>null</code>），都有 <code>isValid(value) == ! nonValid(value)</code>。
 * 3.  <code>isValid(null)</code>始终为 <code>false</code>，而 <code>nonValid(null)</code>始终为 <code>true</code>。
 * 4.  多次调用{@link #getDefaultValue()} 返回的值应该始终不变，而且返回的值必须能通过自身检查。
 * 5. 如果一个值能通过检查，那么调用方法 {@link #parseValue(String)} 必须能够被转换成非 <code>null</code> 对象。
 * 6. 如果一个值不能通过检查，那么调用方法 {@link #parseValue(String)} 必须被转换成 <code>null</code>。
 * 7. 对于任何一个对象，调用方法 {@link #parseObject(Object)} 返回的值要么是 <code>null</code>，要么能通过自身检查。
 * </pre>
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface SettingInfo {

    /**
     * 检查指定的值是否有效。
     *
     * @param value 指定的值。
     * @return 指定的值是否有效。
     */
    boolean isValid(String value);

    /**
     * 检查指定的值是否无效。
     *
     * @param value 指定的值。
     * @return 指定的值是否无效。
     */
    boolean nonValid(String value);

    /**
     * 将一个 String 类型值解析成 {@link Object} 对象。
     *
     * @param value 指定的 String 值。
     * @return 解析成的 {@link Object} 对象，如果值无效，则返回 <code>null</code>。
     */
    Object parseValue(String value);

    /**
     * 将一个对象解析成字符串。
     *
     * @param object 指定的对象。
     * @return 由指定对象解析成的字符串， 如果对象无效，则返回 <code>null</code>。
     */
    String parseObject(Object object);

    /**
     * 获取配置信息的默认值。
     *
     * @return 配置信息的默认值。
     */
    String getDefaultValue();

    /**
     * 判断一个配置信息是否与另一个对象相等。
     *
     * <p>
     * 有配置信息 A，当另一个对象 B 属于配置信息，且
     *
     * <pre>
     * 1. 对于任何值 <code>value</code>，均有 <code>A.isValid(value) == B.isValid(value)</code>。
     * 2. 对于任何值 <code>value</code>，均有 <code>A.nonValid(value) == B.nonValid(value)</code>。
     * 3. 对于任何值 <code>value</code>，均有 <code>A.parseValue(value)</code> 与 <code>B.parseValue(value)</code> 所得的对象在功能上一致。
     * 4. 对于任何值 <code>object</code>，均有 <code>A.parseObject(object).equals(B.parseObject(object))</code>。
     * 5. 有 <code>A.getDefaultValue().equals(B.getDefaultValue())
     * </pre>
     *
     * <p>
     * 均成立时，可认为 A 与 B 相等。
     *
     * @param obj 指定的对象。
     * @return 该配置信息与指定的对象是否相等。
     */
    @Override
    boolean equals(Object obj);

    /**
     * {@inheritDoc}
     */
    @Override
    int hashCode();
}
