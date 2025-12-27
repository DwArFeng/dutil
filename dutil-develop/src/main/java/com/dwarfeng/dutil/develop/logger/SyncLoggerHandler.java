package com.dwarfeng.dutil.develop.logger;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;

/**
 * 线程同步的记录器处理器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface SyncLoggerHandler extends LoggerHandler, ExternalReadWriteThreadSafe {
}
