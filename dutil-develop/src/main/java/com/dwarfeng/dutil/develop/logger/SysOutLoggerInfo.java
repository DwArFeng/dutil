package com.dwarfeng.dutil.develop.logger;

/**
 * 系统输出流记录器信息。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class SysOutLoggerInfo extends AbstractLoggerInfo {

    /**
     * 生成的系统输出流记录器是否自动更新。
     */
    protected final boolean autoUpdate;

    /**
     * 生成一个键值为指定值的系统输出流记录器信息。
     *
     * @param key 指定的键值。
     */
    public SysOutLoggerInfo(String key) {
        this(key, false);
    }

    /**
     * 生成一个键值为指定值，指定是否自动更新的系统输出流记录器信息。
     *
     * @param key        指定的键值。
     * @param autoUpdate 是否自动更新。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public SysOutLoggerInfo(String key, boolean autoUpdate) {
        super(key);
        this.autoUpdate = autoUpdate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Logger newLogger() throws Exception {
        return new SysOutLogger(autoUpdate);
    }

    /**
     * 指示生成的新系统输出流记录器是否自动更新。
     *
     * @return 生成的新系统输出流记录器是否自动更新。
     */
    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SysOutLoggerInfo [autoUpdate=" + autoUpdate + "]";
    }
}
