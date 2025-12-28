package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.setting.obs.SettingObserver;

import java.util.*;

/**
 * 默认配置处理器。
 *
 * <p>
 * 配置处理器的默认实现。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class DefaultSettingHandler extends AbstractSettingHandler {

    /**
     * 配置处理器中默认的迭代器。
     *
     * @author DwArFeng
     * @since 0.2.0-beta
     */
    private abstract class DefaultIterator<E> implements Iterator<E> {

        /**
         * 迭代器所引用的键值迭代器。
         */
        protected final Iterator<String> keyIterator = settingInfoMap.keySet().iterator();

        /**
         * 迭代器的期望更改计数。
         */
        protected int exceptedModCount = modCount();
        /**
         * 迭代器的当前键引用。
         */
        protected String currRef = null;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return keyIterator.hasNext();
        }

        /**
         * 检查迭代器在迭代期间是否发生了改变。
         */
        protected void checkForComodification() {
            if (exceptedModCount != modCount()) {
                throw new ConcurrentModificationException(
                        DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTSETTINGHANDLER_3));
            }
        }

    }

    /**
     * 默认配置处理器的键值集合。
     *
     * @author DwArFeng
     * @since 0.2.0-beta
     */
    private final class DefaultKeySet extends AbstractSet<String> implements Set<String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return settingInfoMap.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return settingInfoMap.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            return settingInfoMap.containsKey(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<String> iterator() {
            return new DefaultKeySetIterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(String e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            if (Objects.isNull(o))
                return false;
            if (!contains(o))
                return false;

            settingInfoMap.remove(o);
            currentValueMap.remove(o);
            increaceModCount();
            fireKeyRemoved((String) o);
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends String> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTSETTINGHANDLER_2));
            return batchRemove(c, false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTSETTINGHANDLER_2));
            return batchRemove(c, true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            DefaultSettingHandler.this.clear();
        }

        private boolean batchRemove(Collection<?> c, boolean aFlag) {
            boolean result = false;

            for (Iterator<String> i = iterator(); i.hasNext(); ) {
                String key = i.next();

                if (c.contains(key) == aFlag) {
                    i.remove(); // 该方法调用了 Iterator 的方法，在此方法中对 modCount 进行操作。
                    result = true;
                }
            }

            return result;
        }

    }

    /**
     * 配置处理器中的键值迭代器。
     *
     * @author DwArFeng
     * @since 0.2.0-beta
     */
    private final class DefaultKeySetIterator extends DefaultIterator<String> implements Iterator<String> {

        /**
         * {@inheritDoc}
         */
        @Override
        public String next() {
            checkForComodification();

            try {
                currRef = keyIterator.next();
            } catch (Exception e) {
                currRef = null;
                throw e;
            }
            return currRef;
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("DuplicatedCode")
        @Override
        public void remove() {
            if (Objects.isNull(currRef)) {
                throw new IllegalStateException();
            }

            checkForComodification();

            try {
                keyIterator.remove();
                currentValueMap.remove(currRef);
                increaceModCount();
                exceptedModCount++;
                fireKeyRemoved(currRef);
            } finally {
                currRef = null;
            }
        }

    }

    /**
     * 默认配置处理器的入口集合。
     *
     * @author DwArFeng
     * @since 0.2.0-beta
     */
    private final class DefaultEntrySet extends AbstractSet<Entry> implements Set<Entry> {

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return settingInfoMap.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return settingInfoMap.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Entry))
                return false;

            String key = ((Entry) o).getKey();
            if (!settingInfoMap.containsKey(key))
                return false;

            SettingInfo settingInfo = ((Entry) o).getSettingInfo();
            String currentValue = ((Entry) o).getCurrentValue();

            return Objects.equals(settingInfoMap.get(key), settingInfo)
                    && Objects.equals(currentValueMap.get(key), currentValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<Entry> iterator() {
            return new DefaultEntrySetIterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(Entry e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            if (Objects.isNull(o))
                return false;
            if (!contains(o))
                return false;

            String key = ((Entry) o).getKey();

            settingInfoMap.remove(key);
            currentValueMap.remove(key);
            increaceModCount();
            fireKeyRemoved(key);
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends Entry> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTSETTINGHANDLER_2));
            return batchRemove(c, false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTSETTINGHANDLER_2));
            return batchRemove(c, true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            DefaultSettingHandler.this.clear();
        }

        private boolean batchRemove(Collection<?> c, boolean aFlag) {
            boolean result = false;

            for (Iterator<Entry> i = iterator(); i.hasNext(); ) {
                Entry entry = i.next();

                if (c.contains(entry) == aFlag) {
                    i.remove(); // 该方法调用了 Iterator 的方法，在此方法中对 modCount 进行操作。
                    result = true;
                }
            }

            return result;
        }

    }

    /**
     * 配置处理器中的入口迭代器。
     *
     * @author DwArFeng
     * @since 0.2.0-beta
     */
    private final class DefaultEntrySetIterator extends DefaultIterator<Entry> implements Iterator<Entry> {

        /**
         * {@inheritDoc}
         */
        @Override
        public Entry next() {
            checkForComodification();

            try {
                currRef = keyIterator.next();
            } catch (Exception e) {
                currRef = null;
                throw e;
            }

            return new DefaultEntry(currRef);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("DuplicatedCode")
        @Override
        public void remove() {
            if (Objects.isNull(currRef)) {
                throw new IllegalStateException();
            }

            checkForComodification();

            try {
                keyIterator.remove();
                currentValueMap.remove(currRef);
                increaceModCount();
                exceptedModCount++;
                fireKeyRemoved(currRef);
            } finally {
                currRef = null;
            }
        }

    }

    /**
     * 默认配置处理器的入口。
     *
     * @author DwArFeng
     * @since 0.2.0-beta
     */
    private final class DefaultEntry extends AbstractSettingHandler.AbstractEntry implements SettingHandler.Entry {

        private final String key;

        public DefaultEntry(String key) {
            this.key = key;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getKey() {
            return key;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public SettingInfo getSettingInfo() {
            return settingInfoMap.get(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setSettingInfo(SettingInfo settingInfo) {
            if (!containsKey(getKey()))
                return false;

            return DefaultSettingHandler.this.setSettingInfo(getKey(), settingInfo);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getCurrentValue() {
            return currentValueMap.get(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setCurrentValue(String currentValue) {
            if (!containsKey(getKey()))
                return false;

            return DefaultSettingHandler.this.setCurrentValue(getKey(), currentValue);
        }

    }

    /**
     * 配置处理器的配置信息映射。
     */
    protected final Map<String, SettingInfo> settingInfoMap;
    /**
     * 配置处理器的当前值映射。
     */
    protected final Map<String, String> currentValueMap;

    /**
     * The number of times this list has been <i>structurally modified</i>.
     * Structural modifications are those that change the size of the list, or
     * otherwise perturb it in such a fashion that iterations in progress may
     * yield incorrect results.
     *
     * <p>
     * This field is used by the iterator and list iterator implementation
     * returned by the {@code iterator} and {@code listIterator} methods. If the
     * value of this field changes unexpectedly, the iterator (or list iterator)
     * will throw a {@code ConcurrentModificationException} in response to the
     * {@code next}, {@code remove}, {@code previous}, {@code set} or
     * {@code add} operations. This provides <i>fail-fast</i> behavior, rather
     * than non-deterministic behavior in the face of concurrent modification
     * during iteration.
     *
     * <p>
     * <b>Use of this field by subclasses is optional.</b> If a subclass wishes
     * to provide fail-fast iterators (and list iterators), then it merely has
     * to increment this field in its {@code add(int, E)} and
     * {@code remove(int)} methods (and any other methods that it overrides that
     * result in structural modifications to the list). A single call to
     * {@code add(int, E)} or {@code remove(int)} must add no more than one to
     * this field, or the iterators (and list iterators) will throw bogus
     * {@code ConcurrentModificationExceptions}. If an implementation does not
     * wish to provide fail-fast iterators, this field may be ignored.
     */
    private transient int modCount = 0;

    /**
     * 生成一个默认的配置处理器。
     */
    public DefaultSettingHandler() {
        this(new HashMap<>(), new HashMap<>(), Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个由指定的配置信息映射、当前值映射和观察器集合组成的配置处理器。
     *
     * @param settingInfoMap  指定的配置信息映射。
     * @param currentValueMap 指定的当前值映射。
     * @param observers       指定的观察器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DefaultSettingHandler(Map<String, SettingInfo> settingInfoMap, Map<String, String> currentValueMap,
                                 Set<SettingObserver> observers) {
        super(observers);

        Objects.requireNonNull(settingInfoMap,
                DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTSETTINGHANDLER_0));
        Objects.requireNonNull(currentValueMap,
                DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTSETTINGHANDLER_1));

        this.settingInfoMap = settingInfoMap;
        this.currentValueMap = currentValueMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return settingInfoMap.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return settingInfoMap.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean put(String key, SettingInfo settingInfo, String currentValue) {
        if (Objects.isNull(key) || Objects.isNull(settingInfo))
            return false;

        boolean flag_exist = false;
        boolean flag_settingInfoChange = true;
        boolean flag_currentValueChange = true;

        SettingInfo oldSettingInfo = settingInfoMap.get(key);
        String oldCurrentValue = currentValueMap.get(key);

        if (settingInfoMap.containsKey(key)) {
            flag_exist = true;
        }

        if (flag_exist) {
            if (Objects.equals(oldSettingInfo, settingInfo)) {
                flag_settingInfoChange = false;
            } else {
                settingInfoMap.put(key, settingInfo);
            }

            if (Objects.equals(oldCurrentValue, currentValue)) {
                flag_currentValueChange = false;
            } else {
                currentValueMap.put(key, currentValue);
            }
        } else {
            settingInfoMap.put(key, settingInfo);
            currentValueMap.put(key, currentValue);
        }

        if (flag_exist) {
            if (flag_settingInfoChange) {
                fireSettingInfoChanged(key, oldSettingInfo, settingInfo);
            }
            if (flag_currentValueChange) {
                fireCurrentValueChanged(key, oldCurrentValue, currentValue);
            }
        } else {
            increaceModCount();
            fireKeyPut(key, settingInfo, currentValue);
        }

        return !flag_exist || (flag_settingInfoChange || flag_currentValueChange);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry> entrySet() {
        return new DefaultEntrySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        settingInfoMap.clear();
        currentValueMap.clear();
        increaceModCount();
        fireKeyCleared();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> keySet() {
        return new DefaultKeySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        return settingInfoMap.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeKey(Object key) {
        if (Objects.isNull(key))
            return false;
        if (!containsKey(key))
            return false;

        settingInfoMap.remove(key);
        currentValueMap.remove(key);
        increaceModCount();
        fireKeyRemoved((String) key);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTSETTINGHANDLER_2));
        return batchRemove(c, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DEFAULTSETTINGHANDLER_2));
        return batchRemove(c, false);
    }

    private boolean batchRemove(Collection<?> c, boolean aFlag) {
        boolean result = false;

        for (Iterator<String> i = keySet().iterator(); i.hasNext(); ) {
            String key = i.next();

            if (c.contains(key) == aFlag) {
                i.remove(); // 该方法调用了 Iterator 的方法，在此方法中对 modCount 进行操作。
                result = true;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SettingInfo getSettingInfo(String key) {
        if (!containsKey(key))
            return null;
        return settingInfoMap.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setSettingInfo(String key, SettingInfo settingInfo) {
        if (Objects.isNull(settingInfo))
            return false;
        if (!containsKey(key))
            return false;

        SettingInfo oldValue = settingInfoMap.get(key);
        if (Objects.equals(oldValue, settingInfo))
            return false;

        settingInfoMap.put(key, settingInfo);
        fireSettingInfoChanged(key, oldValue, settingInfo);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentValue(String key) {
        if (!containsKey(key))
            return null;

        return currentValueMap.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setCurrentValue(String key, String newValue) {
        if (!containsKey(key))
            return false;

        String oldValue = currentValueMap.get(key);
        if (Objects.equals(oldValue, newValue))
            return false;

        currentValueMap.put(key, newValue);
        fireCurrentValueChanged(key, oldValue, newValue);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetCurrentValue(String key) {
        if (!containsKey(key))
            return false;

        SettingInfo settingInfo = settingInfoMap.get(key);
        if (Objects.isNull(settingInfo))
            return false;

        String defaultValue = settingInfo.getDefaultValue();
        String currentValue = currentValueMap.get(key);

        if (Objects.equals(defaultValue, currentValue))
            return false;

        currentValueMap.put(key, defaultValue);
        fireCurrentValueChanged(key, currentValue, defaultValue);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setParsedValue(String key, Object obj) {
        if (!containsKey(key))
            return false;

        SettingInfo settingInfo = settingInfoMap.get(key);
        if (Objects.isNull(settingInfo))
            return false;

        String oldValue = currentValueMap.get(key);
        String newValue = settingInfo.parseObject(obj);

        if (Objects.equals(oldValue, newValue))
            return false;

        currentValueMap.put(key, newValue);
        fireCurrentValueChanged(key, oldValue, newValue);
        return true;
    }

    /**
     * 获取该配置处理器的更改计数。
     *
     * @return 该配置处理器的更改计数。
     */
    protected int modCount() {
        return modCount;
    }

    /**
     * 增加该配置处理器的更改技术。
     */
    protected void increaceModCount() {
        modCount++;
    }
}
