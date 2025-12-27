package com.dwarfeng.dutil.develop.logger;

/**
 * 抽象记录器信息。
 *
 * <p>
 * 记录器信息的抽象实现。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public abstract class AbstractLoggerInfo implements LoggerInfo {

    /**
     * 该记录器信息的键。
     */
    protected final String key;

    /**
     * 生成一个指定键值的新的抽象记录器模型。
     *
     * @param key 指定的键值。
     */
    public AbstractLoggerInfo(String key) {
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LoggerInfo [key=" + key + "]";
    }
}
