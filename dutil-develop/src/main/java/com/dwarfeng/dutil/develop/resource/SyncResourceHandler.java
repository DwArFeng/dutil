package com.dwarfeng.dutil.develop.resource;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;

/**
 * 同步资源管理器。
 *
 * <p>
 * 该资源管理器是线程安全的。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public interface SyncResourceHandler extends ResourceHandler, ExternalReadWriteThreadSafe {
}
