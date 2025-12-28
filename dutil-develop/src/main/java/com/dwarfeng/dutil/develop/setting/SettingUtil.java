package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.dutil.basic.prog.Filter;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.develop.cfg.struct.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;
import com.dwarfeng.dutil.develop.setting.SettingHandler.Entry;
import com.dwarfeng.dutil.develop.setting.obs.SettingObserver;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 有关配置工具的工具包。
 *
 * <p>
 * 该包中包含关于对配置工具器进行操作的常用方法。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public final class SettingUtil {

    /**
     * 根据指定的配置入口生成一个不可变更的配置入口。
     *
     * @param entry 指定的配置入口。
     * @return 根据指定的配置入口生成的不可变更的配置入口。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static SettingHandler.Entry unmodifiableEntry(SettingHandler.Entry entry) throws NullPointerException {
        Objects.requireNonNull(entry, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_0));
        return new UnmodifiableEntry(entry);
    }

    private static final class UnmodifiableEntry implements SettingHandler.Entry {

        private final SettingHandler.Entry delegate;

        public UnmodifiableEntry(SettingHandler.Entry delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getKey() {
            return delegate.getKey();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SettingInfo getSettingInfo() {
            return delegate.getSettingInfo();
        }

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
        public String getCurrentValue() {
            return delegate.getCurrentValue();
        }

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
        public boolean equals(Object obj) {
            if (obj == this)
                return true;
            if (obj == delegate)
                return true;
            return delegate.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return delegate.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegate.toString();
        }

    }

    /**
     * 根据指定的配置处理器生成一个不可更改的配置处理器。
     *
     * @param settingHandler 指定的配置处理器。
     * @return 根据指定的配置处理器生成的不可更改的配置处理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static SettingHandler unmodifiableSettingHandler(SettingHandler settingHandler) throws NullPointerException {
        Objects.requireNonNull(settingHandler, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_1));
        return new UnmodifableSettingHandler(settingHandler);
    }

    private static final class UnmodifableSettingHandler implements SettingHandler {

        private final SettingHandler delegate;

        public UnmodifableSettingHandler(SettingHandler delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SettingObserver> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SettingObserver observer) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SettingObserver observer) throws UnsupportedOperationException {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("clearObserver");
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
        public boolean put(String key, SettingInfo settingInfo, String currentValue) {
            throw new UnsupportedOperationException("put");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean put(Name key, SettingInfo settingInfo, String currentValue) {
            throw new UnsupportedOperationException("put");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean putAll(SettingHandler handler) {
            throw new UnsupportedOperationException("putAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<Entry> entrySet() {
            return CollectionUtil.readOnlySet(delegate.entrySet(), SettingUtil::unmodifiableEntry);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            delegate.clear();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<String> keySet() {
            return Collections.unmodifiableSet(delegate.keySet());
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
        public boolean containsAllKey(Collection<?> c) {
            return delegate.containsAllKey(c);
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
        public SettingInfo getSettingInfo(String key) {
            return delegate.getSettingInfo(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SettingInfo getSettingInfo(Name key) {
            return delegate.getSettingInfo(key);
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
            throw new UnsupportedOperationException("setSettingInfo");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValueValid(String key, String value) {
            return delegate.isValueValid(key, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValueValid(Name key, String value) {
            return delegate.isValueValid(key, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValidValue(String key) {
            return delegate.getValidValue(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValidValue(Name key) {
            return delegate.getValidValue(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCurrentValue(String key) {
            return delegate.getCurrentValue(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCurrentValue(Name key) {
            return delegate.getCurrentValue(key);
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
            throw new UnsupportedOperationException("setCurrentValue");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setAllCurrentValue(Map<String, String> m) {
            throw new UnsupportedOperationException("setAllCurrentValue");
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
            throw new UnsupportedOperationException("resetCurrentValue");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetAllCurrentValue(Collection<String> c) {
            throw new UnsupportedOperationException("resetAllCurrentValue");
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
        public Object getParsedValue(String key) {
            return delegate.getParsedValue(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getParsedValue(Name key) {
            return delegate.getParsedValue(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T getParsedValue(String key, Class<T> clas) {
            return delegate.getParsedValue(key, clas);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T getParsedValue(Name key, Class<T> clas) {
            return delegate.getParsedValue(key, clas);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getParsedValidValue(String key) {
            return delegate.getParsedValidValue(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getParsedValidValue(Name key) {
            return delegate.getParsedValidValue(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T getParsedValidValue(String key, Class<T> clas) {
            return delegate.getParsedValidValue(key, clas);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T getParsedValidValue(Name key, Class<T> clas) {
            return delegate.getParsedValidValue(key, clas);
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
            throw new UnsupportedOperationException("setParsedValue");
        }

    }

    /**
     * 根据指定的配置处理器生成一个线程安全的配置处理器。
     *
     * @param settingHandler 指定的配置处理器。
     * @return 根据指定的配置处理器生成的线程安全的配置处理器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static SyncSettingHandler syncSettingHandler(SettingHandler settingHandler) throws NullPointerException {
        Objects.requireNonNull(settingHandler, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_1));
        return new SyncSettingHandlerImpl(settingHandler);
    }

    private static final class SyncSettingHandlerImpl implements SyncSettingHandler {

        private final SettingHandler delegate;

        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncSettingHandlerImpl(SettingHandler delegate) {
            this.delegate = delegate;
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
        public boolean put(String key, SettingInfo settingInfo, String currentValue) {
            lock.writeLock().lock();
            try {
                return delegate.put(key, settingInfo, currentValue);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean put(Name key, SettingInfo settingInfo, String currentValue) {
            lock.writeLock().lock();
            try {
                return delegate.put(key, settingInfo, currentValue);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean putAll(SettingHandler handler) {
            lock.writeLock().lock();
            try {
                return delegate.putAll(handler);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<Entry> entrySet() {
            lock.readLock().lock();
            try {
                return delegate.entrySet();
            } finally {
                lock.readLock().unlock();
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
        public Set<String> keySet() {
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
        public boolean containsKey(Object key) {
            lock.readLock().lock();
            try {
                return delegate.containsKey(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAllKey(Collection<?> c) {
            lock.readLock().lock();
            try {
                return delegate.containsAllKey(c);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeKey(Object key) {
            lock.writeLock().lock();
            try {
                return delegate.removeKey(key);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAllKey(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.removeAllKey(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAllKey(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.retainAllKey(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SettingInfo getSettingInfo(String key) {
            lock.readLock().lock();
            try {
                return delegate.getSettingInfo(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SettingInfo getSettingInfo(Name key) {
            lock.readLock().lock();
            try {
                return delegate.getSettingInfo(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setSettingInfo(String key, SettingInfo settingInfo) {
            lock.writeLock().lock();
            try {
                return delegate.setSettingInfo(key, settingInfo);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setSettingInfo(Name key, SettingInfo settingInfo) {
            lock.writeLock().lock();
            try {
                return delegate.setSettingInfo(key, settingInfo);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValueValid(String key, String value) {
            lock.readLock().lock();
            try {
                return delegate.isValueValid(key, value);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValueValid(Name key, String value) {
            lock.readLock().lock();
            try {
                return delegate.isValueValid(key, value);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValidValue(String key) {
            lock.readLock().lock();
            try {
                return delegate.getValidValue(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValidValue(Name key) {
            lock.readLock().lock();
            try {
                return delegate.getValidValue(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCurrentValue(String key) {
            lock.readLock().lock();
            try {
                return delegate.getCurrentValue(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCurrentValue(Name key) {
            lock.readLock().lock();
            try {
                return delegate.getCurrentValue(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setCurrentValue(String key, String newValue) {
            lock.writeLock().lock();
            try {
                return delegate.setCurrentValue(key, newValue);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setCurrentValue(Name key, String newValue) {
            lock.writeLock().lock();
            try {
                return delegate.setCurrentValue(key, newValue);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setAllCurrentValue(Map<String, String> m) {
            lock.writeLock().lock();
            try {
                return delegate.setAllCurrentValue(m);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetCurrentValue(String key) {
            lock.writeLock().lock();
            try {
                return delegate.resetCurrentValue(key);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetCurrentValue(Name key) {
            lock.writeLock().lock();
            try {
                return delegate.resetCurrentValue(key);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean resetAllCurrentValue(Collection<String> c) {
            lock.writeLock().lock();
            try {
                return delegate.resetAllCurrentValue(c);
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
        public Object getParsedValue(String key) {
            lock.readLock().lock();
            try {
                return delegate.getParsedValue(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getParsedValue(Name key) {
            lock.readLock().lock();
            try {
                return delegate.getParsedValue(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T getParsedValue(String key, Class<T> clas) {
            lock.readLock().lock();
            try {
                return delegate.getParsedValue(key, clas);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T getParsedValue(Name key, Class<T> clas) {
            lock.readLock().lock();
            try {
                return delegate.getParsedValue(key, clas);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getParsedValidValue(String key) {
            lock.readLock().lock();
            try {
                return delegate.getParsedValidValue(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getParsedValidValue(Name key) {
            lock.readLock().lock();
            try {
                return delegate.getParsedValidValue(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T getParsedValidValue(String key, Class<T> clas) {
            lock.readLock().lock();
            try {
                return delegate.getParsedValidValue(key, clas);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T getParsedValidValue(Name key, Class<T> clas) {
            lock.readLock().lock();
            try {
                return delegate.getParsedValidValue(key, clas);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setParsedValue(String key, Object obj) {
            lock.writeLock().lock();
            try {
                return delegate.setParsedValue(key, obj);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setParsedValue(Name key, Object obj) {
            lock.writeLock().lock();
            try {
                return delegate.setParsedValue(key, obj);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SettingObserver> getObservers() {
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
        public boolean addObserver(SettingObserver observer) throws UnsupportedOperationException {
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
        public boolean removeObserver(SettingObserver observer) throws UnsupportedOperationException {
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
        public void clearObserver() throws UnsupportedOperationException {
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
        public ReadWriteLock getLock() {
            return lock;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            lock.readLock().lock();
            try {
                return delegate.hashCode();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            lock.readLock().lock();
            try {
                if (obj == this)
                    return true;
                if (obj == delegate)
                    return true;

                return delegate.equals(obj);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            lock.readLock().lock();
            try {
                return delegate.toString();
            } finally {
                lock.readLock().unlock();
            }
        }

    }

    /**
     * 将指定枚举中的条目添加到指定的配置处理器中。
     *
     * <p>
     * 该方法要求枚举实现 {@link SettingEnumItem} 接口， 并将枚举中的所有对象按照接口的格式依次添加到指定的配置处理器中。
     *
     * @param clazz          指定的枚举对应的类。
     * @param settingHandler 指定的配置处理器。
     * @return 该操作是否对指定的配置处理器造成了改变。
     * @throws IllegalStateException 指定的枚举类没有实现 <code>SettingEnumItem</code> 接口。
     * @throws NullPointerException  指定的入口参数为 <code> null </code>。
     */
    public static <T extends Enum<T>> boolean putEnumItems(Class<T> clazz, SettingHandler settingHandler)
            throws NullPointerException, IllegalStateException {
        Objects.requireNonNull(clazz, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_2));
        Objects.requireNonNull(settingHandler, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_1));

        if (!SettingEnumItem.class.isAssignableFrom(clazz))
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_3));

        SettingEnumItem[] items = (SettingEnumItem[]) clazz.getEnumConstants();
        return putEnumItems(items, settingHandler);
    }

    /**
     * 将指定集合中的所有配置枚举条目添加到指定的配置处理器中。
     *
     * @param items          指定的配置枚举条目组成的集合。
     * @param settingHandler 指定的配置处理器。
     * @return 该操作是否对指定的配置处理器造成了改变。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static boolean putEnumItems(Collection<SettingEnumItem> items, SettingHandler settingHandler)
            throws NullPointerException {
        Objects.requireNonNull(items, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_4));
        Objects.requireNonNull(settingHandler, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_1));

        return putEnumItems(items.toArray(new SettingEnumItem[0]), settingHandler);
    }

    /**
     * 将指定数组中的所有配置枚举条目添加到指定的配置处理器中。
     *
     * @param items          指定的配置枚举条目组成的数组。
     * @param settingHandler 指定的配置处理器。
     * @return 该操作是否对指定的配置处理器造成了改变。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static boolean putEnumItems(SettingEnumItem[] items, SettingHandler settingHandler)
            throws NullPointerException {
        Objects.requireNonNull(items, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_4));
        Objects.requireNonNull(settingHandler, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_1));

        boolean aFlag = false;
        for (SettingEnumItem item : items) {
            if (settingHandler.put(item.getName(), item.getSettingInfo(), item.getSettingInfo().getDefaultValue()))
                aFlag = true;
        }
        return aFlag;
    }

    /**
     * 将指定的配置值检查器、值转换器、默认值转换为配置信息。
     *
     * <p>
     * 该方法可以由 com.dwarfeng.dutil.develop.cfg 包中的配置值检查器以及值转换器生成新的配置信息。
     *
     * @param configChecker 指定的配置值检查器。
     * @param valueParser   制定的值转换器。
     * @param defaultValue  制定的默认值。
     * @return 由指定的配置值检查器、指定的值转换器、指定的配置信息转换而成的配置信息。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 指定的默认值无法通过配置值检查器的检验。
     * @see ConfigChecker
     * @see ValueParser
     */
    public static SettingInfo toSettingInfo(ConfigChecker configChecker, ValueParser valueParser, String defaultValue)
            throws NullPointerException, IllegalArgumentException {
        return new CvSettingInfo(configChecker, valueParser, defaultValue);
    }

    private static final class CvSettingInfo extends AbstractSettingInfo implements SettingInfo {

        private final ConfigChecker configChecker;
        private final ValueParser valueParser;

        public CvSettingInfo(ConfigChecker configChecker, ValueParser valueParser, String defaultValue)
                throws NullPointerException, IllegalArgumentException {
            super(defaultValue);
            Objects.requireNonNull(configChecker, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_5));
            Objects.requireNonNull(valueParser, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_6));

            this.configChecker = configChecker;
            this.valueParser = valueParser;

            checkDefaultValue();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((configChecker == null) ? 0 : configChecker.hashCode());
            result = prime * result + ((valueParser == null) ? 0 : valueParser.hashCode());
            result = prime * result + ((defaultValue == null) ? 0 : defaultValue.hashCode());
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
            if (getClass() != obj.getClass())
                return false;
            CvSettingInfo other = (CvSettingInfo) obj;
            if (configChecker == null) {
                if (other.configChecker != null)
                    return false;
            } else if (!configChecker.equals(other.configChecker))
                return false;
            if (valueParser == null) {
                return other.valueParser == null;
            } else if (!valueParser.equals(other.valueParser)) {
                return false;
            } else if (defaultValue == null) {
                return other.defaultValue == null;
            } else return defaultValue.equals(other.defaultValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "CvSettingInfo [defaultValue=" + defaultValue + ", configChecker=" + configChecker + ", valueParser="
                    + valueParser + "]";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected boolean isNonNullValid(String value) {
            return configChecker.isValid(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Object parseValidValue(String value) {
            return valueParser.parseValue(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String parseNonNullObject(Object object) {
            return valueParser.parseObject(object);
        }

    }

    /**
     * 将指定的配置枚举条目、当前值转换为配置入口。
     *
     * @param item         指定的配置枚举条目。
     * @param currentValue 指定的当前值。
     * @return 由制定的配置枚举条目、指定的当前值转换而成的配置入口。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static SettingHandler.Entry toEntry(SettingEnumItem item, String currentValue) throws NullPointerException {
        return new SsEntry(item, currentValue);
    }

    private static final class SsEntry extends AbstractSettingHandler.AbstractEntry implements Entry {

        private final SettingEnumItem item;
        private final String currentValue;

        public SsEntry(SettingEnumItem item, String currentValue) {
            this.item = item;
            this.currentValue = currentValue;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getKey() {
            return item.getName();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SettingInfo getSettingInfo() {
            return item.getSettingInfo();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCurrentValue() {
            return currentValue;
        }

    }

    /**
     * 根据指定的配置信息和指定的值过滤器生成一个新的配置信息。
     *
     * <p>
     * 只有同时满足配置信息值检查和过滤器的值检查，才能算是有效的值。 <br>
     * 该方法使一个配置信息的有效范围按照指定的要求进一步缩小。
     *
     * @param settingInfo 指定的配置信息。
     * @param valueFilter 指定的值过滤器。
     * @return 根据指定的配置信息和指定的值过滤器生成的一个新的配置信息。
     * @throws IllegalArgumentException 指定配置信息的默认值不能通过过滤器。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     */
    public static SettingInfo valueFilteredSettingInfo(SettingInfo settingInfo, Filter<String> valueFilter)
            throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(settingInfo, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_7));
        Objects.requireNonNull(valueFilter, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_8));
        if (!valueFilter.accept(settingInfo.getDefaultValue()))
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_9));

        return new ValueFilteredSettingInfo(settingInfo, valueFilter);
    }

    private static final class ValueFilteredSettingInfo implements SettingInfo {

        private final SettingInfo delegate;
        private final Filter<String> valueFilter;

        public ValueFilteredSettingInfo(SettingInfo delegate, Filter<String> valueFilter) {
            this.delegate = delegate;
            this.valueFilter = valueFilter;

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(String value) {
            if (Objects.isNull(value))
                return false;
            if (delegate.nonValid(value))
                return false;

            return valueFilter.accept(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean nonValid(String value) {
            if (Objects.isNull(value))
                return true;
            return !isValid(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object parseValue(String value) {
            if (Objects.isNull(value) || nonValid(value))
                return null;
            return delegate.parseValue(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String parseObject(Object object) {
            if (Objects.isNull(object))
                return null;
            String value = delegate.parseObject(object);
            return valueFilter.accept(value) ? value : null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDefaultValue() {
            return delegate.getDefaultValue();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((delegate == null) ? 0 : delegate.hashCode());
            result = prime * result + ((valueFilter == null) ? 0 : valueFilter.hashCode());
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
            if (!(obj instanceof ValueFilteredSettingInfo))
                return false;
            ValueFilteredSettingInfo other = (ValueFilteredSettingInfo) obj;
            if (delegate == null) {
                if (other.delegate != null)
                    return false;
            } else if (!delegate.equals(other.delegate))
                return false;
            if (valueFilter == null) {
                return other.valueFilter == null;
            } else return valueFilter.equals(other.valueFilter);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "FilteredSettingInfo [delegate=" + delegate + ", valueFilter=" + valueFilter + "]";
        }
    }

    /**
     * 根据指定的配置信息和指定的对象过滤器生成一个新的配置信息。
     *
     * <p>
     * 只有同时满足配置信息值检查和过滤器的值检查，才能算是有效的值。 <br>
     * 该方法使一个配置信息的有效范围按照指定的要求进一步缩小。
     *
     * @param settingInfo  指定的配置信息。
     * @param objectFilter 指定的对象过滤器。
     * @return 根据指定的配置信息和指定的对象过滤器生成的一个新的配置信息。
     * @throws IllegalArgumentException 指定配置信息的默认值不能通过过滤器。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     */
    public static SettingInfo objectFilteredSettingInfo(SettingInfo settingInfo, Filter<Object> objectFilter)
            throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(settingInfo, DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_7));
        Objects.requireNonNull(objectFilter, "入口参数 objectFilter 不能为 null。");
        if (!objectFilter.accept(settingInfo.parseValue(settingInfo.getDefaultValue())))
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.SETTINGUTIL_9));

        return new ObjectFilteredSettingInfo(settingInfo, objectFilter);
    }

    private static final class ObjectFilteredSettingInfo implements SettingInfo {

        private final SettingInfo delegate;
        private final Filter<Object> objectFilter;

        public ObjectFilteredSettingInfo(SettingInfo delegate, Filter<Object> objectFilter) {
            this.delegate = delegate;
            this.objectFilter = objectFilter;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid(String value) {
            if (Objects.isNull(value))
                return false;
            if (delegate.nonValid(value))
                return false;

            Object object = delegate.parseValue(value);
            return objectFilter.accept(object);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean nonValid(String value) {
            if (Objects.isNull(value))
                return true;
            return !isValid(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object parseValue(String value) {
            if (Objects.isNull(value) || nonValid(value))
                return null;
            return delegate.parseValue(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String parseObject(Object object) {
            if (Objects.isNull(object))
                return null;
            if (!objectFilter.accept(object))
                return null;

            return delegate.parseObject(object);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDefaultValue() {
            return delegate.getDefaultValue();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((delegate == null) ? 0 : delegate.hashCode());
            result = prime * result + ((objectFilter == null) ? 0 : objectFilter.hashCode());
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
            if (!(obj instanceof ObjectFilteredSettingInfo))
                return false;
            ObjectFilteredSettingInfo other = (ObjectFilteredSettingInfo) obj;
            if (delegate == null) {
                if (other.delegate != null)
                    return false;
            } else if (!delegate.equals(other.delegate))
                return false;
            if (objectFilter == null) {
                return other.objectFilter == null;
            } else return objectFilter.equals(other.objectFilter);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "ObjectFilteredSettingInfo [delegate=" + delegate + ", objectFilter=" + objectFilter + "]";
        }

    }

    // 禁止外部实例化。
    private SettingUtil() {
    }
}
