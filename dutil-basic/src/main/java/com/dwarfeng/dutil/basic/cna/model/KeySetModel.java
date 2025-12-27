package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.prog.WithKey;

import java.util.Collection;
import java.util.List;

/**
 * 键值集合模型。
 *
 * <p>
 * 模型可以容纳一组拥有主键的元素，并提供一系列的操作方法。模型中的元素是没有顺序的， 相同的主键只能对应一个元素。
 * 该模型同样可以对元素根据主键进行操作。该模型是一个 {@link List} 现。
 *
 * @param <K> 泛型 K，代表元素键的类型。
 * @param <V> 泛型 V，代表元素的类型。
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface KeySetModel<K, V extends WithKey<K>> extends SetModel<V> {

    /**
     * 获取指定的键对应的值。
     *
     * <p>
     * 如果键值集合模型中没有指定的键，则返回 <code>null</code>。
     *
     * @param key 指定的键。
     * @return 指定的键对应的值。
     */
    V get(K key);

    /**
     * 如果集合包含指定的键，则返回 <code>true</code>。
     *
     * @param key 指定的键。
     * @return 如果包含指定的键，则返回 <code>true</code>。
     */
    boolean containsKey(Object key);

    /**
     * 如果集合包含指定 {@link Collection} 的所有键，则返回 <code>true</code>。
     *
     * @param c 要在集合中检查其包含性的 {@link Collection}
     * @return 如果集合包含指定 {@link Collection} 的所有键，则返回 <code>true</code>。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    boolean containsAllKey(Collection<?> c);

    /**
     * 从此集合中移除指定键（如果存在）（可选操作）。
     *
     * @param key 要从该集合中移除的键。
     * @return 如果集合包含指定的键，则返回 <code>true</code>。
     */
    boolean removeKey(Object key);

    /**
     * 从集合中移除指定 {@link Collection} 中包含的其所有元素（可选操作）。
     *
     * @param c 包含从此集合中移除的元素的 {@link Collection}。
     * @return 如果此集合由于调用而发生更改，则返回 <code>true</code>。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    boolean removeAllKey(Collection<?> c);

    /**
     * 仅在集合中保留指定 {@link Collection} 中所包含的元素（可选操作）。
     *
     * @param c 包含将保留在此集合中的元素的 {@link Collection}。
     * @return 如果此集合由于调用而发生更改，则返回 <code>true</code>。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    boolean retainAllKey(Collection<?> c);
}
