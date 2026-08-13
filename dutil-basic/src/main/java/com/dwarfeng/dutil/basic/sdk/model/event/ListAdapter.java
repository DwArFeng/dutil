package com.dwarfeng.dutil.basic.sdk.model.event;

import com.dwarfeng.dutil.basic.stack.model.event.ListObserver;

/**
 * 列表模型观察器适配器。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public abstract class ListAdapter<E> implements ListObserver<E> {

    @Override
    public void fireAdded(int index, E element) {
    }

    @Override
    public void fireRemoved(int index, E element) {
    }

    @Override
    public void fireChanged(int index, E oldElement, E newElement) {
    }

    @Override
    public void fireCleared() {
    }
}
