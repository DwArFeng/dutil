package com.dwarfeng.dutil.basic.sdk.model.event;

import com.dwarfeng.dutil.basic.stack.model.event.SetObserver;

/**
 * 集合模型观察器适配器。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public abstract class SetAdapter<E> implements SetObserver<E> {

    @Override
    public void fireAdded(E element) {
    }

    @Override
    public void fireRemoved(E element) {
    }

    @Override
    public void fireCleared() {
    }
}
