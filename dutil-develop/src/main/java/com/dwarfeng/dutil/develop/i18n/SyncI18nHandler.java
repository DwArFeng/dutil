package com.dwarfeng.dutil.develop.i18n;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;

/**
 * 线程安全的国际化处理器。
 *
 * <p>
 * 该国际化处理器线程安全。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public interface SyncI18nHandler extends I18nHandler, ExternalReadWriteThreadSafe {
}
