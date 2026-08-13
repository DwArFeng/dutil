package com.dwarfeng.dutil.basic.stack.model.event;

/**
 * 引用模型侦听器。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public interface ReferenceObserver<E> extends Observer {

    /**
     * 通知模型中的元素发生了改变。
     *
     * @param oldValue 旧的值。
     * @param newValue 新的值。
     */
    void fireSet(E oldValue, E newValue);

    /**
     * 通知模型中的元素被清除。
     */
    void fireCleared();
}
