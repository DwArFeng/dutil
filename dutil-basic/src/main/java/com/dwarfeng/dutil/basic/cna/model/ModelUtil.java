package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.dutil.basic.cna.model.obs.ListObserver;
import com.dwarfeng.dutil.basic.cna.model.obs.MapObserver;
import com.dwarfeng.dutil.basic.cna.model.obs.ReferenceObserver;
import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;
import com.dwarfeng.dutil.basic.prog.ReadOnlyGenerator;
import com.dwarfeng.dutil.basic.prog.WithKey;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 有关模型的工具包。
 *
 * <p>
 * 该包中包含模型的常用方法，包括由模型生成线程安全的模型。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public final class ModelUtil {

    /**
     * 由指定的列表模型生成一个不可编辑的列表模型。
     *
     * @param listModel 指定的列表模型。
     * @param <E>       列表模型的元素类型。
     * @return 由指定的列表模型生成的不可编辑的列表模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> ListModel<E> unmodifiableListModel(ListModel<E> listModel) {
        Objects.requireNonNull(listModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_0));
        return new UnmodifiableListModel<>(listModel);
    }

    private static final class UnmodifiableListModel<E> implements ListModel<E> {

        private final ListModel<E> delegate;

        public UnmodifiableListModel(ListModel<E> delegate) {
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
            return CollectionUtil.unmodifiableIterator(delegate.iterator());
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
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
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
            throw new UnsupportedOperationException("set");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, E element) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E remove(int index) {
            throw new UnsupportedOperationException("remove");
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
            return CollectionUtil.unmodifiableListIterator(delegate.listIterator());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator(int index) {
            return CollectionUtil.unmodifiableListIterator(delegate.listIterator(index));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return Collections.unmodifiableList(delegate.subList(fromIndex, toIndex));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ListObserver<E>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ListObserver<E> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ListObserver<E> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
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
            if (obj == delegate)
                return true;
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

    /**
     * 由指定的列表模型和指定的只读生成器生成一个只读的列表模型。
     *
     * @param listModel 指定的列表模型。
     * @param generator 指定的只读生成器。
     * @param <E>       列表模型的元素类型。
     * @return 由指定的列表模型和指定的只读生成器生成的只读的列表模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> ListModel<E> readOnlyListModel(ListModel<E> listModel, ReadOnlyGenerator<E> generator) {
        Objects.requireNonNull(listModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_0));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_5));
        return new ReadOnlyListModel<>(listModel, generator);
    }

    private static final class ReadOnlyListModel<E> implements ListModel<E> {

        private final ListModel<E> delegate;
        private final ReadOnlyGenerator<E> generator;

        public ReadOnlyListModel(ListModel<E> delegate, ReadOnlyGenerator<E> generator) {
            this.delegate = delegate;
            this.generator = generator;
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
            return CollectionUtil.readOnlyIterator(delegate.iterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            @SuppressWarnings("unchecked")
            E[] eArray = (E[]) delegate.toArray();
            return ArrayUtil.readOnlyArray(eArray, generator);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            T[] tArray = delegate.toArray(a);
            return (T[]) ArrayUtil.readOnlyArray(((E[]) tArray), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E get(int index) {
            return generator.readOnly(delegate.get(index));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E set(int index, E element) {
            throw new UnsupportedOperationException("set");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, E element) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E remove(int index) {
            throw new UnsupportedOperationException("remove");
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
            return CollectionUtil.readOnlyListIterator(delegate.listIterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator(int index) {
            return CollectionUtil.readOnlyListIterator(delegate.listIterator(index), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return CollectionUtil.readOnlyList(delegate.subList(fromIndex, toIndex), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ListObserver<E>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ListObserver<E> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ListObserver<E> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
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
            if (obj == delegate)
                return true;
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

    /**
     * 由指定的列表模型生成一个线程安全的列表模型。
     *
     * @param listModel 指定的列表模型。
     * @param <E>       列表模型的元素类型。
     * @return 由指定的列表模型生成的线程安全的列表模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> SyncListModel<E> syncListModel(ListModel<E> listModel) {
        Objects.requireNonNull(listModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_0));
        return new SyncListModelImpl<>(listModel);
    }

    private static class SyncListModelImpl<E> implements SyncListModel<E> {

        private final ListModel<E> delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncListModelImpl(ListModel<E> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return lock;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ListObserver<E>> getObservers() {
            lock.readLock().lock();
            try {
                return delegate.getObservers();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ListObserver<E> observer) {
            lock.writeLock().lock();
            try {
                return delegate.addObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ListObserver<E> observer) {
            lock.writeLock().lock();
            try {
                return delegate.removeObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            lock.writeLock().lock();
            try {
                delegate.clearObserver();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            lock.readLock().lock();
            try {
                return delegate.size();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            lock.readLock().lock();
            try {
                return delegate.isEmpty();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            lock.readLock().lock();
            try {
                return delegate.contains(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<E> iterator() {
            lock.readLock().lock();
            try {
                return delegate.iterator();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            lock.readLock().lock();
            try {
                return delegate.toArray();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            lock.readLock().lock();
            try {
                return delegate.toArray(a);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            lock.writeLock().lock();
            try {
                return delegate.add(e);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            lock.writeLock().lock();
            try {
                return delegate.remove(o);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            lock.readLock().lock();
            try {
                return delegate.containsAll(c);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            lock.writeLock().lock();
            try {
                return delegate.addAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            lock.writeLock().lock();
            try {
                return delegate.addAll(index, c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.removeAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.retainAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            lock.writeLock().lock();
            try {
                delegate.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
            lock.readLock().lock();
            try {
                return delegate.equals(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            lock.readLock().lock();
            try {
                return delegate.hashCode();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E get(int index) {
            lock.readLock().lock();
            try {
                return delegate.get(index);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E set(int index, E element) {
            lock.writeLock().lock();
            try {
                return delegate.set(index, element);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, E element) {
            lock.writeLock().lock();
            try {
                delegate.add(index, element);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E remove(int index) {
            lock.writeLock().lock();
            try {
                return delegate.remove(index);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int indexOf(Object o) {
            lock.readLock().lock();
            try {
                return delegate.indexOf(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int lastIndexOf(Object o) {
            lock.readLock().lock();
            try {
                return delegate.lastIndexOf(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator() {
            lock.readLock().lock();
            try {
                return delegate.listIterator();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator(int index) {
            lock.readLock().lock();
            try {
                return delegate.listIterator(index);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            lock.readLock().lock();
            try {
                return delegate.subList(fromIndex, toIndex);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            lock.readLock().lock();
            try {
                return delegate.toString();
            } finally {
                lock.readLock().unlock();
            }
        }

    }

    /**
     * 由指定的集合模型生成一个线程安全的集合模型。
     *
     * @param setModel 指定的集合模型。
     * @param <E>      集合模型的元素类型。
     * @return 由指定的集合模型生成的线程安全的集合模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     * @deprecated 该方法由于命名错误，已经过时，由 {@link ModelUtil#syncSetModel(SetModel)} 替。
     */
    public static <E> SyncSetModel<E> syncSetMdel(SetModel<E> setModel) {
        return syncSetModel(setModel);
    }

    /**
     * 由指定的集合模型生成一个线程安全的集合模型。
     *
     * @param setModel 指定的集合模型。
     * @param <E>      集合模型的元素类型。
     * @return 由指定的集合模型生成的线程安全的集合模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> SyncSetModel<E> syncSetModel(SetModel<E> setModel) {
        Objects.requireNonNull(setModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_1));
        return new SyncSetModelImpl<>(setModel);
    }

    private static class SyncSetModelImpl<E> implements SyncSetModel<E> {

        private final SetModel<E> delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncSetModelImpl(SetModel<E> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return lock;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SetObserver<E>> getObservers() {
            lock.readLock().lock();
            try {
                return delegate.getObservers();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<E> observer) {
            lock.writeLock().lock();
            try {
                return delegate.addObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<E> observer) {
            lock.writeLock().lock();
            try {
                return delegate.removeObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            lock.writeLock().lock();
            try {
                delegate.clearObserver();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            lock.readLock().lock();
            try {
                return delegate.size();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            lock.readLock().lock();
            try {
                return delegate.isEmpty();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            lock.readLock().lock();
            try {
                return delegate.contains(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<E> iterator() {
            lock.readLock().lock();
            try {
                return delegate.iterator();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            lock.readLock().lock();
            try {
                return delegate.toArray();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            lock.readLock().lock();
            try {
                return delegate.toArray(a);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            lock.writeLock().lock();
            try {
                return delegate.add(e);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            lock.writeLock().lock();
            try {
                return delegate.remove(o);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            lock.readLock().lock();
            try {
                return delegate.containsAll(c);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            lock.writeLock().lock();
            try {
                return delegate.addAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.retainAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.removeAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            lock.writeLock().lock();
            try {
                delegate.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
            lock.readLock().lock();
            try {
                return delegate.equals(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            lock.readLock().lock();
            try {
                return delegate.hashCode();
            } finally {
                lock.readLock().unlock();
            }
        }

    }

    /**
     * 由指定的集合模型生成一个不可编辑的集合模型。
     *
     * @param setModel 指定的集合模型。
     * @param <E>      集合模型的元素类型。
     * @return 由指定的集合模型生成的不可编辑的集合模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> SetModel<E> unmodifiableSetModel(SetModel<E> setModel) {
        Objects.requireNonNull(setModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_1));
        return new UnmodifiableSetModel<>(setModel);
    }

    private static final class UnmodifiableSetModel<E> implements SetModel<E> {

        private final SetModel<E> delegate;

        public UnmodifiableSetModel(SetModel<E> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SetObserver<E>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<E> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<E> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
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
            return CollectionUtil.unmodifiableIterator(delegate.iterator());
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
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
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
            if (obj == delegate)
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

    /**
     * 由指定的集合模型和指定的只读生成器生成一个只读的集合模型。
     *
     * @param setModel  指定的集合模型。
     * @param generator 指定的只读生成器。
     * @param <E>       集合模型的元素类型。
     * @return 由指定的集合模型和指定的只读生成器生成的只读的集合模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> SetModel<E> readOnlySetModel(SetModel<E> setModel, ReadOnlyGenerator<E> generator) {
        Objects.requireNonNull(setModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_1));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_5));
        return new ReadOnlySetModel<>(setModel, generator);
    }

    private static final class ReadOnlySetModel<E> implements SetModel<E> {

        private final SetModel<E> delegate;
        private final ReadOnlyGenerator<E> generator;

        public ReadOnlySetModel(SetModel<E> delegate, ReadOnlyGenerator<E> generator) {
            this.delegate = delegate;
            this.generator = generator;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SetObserver<E>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<E> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<E> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
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
            return CollectionUtil.readOnlyIterator(delegate.iterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            @SuppressWarnings("unchecked")
            E[] eArray = (E[]) delegate.toArray();
            return ArrayUtil.readOnlyArray(eArray, generator);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            T[] tArray = delegate.toArray(a);
            return (T[]) ArrayUtil.readOnlyArray(((E[]) tArray), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
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
            if (obj == delegate)
                return true;
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

    /**
     * 由指定的映射模型生成一个线程安全的映射模型。
     *
     * @param mapModel 指定的映射模型。
     * @param <K>      映射模型的键的类型。
     * @param <V>      映射的值的模型。
     * @return 由指定的映射模型生成的线程安全的映射模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <K, V> SyncMapModel<K, V> syncMapModel(MapModel<K, V> mapModel) {
        Objects.requireNonNull(mapModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_2));
        return new SyncMapModelImpl<>(mapModel);
    }

    private static class SyncMapModelImpl<K, V> implements SyncMapModel<K, V> {

        private final MapModel<K, V> delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncMapModelImpl(MapModel<K, V> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return lock;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<MapObserver<K, V>> getObservers() {
            lock.readLock().lock();
            try {
                return delegate.getObservers();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(MapObserver<K, V> observer) {
            lock.writeLock().lock();
            try {
                return delegate.addObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(MapObserver<K, V> observer) {
            lock.writeLock().lock();
            try {
                return delegate.removeObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            lock.writeLock().lock();
            try {
                delegate.clearObserver();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            lock.readLock().lock();
            try {
                return delegate.size();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            lock.readLock().lock();
            try {
                return delegate.isEmpty();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            lock.readLock().lock();
            try {
                return delegate.containsKey(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsValue(Object value) {
            lock.readLock().lock();
            try {
                return delegate.containsValue(value);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(Object key) {
            lock.readLock().lock();
            try {
                return delegate.get(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V put(K key, V value) {
            lock.writeLock().lock();
            try {
                return delegate.put(key, value);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(Object key) {
            lock.writeLock().lock();
            try {
                return delegate.remove(key);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            lock.writeLock().lock();
            try {
                delegate.putAll(m);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            lock.writeLock().lock();
            try {
                delegate.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<K> keySet() {
            lock.readLock().lock();
            try {
                return delegate.keySet();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<V> values() {
            lock.readLock().lock();
            try {
                return delegate.values();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<java.util.Map.Entry<K, V>> entrySet() {
            lock.readLock().lock();
            try {
                return delegate.entrySet();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
            lock.readLock().lock();
            try {
                return delegate.equals(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            lock.readLock().lock();
            try {
                return delegate.hashCode();
            } finally {
                lock.readLock().unlock();
            }
        }

    }

    /**
     * 由指定的映射模型生成一个不可编辑的映射模型。
     *
     * @param mapModel 指定的映射模型。
     * @param <K>      映射模型的键的类型。
     * @param <V>      映射的值的模型。
     * @return 由指定的映射模型生成的不可编辑的映射模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <K, V> MapModel<K, V> unmodifiableMapModel(MapModel<K, V> mapModel) {
        Objects.requireNonNull(mapModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_2));
        return new UnmodifiableMapModel<>(mapModel);
    }

    private static final class UnmodifiableMapModel<K, V> implements MapModel<K, V> {

        private final MapModel<K, V> delegate;

        public UnmodifiableMapModel(MapModel<K, V> delegate) {
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
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsValue(Object value) {
            return delegate.containsValue(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(Object key) {
            return delegate.get(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V put(K key, V value) {
            throw new UnsupportedOperationException("put");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(Object key) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            throw new UnsupportedOperationException("putAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<K> keySet() {
            return Collections.unmodifiableSet(delegate.keySet());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<V> values() {
            return Collections.unmodifiableCollection(delegate.values());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<Entry<K, V>> entrySet() {
            return Collections.unmodifiableSet(delegate.entrySet());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<MapObserver<K, V>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(MapObserver<K, V> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(MapObserver<K, V> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
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
            if (obj == delegate)
                return true;
            if (obj == this)
                return false;
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

    /**
     * 由指定的映射模型和指定的只读生成器生成一个只读的映射模型。
     *
     * @param mapModel 指定的映射模型。
     * @param keyGen   指定的键只读生成器。
     * @param valueGen 指定的值只读生成器。
     * @param <K>      只读生成器的键的泛型。
     * @param <V>      只读生成器的值的泛型。
     * @return 由指定的映射模型和指定的只读生成器生成的只读映射模型。
     */
    public static <K, V> MapModel<K, V> readOnlyMapModel(MapModel<K, V> mapModel, ReadOnlyGenerator<K> keyGen,
                                                         ReadOnlyGenerator<V> valueGen) {
        Objects.requireNonNull(mapModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_2));
        Objects.requireNonNull(keyGen, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_6));
        Objects.requireNonNull(valueGen, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_7));
        return new ReadOnlyMapModel<>(mapModel, keyGen, valueGen);
    }

    private static final class ReadOnlyMapModel<K, V> implements MapModel<K, V> {

        private final MapModel<K, V> delegate;
        private final ReadOnlyGenerator<K> keyGen;
        private final ReadOnlyGenerator<V> valueGen;

        public ReadOnlyMapModel(MapModel<K, V> delegate, ReadOnlyGenerator<K> keyGen, ReadOnlyGenerator<V> valueGen) {
            this.delegate = delegate;
            this.keyGen = keyGen;
            this.valueGen = valueGen;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<MapObserver<K, V>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(MapObserver<K, V> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(MapObserver<K, V> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
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
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsValue(Object value) {
            return delegate.containsValue(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(Object key) {
            return valueGen.readOnly(delegate.get(key));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V put(K key, V value) {
            throw new UnsupportedOperationException("put");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(Object key) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            throw new UnsupportedOperationException("putAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<K> keySet() {
            return CollectionUtil.readOnlySet(delegate.keySet(), keyGen);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<V> values() {
            return CollectionUtil.readOnlyCollection(delegate.values(), valueGen);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<Entry<K, V>> entrySet() {
            return CollectionUtil.readOnlySet(delegate.entrySet(), element -> {
                return CollectionUtil.readOnlyMapEntry(element, keyGen, valueGen);
            });
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
            if (obj == delegate)
                return true;
            if (obj == this)
                return false;
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

    /**
     * 由指定的键值列表模型生成一个线程安全的键值列表模型。
     *
     * @param keyListModel 指定的键值列表模型。
     * @param <K>          键值列表模型的键的类型。
     * @param <V>          键值列表的值的模型。
     * @return 由指定的键值列表模型生成的线程安全的键值列表模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <K, V extends WithKey<K>> SyncKeyListModel<K, V> syncKeyListModel(KeyListModel<K, V> keyListModel) {
        Objects.requireNonNull(keyListModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_3));
        return new SyncKeyListModelImpl<>(keyListModel);
    }

    private static class SyncKeyListModelImpl<K, V extends WithKey<K>> implements SyncKeyListModel<K, V> {

        private final KeyListModel<K, V> delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncKeyListModelImpl(KeyListModel<K, V> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return lock;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ListObserver<V>> getObservers() {
            lock.readLock().lock();
            try {
                return delegate.getObservers();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ListObserver<V> observer) {
            lock.writeLock().lock();
            try {
                return delegate.addObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ListObserver<V> observer) {
            lock.writeLock().lock();
            try {
                return delegate.removeObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            lock.writeLock().lock();
            try {
                delegate.clearObserver();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(K key) {
            lock.readLock().lock();
            try {
                return delegate.get(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            lock.readLock().lock();
            try {
                return delegate.containsKey(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAllKey(Collection<?> c) {
            lock.readLock().lock();
            try {
                return delegate.containsAllKey(c);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int indexOfKey(Object o) {
            lock.readLock().lock();
            try {
                return delegate.indexOfKey(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int lastIndexOfKey(Object o) {
            lock.readLock().lock();
            try {
                return delegate.lastIndexOfKey(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeKey(Object key) {
            lock.writeLock().lock();
            try {
                return delegate.removeKey(key);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAllKey(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.removeAllKey(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAllKey(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.retainAllKey(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<V> subList(int fromIndex, int toIndex) {
            lock.readLock().lock();
            try {
                return delegate.subList(fromIndex, toIndex);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            lock.readLock().lock();
            try {
                return delegate.size();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            lock.readLock().lock();
            try {
                return delegate.isEmpty();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            lock.readLock().lock();
            try {
                return delegate.contains(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<V> iterator() {
            lock.readLock().lock();
            try {
                return delegate.iterator();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            lock.readLock().lock();
            try {
                return delegate.toArray();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            lock.readLock().lock();
            try {
                return delegate.toArray(a);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(V e) {
            lock.writeLock().lock();
            try {
                return delegate.add(e);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            lock.writeLock().lock();
            try {
                return delegate.remove(o);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            lock.readLock().lock();
            try {
                return delegate.containsAll(c);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends V> c) {
            lock.writeLock().lock();
            try {
                return delegate.addAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(int index, Collection<? extends V> c) {
            lock.writeLock().lock();
            try {
                return delegate.addAll(index, c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.removeAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.retainAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            lock.writeLock().lock();
            try {
                delegate.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
            lock.readLock().lock();
            try {
                return delegate.equals(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            lock.readLock().lock();
            try {
                return delegate.hashCode();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(int index) {
            lock.readLock().lock();
            try {
                return delegate.get(index);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V set(int index, V element) {
            lock.writeLock().lock();
            try {
                return delegate.set(index, element);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, V element) {
            lock.writeLock().lock();
            try {
                delegate.add(index, element);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(int index) {
            lock.writeLock().lock();
            try {
                return delegate.remove(index);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int indexOf(Object o) {
            lock.readLock().lock();
            try {
                return delegate.indexOf(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int lastIndexOf(Object o) {
            lock.readLock().lock();
            try {
                return delegate.lastIndexOf(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<V> listIterator() {
            lock.readLock().lock();
            try {
                return delegate.listIterator();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<V> listIterator(int index) {
            lock.readLock().lock();
            try {
                return delegate.listIterator(index);
            } finally {
                lock.readLock().unlock();
            }
        }

    }

    /**
     * 由指定的键值列表模型生成一个不可更改的键值列表模型。
     *
     * @param keyListModel 指定的键值列表模型。
     * @param <K>          键值列表模型的键的类型。
     * @param <V>          键值列表的值的模型。
     * @return 由指定的键值列表模型生成的不可更改的键值列表模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <K, V extends WithKey<K>> KeyListModel<K, V> unmodifiableKeyListModel(
            KeyListModel<K, V> keyListModel) {
        Objects.requireNonNull(keyListModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_3));
        return new UnmodifiableKeyListModel<>(keyListModel);
    }

    private static final class UnmodifiableKeyListModel<K, V extends WithKey<K>> implements KeyListModel<K, V> {

        private final KeyListModel<K, V> delegate;

        public UnmodifiableKeyListModel(KeyListModel<K, V> delegate) {
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
        public Iterator<V> iterator() {
            return CollectionUtil.unmodifiableIterator(delegate.iterator());
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
        public boolean add(V e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends V> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(int index, Collection<? extends V> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(int index) {
            return delegate.get(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V set(int index, V element) {
            throw new UnsupportedOperationException("set");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, V element) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(int index) {
            throw new UnsupportedOperationException("remove");
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
        public ListIterator<V> listIterator() {
            return CollectionUtil.unmodifiableListIterator(delegate.listIterator());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<V> listIterator(int index) {
            return CollectionUtil.unmodifiableListIterator(delegate.listIterator(index));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<V> subList(int fromIndex, int toIndex) {
            return delegate.subList(fromIndex, toIndex);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ListObserver<V>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ListObserver<V> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ListObserver<V> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(K key) {
            return delegate.get(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAllKey(Collection<?> c) {
            return delegate.containsAllKey(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int indexOfKey(Object o) {
            return delegate.indexOfKey(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int lastIndexOfKey(Object o) {
            return delegate.lastIndexOfKey(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeKey(Object key) {
            throw new UnsupportedOperationException("removeKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAllKey(Collection<?> c) {
            throw new UnsupportedOperationException("removeAllKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAllKey(Collection<?> c) {
            throw new UnsupportedOperationException("retainAllKey");
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
            if (obj == delegate)
                return true;
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

    /**
     * 由指定的键值列表模型和指定的只读生成器生成一个只读的键值列表模型。
     *
     * @param keyListModel 指定的键值列表模型。
     * @param generator    指定的只读生成器。
     * @param <K>          只读生成器的键的泛型。
     * @param <V>          只读生成器的值的泛型。
     * @return 由指定的键值列表模型和指定的只读生成器生成的只读键值列表模型。
     */
    public static <K, V extends WithKey<K>> KeyListModel<K, V> readOnlyKeyListModel(KeyListModel<K, V> keyListModel,
                                                                                    ReadOnlyGenerator<V> generator) {
        Objects.requireNonNull(keyListModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_3));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_5));
        return new ReadOnlyKeyListModel<>(keyListModel, generator);
    }

    private static final class ReadOnlyKeyListModel<K, V extends WithKey<K>> implements KeyListModel<K, V> {

        private final KeyListModel<K, V> delegate;
        private final ReadOnlyGenerator<V> generator;

        public ReadOnlyKeyListModel(KeyListModel<K, V> delegate, ReadOnlyGenerator<V> generator) {
            this.delegate = delegate;
            this.generator = generator;
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
        public Iterator<V> iterator() {
            return CollectionUtil.readOnlyIterator(delegate.iterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            @SuppressWarnings("unchecked")
            V[] eArray = (V[]) delegate.toArray();
            return ArrayUtil.readOnlyArray(eArray, generator);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            T[] tArray = delegate.toArray(a);
            return (T[]) ArrayUtil.readOnlyArray(((V[]) tArray), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(V e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends V> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(int index, Collection<? extends V> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(int index) {
            return generator.readOnly(delegate.get(index));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V set(int index, V element) {
            throw new UnsupportedOperationException("set");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, V element) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(int index) {
            throw new UnsupportedOperationException("remove");
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
        public ListIterator<V> listIterator() {
            return CollectionUtil.readOnlyListIterator(delegate.listIterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<V> listIterator(int index) {
            return CollectionUtil.readOnlyListIterator(delegate.listIterator(index), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<V> subList(int fromIndex, int toIndex) {
            return CollectionUtil.readOnlyList(delegate.subList(fromIndex, toIndex), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ListObserver<V>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ListObserver<V> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ListObserver<V> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(K key) {
            return delegate.get(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAllKey(Collection<?> c) {
            return delegate.containsAllKey(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int indexOfKey(Object o) {
            return delegate.indexOfKey(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int lastIndexOfKey(Object o) {
            return delegate.lastIndexOfKey(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeKey(Object key) {
            throw new UnsupportedOperationException("removeKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAllKey(Collection<?> c) {
            throw new UnsupportedOperationException("removeAllKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAllKey(Collection<?> c) {
            throw new UnsupportedOperationException("retainAllKey");
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
            if (obj == delegate)
                return true;
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

    /**
     * 由指定的键值集合模型生成一个线程安全的键值集合模型。
     *
     * @param keySetModel 指定的键值集合模型。
     * @param <K>         键值集合模型的键的类型。
     * @param <V>         键值集合的值的模型。
     * @return 由指定的键值集合模型生成的线程安全的键值集合模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <K, V extends WithKey<K>> SyncKeySetModel<K, V> syncKeySetModel(KeySetModel<K, V> keySetModel) {
        Objects.requireNonNull(keySetModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_4));
        return new SyncKeySetModelImpl<>(keySetModel);
    }

    private static class SyncKeySetModelImpl<K, V extends WithKey<K>> implements SyncKeySetModel<K, V> {

        private final KeySetModel<K, V> delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncKeySetModelImpl(KeySetModel<K, V> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return lock;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SetObserver<V>> getObservers() {
            lock.readLock().lock();
            try {
                return delegate.getObservers();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<V> observer) {
            lock.writeLock().lock();
            try {
                return delegate.addObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<V> observer) {
            lock.writeLock().lock();
            try {
                return delegate.removeObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            lock.writeLock().lock();
            try {
                delegate.clearObserver();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(K key) {
            lock.readLock().lock();
            try {
                return delegate.get(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            lock.readLock().lock();
            try {
                return delegate.containsKey(key);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAllKey(Collection<?> c) {
            lock.readLock().lock();
            try {
                return delegate.containsAllKey(c);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeKey(Object key) {
            lock.writeLock().lock();
            try {
                return delegate.removeKey(key);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAllKey(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.removeAllKey(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAllKey(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.retainAllKey(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            lock.readLock().lock();
            try {
                return delegate.size();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            lock.readLock().lock();
            try {
                return delegate.isEmpty();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            lock.readLock().lock();
            try {
                return delegate.contains(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<V> iterator() {
            lock.readLock().lock();
            try {
                return delegate.iterator();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            lock.readLock().lock();
            try {
                return delegate.toArray();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            lock.readLock().lock();
            try {
                return delegate.toArray(a);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(V e) {
            lock.writeLock().lock();
            try {
                return delegate.add(e);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            lock.writeLock().lock();
            try {
                return delegate.remove(o);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            lock.readLock().lock();
            try {
                return delegate.containsAll(c);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends V> c) {
            lock.writeLock().lock();
            try {
                return delegate.addAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.retainAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            lock.writeLock().lock();
            try {
                return delegate.removeAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            lock.writeLock().lock();
            try {
                delegate.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override

        public boolean equals(Object o) {
            lock.readLock().lock();
            try {
                return delegate.equals(o);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            lock.readLock().lock();
            try {
                return delegate.hashCode();
            } finally {
                lock.readLock().unlock();
            }
        }

    }

    /**
     * 由指定的键值列表模型和指定的只读生成器生成不可编辑的键值列表模型。
     *
     * @param keySetModel 指定的键值列表模型。
     * @param generator   指定的只读生成器。
     * @param <K>         映射模型的键的类型。
     * @param <V>         映射的值的模型。
     * @return 由指定的键值列表模型和指定的只读生成器生成的不可编辑的键值列表模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     * @deprecated 该方法由于错误的拥有一个多余的参数，已经由
     * <code>unmodifiableKeySetModel(KeySetModel&lt;K,V&gt;)</code>代替。
     */
    public static <K, V extends WithKey<K>> KeySetModel<K, V> unmodifiableKeySetModel(KeySetModel<K, V> keySetModel,
                                                                                      ReadOnlyGenerator<V> generator) {
        Objects.requireNonNull(keySetModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_4));
        return new UnmodifiableKeySetModel<>(keySetModel);
    }

    /**
     * 有指定的键值列表模型生成不可编辑的键值列表模型。
     *
     * @param keySetModel 指定的键值列表模型。
     * @param <K>         映射模型的键的类型。
     * @param <V>         映射的值的模型。
     * @return 由指定的键值列表模型生成的不可编辑的键值列表模型。
     */
    public static <K, V extends WithKey<K>> KeySetModel<K, V> unmodifiableKeySetModel(KeySetModel<K, V> keySetModel) {
        Objects.requireNonNull(keySetModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_4));
        return new UnmodifiableKeySetModel<>(keySetModel);
    }

    private static final class UnmodifiableKeySetModel<K, V extends WithKey<K>> implements KeySetModel<K, V> {

        private final KeySetModel<K, V> delegate;

        public UnmodifiableKeySetModel(KeySetModel<K, V> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SetObserver<V>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<V> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<V> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
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
        public Iterator<V> iterator() {
            return CollectionUtil.unmodifiableIterator(delegate.iterator());
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
        public boolean add(V e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends V> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(K key) {
            return delegate.get(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAllKey(Collection<?> c) {
            return delegate.containsAllKey(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeKey(Object key) {
            throw new UnsupportedOperationException("removeKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAllKey(Collection<?> c) {
            throw new UnsupportedOperationException("removeAllKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAllKey(Collection<?> c) {
            throw new UnsupportedOperationException("retainAllKey");
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
            if (obj == delegate)
                return true;
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

    /**
     * 由指定的键值集合模型和指定的只读生成器生成一个指定键值集合模型。
     *
     * @param keySetModel 指定的键值集合模型。
     * @param generator   指定的只读生成器。
     * @param <K>         只读生成器的键的泛型。
     * @param <V>         只读生成器的值的泛型。
     * @return 由指定的键值集合模型和指定的只读生成器生成一个指定的只读键值集合模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <K, V extends WithKey<K>> KeySetModel<K, V> readOnlyKeySetModel(KeySetModel<K, V> keySetModel,
                                                                                  ReadOnlyGenerator<V> generator) {
        Objects.requireNonNull(keySetModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_4));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_5));
        return new ReadOnlyKeySetModel<>(keySetModel, generator);
    }

    private static final class ReadOnlyKeySetModel<K, V extends WithKey<K>> implements KeySetModel<K, V> {

        private final KeySetModel<K, V> delegate;
        private final ReadOnlyGenerator<V> generator;

        public ReadOnlyKeySetModel(KeySetModel<K, V> delegate, ReadOnlyGenerator<V> generator) {
            this.delegate = delegate;
            this.generator = generator;
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
        public Iterator<V> iterator() {
            return CollectionUtil.readOnlyIterator(delegate.iterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            @SuppressWarnings("unchecked")
            V[] eArray = (V[]) delegate.toArray();
            return ArrayUtil.readOnlyArray(eArray, generator);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            T[] tArray = delegate.toArray(a);
            return (T[]) ArrayUtil.readOnlyArray(((V[]) tArray), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(V e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends V> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SetObserver<V>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<V> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<V> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(K key) {
            return generator.readOnly(delegate.get(key));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAllKey(Collection<?> c) {
            return delegate.containsAllKey(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeKey(Object key) {
            throw new UnsupportedOperationException("removeKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAllKey(Collection<?> c) {
            throw new UnsupportedOperationException("removeAllKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAllKey(Collection<?> c) {
            throw new UnsupportedOperationException("retainAllKey");
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
            if (obj == delegate)
                return true;
            if (obj == this)
                return false;
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

    /**
     * 由指定的引用模型生成一个线程安全的引用模型。
     *
     * @param referenceModel 指定的引用模型。
     * @param <E>            引用模型的元素类型。
     * @return 由指定的引用模型生成的线程安全的引用模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> SyncReferenceModel<E> syncReferenceModel(ReferenceModel<E> referenceModel) {
        Objects.requireNonNull(referenceModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_8));
        return new SyncReferenceModelImpl<>(referenceModel);
    }

    private static final class SyncReferenceModelImpl<E> implements SyncReferenceModel<E> {

        private final ReferenceModel<E> delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncReferenceModelImpl(ReferenceModel<E> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E get() {
            lock.readLock().lock();
            try {
                return delegate.get();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E set(E element) {
            lock.writeLock().lock();
            try {
                return delegate.set(element);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            lock.writeLock().lock();
            try {
                delegate.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ReferenceObserver<E>> getObservers() {
            lock.readLock().lock();
            try {
                return delegate.getObservers();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ReferenceObserver<E> observer) {
            lock.writeLock().lock();
            try {
                return delegate.addObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ReferenceObserver<E> observer) {
            lock.writeLock().lock();
            try {
                return delegate.removeObserver(observer);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            lock.writeLock().lock();
            try {
                delegate.clearObserver();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ReadWriteLock getLock() {
            return lock;
        }

        @Override
        public int hashCode() {
            lock.readLock().lock();
            try {
                return delegate.hashCode();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == delegate)
                return true;
            if (obj == this)
                return true;
            return delegate.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            lock.readLock().lock();
            try {
                return delegate.toString();
            } finally {
                lock.readLock().unlock();
            }
        }

    }

    /**
     * 根据已有的引用模型生成一个不可编辑的引用模型。
     *
     * @param referenceModel 已有的引用模型。
     * @param <E>            引用模型的元素类型。
     * @return 根据已有的引用模型生成的不可编辑的引用模型。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> ReferenceModel<E> unmodifiableReferenceModel(ReferenceModel<E> referenceModel) {
        Objects.requireNonNull(referenceModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_8));
        return new UnmodifiableReferenceModel<>(referenceModel);
    }

    private static final class UnmodifiableReferenceModel<E> implements ReferenceModel<E> {

        private final ReferenceModel<E> delegate;

        public UnmodifiableReferenceModel(ReferenceModel<E> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ReferenceObserver<E>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ReferenceObserver<E> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ReferenceObserver<E> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E get() {
            return delegate.get();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E set(E element) {
            throw new UnsupportedOperationException("set");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
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
            if (obj == delegate)
                return true;
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

    /**
     * 由指定的引用模型和指定的只读生成器生成的只读引用模型。
     *
     * @param referenceModel 指定的引用模型。
     * @param generator      指定的只读生成器。
     * @param <E>            引用模型的元素类型。
     * @return 由指定的引用模型和指定的只读生成器生成的只读引用模型。
     */
    public static <E> ReferenceModel<E> readOnlyReferenceModel(ReferenceModel<E> referenceModel,
                                                               ReadOnlyGenerator<E> generator) {
        Objects.requireNonNull(referenceModel, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_8));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.MODELUTIL_5));
        return new ReadOnlyReferenceModel<>(referenceModel, generator);
    }

    private static final class ReadOnlyReferenceModel<E> implements ReferenceModel<E> {

        private final ReferenceModel<E> delegate;
        private final ReadOnlyGenerator<E> generator;

        public ReadOnlyReferenceModel(ReferenceModel<E> delegate, ReadOnlyGenerator<E> generator) {
            this.delegate = delegate;
            this.generator = generator;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<ReferenceObserver<E>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(ReferenceObserver<E> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(ReferenceObserver<E> observer) {
            throw new UnsupportedOperationException("removeObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E get() {
            return generator.readOnly(delegate.get());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E set(E element) {
            throw new UnsupportedOperationException("set");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
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
            if (obj == delegate)
                return true;
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

    // 禁止外部实例化。
    private ModelUtil() {
    }
}
