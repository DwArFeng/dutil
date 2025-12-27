package com.dwarfeng.dutil.basic.cna.model.obs;

import com.dwarfeng.dutil.basic.cna.model.MapModel;
import com.dwarfeng.dutil.basic.prog.Observer;

/**
 * 映射模型观察器。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public interface MapObserver<K, V> extends Observer {

    /**
     * 通知指定的键-值对被添加。
     *
     * @param key   指定的键。
     * @param value 指定的值。
     */
    void firePut(K key, V value);

    /**
     * 通知指定的键映射的元素被改变。
     *
     * <p>
     * 注意，当调用 {@link MapModel#put(Object, Object)} 方法时，如果模型中已经存在指定的键，
     * 则模型会调用该方法进行通知，而不是调用 {@link #firePut(Object, Object)} 法进行通知。
     *
     * @param key      指定的键。
     * @param oldValue 该键旧的映射值。
     * @param newValue 该键新的映射值。
     */
    void fireChanged(K key, V oldValue, V newValue);

    /**
     * 通知模型中移除了指定的键。
     *
     * @param key   指定的键。
     * @param value 该键的对应值。
     */
    void fireRemoved(K key, V value);

    /**
     * 通知模型中的内容被清除。
     *
     * <p>
     * 该方法是为了提高效率而定义的，当模型执行 {@link MapModel#clear()} 方法的时候，
     * 会触发该通知而不是一条条地触发 {@link #fireRemoved(Object, Object)}。
     */
    void fireCleared();
}
