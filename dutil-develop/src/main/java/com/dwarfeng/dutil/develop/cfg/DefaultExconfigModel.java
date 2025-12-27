package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.cfg.obs.ExconfigObserver;
import com.dwarfeng.dutil.develop.cfg.struct.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.struct.ExconfigEntry;
import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.*;
import java.util.Map.Entry;

/**
 * 默认 Ex 配置模型。
 *
 * <p>
 * Ex 配置模型的默认实现。
 *
 * <p>
 * 该实现使用了一个映射来存储所有的配置信息。该映射的值的类型是 {@link ExconfigBean}，
 * 它能获取和设置配置的固定属性、当前值、值解析器。相比于 {@link DefaultConfigModel} 双重映射， 该模型的结构是十分简单的。
 *
 * <p>
 * 只有一个映射意味着设置代理和初值是非常方便的，不像 {@link DefaultConfigModel} 那样需要设置两个映射的代理，
 * 该实现只需要设置一个映射的代理，从而避免了 {@link DefaultConfigModel} 代理表现不一致的问题。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class DefaultExconfigModel extends AbstractExconfigModel {

    /**
     * Ex 配置 Bean。
     *
     * <p>
     * 用于获取和设置 {@link ExconfigModel} 中需要的固定属性、当前值、值解析器。
     *
     * @author DwArFeng
     * @since 0.1.0-beta
     */
    public static class ExconfigBean {

        private ConfigFirmProps configFirmProps;
        private String currentValue;
        private ValueParser valueParser;

        /**
         * 生成一个默认的 Ex 配置 Bean。
         */
        public ExconfigBean() {
            this(null, null, null);
        }

        /**
         * 生成具有指定属性值的 Ex 配置 Bean。
         *
         * @param configFirmProps 指定的固定属性。
         * @param currentValue    指定的当前值。
         * @param valueParser     指定的值解析器。
         */
        public ExconfigBean(ConfigFirmProps configFirmProps, String currentValue, ValueParser valueParser) {
            this.configFirmProps = configFirmProps;
            this.currentValue = currentValue;
            this.valueParser = valueParser;
        }

        /**
         * 获取该 Bean 中的配置固定属性。
         *
         * @return 该 Bean 中的配置固定属性。
         */
        public ConfigFirmProps getConfigFirmProps() {
            return configFirmProps;
        }

        /**
         * 设置该 Bean 中的配置固定属性为指定值。
         *
         * @param configFirmProps 指定的配置固定属性。
         */
        public void setConfigFirmProps(ConfigFirmProps configFirmProps) {
            this.configFirmProps = configFirmProps;
        }

        /**
         * 获取该 Bean 中的当前值。
         *
         * @return 该 Bean 中的当前值。
         */
        public String getCurrentValue() {
            return currentValue;
        }

        /**
         * 设置该 Bean 中的当前值为指定值。
         *
         * @param currentValue 指定的当前值。
         */
        public void setCurrentValue(String currentValue) {
            this.currentValue = currentValue;
        }

        /**
         * 获取该 Bean 中的值解析器。
         *
         * @return 该 Bean 中的值解析器。
         */
        public ValueParser getValueParser() {
            return valueParser;
        }

        /**
         * 设置该 Bean 中的值解析器为指定值。
         *
         * @param valueParser 该 Bean 中的值解析器。
         */
        public void setValueParser(ValueParser valueParser) {
            this.valueParser = valueParser;
        }

    }

    /**
     * Ex 配置模型的映射代理。
     */
    protected final Map<ConfigKey, ExconfigBean> delegate;

    /**
     * 生成一个默认的 Ex 配置模型。
     */
    public DefaultExconfigModel() {
        this(new HashSet<>(), new HashMap<>(), Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定初始值入口的 Ex 配置模型。
     *
     * @param entries 指定的初始值入口的集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DefaultExconfigModel(Collection<ExconfigEntry> entries) {
        this(entries, new HashMap<>(), Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定初始值入口、映射代理、指定的观察器集合的 Ex 配置模型。
     *
     * <p>
     * 只有初始值入口集合中的有效入口才会被添加到模型中。
     *
     * @param entries   指定的初始值入口的集合。
     * @param delegate  指定的映射代理。
     * @param obversers 指定的观察器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DefaultExconfigModel(Collection<ExconfigEntry> entries, Map<ConfigKey, ExconfigBean> delegate,
                                Set<ExconfigObserver> obversers) {
        super(obversers);

        Objects.requireNonNull(delegate, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTEXCONFIGMODEL_0));
        Objects.requireNonNull(entries, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTEXCONFIGMODEL_1));

        this.delegate = delegate;

        for (ExconfigEntry exconfigEntry : entries) {
            if (Objects.isNull(exconfigEntry))
                continue;
            if (ConfigUtil.nonValid(exconfigEntry))
                continue;
            if (delegate.containsKey(exconfigEntry.getConfigKey()))
                continue;
            delegate.put(exconfigEntry.getConfigKey(), new ExconfigBean(exconfigEntry.getConfigFirmProps(),
                    exconfigEntry.getCurrentValue(), exconfigEntry.getValueParser()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        delegate.clear();
        fireConfigKeyCleared();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(ConfigKey configKey) {
        return delegate.containsKey(configKey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentValue(ConfigKey configKey) {
        if (!containsKey(configKey))
            return null;
        return delegate.get(configKey).getCurrentValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<ConfigKey, String> getAllCurrentValue() {
        return new CurrentValueMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ConfigKey> keySet() {
        return delegate.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(ExconfigEntry exconfigEntry) {
        if (Objects.isNull(exconfigEntry))
            return false;
        if (ConfigUtil.nonValid(exconfigEntry))
            return false;
        if (delegate.containsKey(exconfigEntry.getConfigKey()))
            return false;
        delegate.put(exconfigEntry.getConfigKey(), new ExconfigBean(exconfigEntry.getConfigFirmProps(),
                exconfigEntry.getCurrentValue(), exconfigEntry.getValueParser()));
        fireConfigKeyAdded(exconfigEntry.getConfigKey(), exconfigEntry.getConfigFirmProps(),
                exconfigEntry.getValueParser(), exconfigEntry.getCurrentValue());
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<ExconfigEntry> exconfigEntries) {
        boolean aFlag = false;
        for (ExconfigEntry exconfigEntry : exconfigEntries) {
            if (add(exconfigEntry))
                aFlag = true;
        }
        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(ConfigKey configKey) {
        if (!containsKey(configKey))
            return false;
        ExconfigBean bean = delegate.remove(configKey);
        fireConfigKeyRemoved(configKey, bean.getConfigFirmProps(), bean.getValueParser(), bean.getCurrentValue());
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<ConfigKey> configKeys) {
        Objects.requireNonNull(configKeys, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTEXCONFIGMODEL_2));
        return batchRemove(configKeys, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<ConfigKey> configKeys) {
        Objects.requireNonNull(configKeys, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTEXCONFIGMODEL_2));
        return batchRemove(configKeys, false);
    }

    private boolean batchRemove(Collection<ConfigKey> configKeys, boolean aFlag) {
        boolean result = false;

        for (Iterator<Map.Entry<ConfigKey, ExconfigBean>> i = delegate.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry<ConfigKey, ExconfigBean> entry = i.next();

            ConfigKey configKey = entry.getKey();
            ExconfigBean bean = entry.getValue();

            if (configKeys.contains(configKey) == aFlag) {
                i.remove();
                fireConfigKeyRemoved(configKey, bean.getConfigFirmProps(), bean.getValueParser(),
                        bean.getCurrentValue());
                result = true;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return delegate.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValueValid(ConfigKey configKey, String value) {
        if (Objects.isNull(configKey))
            return false;
        if (Objects.isNull(value))
            return false;

        if (!delegate.containsKey(configKey))
            return false;

        String currentValue = getCurrentValue(configKey);
        ConfigChecker configChecker = getConfigFirmProps(configKey).getConfigChecker();

        return configChecker.isValid(currentValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValidValue(ConfigKey configKey) {
        if (Objects.isNull(configKey))
            return null;

        if (!containsKey(configKey))
            return null;

        String defaultValue = getConfigFirmProps(configKey).getDefaultValue();
        String currentValue = getCurrentValue(configKey);

        return isValueValid(configKey, currentValue) ? currentValue : defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigFirmProps getConfigFirmProps(ConfigKey configKey) {
        if (!containsKey(configKey))
            return null;
        return delegate.get(configKey).getConfigFirmProps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setConfigFirmProps(ConfigKey configKey, ConfigFirmProps configFirmProps) {
        if (ConfigUtil.nonValid(configFirmProps))
            return false;
        if (!containsKey(configKey))
            return false;

        ConfigFirmProps oldOne = delegate.get(configKey).getConfigFirmProps();

        delegate.get(configKey).setConfigFirmProps(configFirmProps);

        fireConfigFirmPropsChanged(configKey, oldOne, configFirmProps);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setCurrentValue(ConfigKey configKey, String currentValue) {
        if (Objects.isNull(configKey))
            return false;
        if (Objects.isNull(currentValue))
            return false;

        if (!containsKey(configKey))
            return false;

        String oldOne = delegate.get(configKey).getCurrentValue();

        delegate.get(configKey).setCurrentValue(currentValue);

        fireCurrentValueChanged(configKey, oldOne, currentValue, getValidValue(configKey));

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setAllCurrentValue(Map<ConfigKey, String> map) {
        Objects.requireNonNull(map, DwarfUtil.getExceptionString(ExceptionStringKey.DefaultConfigModel_3));

        boolean aFlag = false;

        for (Map.Entry<ConfigKey, String> entry : map.entrySet()) {
            if (setCurrentValue(entry.getKey(), entry.getValue()))
                aFlag = true;
        }

        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetCurrentValue(ConfigKey configKey) {
        if (Objects.isNull(configKey))
            return false;
        return setCurrentValue(configKey, getConfigFirmProps(configKey).getDefaultValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetAllCurrentValue() {
        boolean aFlag = false;

        for (ConfigKey configKey : keySet()) {
            if (resetCurrentValue(configKey))
                aFlag = true;
        }

        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ValueParser getValueParser(ConfigKey configKey) {
        if (!containsKey(configKey))
            return null;
        return delegate.get(configKey).getValueParser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setValueParser(ConfigKey configKey, ValueParser valueParser) {
        if (Objects.isNull(configKey))
            return false;
        if (Objects.isNull(valueParser))
            return false;

        if (!containsKey(configKey))
            return false;

        ValueParser oldOne = delegate.get(configKey).getValueParser();

        delegate.get(configKey).setValueParser(valueParser);

        fireValueParserChanged(configKey, oldOne, valueParser);

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getParsedValue(ConfigKey configKey) {
        if (Objects.isNull(configKey))
            return null;
        if (!containsKey(configKey))
            return null;

        String validValue = getValidValue(configKey);
        ValueParser valueParser = getValueParser(configKey);

        return valueParser.parseValue(validValue);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public <T> T getParsedValue(ConfigKey configKey, Class<T> clas) {
        if (Objects.isNull(configKey))
            return null;
        if (!containsKey(configKey))
            return null;

        String validValue = getValidValue(configKey);
        ValueParser valueParser = getValueParser(configKey);

        // 该处转换不是安全的，但是允许抛出 ClassCastException()。
        @SuppressWarnings("unchecked")
        T t = (T) valueParser.parseValue(validValue);

        return t;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setParsedValue(ConfigKey configKey, Object obj) {
        if (Objects.isNull(obj))
            return false;
        if (Objects.isNull(configKey))
            return false;

        if (!containsKey(configKey))
            return false;

        ExconfigBean bean = delegate.get(configKey);
        return setCurrentValue(configKey, bean.getValueParser().parseObject(obj));
    }

    private class CurrentValueMap implements Map<ConfigKey, String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return delegate.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsValue(Object value) {
            for (ExconfigBean bean : delegate.values()) {
                if (bean.getCurrentValue().equals(value))
                    return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String get(Object key) {
            if (!containsKey(key))
                return null;
            return delegate.get(key).getCurrentValue();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String put(ConfigKey key, String value) {
            // 该类是只读的。
            throw new UnsupportedOperationException("put");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String remove(Object key) {
            // 该类是只读的。
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void putAll(Map<? extends ConfigKey, ? extends String> m) {
            // 该类是只读的。
            throw new UnsupportedOperationException("putAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            // 该类是只读的。
            throw new UnsupportedOperationException("clear");
        }

        private transient Set<ConfigKey> keySet;
        private transient Set<Map.Entry<ConfigKey, String>> entrySet;
        private transient Collection<String> values;

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ConfigKey> keySet() {
            if (keySet == null)
                keySet = Collections.unmodifiableSet(keySet());
            return keySet;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<String> values() {
            if (values == null) {
                values = new TransValuesCollection(delegate.values());
            }
            return values;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<java.util.Map.Entry<ConfigKey, String>> entrySet() {
            if (entrySet == null) {
                entrySet = new TransEntrySet(delegate.entrySet());
            }
            return entrySet;
        }

    }

    private static class TransValuesCollection implements Collection<String> {

        private final Collection<ExconfigBean> delegate;

        public TransValuesCollection(Collection<ExconfigBean> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return delegate.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            for (ExconfigBean bean : delegate) {
                if (bean.getCurrentValue().equals(o))
                    return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<String> iterator() {
            return new TransValuesIterator(delegate.iterator());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            Object[] arr = delegate.toArray();
            Object[] strArr = new Object[arr.length];
            for (int i = 0; i < strArr.length; i++) {
                // 此处转换是安全的。
                strArr[i] = ((ExconfigBean) arr[i]).getCurrentValue();
            }
            return strArr;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            Object[] strArr = toArray();
            // 此处转换是安全的。
            @SuppressWarnings("unchecked")
            T[] r = a.length >= strArr.length ? a
                    : (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), strArr.length);
            for (int i = 0; i < strArr.length; i++) {
                // 此处转化有可能不安全，但是允许抛出 ClassCastException。
                @SuppressWarnings("unchecked")
                T t = (T) strArr[i];
                r[i] = t;
            }
            return r;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(String e) {
            // 该类是只读的
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            // 该类是只读的
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            Objects.requireNonNull(c);
            for (Object obj : c) {
                if (!contains(obj))
                    return false;
            }
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends String> c) {
            // 该类是只读的
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            // 该类是只读的
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            // 该类是只读的
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            // 该类是只读的
            throw new UnsupportedOperationException("clear");
        }

    }

    private static class TransValuesIterator implements Iterator<String> {

        private final Iterator<ExconfigBean> delegate;

        public TransValuesIterator(Iterator<ExconfigBean> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String next() {
            return delegate.next().getCurrentValue();
        }

    }

    private static class TransEntrySet implements Set<Map.Entry<ConfigKey, String>> {

        private final Set<Map.Entry<ConfigKey, ExconfigBean>> delegate;

        public TransEntrySet(Set<Map.Entry<ConfigKey, ExconfigBean>> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return delegate.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            if (o instanceof Map.Entry<?, ?>) {
                for (Map.Entry<ConfigKey, ExconfigBean> entry : delegate) {
                    if (new TransEntrySetEntry(entry).equals(o))
                        return true;
                }
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<Entry<ConfigKey, String>> iterator() {
            return new TransEntrySetIterator(delegate.iterator());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            Object[] arr = delegate.toArray();
            Object[] strArr = new Object[arr.length];
            for (int i = 0; i < strArr.length; i++) {
                // 此处转换是安全的。
                @SuppressWarnings("unchecked")
                Map.Entry<ConfigKey, ExconfigBean> entry = (Map.Entry<ConfigKey, DefaultExconfigModel.ExconfigBean>) arr[i];
                strArr[i] = new TransEntrySetEntry(entry);
            }
            return strArr;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            Object[] strArr = toArray();
            // 此处转换是安全的。
            @SuppressWarnings("unchecked")
            T[] r = a.length >= strArr.length ? a
                    : (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), strArr.length);
            for (int i = 0; i < strArr.length; i++) {
                // 此处转化有可能不安全，但是允许抛出 ClassCastException。
                @SuppressWarnings("unchecked")
                T t = (T) strArr[i];
                r[i] = t;
            }
            return r;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(Entry<ConfigKey, String> e) {
            // 该类是只读的
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            // 该类是只读的
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            Objects.requireNonNull(c);
            for (Object obj : c) {
                if (!contains(obj))
                    return false;
            }
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends Entry<ConfigKey, String>> c) {
            // 该类是只读的
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            // 该类是只读的
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            // 该类是只读的
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            // 该类是只读的
            throw new UnsupportedOperationException("clear");
        }

    }

    private static class TransEntrySetIterator implements Iterator<Map.Entry<ConfigKey, String>> {

        private final Iterator<Map.Entry<ConfigKey, ExconfigBean>> delegate;

        public TransEntrySetIterator(Iterator<Map.Entry<ConfigKey, ExconfigBean>> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Entry<ConfigKey, String> next() {
            return new TransEntrySetEntry(delegate.next());
        }

    }

    private static class TransEntrySetEntry implements Map.Entry<ConfigKey, String> {

        private final Map.Entry<ConfigKey, ExconfigBean> delegate;

        public TransEntrySetEntry(Entry<ConfigKey, ExconfigBean> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ConfigKey getKey() {
            return delegate.getKey();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValue() {
            return delegate.getValue().getCurrentValue();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String setValue(String value) {
            // 该类是只读的
            throw new UnsupportedOperationException("setValue");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (Objects.isNull(obj))
                return false;
            if (obj == TransEntrySetEntry.this)
                return true;
            if (!(obj instanceof TransEntrySetEntry))
                return false;
            TransEntrySetEntry that = (TransEntrySetEntry) obj;
            return Objects.equals(this.getKey(), that.getKey()) && Objects.equals(this.getValue(), that.getValue());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return getKey().hashCode() * 177 + getValue().hashCode();
        }

    }
}
