package com.dwarfeng.dutil.struct.tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * 无序树。
 *
 * <p>
 * 无序树是指在一个根下的子节点是没有顺序的，以 {@link Set} 方式存储它们。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface UnorderedTree<E> extends Tree<E> {

    /**
     * {@inheritDoc}
     */
    @Override
    boolean add(E root, E element);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean add(ParentChildPair<E> pair);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean addAll(Collection<? extends ParentChildPair<E>> c);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean addAll(E parent, Collection<? extends E> c);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean addRoot(E root);

    /**
     * {@inheritDoc}
     */
    @Override
    void clear();

    /**
     * {@inheritDoc}
     */
    @Override
    boolean contains(Object o);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean containsAll(Collection<?> c);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean equals(Object obj);

    /**
     * {@inheritDoc}
     */
    @Override
    Set<E> getChilds(Object o);

    /**
     * {@inheritDoc}
     */
    @Override
    int getDepth(Object o);

    /**
     * {@inheritDoc}
     */
    @Override
    E getParent(Object o);

    /**
     * {@inheritDoc}
     */
    @Override
    Path<E> getPath(Object o);

    /**
     * {@inheritDoc}
     */
    @Override
    E getRoot();

    /**
     * {@inheritDoc}
     */
    @Override
    int hashCode();

    /**
     * {@inheritDoc}
     */
    @Override
    boolean isAncestor(Object anscetor, Object o);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean isEmpty();

    /**
     * {@inheritDoc}
     */
    @Override
    boolean isLeaf(Object o);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean isRoot(Object o);

    /**
     * {@inheritDoc}
     */
    @Override
    Iterator<E> iterator();

    /**
     * {@inheritDoc}
     */
    @Override
    boolean remove(Object o);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean removeAll(Collection<?> c);

    /**
     * {@inheritDoc}
     */
    @Override
    int size();

    /**
     * {@inheritDoc}
     */
    @Override
    Tree<E> subTree(Object root);

    /**
     * {@inheritDoc}
     */
    @Override
    Object[] toArray();

    /**
     * {@inheritDoc}
     */
    @Override
    <T> T[] toArray(T[] a);

    /**
     * {@inheritDoc}
     */
    @Override
    String toString();
}
