package com.dwarfeng.dutil.develop.resource;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;
import com.dwarfeng.dutil.basic.prog.ReadOnlyGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 有关资源管理的工具包。
 *
 * <p>
 * 该包中包含关于对资源管理器进行操作的常用方法。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public final class ResourceUtil {

    /**
     * 根据指定的资源管理器生成一个不可编辑的资源管理器。
     *
     * @param resourceHandler 指定的资源管理器。
     * @return 根据指定的资源管理器生成的不可编辑的资源管理器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static ResourceHandler unmodifiableResourceHandler(ResourceHandler resourceHandler) {
        Objects.requireNonNull(resourceHandler, DwarfUtil.getExceptionString(ExceptionStringKey.RESOURCEUTIL_0));
        return new UnmodifiableResourceHandler(resourceHandler);
    }

    private static class UnmodifiableResourceHandler implements ResourceHandler {

        private final ResourceHandler delegate;

        public UnmodifiableResourceHandler(ResourceHandler delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Resource get(String key) {
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
        public Iterator<Resource> iterator() {
            return new UnmodifiableIterator(delegate.iterator());
        }

        private class UnmodifiableIterator implements Iterator<Resource> {

            private final Iterator<Resource> delegateIterator;

            public UnmodifiableIterator(Iterator<Resource> delegateIterator) {
                this.delegateIterator = delegateIterator;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return delegateIterator.hasNext();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Resource next() {
                return delegateIterator.next();
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
        public boolean add(Resource e) {
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
        public boolean addAll(Collection<? extends Resource> c) {
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
        public Set<SetObserver<Resource>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<Resource> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<Resource> observer) {
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
            if (obj == this)
                return true;
            return delegate.equals(obj);
        }

    }

    /**
     * 根据指定的资源管理器生成一个线程安全的资源管理器。
     *
     * @param resourceHandler 指定的资源管理器。
     * @return 根据指定的资源管理器生成的线程安全的资源管理器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static SyncResourceHandler syncResourceHandler(ResourceHandler resourceHandler) {
        Objects.requireNonNull(resourceHandler, DwarfUtil.getExceptionString(ExceptionStringKey.RESOURCEUTIL_0));
        return new SyncResourceHandlerImpl(resourceHandler);
    }

    private static class SyncResourceHandlerImpl implements SyncResourceHandler {

        private final ResourceHandler delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncResourceHandlerImpl(ResourceHandler delegate) {
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
        public Set<SetObserver<Resource>> getObservers() {
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
        public boolean addObserver(SetObserver<Resource> observer) {
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
        public Resource get(String key) {
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
        public boolean removeObserver(SetObserver<Resource> observer) {
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
        public Iterator<Resource> iterator() {
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
        public boolean add(Resource e) {
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
        public boolean addAll(Collection<? extends Resource> c) {
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
                if (o == this)
                    return true;
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
     * 由指定的资源生成一个不可编辑的资源。
     *
     * @param resource 指定的资源。
     * @return 由指定的资源生成的不可编辑的资源
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static Resource unmodifiableResource(Resource resource) {
        Objects.requireNonNull(DwarfUtil.getExceptionString(ExceptionStringKey.RESOURCEUTIL_1));
        return new UnmodifiableResource(resource);
    }

    private static final class UnmodifiableResource implements Resource {

        private final Resource delegate;

        public UnmodifiableResource(Resource delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getKey() {
            return delegate.getKey();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public InputStream openInputStream() throws IOException {
            throw new UnsupportedOperationException("openInputStream");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OutputStream openOutputStream() throws IOException {
            throw new UnsupportedOperationException("openOutputStream");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void reset() throws IOException {
            throw new UnsupportedOperationException("reset");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid() {
            return delegate.isValid();
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

    /**
     * 由指定的资源管理器生成一个只读的资源管理器。
     *
     * @param resourceHandler 指定的资源管理器。
     * @return 由指定的管理器生成的只读的资源管理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static ResourceHandler readOnlyResourceHandler(ResourceHandler resourceHandler) {
        Objects.requireNonNull(resourceHandler, DwarfUtil.getExceptionString(ExceptionStringKey.RESOURCEUTIL_0));
        return readOnlyResourceHandler(resourceHandler, resource -> {
            return unmodifiableResource(resource);
        });
    }

    /**
     * 由指定的资源管理器和指定的只读生成器生一个只读的资源管理器。
     *
     * @param resourceHandler 指定的资源管理器。
     * @param generator       指定的只读生成器。
     * @return 由指定的资源管理器和指定的只读生成器生成的资源管理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static ResourceHandler readOnlyResourceHandler(ResourceHandler resourceHandler,
                                                          ReadOnlyGenerator<Resource> generator) {
        Objects.requireNonNull(resourceHandler, DwarfUtil.getExceptionString(ExceptionStringKey.RESOURCEUTIL_0));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.RESOURCEUTIL_2));
        return new ReadOnlyResourceHandler(resourceHandler, generator);
    }

    private final static class ReadOnlyResourceHandler implements ResourceHandler {

        private final ResourceHandler delegate;
        private final ReadOnlyGenerator<Resource> generator;

        public ReadOnlyResourceHandler(ResourceHandler delegate, ReadOnlyGenerator<Resource> generator) {
            this.delegate = delegate;
            this.generator = generator;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Resource get(String key) {
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
        public Set<SetObserver<Resource>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<Resource> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<Resource> observer) {
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
        public Iterator<Resource> iterator() {
            return CollectionUtil.readOnlyIterator(delegate.iterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            Resource[] eArray = (Resource[]) delegate.toArray();
            return ArrayUtil.readOnlyArray(eArray, generator);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            T[] tArray = delegate.toArray(a);
            return (T[]) ArrayUtil.readOnlyArray(((Resource[]) tArray), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(Resource e) {
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
        public boolean addAll(Collection<? extends Resource> c) {
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
        public InputStream openInputStream(String key) throws IOException, IllegalArgumentException {
            throw new UnsupportedOperationException("openInputStream");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OutputStream openOutputStream(String key) throws IOException, IllegalArgumentException {
            throw new UnsupportedOperationException("openOutputStream");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void reset(String key) throws IOException, IllegalArgumentException {
            throw new UnsupportedOperationException("reset");
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

    // 禁止外部实例化。
    private ResourceUtil() {
    }
}
