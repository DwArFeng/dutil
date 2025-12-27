package com.dwarfeng.dutil.develop.reuse;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 复用工具类。
 *
 * <p>
 * 由于只是包含静态字段以及静态方法的工具类，因此该类禁止继承或者外部实例化。
 *
 * @author DwArFeng
 * @since 0.2.1-beta
 */
public final class ResuseUtil {

    /**
     * 根据指定的复用池生成一个线程安全的复用池。
     *
     * @param <E>       复用池的存储元素的类型。
     * @param reusePool 指定的复用池。
     * @return 指定的复用池生成的线程安全的复用池。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> SyncReusePool<E> syncReusePool(ReusePool<E> reusePool) throws NullPointerException {
        Objects.requireNonNull(reusePool, "入口参数 reusePool 不能为 null。");
        return new InnerSyncReusePool<>(reusePool);
    }

    private static final class InnerSyncReusePool<E> implements SyncReusePool<E> {

        private final ReusePool<E> delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public InnerSyncReusePool(ReusePool<E> delegate) {
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
        public boolean contains(Object object) {
            lock.readLock().lock();
            try {
                return delegate.contains(object);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> collection) throws NullPointerException {
            lock.readLock().lock();
            try {
                return delegate.containsAll(collection);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Condition getCondition(Object object) {
            lock.readLock().lock();
            try {
                return delegate.getCondition(object);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean put(E element, Condition condition) {
            lock.writeLock().lock();
            try {
                return delegate.put(element, condition);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object object) {
            lock.writeLock().lock();
            try {
                return delegate.remove(object);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> collection) {
            lock.writeLock().lock();
            try {
                return delegate.removeAll(collection);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> collection) {
            lock.writeLock().lock();
            try {
                return delegate.retainAll(collection);
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
        public BatchOperator<E> batchOperator() {
            lock.readLock().lock();
            try {
                return delegate.batchOperator();
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
            lock.readLock().lock();
            try {
                return delegate.equals(obj);
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

    // 禁止外部实例化。
    private ResuseUtil() {

    }
}
