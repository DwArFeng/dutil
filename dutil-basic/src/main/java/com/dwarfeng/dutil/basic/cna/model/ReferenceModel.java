package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.cna.model.obs.ReferenceObserver;
import com.dwarfeng.dutil.basic.prog.ObserverSet;

import java.util.Objects;

/**
 * 引用模型。
 *
 * <p>
 * 该模型是一个单一元素的模型。当元素被更改、清空的时候，会将必要的信息提供给注册在模型上的侦听器集合。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public interface ReferenceModel<E> extends ObserverSet<ReferenceObserver<E>> {

    /**
     * 获取该模型中的元素。
     *
     * @return 该模型中的元素。
     */
    E get();

    /**
     * 设置该模型的元素为指定的元素。
     *
     * @param element 指定的元素。
     * @return 之前的旧元素。
     */
    E set(E element);

    /**
     * 清空模型中的元素。
     *
     * <p>
     * 清空元素相当于调用 <code>set(null)</code>，但与之不同的是，
     * 该方法会广播<code>fireCleared()</code>方法， 而<code>set(null)</code>不会。
     */
    void clear();

    /**
     * 返回该模型是否是空的。
     *
     * @return 该模型是否是空的。
     */
    default boolean isEmpty() {
        return Objects.isNull(get());
    }
}
