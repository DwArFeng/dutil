package com.dwarfeng.dutil.develop.logger;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import org.apache.logging.log4j.core.LoggerContext;

import java.util.Objects;

/**
 * 通过记录 log4j 有关的信息实现的记录器信息。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class Log4jLoggerInfo extends AbstractLoggerInfo {

    private final LoggerContext loggerContext;

    /**
     * 生成一个由指定的键值信息，指定的 <code>LoggerContext</code> 组成的记录器信息。
     *
     * @param key           指定的键值信息。
     * @param loggerContext 指定的记录器上下文。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public Log4jLoggerInfo(String key, LoggerContext loggerContext) {
        super(key);

        Objects.requireNonNull(loggerContext, DwarfUtil.getExceptionString(ExceptionStringKey.LOG4JLOGGERINFO_0));
        this.loggerContext = loggerContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Logger newLogger() {
        return new Log4jLogger(loggerContext.getLogger(getKey()));
    }
}
