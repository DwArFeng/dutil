package com.dwarfeng.dutil.develop.reuse;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;

/**
 * 线程安全的复用池。
 *
 * @author DwArFeng
 * @since 0.2.1-beta
 */
public interface SyncReusePool<E> extends ReusePool<E>, ExternalReadWriteThreadSafe {
}
