package com.dwarfeng.dutil.develop.logger.obs;

import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;
import com.dwarfeng.dutil.develop.logger.Logger;
import com.dwarfeng.dutil.develop.logger.LoggerInfo;

/**
 * 记录器观察器。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public interface LoggerObserver extends SetObserver<LoggerInfo> {

    /**
     * 通知观察器记录器处理器禁用了指定的记录器。
     *
     * @param key        相关记录器信息的键。
     * @param loggerInfo 相关的记录器信息。
     * @param logger     禁用的记录器。
     */
    void fireLoggerUnused(String key, LoggerInfo loggerInfo, Logger logger);

    /**
     * 通知观察器记录器处理器使用了指定的记录器。
     *
     * @param key        相关记录器信息的键。
     * @param loggerInfo 相关的记录器信息。
     * @param logger     使用的记录器。
     */
    void fireLoggerUsed(String key, LoggerInfo loggerInfo, Logger logger);
}
