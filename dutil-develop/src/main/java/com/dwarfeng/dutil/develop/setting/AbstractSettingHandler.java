package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.develop.setting.obs.SettingObserver;

import java.util.*;

/**
 * 抽象配置处理器。
 *
 * <p>
 * 配置处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public abstract class AbstractSettingHandler implements SettingHandler {

    /**
     * 抽象入口。
     *
     * <p>
     * 实现了<code>equals(), hashCode(), toString()</code> 方法的入口。
     *
     * @author DwArFeng
     * @since 0.2.0-beta
     */
    public abstract static class AbstractEntry implements SettingHandler.Entry {

        /**
         * {@inheritDoc}
         */
        @Override
        public abstract String getKey();

        /**
         * {@inheritDoc}
         */
        @Override
        public abstract SettingInfo getSettingInfo();

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setSettingInfo(SettingInfo settingInfo) {
            throw new UnsupportedOperationException("setSettingInfo");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public abstract String getCurrentValue();

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setCurrentValue(String currentValue) {
            throw new UnsupportedOperationException("setCurrentValue");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return this.getKey().hashCode() * 31 + this.getSettingInfo().hashCode() * 17
                    + this.getCurrentValue().hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;

            if (!(obj instanceof SettingHandler.Entry))
                return false;

            SettingHandler.Entry that = (Entry) obj;

            return Objects.equals(this.getKey(), that.getKey())
                    && Objects.equals(this.getSettingInfo(), that.getSettingInfo())
                    && Objects.equals(this.getCurrentValue(), that.getCurrentValue());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Entry [getKey()=" + getKey() + ", getSettingInfo()=" + getSettingInfo() + ", getCurrentValue()="
                    + getCurrentValue() + "]";
        }

    }

    /**
     * 观察器集合
     */
    protected final Set<SettingObserver> observers;

    /**
     * 生成一个默认的抽象配置处理器。
     */
    public AbstractSettingHandler() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定的侦听器集合的的抽象Ex 配置模型。
     *
     * @param observers 指定的侦听器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public AbstractSettingHandler(Set<SettingObserver> observers) {
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTEXCONFIGMODEL_0));
        this.observers = observers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SettingObserver> getObservers() {
        return Collections.unmodifiableSet(observers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addObserver(SettingObserver observer) {
        if (Objects.isNull(observer))
            return false;
        return observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(SettingObserver observer) {
        return observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearObserver() {
        observers.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int size();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean isEmpty();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean put(String key, SettingInfo settingInfo, String currentValue) {
        throw new UnsupportedOperationException("put");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean put(Name key, SettingInfo settingInfo, String currentValue) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return put(key.getName(), settingInfo, currentValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean putAll(SettingHandler handler) {
        Objects.requireNonNull(handler, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_5));

        boolean aFlag = false;
        for (Entry entry : handler.entrySet()) {
            if (put(entry.getKey(), entry.getSettingInfo(), entry.getCurrentValue()))
                aFlag = true;
        }
        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract Set<Entry> entrySet();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void clear();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract Set<String> keySet();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean containsKey(Object key);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_3));

        for (Object obj : c) {
            if (!containsKey(obj))
                return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeKey(Object key) {
        throw new UnsupportedOperationException("removeKey");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAllKey(Collection<?> c) {
        throw new UnsupportedOperationException("removeAllKey");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAllKey(Collection<?> c) {
        throw new UnsupportedOperationException("retainAllKey");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract SettingInfo getSettingInfo(String key);

    /**
     * {@inheritDoc}
     */
    @Override
    public SettingInfo getSettingInfo(Name key) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return getSettingInfo(key.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setSettingInfo(String key, SettingInfo settingInfo) {
        throw new UnsupportedOperationException("setSettingInfo");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setSettingInfo(Name key, SettingInfo settingInfo) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return setSettingInfo(key.getName(), settingInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValueValid(String key, String value) {
        if (Objects.isNull(value))
            return false;
        if (!containsKey(key))
            return false;

        SettingInfo settingInfo = getSettingInfo(key);
        if (Objects.isNull(settingInfo))
            return false;

        return settingInfo.isValid(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValueValid(Name key, String value) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return isValueValid(key.getName(), value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValidValue(String key) {
        if (!containsKey(key))
            return null;

        SettingInfo settingInfo = getSettingInfo(key);
        if (Objects.isNull(settingInfo))
            return null;

        String currentValue = getCurrentValue(key);
        return settingInfo.isValid(currentValue) ? currentValue : settingInfo.getDefaultValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String getCurrentValue(String key);

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentValue(Name key) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return getCurrentValue(key.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValidValue(Name key) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return getValidValue(key.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setCurrentValue(String key, String newValue) {
        throw new UnsupportedOperationException("setCurrentValue");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setCurrentValue(Name key, String newValue) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return setCurrentValue(key.getName(), newValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setAllCurrentValue(Map<String, String> m) {
        Objects.requireNonNull(m, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_2));

        boolean aFlag = false;

        for (Map.Entry<String, String> entry : m.entrySet()) {
            if (setCurrentValue(entry.getKey(), entry.getValue())) {
                aFlag = true;
            }
        }

        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetCurrentValue(String key) {
        throw new UnsupportedOperationException("resetCurrentValue");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetCurrentValue(Name key) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return resetCurrentValue(key.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetAllCurrentValue(Collection<String> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_3));

        boolean aFlag = false;

        for (String key : c) {
            if (resetCurrentValue(key)) {
                aFlag = true;
            }
        }

        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetAllCurrentValue() {
        boolean aFlag = false;

        for (String key : keySet()) {
            if (resetCurrentValue(key)) {
                aFlag = true;
            }
        }

        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getParsedValue(String key) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));

        if (!containsKey(key))
            return null;

        SettingInfo settingInfo = getSettingInfo(key);
        if (Objects.isNull(settingInfo))
            return null;

        String currentValue = getCurrentValue(key);
        if (Objects.isNull(currentValue))
            return null;
        if (settingInfo.nonValid(currentValue))
            return null;

        return settingInfo.parseValue(currentValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getParsedValue(Name key) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return getParsedValue(key.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getParsedValue(String key, Class<T> clas) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        Objects.requireNonNull(clas, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_1));

        return clas.cast(getParsedValue(key));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getParsedValue(Name key, Class<T> clas) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        Objects.requireNonNull(clas, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_1));

        return clas.cast(getParsedValue(key.getName()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getParsedValidValue(String key) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));

        if (!containsKey(key))
            return null;

        SettingInfo settingInfo = getSettingInfo(key);
        if (Objects.isNull(settingInfo))
            return null;

        String validValue = getValidValue(key);
        if (Objects.isNull(validValue))
            return null;
        if (settingInfo.nonValid(validValue))
            return null;

        return settingInfo.parseValue(validValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getParsedValidValue(Name key) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return getParsedValidValue(key.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getParsedValidValue(String key, Class<T> clas) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        Objects.requireNonNull(clas, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_1));

        return clas.cast(getParsedValidValue(key));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getParsedValidValue(Name key, Class<T> clas) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        Objects.requireNonNull(clas, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_1));

        return clas.cast(getParsedValidValue(key.getName()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setParsedValue(String key, Object obj) {
        throw new UnsupportedOperationException("setParsedValue");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setParsedValue(Name key, Object obj) {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETTINGHANDLER_0));
        return setParsedValue(key.getName(), obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int h = 0;
        for (Entry entry : entrySet()) {
            h += entry.hashCode();
        }

        return h;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof SettingHandler))
            return false;

        SettingHandler that = (SettingHandler) obj;

        if (this.size() != that.size())
            return false;

        for (Entry entry : entrySet()) {
            String thisKey = entry.getKey();
            SettingInfo thisSettingInfo = entry.getSettingInfo();
            String thisCurrentValue = entry.getCurrentValue();

            if (!that.containsKey(thisKey))
                return false;

            SettingInfo thatSettingInfo = that.getSettingInfo(thisKey);
            String thatCurrentValue = that.getCurrentValue(thisKey);

            if (!Objects.equals(thisSettingInfo, thatSettingInfo)
                    || !Objects.equals(thisCurrentValue, thatCurrentValue))
                return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Iterator<SettingHandler.Entry> it = entrySet().iterator();

        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            SettingHandler.Entry entry = it.next();
            sb.append(entry.toString());
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    /**
     * 通知观察器指定的键值被添加。
     *
     * @param key          指定的键值。
     * @param settingInfo  指定的键对应的配置信息。
     * @param currentValue 指定的键对应的当前值。
     */
    protected void fireKeyPut(String key, SettingInfo settingInfo, String currentValue) {
        for (SettingObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireKeyPut(key, settingInfo, currentValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器指定的键值被移除。
     *
     * @param key 指定的键值。
     */
    protected void fireKeyRemoved(String key) {
        for (SettingObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireKeyRemoved(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器键值被清空。
     */
    protected void fireKeyCleared() {
        for (SettingObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireKeyCleared();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器指定键值的设置信息被改变。
     *
     * @param key      指定的键。
     * @param oldValue 指定的键对应的旧的配置信息。
     * @param newValue 指定的键对应的新的配置信息。
     */
    protected void fireSettingInfoChanged(String key, SettingInfo oldValue, SettingInfo newValue) {
        for (SettingObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireSettingInfoChanged(key, oldValue, newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知指定键值的当前值被改变。
     *
     * @param key      指定的键值。
     * @param oldValue 指定的键对应的旧的当前值。
     * @param newValue 指定的键对应的新的当前值。
     */
    protected void fireCurrentValueChanged(String key, String oldValue, String newValue) {
        for (SettingObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireCurrentValueChanged(key, oldValue, newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
