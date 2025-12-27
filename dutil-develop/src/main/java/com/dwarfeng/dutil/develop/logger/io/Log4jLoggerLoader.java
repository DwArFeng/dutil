package com.dwarfeng.dutil.develop.logger.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.dutil.develop.logger.Log4jLoggerInfo;
import com.dwarfeng.dutil.develop.logger.LoggerHandler;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Log4j 记录处理器读取器。
 *
 * <p>
 * 通过读取符合 <code>log4j</code> 格式的配置文件向指定的记录处理器中读取记录器信息。
 *
 * <p>
 * 配置文件的具体格式请参照
 * <a href="http:// logging.apache.org/log4j/2.x/manual/configuration.html">
 * log4j 配置手册 </a>
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public final class Log4jLoggerLoader extends StreamLoader<LoggerHandler> {

    private boolean readFlag = false;

    /**
     * 新实例。
     *
     * @param in 指定的输入流。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public Log4jLoggerLoader(InputStream in) {
        super(in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(LoggerHandler loggerHandler) throws LoadFailedException, IllegalStateException {
        if (readFlag)
            throw new IllegalStateException("该读取器已经使用过了");
        Objects.requireNonNull(loggerHandler, "入口参数 loggerHandler 不能为 null。");

        readFlag = true;
        try {
            ConfigurationSource cs = new ConfigurationSource(in);
            LoggerContext loggerContext = Configurator.initialize(null, cs);
            Configuration cfg = loggerContext.getConfiguration();
            Set<String> loggerNames = cfg.getLoggers().keySet();

            for (String loggerName : loggerNames) {
                loggerHandler.add(new Log4jLoggerInfo(loggerName, loggerContext));
            }
        } catch (Exception e) {
            throw new LoadFailedException("无法向指定的记录器模型中读取流中的数据", e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<LoadFailedException> countinuousLoad(LoggerHandler loggerHandler) throws IllegalStateException {
        if (readFlag)
            throw new IllegalStateException("该读取器已经使用过了");
        Objects.requireNonNull(loggerHandler, "入口参数 loggerHandler 不能为 null。");

        readFlag = true;
        Set<LoadFailedException> exceptions = new LinkedHashSet<>();
        try {
            ConfigurationSource cs = new ConfigurationSource(in);
            LoggerContext loggerContext = Configurator.initialize(null, cs);
            Configuration cfg = loggerContext.getConfiguration();
            Set<String> loggerNames = cfg.getLoggers().keySet();

            for (String loggerName : loggerNames) {
                loggerHandler.add(new Log4jLoggerInfo(loggerName, loggerContext));
            }
        } catch (Exception e) {
            exceptions.add(new LoadFailedException("无法向指定的记录器模型中读取流中的数据", e));
        }
        return exceptions;
    }
}
