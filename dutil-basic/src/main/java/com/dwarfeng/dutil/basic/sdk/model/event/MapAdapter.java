package com.dwarfeng.dutil.basic.sdk.model.event;

import com.dwarfeng.dutil.basic.stack.model.event.MapObserver;

/**
 * 映射模型观察器适配器。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public abstract class MapAdapter<K, V> implements MapObserver<K, V> {

    @Override
    public void firePut(K key, V value) {
    }

    @Override
    public void fireChanged(K key, V oldValue, V newValue) {
    }

    @Override
    public void fireRemoved(K key, V value) {
    }

    @Override
    public void fireCleared() {
    }
}
