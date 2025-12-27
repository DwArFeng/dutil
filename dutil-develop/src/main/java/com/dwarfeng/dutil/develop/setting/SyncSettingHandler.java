package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;

/**
 * 线程安全的配置处理器。
 *
 * <p>
 * 该配置处理器线程安全。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface SyncSettingHandler extends SettingHandler, ExternalReadWriteThreadSafe {
}
