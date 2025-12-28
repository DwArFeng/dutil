package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.obs.MapObserver;

import java.util.*;

/**
 * 代理映射模型。 通过代理一个 {@link Map} 实现映射模型。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class DelegateMapModel<K, V> extends AbstractMapModel<K, V> {

    /**
     * 该映射模型的代理。
     */
    protected final Map<K, V> delegate;

    /**
     * 生成一个默认的映射列表模型。
     */
    public DelegateMapModel() {
        this(new HashMap<>(), Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个指定的代理，指定的观察器集合的代理映射模型。
     *
     * @param delegate  指定的代理映射。
     * @param observers 指定的代理映射模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DelegateMapModel(Map<K, V> delegate, Set<MapObserver<K, V>> observers) {
        super(observers);
        Objects.requireNonNull(delegate, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_0));
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
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(Object key) {
        return delegate.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(K key, V value) {
        boolean aFlag = delegate.containsKey(key);
        V oldValue = delegate.put(key, value);
        if (aFlag) {
            fireChanged(key, oldValue, value);
        } else {
            firePut(key, value);
        }
        return oldValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(Object key) {
        // 此处对 delegate 的调用符合 Map 的规范，故不会出现类型转换异常。
        @SuppressWarnings("SuspiciousMethodCalls")
        boolean aFlag = delegate.containsKey(key);
        V value = delegate.remove(key);
        if (aFlag) {
            // 如果代理中存在 key，则 key 一定属于 K，该转换是安全的。
            @SuppressWarnings("unchecked")
            K k = (K) key;
            fireRemoved(k, value);
        }
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Objects.requireNonNull(m, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_1));
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        delegate.clear();
        fireCleared();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet() {
        return new KeySet(delegate.keySet());
    }

    private class KeySet implements Set<K> {

        private final Set<K> delegateKeySet;

        public KeySet(Set<K> delegateKeySet) {
            this.delegateKeySet = delegateKeySet;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return delegateKeySet.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return delegateKeySet.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            return delegateKeySet.contains(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<K> iterator() {
            return new KeySetIterator(delegateKeySet.iterator());
        }

        private class KeySetIterator implements Iterator<K> {

            private final Iterator<K> delegateIterator;
            private K key = null;

            public KeySetIterator(Iterator<K> delegateIterator) {
                this.delegateIterator = delegateIterator;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return delegateIterator.hasNext();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public K next() {
                key = delegateIterator.next();
                return key;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                V value = delegate.get(key);
                delegateIterator.remove();
                fireRemoved(key, value);
            }

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            return delegateKeySet.toArray();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            return delegateKeySet.toArray(a);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(K e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            // 此处对 delegate 的调用符合 Map 的规范，故不会出现类型转换异常。
            @SuppressWarnings("SuspiciousMethodCalls")
            V value = delegate.get(o);
            if (delegateKeySet.remove(o)) {
                // 如果能够移除，则 o 一定属于 K，该类型转换是安全的。
                @SuppressWarnings("unchecked")
                K key = (K) o;
                fireRemoved(key, value);
                return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_2));
            return delegateKeySet.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends K> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_2));
            return batchRemove(c, true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_2));
            return batchRemove(c, false);
        }

        private boolean batchRemove(Collection<?> c, boolean aFlag) {
            boolean result = false;

            for (Iterator<K> i = delegateKeySet.iterator(); i.hasNext(); ) {
                K key = i.next();
                V value = delegate.get(key);

                if (c.contains(key) == aFlag) {
                    i.remove();
                    fireRemoved(key, value);
                    result = true;
                }
            }

            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            delegateKeySet.clear();
            fireCleared();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return delegateKeySet.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        // 代理方法，忽略所有警告。
        @SuppressWarnings("EqualsDoesntCheckParameterClass")
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return delegateKeySet.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegateKeySet.toString();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<V> values() {
        return new Values(delegate.values());
    }

    private class Values implements Collection<V> {

        private final Collection<V> delegateValues;

        public Values(Collection<V> delegateValues) {
            this.delegateValues = delegateValues;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return delegateValues.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return delegateValues.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            return delegateValues.contains(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<V> iterator() {
            return new ValuesIterator(delegateValues.iterator());
        }

        private class ValuesIterator implements Iterator<V> {

            private final Iterator<V> delegateIterator;
            private V value;

            public ValuesIterator(Iterator<V> delegateIterator) {
                this.delegateIterator = delegateIterator;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return delegateIterator.hasNext();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public V next() {
                value = delegateIterator.next();
                return value;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                Set<K> set = findKey(value);
                delegateIterator.remove();
                for (K key : set) {
                    if (!delegate.containsKey(key)) {
                        fireRemoved(key, value);
                    }
                }
            }

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            return delegateValues.toArray();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            return delegateValues.toArray(a);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(V e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            if (!contains(o))
                return false;
            // 如果对象 o 在值集合中，则对象 o 一定属于 V，故该转换类型安全。
            @SuppressWarnings("unchecked")
            V value = (V) o;
            Set<K> set = findKey(value);
            if (delegateValues.remove(o)) {
                for (K key : set) {
                    if (!delegate.containsKey(key)) {
                        fireRemoved(key, value);
                    }
                }
                return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_2));
            return delegateValues.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends V> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_2));
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_2));
            return batchRemove(c, true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            return batchRemove(c, false);
        }

        private boolean batchRemove(Collection<?> c, boolean aFlag) {
            boolean result = false;

            for (Iterator<V> i = delegateValues.iterator(); i.hasNext(); ) {
                V value = i.next();

                if (c.contains(value) == aFlag) {
                    Set<K> set = findKey(value);
                    i.remove();
                    for (K key : set) {
                        if (!delegate.containsKey(key)) {
                            fireRemoved(key, value);
                        }
                    }
                    result = true;
                }
            }

            return result;
        }

        private Set<K> findKey(V value) {
            Set<K> set = new HashSet<>();
            for (Map.Entry<K, V> entry : delegate.entrySet()) {
                if (Objects.equals(value, entry.getValue())) {
                    set.add(entry.getKey());
                }
            }
            return set;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            delegateValues.clear();
            fireCleared();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegateValues.toString();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return new EntrySet(delegate.entrySet());
    }

    private class EntrySet implements Set<Map.Entry<K, V>> {

        private final Set<Map.Entry<K, V>> delegateEntrySet;

        public EntrySet(Set<java.util.Map.Entry<K, V>> delegateEntrySet) {
            this.delegateEntrySet = delegateEntrySet;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return delegateEntrySet.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return delegateEntrySet.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            return delegateEntrySet.contains(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<java.util.Map.Entry<K, V>> iterator() {
            return new EntryIterator(delegateEntrySet.iterator());
        }

        private class EntryIterator implements Iterator<Map.Entry<K, V>> {

            private final Iterator<Map.Entry<K, V>> delegateIterator;
            private Map.Entry<K, V> entry = null;

            public EntryIterator(Iterator<java.util.Map.Entry<K, V>> delegateIterator) {
                this.delegateIterator = delegateIterator;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return delegateIterator.hasNext();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public java.util.Map.Entry<K, V> next() {
                entry = delegateIterator.next();
                return new DelegateEntry(entry);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                K key = entry.getKey();
                V value = entry.getValue();
                delegateIterator.remove();
                fireRemoved(key, value);
            }

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            Object[] objs = delegateEntrySet.toArray();
            Object[] dejaVu = new Object[objs.length];
            for (int i = 0; i < objs.length; i++) {
                // 该转换是安全的。
                @SuppressWarnings("unchecked")
                Map.Entry<K, V> entry = (java.util.Map.Entry<K, V>) objs[i];
                dejaVu[i] = new DelegateEntry(entry);
            }
            return dejaVu;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            Object[] objs = delegateEntrySet.toArray();
            @SuppressWarnings("unchecked")
            T[] r = a.length >= objs.length ? a
                    : (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), objs.length);
            for (int i = 0; i < objs.length; i++) {
                @SuppressWarnings("unchecked")
                T t = (T) objs[i];
                r[i] = t;
            }
            return r;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(java.util.Map.Entry<K, V> e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry<?, ?>))
                return false;
            Map.Entry<?, ?> entry = (java.util.Map.Entry<?, ?>) o;
            Object k = entry.getKey();
            // 此处对 delegate 的调用符合 Map 的规范，故不会出现类型转换异常。
            @SuppressWarnings("SuspiciousMethodCalls")
            V value = delegate.get(k);
            if (delegateEntrySet.remove(entry)) {
                // 如果能够移除，则 k 一定属于 K，该类型转换是安全的。
                @SuppressWarnings("unchecked")
                K key = (K) k;
                fireRemoved(key, value);
                return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_2));
            return delegateEntrySet.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends java.util.Map.Entry<K, V>> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_2));
            return batchRemove(c, true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEMAPMODEL_2));
            return batchRemove(c, false);
        }

        private boolean batchRemove(Collection<?> c, boolean aFlag) {
            boolean result = false;

            for (Iterator<Map.Entry<K, V>> i = delegateEntrySet.iterator(); i.hasNext(); ) {
                Map.Entry<K, V> entry = i.next();
                K key = entry.getKey();
                V value = entry.getValue();
                if (c.contains(entry) == aFlag) {
                    i.remove();
                    fireRemoved(key, value);
                    result = true;
                }
            }

            return result;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            delegateEntrySet.clear();
            fireCleared();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return delegateEntrySet.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        // 代理方法，忽略所有警告。
        @SuppressWarnings("EqualsDoesntCheckParameterClass")
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return delegateEntrySet.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegateEntrySet.toString();
        }

        private class DelegateEntry implements Map.Entry<K, V> {

            private final Map.Entry<K, V> delegateEntry;

            public DelegateEntry(java.util.Map.Entry<K, V> delegateEntry) {
                this.delegateEntry = delegateEntry;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public K getKey() {
                return delegateEntry.getKey();
            }

            @Override
            public V getValue() {
                return delegateEntry.getValue();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public V setValue(V value) {
                K key = delegateEntry.getKey();
                V oldValue = delegateEntry.getValue();
                delegateEntry.setValue(value);
                if (delegate.containsKey(key)) {
                    fireChanged(key, oldValue, value);
                }
                return oldValue;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int hashCode() {
                return delegateEntry.hashCode();
            }

            /**
             * {@inheritDoc}
             */
            // 代理方法，忽略所有警告。
            @SuppressWarnings("EqualsDoesntCheckParameterClass")
            @Override
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                return delegateEntry.equals(obj);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String toString() {
                return delegateEntry.toString();
            }

        }

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
    // 代理方法，忽略所有警告。
    @SuppressWarnings("EqualsDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return delegate.equals(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return delegate.toString();
    }
}
