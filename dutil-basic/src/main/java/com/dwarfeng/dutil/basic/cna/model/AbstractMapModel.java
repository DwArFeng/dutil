package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.obs.MapObserver;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 抽象映射模型。
 *
 * <p>
 * 映射模型的抽象实现。
 *
 * <p>
 * 该类实现了模型中侦听器注册与移除的方法，以及通知侦听器的方法。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public abstract class AbstractMapModel<K, V> implements MapModel<K, V> {

    /**
     * 抽象集合模型的侦听器集合。
     */
    protected final Set<MapObserver<K, V>> observers;

    /**
     * 生成一个默认的抽象映射模型。
     */
    public AbstractMapModel() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定的侦听器集合的的抽象映射模型。
     *
     * @param observers 指定的侦听器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public AbstractMapModel(Set<MapObserver<K, V>> observers) {
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTMAPMODEL_0));
        this.observers = observers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<MapObserver<K, V>> getObservers() {
        return Collections.unmodifiableSet(observers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addObserver(MapObserver<K, V> observer) {
        return observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(MapObserver<K, V> observer) {
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
     * 通知观察器该模型中指定的键-值对被添加。
     *
     * @param key   指定的键。
     * @param value 指定的值。
     */
    protected void firePut(K key, V value) {
        for (MapObserver<K, V> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.firePut(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器该模型中指定的键被移除。
     *
     * @param key   指定的键。
     * @param value 键对应的值。
     */
    protected void fireRemoved(K key, V value) {
        for (MapObserver<K, V> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireRemoved(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器该模型中指定的键的对应值发生改变。
     *
     * @param key      指定的键。
     * @param oldValue 键对应的旧值。
     * @param newValue 键对应的新值。
     */
    protected void fireChanged(K key, V oldValue, V newValue) {
        for (MapObserver<K, V> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireChanged(key, oldValue, newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知该模型中的键-值对被清除。
     */
    protected void fireCleared() {
        for (MapObserver<K, V> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireCleared();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
