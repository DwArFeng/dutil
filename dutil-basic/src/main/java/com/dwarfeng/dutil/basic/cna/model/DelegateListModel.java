package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.obs.ListObserver;

import java.util.*;

/**
 * 代理列表模型。
 *
 * <p>
 * 通过代理一个 {@link List} 实现列表模型。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public class DelegateListModel<E> extends AbstractListModel<E> {

    /**
     * 该列表模型的代理。
     */
    protected final List<E> delegate;

    /**
     * 生成一个默认的代理列表模型。
     */
    public DelegateListModel() {
        this(new ArrayList<>(), Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个指定的代理，指定的观察器集合的代理列表模型。
     *
     * @param delegate  指定的代理列表。
     * @param observers 指定的代理列表模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DelegateListModel(List<E> delegate, Set<ListObserver<E>> observers) {
        super(observers);
        Objects.requireNonNull(delegate, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_0));
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
        private int lastRet = -1;
        private int cursor = 0;

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
            int i = cursor;
            cursor++;
            lastRet = i;
            return itr.next();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            cursor = lastRet;
            E element = delegate.get(lastRet);
            itr.remove();
            fireRemoved(lastRet, element);
            lastRet = -1;
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
        int size = delegate.size();
        delegate.add(e);
        fireAdded(size, e);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        // 此处对 delegate 的调用符合 List 的规范，故不会出现类型转换异常。
        @SuppressWarnings("SuspiciousMethodCalls")
        int index = delegate.indexOf(o);
        if (delegate.remove(o)) {
            // 只要代理能移除该对象，该对象一定属于类型 E，该转换是安全的。
            @SuppressWarnings("unchecked")
            E e = (E) o;
            fireRemoved(index, e);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    // 代理方法，忽略所有警告。
    @SuppressWarnings("SlowListContainsAll")
    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_1));
        return delegate.containsAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_1));
        boolean aFlag = false;
        for (E e : c) {
            add(e);
            aFlag = true;
        }
        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_1));
        int size = delegate.size();
        int i = 0;
        for (E e : c) {
            add(index + i++, e);
        }
        return size != delegate.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_1));
        return batchRemove(c, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_1));
        return batchRemove(c, false);
    }

    private boolean batchRemove(Collection<?> c, boolean aFlag) {
        boolean result = false;

        for (ListIterator<E> i = delegate.listIterator(); i.hasNext(); ) {
            int index = i.nextIndex();
            E element = i.next();

            if (c.contains(element) == aFlag) {
                i.remove();
                fireRemoved(index, element);
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
    public E get(int index) {
        return delegate.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {
        E oldElement = delegate.set(index, element);
        fireChanged(index, oldElement, element);
        return oldElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {
        delegate.add(index, element);
        fireAdded(index, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(int index) {
        E element = delegate.remove(index);
        fireRemoved(index, element);
        return element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object o) {
        return delegate.indexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object o) {
        return delegate.lastIndexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator() {
        return new InnerListIterator(delegate.listIterator(), 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return new InnerListIterator(delegate.listIterator(index), index);
    }

    private class InnerListIterator implements ListIterator<E> {

        private final ListIterator<E> listIterator;
        private int lastRet = -1;
        private int cursor;

        public InnerListIterator(ListIterator<E> listIterator, int cursor) {
            this.listIterator = listIterator;
            this.cursor = cursor;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return listIterator.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            int i = cursor;
            cursor++;
            lastRet = i;
            return listIterator.next();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasPrevious() {
            return listIterator.hasPrevious();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E previous() {
            int i = cursor - 1;
            cursor = i;
            lastRet = i;
            return listIterator.previous();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int nextIndex() {
            return listIterator.nextIndex();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int previousIndex() {
            return listIterator.previousIndex();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            cursor = lastRet;
            E element = delegate.get(lastRet);
            listIterator.remove();
            fireRemoved(lastRet, element);
            lastRet = -1;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();

            E oldValue = delegate.get(lastRet);
            listIterator.set(e);
            fireChanged(lastRet, oldValue, e);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(E e) {
            int i = cursor;
            listIterator.add(e);
            fireAdded(i, e);
            cursor = i + 1;
            lastRet = -1;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new SubList(fromIndex, delegate.subList(fromIndex, toIndex));
    }

    private class SubList implements List<E> {

        private final int offset;
        private final List<E> subDelegate;

        public SubList(int offset, List<E> subDelegate) {
            this.offset = offset;
            this.subDelegate = subDelegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return subDelegate.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return subDelegate.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            return subDelegate.contains(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<E> iterator() {
            return new SubIterator(subDelegate.iterator());
        }

        private class SubIterator implements Iterator<E> {

            private final Iterator<E> itr;
            private int lastRet = -1;
            private int cursor = 0;

            public SubIterator(Iterator<E> itr) {
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
                int i = cursor;
                cursor++;
                lastRet = i;
                return itr.next();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                if (lastRet < 0)
                    throw new IllegalStateException();

                cursor = lastRet;
                E element = subDelegate.get(lastRet);
                itr.remove();
                fireRemoved(lastRet + offset, element);
                lastRet = -1;
            }

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            return subDelegate.toArray();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            return subDelegate.toArray(a);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            int size = subDelegate.size();
            subDelegate.add(e);
            fireAdded(size + offset, e);
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            // 此处对 delegate 的调用符合 List 的规范，故不会出现类型转换异常。
            @SuppressWarnings("SuspiciousMethodCalls")
            int index = subDelegate.indexOf(o);
            if (subDelegate.remove(o)) {
                // 只要代理能移除该对象，该对象一定属于类型 E，该转换是安全的。
                @SuppressWarnings("unchecked")
                E e = (E) o;
                fireRemoved(index + offset, e);
                return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_1));
            return new HashSet<>(subDelegate).containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_1));
            boolean aFlag = false;
            for (E e : c) {
                add(e);
                aFlag = true;
            }
            return aFlag;
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("DuplicatedCode")
        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_1));
            int size = subDelegate.size();
            int i = 0;
            for (E e : c) {
                add(index + i++, e);
            }
            return size != subDelegate.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_1));
            return batchRemove(c, true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATELISTMODEL_1));
            return batchRemove(c, false);
        }

        private boolean batchRemove(Collection<?> c, boolean aFlag) {
            boolean result = false;

            for (ListIterator<E> i = subDelegate.listIterator(); i.hasNext(); ) {
                int index = i.nextIndex();
                E element = i.next();

                if (c.contains(element) == aFlag) {
                    i.remove();
                    fireRemoved(index + offset, element);
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
            int size = subDelegate.size();
            for (int i = 0; i < size; i++) {
                remove(0);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E get(int index) {
            return subDelegate.get(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E set(int index, E element) {
            E oldElement = subDelegate.set(index, element);
            fireChanged(index + offset, oldElement, element);
            return oldElement;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, E element) {
            subDelegate.add(index, element);
            fireAdded(index + offset, element);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E remove(int index) {
            E element = subDelegate.remove(index);
            fireRemoved(index + offset, element);
            return element;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int indexOf(Object o) {
            return subDelegate.indexOf(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int lastIndexOf(Object o) {
            return subDelegate.lastIndexOf(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator() {
            return new SubListIterator(subDelegate.listIterator(), 0);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator(int index) {
            return new SubListIterator(subDelegate.listIterator(index), index);
        }

        private class SubListIterator implements ListIterator<E> {

            private final ListIterator<E> listIterator;
            private int lastRet = -1;
            private int cursor;

            public SubListIterator(ListIterator<E> listIterator, int cursor) {
                this.listIterator = listIterator;
                this.cursor = cursor;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return listIterator.hasNext();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public E next() {
                int i = cursor;
                cursor++;
                lastRet = i;
                return listIterator.next();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasPrevious() {
                return listIterator.hasPrevious();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public E previous() {
                int i = cursor - 1;
                cursor = i;
                lastRet = i;
                return listIterator.previous();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int nextIndex() {
                return listIterator.nextIndex();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public int previousIndex() {
                return listIterator.previousIndex();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void remove() {
                if (lastRet < 0)
                    throw new IllegalStateException();

                cursor = lastRet;
                E element = subDelegate.get(lastRet);
                listIterator.remove();
                fireRemoved(lastRet + offset, element);
                lastRet = -1;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void set(E e) {
                if (lastRet < 0)
                    throw new IllegalStateException();

                E oldValue = subDelegate.get(lastRet);
                listIterator.set(e);
                fireChanged(lastRet + offset, oldValue, e);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void add(E e) {
                int i = cursor;
                listIterator.add(e);
                fireAdded(i + offset, e);
                cursor = i + 1;
                lastRet = -1;
            }

        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return new SubList(offset + fromIndex, subDelegate.subList(fromIndex, toIndex));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return subDelegate.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        // 代理方法，忽略所有警告。
        @SuppressWarnings("EqualsDoesntCheckParameterClass")
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return subDelegate.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return subDelegate.toString();
        }

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
    // 代理方法，忽略所有警告。
    @SuppressWarnings("EqualsDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
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
