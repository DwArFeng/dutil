package com.dwarfeng.dutil.basic.threads;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Objects;

/**
 * 有关线程安全的工具类。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.1.2-beta
 * @deprecated 该类已经由 {@link ThreadUtil} 替。
 */
public final class ThreadSafeUtil {

    /**
     * 如果读写同步，则锁定读锁。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void readLockIfSync(Object object) {
        Objects.requireNonNull(object, DwarfUtil.getExceptionString(ExceptionStringKey.THREADSAFEUTIL_0));

        if (object instanceof ExternalReadWriteThreadSafe) {
            ((ExternalReadWriteThreadSafe) object).getLock().readLock().lock();
        }
    }

    /**
     * 如果读写同步，则解锁读锁。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void readUnlockIfSync(Object object) {
        Objects.requireNonNull(object, DwarfUtil.getExceptionString(ExceptionStringKey.THREADSAFEUTIL_0));

        if (object instanceof ExternalReadWriteThreadSafe) {
            ((ExternalReadWriteThreadSafe) object).getLock().readLock().unlock();
        }
    }

    /**
     * 如果读写同步，则锁定写锁。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void writeLockIfSync(Object object) {
        Objects.requireNonNull(object, DwarfUtil.getExceptionString(ExceptionStringKey.THREADSAFEUTIL_0));

        if (object instanceof ExternalReadWriteThreadSafe) {
            ((ExternalReadWriteThreadSafe) object).getLock().writeLock().lock();
        }
    }

    /**
     * 如果读写同步，则解锁写锁。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void writeUnlockIfSync(Object object) {
        Objects.requireNonNull(object, DwarfUtil.getExceptionString(ExceptionStringKey.THREADSAFEUTIL_0));

        if (object instanceof ExternalReadWriteThreadSafe) {
            ((ExternalReadWriteThreadSafe) object).getLock().writeLock().unlock();
        }
    }

    /**
     * 如果同步，则锁定。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void lockIfSync(Object object) {
        Objects.requireNonNull(object, DwarfUtil.getExceptionString(ExceptionStringKey.THREADSAFEUTIL_0));

        if (object instanceof ExternalThreadSafe) {
            ((ExternalThreadSafe) object).getLock().lock();
        }
    }

    /**
     * 如果同步，则解除锁定。
     *
     * @param object 指定的对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void unlockIfSync(Object object) {
        Objects.requireNonNull(object, DwarfUtil.getExceptionString(ExceptionStringKey.THREADSAFEUTIL_0));

        if (object instanceof ExternalThreadSafe) {
            ((ExternalThreadSafe) object).getLock().unlock();
        }
    }

    // 禁止外部实例化。
    private ThreadSafeUtil() {
    }
}
