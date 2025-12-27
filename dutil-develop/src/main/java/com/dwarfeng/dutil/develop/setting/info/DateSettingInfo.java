package com.dwarfeng.dutil.develop.setting.info;

import com.dwarfeng.dutil.develop.setting.AbstractSettingInfo;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 日期配置信息。
 *
 * <p>
 * 提供 {@link java.util.Date} 对象的检查和值转换。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class DateSettingInfo extends AbstractSettingInfo {

    private static final int RADIX = 10;

    private String lastCheckedValue = null;
    private Date lastParsedValue = null;

    private final Lock lock = new ReentrantLock();

    /**
     * 生成一个默认的日期配置信息。
     *
     * @param defaultValue 指定的默认值。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 指定的默认值不能通过自身检查。
     */
    public DateSettingInfo(String defaultValue) throws NullPointerException, IllegalArgumentException {
        super(defaultValue);
        checkDefaultValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return DateSettingInfo.class.hashCode() * 61 + defaultValue.hashCode() * 17;
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
        if (!(obj.getClass() == DateSettingInfo.class))
            return false;

        DateSettingInfo that = (DateSettingInfo) obj;
        return Objects.equals(this.defaultValue, that.defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DateSettingInfo [defaultValue=" + defaultValue + "]";
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
                lastParsedValue = new Date(Long.parseLong(value, RADIX));
            } catch (Exception e) {
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
                lastParsedValue = new Date(Long.parseLong(value, RADIX));
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

            if (!(object instanceof Date))
                return null;

            return Long.toString(((Date) object).getTime(), RADIX);
        } finally {
            lock.unlock();
        }
    }
}
