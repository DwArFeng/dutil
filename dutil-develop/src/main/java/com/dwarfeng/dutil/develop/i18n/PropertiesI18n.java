package com.dwarfeng.dutil.develop.i18n;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Objects;
import java.util.Properties;

/**
 * Properties 国际化接口。
 *
 * <p>
 * 使用 {@link Properties} 实现的国际化接口。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public class PropertiesI18n implements I18n {

    /**
     * 该国际化接口中的 Properties 对象。
     */
    protected final Properties properties;

    /**
     * 生成一个具有指定的 Properties 对象的国际化接口。
     *
     * @param properties 指定的 Properties 对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public PropertiesI18n(Properties properties) {
        Objects.requireNonNull(properties, DwarfUtil.getExceptionString(ExceptionStringKey.PROPERTIESI18N_0));
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(String key) {
        return properties.getProperty(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((properties == null) ? 0 : properties.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof PropertiesI18n))
            return false;
        PropertiesI18n other = (PropertiesI18n) obj;
        if (properties == null) {
            return other.properties == null;
        } else return properties.equals(other.properties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PropertiesI18n [properties=" + properties + "]";
    }
}
