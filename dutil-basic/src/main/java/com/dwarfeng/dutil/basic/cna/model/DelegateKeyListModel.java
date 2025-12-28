package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.obs.ListObserver;
import com.dwarfeng.dutil.basic.prog.WithKey;

import java.util.*;

/**
 * 代理键值列表模型。
 *
 * <p>
 * 通过代理一个 {@link List} 实现键值列表模型。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class DelegateKeyListModel<K, V extends WithKey<K>> extends DelegateListModel<V> implements KeyListModel<K, V> {

    /**
     * 生成一个默认的代理键值列表模型。
     */
    public DelegateKeyListModel() {
        super();
    }

    /**
     * 生成一个具有指定的代理，指定的观察器列表的代理键值列表模型。
     *
     * @param delegate  指定的代理。
     * @param observers 指定的观察器列表。
     * @throws NullPointerException 入口参数为 null。
     */
    public DelegateKeyListModel(List<V> delegate, Set<ListObserver<V>> observers) {
        super(delegate, observers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(K key) {
        for (V value : this) {
            if (Objects.equals(value == null ? null : value.getKey(), key))
                return value;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        for (V value : delegate) {
            if (Objects.equals(key, value == null ? null : value.getKey())) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEKEYLISTMODEL_0));
        for (Object o : c) {
            if (!containsKey(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOfKey(Object o) {
        for (ListIterator<V> i = delegate.listIterator(0); i.hasNext(); ) {
            int index = i.nextIndex();
            V value = i.next();
            if (Objects.equals(o, value == null ? null : value.getKey()))
                return index;
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOfKey(Object o) {
        for (ListIterator<V> i = delegate.listIterator(delegate.size()); i.hasPrevious(); ) {
            int index = i.previousIndex();
            V value = i.previous();
            if (Objects.equals(o, value == null ? null : value.getKey()))
                return index;
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeKey(Object key) {
        for (ListIterator<V> i = delegate.listIterator(0); i.hasNext(); ) {
            int index = i.nextIndex();
            V value = i.next();
            if (Objects.equals(key, value == null ? null : value.getKey())) {
                i.remove();
                fireRemoved(index, value);
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEKEYLISTMODEL_0));
        return batchRemoveKey(c, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEKEYLISTMODEL_0));
        return batchRemoveKey(c, false);

    }

    private boolean batchRemoveKey(Collection<?> c, boolean aFlag) {
        boolean result = false;

        for (ListIterator<V> i = delegate.listIterator(); i.hasNext(); ) {
            int index = i.nextIndex();
            V value = i.next();

            if (c.contains(value == null ? null : value.getKey()) == aFlag) {
                i.remove();
                fireRemoved(index, value);
                result = true;
            }
        }

        return result;
    }
}
