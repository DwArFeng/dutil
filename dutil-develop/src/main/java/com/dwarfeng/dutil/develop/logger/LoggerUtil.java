package com.dwarfeng.dutil.develop.logger;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.CollectionUtil;
import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;
import com.dwarfeng.dutil.basic.prog.ReadOnlyGenerator;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 记录器的工具方法。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public final class LoggerUtil {

    private static final class ReadOnlyLoggerHandler implements LoggerHandler {

        private final LoggerHandler delegate;
        private final ReadOnlyGenerator<LoggerInfo> loggerInfoGen;
        private final ReadOnlyGenerator<Logger> loggerGen;

        public ReadOnlyLoggerHandler(LoggerHandler delegate, ReadOnlyGenerator<LoggerInfo> loggerInfoGen,
                                     ReadOnlyGenerator<Logger> loggerGen) {
            this.delegate = delegate;
            this.loggerInfoGen = loggerInfoGen;
            this.loggerGen = loggerGen;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(LoggerInfo e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends LoggerInfo> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<LoggerInfo> observer) {
            throw new UnsupportedOperationException("addObserver");
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
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
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
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
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
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public LoggerInfo get(String key) {
            return loggerInfoGen.readOnly(delegate.get(key));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SetObserver<LoggerInfo>> getObservers() {
            return delegate.getObservers();
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
        public Iterator<LoggerInfo> iterator() {
            return CollectionUtil.readOnlyIterator(delegate.iterator(), loggerInfoGen);
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
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
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
        public boolean removeKey(Object key) {
            throw new UnsupportedOperationException("removeKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<LoggerInfo> observer) {
            throw new UnsupportedOperationException("removeObserver");
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
        public boolean retainAllKey(Collection<?> c) {
            throw new UnsupportedOperationException("retainAllKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainUse(Collection<LoggerInfo> c) {
            throw new UnsupportedOperationException("retainUse");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainUseKey(Collection<String> c) {
            throw new UnsupportedOperationException("retainUseKey");
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
        public boolean unuse(LoggerInfo loggerInfo) {
            throw new UnsupportedOperationException("unuse");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unuseAll() {
            throw new UnsupportedOperationException("unuseAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuseAll(Collection<LoggerInfo> c) {
            throw new UnsupportedOperationException("unuseAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuseAllKey(Collection<String> c) {
            throw new UnsupportedOperationException("unuseAllKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuseKey(String key) {
            throw new UnsupportedOperationException("unuseKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean use(LoggerInfo loggerInfo) {
            throw new UnsupportedOperationException("use");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void useAll() {
            throw new UnsupportedOperationException("useAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean useAll(Collection<LoggerInfo> c) {
            throw new UnsupportedOperationException("useAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean useAllKey(Collection<String> c) {
            throw new UnsupportedOperationException("useAllKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<Logger> usedLoggers() {
            return CollectionUtil.readOnlyCollection(delegate.usedLoggers(), loggerGen);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean useKey(String key) {
            throw new UnsupportedOperationException("useKey");
        }

    }

    private static final class SyncLoggerHandlerImpl implements SyncLoggerHandler {

        private final LoggerHandler delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public SyncLoggerHandlerImpl(LoggerHandler delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(LoggerInfo e) {
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
        public boolean addAll(Collection<? extends LoggerInfo> c) {
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
        public boolean addObserver(SetObserver<LoggerInfo> observer) {
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
        public boolean equals(Object obj) {
            lock.readLock().lock();
            try {
                if (obj == this || obj == delegate) {
                    return true;
                }
                return delegate.equals(obj);
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public LoggerInfo get(String key) {
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
        public ReadWriteLock getLock() {
            return lock;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SetObserver<LoggerInfo>> getObservers() {
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
        public Iterator<LoggerInfo> iterator() {
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
        public boolean removeObserver(SetObserver<LoggerInfo> observer) {
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
        public boolean retainUse(Collection<LoggerInfo> c) {
            lock.writeLock().lock();
            try {
                return delegate.retainUse(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainUseKey(Collection<String> c) {
            lock.writeLock().lock();
            try {
                return delegate.retainUseKey(c);
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
        public String toString() {
            lock.readLock().lock();
            try {
                return delegate.toString();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuse(LoggerInfo loggerInfo) {
            lock.writeLock().lock();
            try {
                return delegate.unuse(loggerInfo);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unuseAll() {
            lock.writeLock().lock();
            try {
                delegate.unuseAll();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuseAll(Collection<LoggerInfo> c) {
            lock.writeLock().lock();
            try {
                return delegate.unuseAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuseAllKey(Collection<String> c) {
            lock.writeLock().lock();
            try {
                return delegate.unuseAllKey(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuseKey(String key) {
            lock.writeLock().lock();
            try {
                return delegate.unuseKey(key);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean use(LoggerInfo loggerInfo) {
            lock.writeLock().lock();
            try {
                return delegate.use(loggerInfo);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void useAll() {
            lock.writeLock().lock();
            try {
                delegate.useAll();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean useAll(Collection<LoggerInfo> c) {
            lock.writeLock().lock();
            try {
                return delegate.useAll(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean useAllKey(Collection<String> c) {
            lock.writeLock().lock();
            try {
                return delegate.useAllKey(c);
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<Logger> usedLoggers() {
            lock.readLock().lock();
            try {
                return delegate.usedLoggers();
            } finally {
                lock.readLock().unlock();
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean useKey(String key) {
            lock.writeLock().lock();
            try {
                return delegate.useKey(key);
            } finally {
                lock.writeLock().unlock();
            }
        }

    }

    private static final class UnmodifiableLogger implements Logger {

        private final Logger delegate;

        public UnmodifiableLogger(Logger delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void debug(String message) {
            throw new UnsupportedOperationException("debug");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this || obj == delegate) {
                return true;
            }
            return delegate.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void error(String message, Throwable t) {
            throw new UnsupportedOperationException("error");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void fatal(String message, Throwable t) {
            throw new UnsupportedOperationException("fatal");
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
        public void info(String message) {
            throw new UnsupportedOperationException("info");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegate.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void trace(String message) {
            throw new UnsupportedOperationException("trace");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void warn(String message) {
            throw new UnsupportedOperationException("warn");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void warn(String message, Throwable t) {
            throw new UnsupportedOperationException("warn");
        }

    }

    private static final class UnmodifiableLoggerHandler implements LoggerHandler {

        private final LoggerHandler delegate;

        public UnmodifiableLoggerHandler(LoggerHandler delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(LoggerInfo e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends LoggerInfo> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addObserver(SetObserver<LoggerInfo> observer) {
            throw new UnsupportedOperationException("addObserver");
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
        public void clearObserver() {
            throw new UnsupportedOperationException("clearObserver");
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
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
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
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this || obj == delegate) {
                return true;
            }
            return delegate.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public LoggerInfo get(String key) {
            return delegate.get(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<SetObserver<LoggerInfo>> getObservers() {
            return delegate.getObservers();
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
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<LoggerInfo> iterator() {
            return delegate.iterator();
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
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
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
        public boolean removeKey(Object key) {
            throw new UnsupportedOperationException("removeKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeObserver(SetObserver<LoggerInfo> observer) {
            throw new UnsupportedOperationException("removeObserver");
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
        public boolean retainAllKey(Collection<?> c) {
            throw new UnsupportedOperationException("retainAllKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainUse(Collection<LoggerInfo> c) {
            throw new UnsupportedOperationException("retainUse");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainUseKey(Collection<String> c) {
            throw new UnsupportedOperationException("retainUseKey");
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
        public String toString() {
            return delegate.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuse(LoggerInfo loggerInfo) {
            throw new UnsupportedOperationException("unuse");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unuseAll() {
            throw new UnsupportedOperationException("unuseAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuseAll(Collection<LoggerInfo> c) {
            throw new UnsupportedOperationException("unuseAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuseAllKey(Collection<String> c) {
            throw new UnsupportedOperationException("unuseAllKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean unuseKey(String key) {
            throw new UnsupportedOperationException("unuseKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean use(LoggerInfo loggerInfo) {
            throw new UnsupportedOperationException("use");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void useAll() {
            throw new UnsupportedOperationException("useAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean useAll(Collection<LoggerInfo> c) {
            throw new UnsupportedOperationException("useAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean useAllKey(Collection<String> c) {
            throw new UnsupportedOperationException("useAllKey");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<Logger> usedLoggers() {
            return delegate.usedLoggers();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean useKey(String key) {
            throw new UnsupportedOperationException("useKey");
        }

    }

    private static final class UnmodifiableLoggerInfo implements LoggerInfo {

        private final LoggerInfo delegate;

        public UnmodifiableLoggerInfo(LoggerInfo delegate) {
            super();
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this || obj == delegate) {
                return true;
            }
            return delegate.equals(obj);
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
        public int hashCode() {
            return delegate.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Logger newLogger() {
            throw new UnsupportedOperationException("newLogger");
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
     * 根据指定的记录器处理器生成一个只读的记录器处理器。
     *
     * @param loggerHandler 指定的记录器处理器。
     * @return 根据指定的记录器处理器生成的只读的记录器处理器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static LoggerHandler readOnlyLoggerHandler(LoggerHandler loggerHandler) {
        Objects.requireNonNull(loggerHandler, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERUTIL_0));
        return readOnlyLoggerHandler(loggerHandler, LoggerUtil::unmodifiableLoggerInfo, LoggerUtil::unmodifiableLogger);
    }

    /**
     * 根据指定的记录器处理器以及指定的只读生成器生成一个只读的记录器处理器。
     *
     * @param loggerHandler 指定的记录器处理器。
     * @param loggerInfoGen 指定的记录器信息只读生成器。
     * @param loggerGen     指定的记录器只读生成器。
     * @return 根据指定的记录器处理器以及指定的只读生成器生成的只读的记录器处理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static LoggerHandler readOnlyLoggerHandler(LoggerHandler loggerHandler,
                                                      ReadOnlyGenerator<LoggerInfo> loggerInfoGen, ReadOnlyGenerator<Logger> loggerGen) {
        Objects.requireNonNull(loggerHandler, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERUTIL_0));
        Objects.requireNonNull(loggerInfoGen, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERUTIL_2));
        Objects.requireNonNull(loggerGen, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERUTIL_4));

        return new ReadOnlyLoggerHandler(loggerHandler, loggerInfoGen, loggerGen);
    }

    /**
     * 由指定的记录器处理器生成一个线程安全的记录器处理器。
     *
     * @param loggerHandler 指定的记录器处理器。
     * @return 由指定的记录器处理器生成的线程安全的记录器处理器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static SyncLoggerHandler syncLoggerHandler(LoggerHandler loggerHandler) {
        Objects.requireNonNull(loggerHandler, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERUTIL_0));
        return new SyncLoggerHandlerImpl(loggerHandler);
    }

    /**
     * 根据指定的记录器生成一个不可编辑的记录器。
     *
     * @param logger 指定的记录器。
     * @return 根据指定的记录器生成的不可编辑的记录器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Logger unmodifiableLogger(Logger logger) {
        Objects.requireNonNull(logger, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERUTIL_3));
        return new UnmodifiableLogger(logger);
    }

    /**
     * 根据指定的记录器处理器生成一个不可编辑的记录器处理器。
     *
     * @param loggerHandler 指定的记录器处理器。
     * @return 根据指定的记录器处理器生成的不可编辑的记录器处理器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static LoggerHandler unmodifiableLoggerHandler(LoggerHandler loggerHandler) {
        Objects.requireNonNull(loggerHandler, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERUTIL_0));
        return new UnmodifiableLoggerHandler(loggerHandler);
    }

    /**
     * 根据指定的记录器信息生成一个不可编辑的记录器信息。
     *
     * @param loggerInfo 指定的记录器信息。
     * @return 根据指定的记录器信息生成的不可编辑的记录器信息。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static LoggerInfo unmodifiableLoggerInfo(LoggerInfo loggerInfo) {
        Objects.requireNonNull(loggerInfo, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERUTIL_1));
        return new UnmodifiableLoggerInfo(loggerInfo);
    }

    // 禁止外部实例化。
    private LoggerUtil() {
    }
}
