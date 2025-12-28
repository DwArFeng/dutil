package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;
import com.dwarfeng.dutil.basic.prog.WithKey;

import java.util.*;

/**
 * 映射键值集合模型。
 *
 * <p>
 * 该模型中使用一个映射来处理集合的元素，这使得模型在处理键的时候拥有较高的性能。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class MapKeySetModel<K, V extends WithKey<K>> extends AbstractSetModel<V> implements KeySetModel<K, V> {

    /**
     * 负责处理列表的映射。
     */
    protected final Map<K, V> map;

    /**
     * 生成一个默认的映射键值集合模型。
     */
    public MapKeySetModel() {
        this(new HashMap<>(), Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个拥有指定的映射，指定的侦听器集合的映射键值集合模型。
     *
     * @param map       指定的映射。
     * @param observers 指定的侦听器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public MapKeySetModel(Map<K, V> map, Set<SetObserver<V>> observers) {
        super(observers);
        Objects.requireNonNull(map, DwarfUtil.getExceptionString(ExceptionStringKey.MAPKEYSETMODEL_0));
        this.map = map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return map.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        // 此处对 delegate 的调用符合 Map 的规范，故不会出现类型转换异常。
        @SuppressWarnings("SuspiciousMethodCalls")
        boolean result = map.containsValue(o);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<V> iterator() {
        return new KeySetIterator(map.values().iterator());
    }

    private class KeySetIterator implements Iterator<V> {

        private final Iterator<V> delegateIterator;
        private V value;

        public KeySetIterator(Iterator<V> delegateIterator) {
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
            delegateIterator.remove();
            fireRemoved(value);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return map.values().toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return map.values().toArray(a);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(V e) {
        K key = e == null ? null : e.getKey();
        if (map.containsKey(key)) {
            return false;
        }
        map.put(key, e);
        fireAdded(e);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        boolean aFlag = map.values().remove(o);
        if (aFlag) {
            // 如果能够在映射的值集合中移除对象 o，则 o 一定属于类型 V，故该转换是安全的。
            @SuppressWarnings("unchecked")
            V v = (V) o;
            fireRemoved(v);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.MAPKEYSETMODEL_1));
        return map.values().containsAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends V> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.MAPKEYSETMODEL_1));
        boolean aFlag = false;
        for (V v : c) {
            if (add(v))
                aFlag = true;
        }
        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.MAPKEYSETMODEL_1));
        return batchRemove(c, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.MAPKEYSETMODEL_1));
        return batchRemove(c, false);
    }

    private boolean batchRemove(Collection<?> c, boolean aFlag) {
        boolean result = false;

        for (Iterator<V> i = map.values().iterator(); i.hasNext(); ) {
            V element = i.next();

            if (c.contains(element) == aFlag) {
                i.remove();
                fireRemoved(element);
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
        map.clear();
        fireCleared();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        return map.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        // 此处对 delegate 的调用符合 Map 的规范，故不会出现类型转换异常。
        @SuppressWarnings("SuspiciousMethodCalls")
        boolean result = map.containsKey(key);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.MAPKEYSETMODEL_1));
        // 此处对 delegate 的调用符合 Map 的规范，故不会出现类型转换异常。
        @SuppressWarnings("SuspiciousMethodCalls")
        boolean result = map.keySet().containsAll(c);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeKey(Object key) {
        // 此处对 delegate 的调用符合 Map 的规范，故不会出现类型转换异常。
        @SuppressWarnings("SuspiciousMethodCalls")
        boolean aFlag = map.containsKey(key);
        if (aFlag) {
            // 此处对 delegate 的调用符合 Map 的规范，故不会出现类型转换异常。
            @SuppressWarnings("SuspiciousMethodCalls")
            V v = map.remove(key);
            fireRemoved(v);
        }
        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.MAPKEYSETMODEL_1));
        return batchRemoveKey(c, true);
    }

    @Override
    public boolean retainAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.MAPKEYSETMODEL_1));
        return batchRemoveKey(c, false);
    }

    private boolean batchRemoveKey(Collection<?> c, boolean aFlag) {
        boolean result = false;

        for (Iterator<V> i = map.values().iterator(); i.hasNext(); ) {
            V element = i.next();

            if (c.contains(element == null ? null : element.getKey()) == aFlag) {
                i.remove();
                fireRemoved(element);
                result = true;
            }
        }

        return result;
    }

    @Override
    public int hashCode() {
        return new HashSet<>(map.values()).hashCode();
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
        return new HashSet<>(map.values()).equals(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return map.values().toString();
    }
}
