package com.dwarfeng.dutil.basic.threads;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 外部读写线程安全接口。
 *
 * <p>
 * 实现该接口意味着实现类是线程安全的，并且能够从外部控制此对象的同步读写锁。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface ExternalReadWriteThreadSafe {

    /**
     * 获取该外部读写线程安全对象中的同步读写锁。
     *
     * @return 此外部线程安全对象中的同步读写锁。
     */
    ReadWriteLock getLock();
}
