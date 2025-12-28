package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;
import com.dwarfeng.dutil.basic.prog.WithKey;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * 代理键值集合模型。
 *
 * <p>
 * 该集合模型是通过代理以及集合来实现的。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class DelegateKeySetModel<K, V extends WithKey<K>> extends DelegateSetModel<V> implements KeySetModel<K, V> {

    /**
     * 生成一个默认的代理键值集合模型。
     */
    public DelegateKeySetModel() {
        super();
    }

    /**
     * 生成一个具有指定的代理，指定的观察器集合的代理键值集合模型。
     *
     * @param delegate  指定的代理。
     * @param observers 指定的观察器集合。
     * @throws NullPointerException 入口参数为 null。
     */
    public DelegateKeySetModel(Set<V> delegate, Set<SetObserver<V>> observers) {
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
            if (Objects.equals(value == null ? null : value.getKey(), key)) {
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
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEKEYSETMODEL_0));

        for (Object obj : c) {
            if (!containsKey(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeKey(Object key) {
        for (V value : delegate) {
            if (Objects.equals(value == null ? null : value.getKey(), key)) {
                if (delegate.remove(value)) {
                    fireRemoved(value);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEKEYSETMODEL_0));
        return batchRemoveKey(c, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAllKey(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEKEYSETMODEL_0));
        return batchRemoveKey(c, false);
    }

    private boolean batchRemoveKey(Collection<?> c, boolean aFlag) {
        boolean result = false;

        for (Iterator<V> i = delegate.iterator(); i.hasNext(); ) {
            V element = i.next();

            if (c.contains(element == null ? null : element.getKey()) == aFlag) {
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
    public boolean add(V e) {
        K key = e == null ? null : e.getKey();
        if (containsKey(key)) {
            return false;
        }
        delegate.add(e);
        fireAdded(e);
        return true;
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
}
