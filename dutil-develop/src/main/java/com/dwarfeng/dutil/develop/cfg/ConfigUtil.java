package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.cfg.obs.ConfigObserver;
import com.dwarfeng.dutil.develop.cfg.obs.ExconfigObserver;
import com.dwarfeng.dutil.develop.cfg.struct.ExconfigEntry;
import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 有关于配置包的一些常用方法。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class ConfigUtil {

    /**
     * 判断指定的配置固定属性是否有效。
     *
     * <p>
     * 当指定的配置固定属性不为 <code>null</code>，并且其中的配置值检查器不为 <code>null</code>，
     * 且其默认值能通过配置值检查器时，认为指定的配置固定属性有效。
     *
     * @param configFirmProps 指定的配置固定属性。
     * @return 指定的配置固定属性是否有效。
     */
    public static boolean isValid(ConfigFirmProps configFirmProps) {
        if (Objects.isNull(configFirmProps))
            return false;
        if (Objects.isNull(configFirmProps.getConfigChecker()))
            return false;
        if (Objects.isNull(configFirmProps.getDefaultValue()))
            return false;

        return configFirmProps.getConfigChecker().isValid(configFirmProps.getDefaultValue());
    }

    /**
     * 判断指定的配置固定属性是否无效。
     *
     * <p>
     * 如果配置固定值不有效，则无效，即该方法等同于 <code> ! isValid(configFirmProps)</code>
     *
     * @param configFirmProps 指定的配置固定属性。
     * @return 指定的配置固定属性值是否无效。
     */
    public static boolean nonValid(ConfigFirmProps configFirmProps) {
        return !isValid(configFirmProps);
    }

    /**
     * 判断指定的配置入口是否有效。
     *
     * <p>
     * 当指定的配置入口不为 <code>null</code>， 且其中的配置键不为 <code>null</code>，
     * 且其中的配置固定值有效时，认为指定的配置入口有效。
     *
     * @param configEntry 指定的配置入口。
     * @return 指定的配置入口是否有效。
     */
    public static boolean isValid(ConfigEntry configEntry) {
        if (Objects.isNull(configEntry))
            return false;
        if (Objects.isNull(configEntry.getConfigKey()))
            return false;

        return isValid(configEntry.getConfigFirmProps());
    }

    /**
     * 判断指定的配置入口是否无效。
     *
     * <p>
     * 如果配置入口不有效，则无效，即该方法等同于 <code>！ isValid(configEntry)</code>
     *
     * @param configEntry 指定的配置入口。
     * @return 指定的配置入口是否无效。
     */
    public static boolean nonValid(ConfigEntry configEntry) {
        return !isValid(configEntry);
    }

    /**
     * 根据指定的配置模型生成一个不可更改的配置模型。
     *
     * @param configModel 指定的配置模型。
     * @return 根据指定模型生成的不可更改的配置模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static ConfigModel unmodifiableConfigModel(ConfigModel configModel) {
        Objects.requireNonNull(configModel, DwarfUtil.getExceptionString(ExceptionStringKey.CONFIGUTIL_0));
        return new UnmodifiableConfigModel(configModel);
    }

    private static final class UnmodifiableConfigModel implements ConfigModel {

        private final ConfigModel delegate;

        public UnmodifiableConfigModel(ConfigModel delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ConfigObserver> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ConfigObserver observer) {
            return delegate.addObserver(observer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ConfigObserver observer) {
            return delegate.removeObserver(observer);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            delegate.clearObserver();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
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
            return delegate.getCurrentValue(configKey);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Map<ConfigKey, String> getAllCurrentValue() {
            return delegate.getAllCurrentValue();
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
        public boolean add(ConfigEntry configEntry) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<ConfigEntry> configEntries) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(ConfigKey configKey) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<ConfigKey> configKeys) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<ConfigKey> configKeys) {
            throw new UnsupportedOperationException("retainAll");
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
            return delegate.isValueValid(configKey, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValidValue(ConfigKey configKey) {
            return delegate.getValidValue(configKey);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ConfigFirmProps getConfigFirmProps(ConfigKey configKey) {
            return delegate.getConfigFirmProps(configKey);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setConfigFirmProps(ConfigKey configKey, ConfigFirmProps configFirmProps) {
            throw new UnsupportedOperationException("setConfigFirmProps");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setCurrentValue(ConfigKey configKey, String currentValue) {
            return delegate.setCurrentValue(configKey, currentValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setAllCurrentValue(Map<ConfigKey, String> map) {
            return delegate.setAllCurrentValue(map);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetCurrentValue(ConfigKey configKey) {
            return delegate.resetCurrentValue(configKey);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetAllCurrentValue() {
            return delegate.resetAllCurrentValue();
        }

    }

    /**
     * 判断指定的 Ex 配置入口是不是有效的。
     *
     * @param entry 指定的配置入口。
     * @return 指定的配置入口是不是有效的。
     */
    public static boolean isValid(ExconfigEntry entry) {
        if (Objects.isNull(entry))
            return false;
        if (Objects.isNull(entry.getConfigKey()))
            return false;
        if (Objects.isNull(entry.getValueParser()))
            return false;
        if (Objects.isNull(entry.getConfigFirmProps()))
            return false;
        if (Objects.isNull(entry.getCurrentValue()))
            return false;
        return !nonValid(entry.getConfigFirmProps());
    }

    /**
     * 判断指定的 Ex 配置入口是不是无效的。
     *
     * @param entry 指定的配置入口
     * @return 指定的配置入口是不是无效的。
     */
    public static boolean nonValid(ExconfigEntry entry) {
        return !isValid(entry);
    }

    /**
     * 由指定的 Ex 配置模型生成一个线程安全的 Ex 配置模型。
     *
     * @param exconfigModel 指定的 Ex 配置模型。
     * @return 由指定 Ex 配置模型生成的线程安全的 Ex 配置模型。
     */
    public static SyncExconfigModel syncExconfigModel(ExconfigModel exconfigModel) {
        Objects.requireNonNull(exconfigModel, DwarfUtil.getExceptionString(ExceptionStringKey.CONFIGUTIL_1));
        return new SyncExconfigModelImpl(exconfigModel);
    }

    private static class SyncExconfigModelImpl implements SyncExconfigModel {

        private final ExconfigModel delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncExconfigModelImpl(ExconfigModel delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return lock;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ExconfigObserver> getObservers() {
            lock.readLock().lock();
            try {
                return delegate.getObservers();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ExconfigObserver observer) {
            lock.writeLock().lock();
            try {
                return delegate.addObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ExconfigObserver observer) {
            lock.writeLock().lock();
            try {
                return delegate.removeObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            lock.writeLock().lock();
            try {
                delegate.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            lock.writeLock().lock();
            try {
                delegate.clearObserver();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(ConfigKey configKey) {
            lock.readLock().lock();
            try {
                return delegate.containsKey(configKey);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCurrentValue(ConfigKey configKey) {
            lock.readLock().lock();
            try {
                return delegate.getCurrentValue(configKey);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Map<ConfigKey, String> getAllCurrentValue() {
            lock.readLock().lock();
            try {
                return delegate.getAllCurrentValue();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            lock.readLock().lock();
            try {
                return delegate.isEmpty();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ConfigKey> keySet() {
            lock.readLock().lock();
            try {
                return delegate.keySet();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(ExconfigEntry exconfigEntry) {
            lock.writeLock().lock();
            try {
                return delegate.add(exconfigEntry);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<ExconfigEntry> exconfigEntries) {
            lock.writeLock().lock();
            try {
                return delegate.addAll(exconfigEntries);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(ConfigKey configKey) {
            lock.writeLock().lock();
            try {
                return delegate.remove(configKey);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<ConfigKey> configKeys) {
            lock.writeLock().lock();
            try {
                return delegate.removeAll(configKeys);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<ConfigKey> configKeys) {
            lock.writeLock().lock();
            try {
                return delegate.retainAll(configKeys);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            lock.readLock().lock();
            try {
                return delegate.size();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValueValid(ConfigKey configKey, String value) {
            lock.readLock().lock();
            try {
                return delegate.isValueValid(configKey, value);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValidValue(ConfigKey configKey) {
            lock.readLock().lock();
            try {
                return delegate.getValidValue(configKey);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ConfigFirmProps getConfigFirmProps(ConfigKey configKey) {
            lock.readLock().lock();
            try {
                return delegate.getConfigFirmProps(configKey);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setConfigFirmProps(ConfigKey configKey, ConfigFirmProps configFirmProps) {
            lock.writeLock().lock();
            try {
                return delegate.setConfigFirmProps(configKey, configFirmProps);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setCurrentValue(ConfigKey configKey, String currentValue) {
            lock.writeLock().lock();
            try {
                return delegate.setCurrentValue(configKey, currentValue);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setAllCurrentValue(Map<ConfigKey, String> map) {
            lock.writeLock().lock();
            try {
                return delegate.setAllCurrentValue(map);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetCurrentValue(ConfigKey configKey) {
            lock.writeLock().lock();
            try {
                return delegate.resetCurrentValue(configKey);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetAllCurrentValue() {
            lock.writeLock().lock();
            try {
                return delegate.resetAllCurrentValue();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ValueParser getValueParser(ConfigKey configKey) {
            lock.readLock().lock();
            try {
                return delegate.getValueParser(configKey);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setValueParser(ConfigKey configKey, ValueParser valueParser) {
            lock.writeLock().lock();
            try {
                return delegate.setValueParser(configKey, valueParser);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getParsedValue(ConfigKey configKey) {
            lock.readLock().lock();
            try {
                return delegate.getParsedValue(configKey);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T getParsedValue(ConfigKey configKey, Class<T> clas) {
            lock.readLock().lock();
            try {
                return delegate.getParsedValue(configKey, clas);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setParsedValue(ConfigKey configKey, Object obj) {
            lock.writeLock().lock();
            try {
                return delegate.setParsedValue(configKey, obj);
            } finally {
                lock.writeLock().unlock();
            }
        }

    }

    public static SyncConfigModel syncConfigModel(ConfigModel configModel) {
        Objects.requireNonNull(configModel, DwarfUtil.getExceptionString(ExceptionStringKey.CONFIGUTIL_0));
        return new SyncConfigModelImpl(configModel);
    }

    private static class SyncConfigModelImpl implements SyncConfigModel {

        private final ConfigModel delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncConfigModelImpl(ConfigModel delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return lock;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ConfigObserver> getObservers() {
            lock.readLock().lock();
            try {
                return delegate.getObservers();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ConfigObserver observer) {
            lock.writeLock().lock();
            try {
                return delegate.addObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ConfigObserver observer) {
            lock.writeLock().lock();
            try {
                return delegate.removeObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            lock.writeLock().lock();
            try {
                delegate.clearObserver();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            lock.writeLock().lock();
            try {
                delegate.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(ConfigKey configKey) {
            lock.readLock().lock();
            try {
                return delegate.containsKey(configKey);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCurrentValue(ConfigKey configKey) {
            lock.readLock().lock();
            try {
                return delegate.getCurrentValue(configKey);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Map<ConfigKey, String> getAllCurrentValue() {
            lock.readLock().lock();
            try {
                return delegate.getAllCurrentValue();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            lock.readLock().lock();
            try {
                return delegate.isEmpty();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ConfigKey> keySet() {
            lock.readLock().lock();
            try {
                return delegate.keySet();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(ConfigEntry configEntry) {
            lock.writeLock().lock();
            try {
                return delegate.add(configEntry);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<ConfigEntry> configEntries) {
            lock.writeLock().lock();
            try {
                return delegate.addAll(configEntries);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(ConfigKey configKey) {
            lock.writeLock().lock();
            try {
                return delegate.remove(configKey);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<ConfigKey> configKeys) {
            lock.writeLock().lock();
            try {
                return delegate.removeAll(configKeys);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<ConfigKey> configKeys) {
            lock.writeLock().lock();
            try {
                return delegate.retainAll(configKeys);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            lock.readLock().lock();
            try {
                return delegate.size();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValueValid(ConfigKey configKey, String value) {
            lock.readLock().lock();
            try {
                return delegate.isValueValid(configKey, value);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValidValue(ConfigKey configKey) {
            lock.readLock().lock();
            try {
                return delegate.getValidValue(configKey);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ConfigFirmProps getConfigFirmProps(ConfigKey configKey) {
            lock.readLock().lock();
            try {
                return delegate.getConfigFirmProps(configKey);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setConfigFirmProps(ConfigKey configKey, ConfigFirmProps configFirmProps) {
            lock.writeLock().lock();
            try {
                return delegate.setConfigFirmProps(configKey, configFirmProps);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setCurrentValue(ConfigKey configKey, String currentValue) {
            lock.writeLock().lock();
            try {
                return delegate.setCurrentValue(configKey, currentValue);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setAllCurrentValue(Map<ConfigKey, String> map) {
            lock.writeLock().lock();
            try {
                return delegate.setAllCurrentValue(map);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetCurrentValue(ConfigKey configKey) {
            lock.writeLock().lock();
            try {
                return delegate.resetCurrentValue(configKey);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetAllCurrentValue() {
            lock.writeLock().lock();
            try {
                return delegate.resetAllCurrentValue();
            } finally {
                lock.writeLock().unlock();
            }
        }

    }

    /**
     * 根据指定的 Ex 配置模型生成一个不可编辑的 Ex 配置模型。
     *
     * @param exconfigModel 指定的配置模型。
     * @return 不可编辑的 Ex 配置模型。
     */
    public static ExconfigModel unmodifiableExconfigModel(ExconfigModel exconfigModel) {
        Objects.requireNonNull(exconfigModel, DwarfUtil.getExceptionString(ExceptionStringKey.CONFIGUTIL_1));
        return new UnmodifiableExconfigModel(exconfigModel);
    }

    private static class UnmodifiableExconfigModel implements ExconfigModel {

        private final ExconfigModel delegate;

        public UnmodifiableExconfigModel(ExconfigModel delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCurrentValue(ConfigKey configKey) {
            return delegate.getCurrentValue(configKey);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ExconfigObserver> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ExconfigObserver observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Map<ConfigKey, String> getAllCurrentValue() {
            return delegate.getAllCurrentValue();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ExconfigObserver observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setCurrentValue(ConfigKey configKey, String currentValue) {
            throw new UnsupportedOperationException("setCurrentValue");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
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
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ConfigKey> keySet() {
            return Collections.unmodifiableSet(delegate.keySet());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setAllCurrentValue(Map<ConfigKey, String> map) {
            throw new UnsupportedOperationException("setAllCurrentValue");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(ExconfigEntry exconfigEntry) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<ExconfigEntry> exconfigEntries) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(ConfigKey configKey) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<ConfigKey> configKeys) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<ConfigKey> configKeys) {
            throw new UnsupportedOperationException("retainAll");
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
            return delegate.isValueValid(configKey, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValidValue(ConfigKey configKey) {
            return delegate.getValidValue(configKey);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ConfigFirmProps getConfigFirmProps(ConfigKey configKey) {
            return delegate.getConfigFirmProps(configKey);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setConfigFirmProps(ConfigKey configKey, ConfigFirmProps configFirmProps) {
            throw new UnsupportedOperationException("setConfigFirmProps");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetCurrentValue(ConfigKey configKey) {
            throw new UnsupportedOperationException("resetCurrentValue");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetAllCurrentValue() {
            throw new UnsupportedOperationException("resetAllCurrentValue");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ValueParser getValueParser(ConfigKey configKey) {
            return delegate.getValueParser(configKey);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setValueParser(ConfigKey configKey, ValueParser valueParser) {
            throw new UnsupportedOperationException("setValueParser");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getParsedValue(ConfigKey configKey) {
            return delegate.getParsedValue(configKey);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T getParsedValue(ConfigKey configKey, Class<T> clas) {
            return delegate.getParsedValue(configKey, clas);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setParsedValue(ConfigKey configKey, Object obj) {
            throw new UnsupportedOperationException("setParsedValue");
        }

    }

    // 禁止外部实例化。
    private ConfigUtil() {
    }
}
