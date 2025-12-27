package com.dwarfeng.dutil.develop.setting.info;

import com.dwarfeng.dutil.develop.setting.AbstractSettingInfo;

import java.util.Objects;

/**
 * 布尔配置信息。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class BooleanSettingInfo extends AbstractSettingInfo {

    /**
     * 生成一个新的布尔配置信息。
     *
     * @param defaultValue 指定的默认值。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 指定的默认值不能通过自身检查。
     */
    public BooleanSettingInfo(String defaultValue) {
        super(defaultValue);
        checkDefaultValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return BooleanSettingInfo.class.hashCode() * 61 + defaultValue.hashCode() * 17;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (Objects.isNull(obj))
            return false;
        if (!(obj.getClass() == BooleanSettingInfo.class))
            return false;

        BooleanSettingInfo that = (BooleanSettingInfo) obj;
        return Objects.equals(this.defaultValue, that.defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BooleanSettingInfo [defaultValue=" + defaultValue + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isNonNullValid(String value) {
        String str = value.toUpperCase();
        return str.equals("TRUE") || str.equals("FALSE");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object parseValidValue(String value) {
        return Boolean.parseBoolean(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String parseNonNullObject(Object object) {
        if (!(object instanceof Boolean))
            return null;
        return Boolean.toString((boolean) object).toUpperCase();
    }
}
