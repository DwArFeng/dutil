package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;

import java.util.*;

/**
 * 代理集合模型。
 *
 * <p>
 * 通过代理一个 {@link Set} 实现列表模型。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class DelegateSetModel<E> extends AbstractSetModel<E> {

    /**
     * 该集合模型的代理。
     */
    protected final Set<E> delegate;

    /**
     * 生成一个默认的代理集合模型。
     */
    public DelegateSetModel() {
        this(new HashSet<>(), Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定的代理，指定的观察器集合的代理集合模型。
     *
     * @param delegate  指定的代理。
     * @param observers 指定的观察器集合模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DelegateSetModel(Set<E> delegate, Set<SetObserver<E>> observers) {
        super(observers);
        Objects.requireNonNull(delegate, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATESETMODEL_0));
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return delegate.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return new InnerIterator(delegate.iterator());
    }

    private class InnerIterator implements Iterator<E> {

        private final Iterator<E> itr;
        private E cursor = null;

        public InnerIterator(Iterator<E> itr) {
            this.itr = itr;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            cursor = itr.next();
            return cursor;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            itr.remove();
            fireRemoved(cursor);
            cursor = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E e) {
        if (delegate.add(e)) {
            fireAdded(e);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        if (delegate.remove(o)) {
            // 只有 o 属于类型 E，才有可能移除成功，因此此处类型转换是安全的。
            @SuppressWarnings("unchecked")
            E e = (E) o;
            fireRemoved(e);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATESETMODEL_1));
        return delegate.containsAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATESETMODEL_1));

        boolean aFlag = false;
        for (E e : c) {
            if (add(e)) {
                aFlag = true;
            }
        }
        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATESETMODEL_1));
        return batchRemove(c, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATESETMODEL_1));
        return batchRemove(c, false);
    }

    private boolean batchRemove(Collection<?> c, boolean aFlag) {
        boolean result = false;

        for (Iterator<E> i = delegate.iterator(); i.hasNext(); ) {
            E element = i.next();

            if (c.contains(element) == aFlag) {
                i.remove();
                fireRemoved(element);
                result = true;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        delegate.clear();
        fireCleared();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        return delegate.equals(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return delegate.toString();
    }
}
