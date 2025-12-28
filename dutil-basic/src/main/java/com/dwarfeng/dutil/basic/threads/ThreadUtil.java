package com.dwarfeng.dutil.basic.threads;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 有关线程安全的工具类。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.1.2-beta
 */
public final class ThreadUtil {

    /**
     * 如果读写同步，则锁定读锁。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @SuppressWarnings("deprecation")
    public static void readLockIfSync(Object object) {
        ThreadSafeUtil.readLockIfSync(object);
    }

    /**
     * 如果读写同步，则解锁读锁。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @SuppressWarnings("deprecation")
    public static void readUnlockIfSync(Object object) {
        ThreadSafeUtil.readUnlockIfSync(object);
    }

    /**
     * 如果读写同步，则锁定写锁。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @SuppressWarnings("deprecation")
    public static void writeLockIfSync(Object object) {
        ThreadSafeUtil.writeLockIfSync(object);
    }

    /**
     * 如果读写同步，则解锁写锁。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @SuppressWarnings("deprecation")
    public static void writeUnlockIfSync(Object object) {
        ThreadSafeUtil.writeUnlockIfSync(object);
    }

    /**
     * 如果同步，则锁定。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @SuppressWarnings("deprecation")
    public static void lockIfSync(Object object) {
        ThreadSafeUtil.lockIfSync(object);
    }

    /**
     * 如果同步，则解除锁定。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @SuppressWarnings("deprecation")
    public static void unlockIfSync(Object object) {
        ThreadSafeUtil.unlockIfSync(object);
    }

    /**
     * 根据指定的同步锁生成一个不可编辑的同步锁。
     *
     * @param lock 指定的同步锁。
     * @return 不可编辑的同步锁。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Lock unmodifiableLock(Lock lock) {
        Objects.requireNonNull(lock, DwarfUtil.getExceptionString(ExceptionStringKey.THREADUTIL_0));
        return new UnmodifiableLock(lock);
    }

    private static final class UnmodifiableLock implements Lock {

        private final Lock delegate;

        public UnmodifiableLock(Lock delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void lock() {
            throw new UnsupportedOperationException("lock");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void lockInterruptibly() {
            throw new UnsupportedOperationException("lockInterruptibly");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean tryLock() {
            throw new UnsupportedOperationException("tryLock");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean tryLock(long time, TimeUnit unit) {
            throw new UnsupportedOperationException("tryLock");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unlock() {
            throw new UnsupportedOperationException("unlock");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException("newCondition");
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
            if (delegate.equals(obj))
                return true;
            return super.equals(obj);
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
     * 由指定的读写锁生成一个不可更改的读写锁。
     *
     * @param lock 指定的读写锁。
     * @return 由指定的读写锁生成一个不可更改的读写锁。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static ReadWriteLock unmodifiableReadWriteLock(ReadWriteLock lock) {
        Objects.requireNonNull(lock, DwarfUtil.getExceptionString(ExceptionStringKey.THREADUTIL_0));
        return new UnmodifiableReadWriteLock(lock);
    }

    private static final class UnmodifiableReadWriteLock implements ReadWriteLock {

        private final ReadWriteLock delegate;

        public UnmodifiableReadWriteLock(ReadWriteLock delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Lock readLock() {
            return unmodifiableLock(delegate.readLock());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Lock writeLock() {
            return unmodifiableLock(delegate.writeLock());
        }

    }

    // 禁止外部实例化。
    private ThreadUtil() {
    }
}
