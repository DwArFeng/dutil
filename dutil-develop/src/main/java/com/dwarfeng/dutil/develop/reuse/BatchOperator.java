package com.dwarfeng.dutil.develop.reuse;

import com.dwarfeng.dutil.basic.prog.Filter;

import java.util.Collection;
import java.util.ConcurrentModificationException;

/**
 * 批量更新器。
 *
 * <p>
 * 批量更新器可以在用户更新一个复用池中全部的元素是提供便利操作。
 *
 * <p>
 * 批量更新器在更新的全过程中，复用池本题应该不进行任何操作。
 * 批量更新器中拥有快速失败机制，在批量更新时，如果用户对其复用池本题进行操作的话，批量更新器会尽可能的快速失败。 <br>
 * 快速失败机制不能保证所有检测出所有的动作，因此该机制只适合进行调试。 用户应该在编程是避免这一现象的发生。
 *
 * @author DwArFeng
 * @since 0.2.1-beta
 */
public interface BatchOperator<E> {

    /**
     * 更新批量操作器中的一个元素。
     *
     * <p>
     * 指定元素更新之后，下次继续调用，则不再更新。
     *
     * @param element      指定的元素。
     * @param updateObject 指定元素对应的更新对象。
     * @return 批量更新器本身。
     * @throws ConcurrentModificationException 快速失败异常。
     */
    BatchOperator<E> update(E element, Object updateObject) throws ConcurrentModificationException;

    /**
     * 更新批量操作器中的多个元素。
     *
     * <p>
     * 指定的元素更新后，下次继续调用时不再更新。
     *
     * @param collection   指定的元素组成的集合。
     * @param updateObject 指定元素对应的更新对象。
     * @return 批量更新器本身。
     * @throws ConcurrentModificationException 快速失败异常。
     * @throws NullPointerException            入口参数为 <code>null</code>。
     */
    BatchOperator<E> updateAll(Collection<E> collection, Object updateObject)
            throws ConcurrentModificationException, NullPointerException;

    /**
     * 更新批量操作器中满足指定要求的所有元素。
     *
     * <p>
     * 指定的元素更新后，下次继续调用时不再更新。
     *
     * @param filter       指定的过滤器。
     * @param updateObject 指定的元素对应的更新对象。
     * @return 批量更新器本身。
     * @throws ConcurrentModificationException 快速失败异常。
     * @throws NullPointerException            入口参数为 <code>null</code>。
     */
    BatchOperator<E> updateAll(Filter<E> filter, Object updateObject)
            throws ConcurrentModificationException, NullPointerException;

    /**
     * 更新批量操作器中剩余未更新的所有元素。
     *
     * @param updateObject 元素的更新对象。
     * @return 批量更新器本身。
     * @throws ConcurrentModificationException 快速失败异常。
     */
    BatchOperator<E> updateRemain(Object updateObject) throws ConcurrentModificationException;

    /**
     * 获取复用池中不满足复用条件的所有元素。
     *
     * @return 复用池中不满足复用条件的所有元素组成的集合。
     * @throws ConcurrentModificationException 快速失败异常。
     */
    Collection<E> getUnsatisfyElements() throws ConcurrentModificationException;

    /**
     * 移除复用池中不满足复用条件的所有元素。
     *
     * @return 被移除的元素组成的集合。
     * @throws ConcurrentModificationException 快速失败异常。
     */
    Collection<E> removeUnsatisfyElements() throws ConcurrentModificationException;
}
