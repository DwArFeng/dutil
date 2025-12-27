package com.dwarfeng.dutil.develop.setting.info;

import com.dwarfeng.dutil.basic.num.Interval;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Double 配置信息。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class DoubleSettingInfo extends NumberSettingInfo {

    private String lastCheckedValue = null;
    private Double lastParsedValue = null;

    private final Lock lock = new ReentrantLock();

    /**
     * 生成一个新的 Double 配置信息。
     *
     * @param defaultValue 指定的默认值。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 指定的默认值不能通过自身检查。
     */
    public DoubleSettingInfo(String defaultValue) throws NullPointerException, IllegalArgumentException {
        this(defaultValue, Interval.INTERVAL_REALNUMBER);
    }

    /**
     * 生成一个新的 Double 配置信息。
     *
     * @param defaultValue 指定的默认值。
     * @param interval     指定的区间。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 指定的默认值不能通过自身检查。
     */
    public DoubleSettingInfo(String defaultValue, Interval interval)
            throws NullPointerException, IllegalArgumentException {
        super(defaultValue, interval);
        checkDefaultValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return DoubleSettingInfo.class.hashCode() * 61 + defaultValue.hashCode() * 17 + interval.hashCode() * 9;
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
        if (!(obj.getClass() == DoubleSettingInfo.class))
            return false;

        DoubleSettingInfo that = (DoubleSettingInfo) obj;
        return Objects.equals(this.defaultValue, that.defaultValue) && Objects.equals(this.interval, that.interval);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DoubleSettingInfo [defaultValue=" + defaultValue + ", interval=" + interval + "]";
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
                lastParsedValue = Double.parseDouble(value);
            } catch (Exception e) {
                lastParsedValue = null;
                return false;
            }

            if (!interval.contains(lastParsedValue)) {
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
                lastParsedValue = Double.parseDouble(value);
                if (!interval.contains(lastParsedValue)) {
                    lastCheckedValue = null;
                    lastParsedValue = null;
                    return null;
                }
                return lastParsedValue;
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
        if (!(object instanceof Double))
            return null;
        if (!interval.contains((double) object)) {
            return null;
        }

        return Double.toString((double) object);
    }
}
