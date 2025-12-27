package com.dwarfeng.dutil.develop.setting.info;

import com.dwarfeng.dutil.develop.setting.AbstractSettingInfo;

import java.awt.*;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 字体配置信息。
 *
 * <p>
 * 该配置信息提供 <code>java.awt.Font</code> 类的转换方法。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class FontSettingInfo extends AbstractSettingInfo {

    private String lastCheckedValue = null;
    private Font lastParsedValue = null;

    private final Lock lock = new ReentrantLock();

    /**
     * 生成一个新的 Double 配置信息。
     *
     * @param defaultValue 指定的默认值。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 指定的默认值不能通过自身检查。
     */
    public FontSettingInfo(String defaultValue) throws NullPointerException, IllegalArgumentException {
        super(defaultValue);
        checkDefaultValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return FontSettingInfo.class.hashCode() * 61 + defaultValue.hashCode() * 17;
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
        if (!(obj.getClass() == FontSettingInfo.class))
            return false;

        FontSettingInfo that = (FontSettingInfo) obj;
        return Objects.equals(this.defaultValue, that.defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "FontSettingInfo [defaultValue=" + defaultValue + "]";
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
                StringTokenizer st = new StringTokenizer(value, "-");
                String name = st.nextToken();
                int style = Integer.parseInt(st.nextToken());
                int size = Integer.parseInt(st.nextToken());
                lastParsedValue = new Font(name, style, size);
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
                StringTokenizer st = new StringTokenizer(value, "-");
                String name = st.nextToken();
                int style = Integer.parseInt(st.nextToken());
                int size = Integer.parseInt(st.nextToken());
                lastParsedValue = new Font(name, style, size);
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
        if (!(object instanceof Font))
            return null;

        Font font = (Font) object;
        return String.format("%s-%d-%d", font.getName(), font.getStyle(), font.getSize());
    }
}
