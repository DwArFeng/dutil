package com.dwarfeng.dutil.basic.sdk.model.event;

import com.dwarfeng.dutil.basic.stack.model.event.ReferenceObserver;

/**
 * 引用模型观察器适配器。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public abstract class ReferenceAdapter<E> implements ReferenceObserver<E> {

    @Override
    public void fireSet(E oldValue, E newValue) {
    }

    @Override
    public void fireCleared() {
    }
}
