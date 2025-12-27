package com.dwarfeng.dutil.develop.reuse;

import java.util.Collection;

/**
 * 复用池。
 *
 * @author DwArFeng
 * @since 0.2.1-beta
 */
public interface ReusePool<E> extends Iterable<E> {

    /**
     * 获取该复用池中的元素数量。
     *
     * @return 该复用池中的复用元素数量。
     */
    int size();

    /**
     * 获取该复用池中是否不包含任何元素。
     *
     * @return 该复用池中是否不包含任何元素。
     */
    boolean isEmpty();

    /**
     * 查询指定的对象是否存在于复用池中。
     *
     * @param object 指定的对象。
     * @return 指定的对象是否存在于复用池中。
     */
    boolean contains(Object object);

    /**
     * 查询指定的所有对象是否都存在于复用池中。
     *
     * @param collection 指定的所有对象组成的集合。
     * @return 指定的所有对象是否都存在于复用池中。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    boolean containsAll(Collection<?> collection) throws NullPointerException;

    /**
     * 获取指定对象对应的条件。
     *
     * @param object 指定的对象。
     * @return 指定的对象对应的条件。
     */
    Condition getCondition(Object object);

    /**
     * 向复用池中添加指定的元素。
     *
     * <p>
     * 若指定的元素为空，则添加失败；<br>
     * 若指定的元素对应的复用条件为空，则添加失败；<br>
     * 若复用池中已经存在指定的元素，则添加失败。
     *
     * @param element   指定的元素。
     * @param condition 指定的元素对应的复用池。
     * @return 是否添加成功。
     */
    boolean put(E element, Condition condition);

    /**
     * 从复用池中删除指定的对象。
     *
     * <p>
     * 如果复用池中不存在指定的对象，则删除失败。
     *
     * @param object 指定的对象。
     * @return 是否删除成功。
     */
    boolean remove(Object object);

    /**
     * 从复用池中删除指定的多个对象。
     *
     * @param collection 指定的对象组成的集合。
     * @return 该操作是否对该复用池造成了改变。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    boolean removeAll(Collection<?> collection) throws NullPointerException;

    /**
     * 保留复用池中的指定元素。
     *
     * <p>
     * 该方法会删除除了指定元素外的所有元素。
     *
     * @param collection 指定的元素组成的集合。
     * @return 该操作是否对复用池造成了改变。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    boolean retainAll(Collection<?> collection) throws NullPointerException;

    /**
     * 清除复用池中的所有元素。
     */
    void clear();

    /**
     * 获取该复用池的批量操作器。
     *
     * @return 该复用池的批量操作器。
     */
    BatchOperator<E> batchOperator();
}
