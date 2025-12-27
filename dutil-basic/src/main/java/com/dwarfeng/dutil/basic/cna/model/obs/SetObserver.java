package com.dwarfeng.dutil.basic.cna.model.obs;

import com.dwarfeng.dutil.basic.cna.model.SetModel;
import com.dwarfeng.dutil.basic.prog.Observer;

/**
 * 集合模型观察器。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public interface SetObserver<E> extends Observer {

    /**
     * 通知模型中指定的元素被添加。
     *
     * @param element 指定的元素。
     */
    void fireAdded(E element);

    /**
     * 通知模型中指定的元素被移除。
     *
     * @param element 指定的元素。
     */
    void fireRemoved(E element);

    /**
     * 通知模型中所有元素被清除。
     *
     * <p>
     * 该方法是为了优化效率而定义的，因此，在模型执行 {@link SetModel#clear()} 时候，
     * 会调用该方法进行通知，而不是 一条条地调用 {@link #fireRemoved(Object)}。
     */
    void fireCleared();
}
