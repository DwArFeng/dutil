package com.dwarfeng.dutil.develop.setting.info;

import com.dwarfeng.dutil.develop.setting.AbstractSettingInfo;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类配置信息。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class ClassSettingInfo extends AbstractSettingInfo {

    private String lastCheckedValue = null;
    private Class<?> lastParsedValue = null;

    private final Lock lock = new ReentrantLock();

    /**
     * 生成一个新的类配置信息。
     *
     * @param defaultValue 指定的默认值。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 指定的默认值不能通过自身检查。
     */
    public ClassSettingInfo(String defaultValue) throws NullPointerException, IllegalArgumentException {
        super(defaultValue);
        checkDefaultValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return ClassSettingInfo.class.hashCode() * 61 + defaultValue.hashCode() * 17;
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
        if (!(obj.getClass() == ClassSettingInfo.class))
            return false;

        ClassSettingInfo that = (ClassSettingInfo) obj;
        return Objects.equals(this.defaultValue, that.defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ClassSettingInfo [defaultValue=" + defaultValue + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isNonNullValid(String value) {
        lock.lock();
        try {
            if (Objects.equals(value, lastCheckedValue))
                return Objects.nonNull(lastParsedValue);

            try {
                lastCheckedValue = value;
                lastParsedValue = Class.forName(value);
            } catch (ClassNotFoundException e) {
                lastParsedValue = null;
                return false;
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object parseValidValue(String value) {
        lock.lock();
        try {
            if (Objects.equals(value, lastCheckedValue))
                return lastParsedValue;

            try {
                lastCheckedValue = value;
                lastParsedValue = Class.forName(value);
                return lastCheckedValue;
            } catch (Exception e) {
                lastCheckedValue = null;
                lastParsedValue = null;
                throw new IllegalStateException();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String parseNonNullObject(Object object) {
        lock.lock();
        try {
            if (Objects.equals(object, lastParsedValue))
                return lastCheckedValue;

            if (!(object instanceof Class))
                return null;

            return ((Class<?>) object).getName();
        } finally {
            lock.unlock();
        }
    }
}
