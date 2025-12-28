package com.dwarfeng.dutil.develop.i18n;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;
import com.dwarfeng.dutil.basic.prog.ReadOnlyGenerator;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 有关国际化的工具包。
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
public final class I18nUtil {

    /**
     * 根据指定的国际化管理器生成一个不可更改的国际化管理器。
     *
     * @param i18nHandler 指定的国际化管理器。
     * @return 根据指定的国际化管理器生成的不可更改的国际化管理器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static I18nHandler unmodifiableI18nHandler(I18nHandler i18nHandler) {
        Objects.requireNonNull(i18nHandler, DwarfUtil.getExceptionString(ExceptionStringKey.I18NUTIL_0));
        return new UnmodifiableI18nHandler(i18nHandler);
    }

    private static class UnmodifiableI18nHandler implements I18nHandler {

        private final I18nHandler delegate;

        public UnmodifiableI18nHandler(I18nHandler delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SetObserver<I18nInfo>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<I18nInfo> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I18nInfo get(Locale key) {
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
        public Locale getCurrentLocale() {
            return delegate.getCurrentLocale();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<I18nInfo> observer) {
            throw new UnsupportedOperationException("removeObserver");
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
        public boolean setCurrentLocale(Locale locale) {
            throw new UnsupportedOperationException("setCurrentLocale");
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
        public I18n getCurrentI18n() {
            return delegate.getCurrentI18n();
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
        public Iterator<I18nInfo> iterator() {
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
        public boolean add(I18nInfo e) {
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
        public boolean addAll(Collection<? extends I18nInfo> c) {
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
        public boolean equals(Object o) {
            return delegate.equals(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return delegate.hashCode();
        }

    }

    /**
     * 根据指定的国际化管理器生成一个线程安全的国际化管理器。
     *
     * @param i18nHandler 指定的国际化管理器。
     * @return 根据指定的国际化管理器生成的线程安全的国际化管理器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static SyncI18nHandler syncI18nHandler(I18nHandler i18nHandler) {
        Objects.requireNonNull(i18nHandler, DwarfUtil.getExceptionString(ExceptionStringKey.I18NUTIL_0));
        return new SyncI18nHandlerImpl(i18nHandler);
    }

    private static class SyncI18nHandlerImpl implements SyncI18nHandler {

        private final I18nHandler delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncI18nHandlerImpl(I18nHandler delegate) {
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
        public Locale getCurrentLocale() {
            lock.readLock().lock();
            try {
                return delegate.getCurrentLocale();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setCurrentLocale(Locale locale) {
            lock.writeLock().lock();
            try {
                return delegate.setCurrentLocale(locale);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I18n getCurrentI18n() {
            lock.readLock().lock();
            try {
                return delegate.getCurrentI18n();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I18nInfo get(Locale key) {
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
        public Iterator<I18nInfo> iterator() {
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
        public boolean add(I18nInfo e) {
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
        public boolean addAll(Collection<? extends I18nInfo> c) {
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
        public Set<SetObserver<I18nInfo>> getObservers() {
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
        public boolean addObserver(SetObserver<I18nInfo> observer) {
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
        public boolean removeObserver(SetObserver<I18nInfo> observer) {
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
                delegate.clear();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            lock.readLock().lock();
            try {
                if (obj == this)
                    return true;
                return delegate.equals(obj);
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
     * 有指定的国际化信息生成一个不可编辑的国际化信息。
     *
     * @param i18nInfo 指定的国际化信息。
     * @return 由指定的国际化信息生成的不可编辑的国际化信息。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static I18nInfo unmodifiableI18nInfo(I18nInfo i18nInfo) {
        Objects.requireNonNull(i18nInfo, DwarfUtil.getExceptionString(ExceptionStringKey.I18NUTIL_1));
        return new UnmodifiableI18nInfo(i18nInfo);
    }

    private static final class UnmodifiableI18nInfo implements I18nInfo {
        private final I18nInfo delegate;

        public UnmodifiableI18nInfo(I18nInfo delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Locale getKey() {
            return delegate.getKey();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getName() {
            return delegate.getName();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I18n newI18n() {
            throw new UnsupportedOperationException("newI18n");
        }

        @Override
        public boolean equals(Object obj) {
            if (delegate.equals(obj))
                return true;
            return super.equals(obj);
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
        public String toString() {
            return delegate.toString();
        }

    }

    /**
     * 由指定的国际化处理器生成一个只读的国际化处理器。
     *
     * @param i18nHandler 指定的国际化处理器。
     * @return 由指定的国际化处理器生成的只读的国际化处理器。
     */
    public static I18nHandler readOnlyI18nHandler(I18nHandler i18nHandler) {
        Objects.requireNonNull(i18nHandler, DwarfUtil.getExceptionString(ExceptionStringKey.I18NUTIL_0));
        return readOnlyI18nHandler(i18nHandler, I18nUtil::unmodifiableI18nInfo);
    }

    /**
     * 由指定的国际化处理器和指定的只读生成器生成一个只读的国际化处理器。
     *
     * @param i18nHandler 指定的国际化处理器。
     * @param generator   指定的指定生成器。
     * @return 由指定的国际化处理器和指定的只读生成器生成一个只读的国际化处理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static I18nHandler readOnlyI18nHandler(I18nHandler i18nHandler, ReadOnlyGenerator<I18nInfo> generator) {
        Objects.requireNonNull(i18nHandler, DwarfUtil.getExceptionString(ExceptionStringKey.I18NUTIL_0));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.I18NUTIL_2));
        return new ReadOnlyI18nHandler(i18nHandler, generator);
    }

    private static final class ReadOnlyI18nHandler implements I18nHandler {
        private final I18nHandler delegate;
        private final ReadOnlyGenerator<I18nInfo> generator;

        public ReadOnlyI18nHandler(I18nHandler delegate, ReadOnlyGenerator<I18nInfo> generator) {
            this.delegate = delegate;
            this.generator = generator;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I18nInfo get(Locale key) {
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
        public Iterator<I18nInfo> iterator() {
            return CollectionUtil.readOnlyIterator(delegate.iterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            Object[] array = delegate.toArray();
            Object[] targetArray = new Object[array.length];
            for (int i = 0; i < array.length; i++) {
                targetArray[i] = generator.readOnly((I18nInfo) array[i]);
            }
            return targetArray;
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            T[] tArray = delegate.toArray(a);
            return (T[]) ArrayUtil.readOnlyArray(((I18nInfo[]) tArray), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(I18nInfo e) {
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
        public boolean addAll(Collection<? extends I18nInfo> c) {
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
        public Set<SetObserver<I18nInfo>> getObservers() {
            return delegate.getObservers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<I18nInfo> observer) {
            throw new UnsupportedOperationException("addObserver");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<I18nInfo> observer) {
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
        public Locale getCurrentLocale() {
            return delegate.getCurrentLocale();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean setCurrentLocale(Locale locale) {
            throw new UnsupportedOperationException("setCurrentLocale");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I18n getCurrentI18n() {
            return delegate.getCurrentI18n();
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
    private I18nUtil() {
    }
}
