package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Objects;

/**
 * 抽象配置信息。
 *
 * <p>
 * 配置信息的抽象实现。
 *
 * <p>
 * 注意：实现配置信息时应该遵守配置信息接口的约定。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public abstract class AbstractSettingInfo implements SettingInfo {

    /**
     * 配置信息中的默认值。
     */
    protected final String defaultValue;

    /**
     * 生成一个新的抽象配置信息。
     *
     * @param defaultValue 配置信息指定的默认值。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 指定的默认值不能通过自身检查。
     */
    public AbstractSettingInfo(String defaultValue) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(defaultValue, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGINFO_0));
        this.defaultValue = defaultValue;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * 该方法首先会检查入口参数是否为 <code>null</code>，如果是，则返回 <code>false</code>；否则调用方法
     * {@link #isNonNullValid(String)}。
     */
    @Override
    public final boolean isValid(String value) {
        if (Objects.isNull(value))
            return false;
        return isNonNullValid(value);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * 该方法首先会检查入口参数是否为 <code>null</code>，如果是，则返回 <code>true</code>；否则调用方法
     * {@link #isNonNullValid(String)}。
     */
    @Override
    public final boolean nonValid(String value) {
        if (Objects.isNull(value))
            return true;
        return !isNonNullValid(value);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * 该方法首先会检查入口参数是否合法，或者是否为 <code>null</code>，如果是，则返回 <code>null</code>；否则调用方法
     * {@link #parseValidValue(String)}。
     */
    @Override
    public final Object parseValue(String value) {
        if (Objects.isNull(value) || nonValid(value))
            return null;
        return parseValidValue(value);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * 该方法首先会检查入口参数是否为 <code>null</code>，如果是，则返回 <code>null</code>；否则调用方法
     * {@link #parseNonNullObject(Object)}。
     */
    @Override
    public final String parseObject(Object object) {
        if (Objects.isNull(object))
            return null;
        return parseNonNullObject(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 检查默认值是否合法。
     *
     * <p>
     * 该方法用该用在其子类构造器的最后一句，比如：
     * <code><pre>class SubSettingInfo extends AbstractSettingInfo{
     * 	// 构造器方法。
     * 	public SubSettingInfo(String defaultValue){
     * 		super(defaultValue);
     * 		// Do some initialize work...
     * 		<b>checkDefaultValue();</b>
     *    }
     * 	// implemention code ...
     * }</pre></code>
     *
     * @throws IllegalArgumentException 默认值不合法时抛出该异常。
     */
    protected final void checkDefaultValue() throws IllegalArgumentException {
        if (nonValid(defaultValue))
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGINFO_1));
    }

    /**
     * 判断指定的非空字符串是否合法。
     *
     * <p>
     * 该方法在执行 {@link #isValid(String)} 且入口参数非空时被调用。
     *
     * @param value 指定的非空值。
     * @return 指定的非空值是否合法。
     */
    protected abstract boolean isNonNullValid(String value);

    /**
     * 将指定的合法的值转化为对象。
     *
     * <p>
     * 该方法在执行 {@link #parseValue(String)} 且入口参数合法时被调用。
     *
     * @param value 指定的合法值。
     * @return 指定的合法值转化而成的对象。
     */
    protected abstract Object parseValidValue(String value);

    /**
     * 将指定的非空对象转化为字符串值。
     *
     * <p>
     * 该方法在执行 {@link #parseObject(Object))} 且入口非空时被调用。
     *
     * @param object 指定的非空对象。
     * @return 指定的非空对象转化而成的字符串值。
     */
    protected abstract String parseNonNullObject(Object object);
}
