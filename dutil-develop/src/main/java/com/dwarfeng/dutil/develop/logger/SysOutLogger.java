package com.dwarfeng.dutil.develop.logger;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通过系统输出流进行记录的记录器。
 *
 * <p>
 * 该记录器通过向当前的或者是过去的系统输出流输出记录数据进行记录。通过入口参数 <code>autoUpdate</code>
 * 来指定该记录器是否将输出流更新到最新的系统输出流。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class SysOutLogger implements Logger {

    /**
     * 是否更新到最新的系统输出流。
     */
    protected final boolean autoUpdate;

    private PrintStream sysOut;

    public SysOutLogger() {
        this(false);
    }

    public SysOutLogger(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
        sysOut = System.out;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(String message) {
        autoUpdate();
        sysOut.printf("%s\t[DEBUG]\t%s%n", formatDate(), message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(String message, Throwable t) {
        autoUpdate();
        sysOut.printf("%s\t[ERROR]\t%s%n", formatDate(), message);
        t.printStackTrace(sysOut);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatal(String message, Throwable t) {
        autoUpdate();
        sysOut.printf("%s\t[FATAL]\t%s%n", formatDate(), message);
        t.printStackTrace(sysOut);
    }

    /**
     * 获取当前记录器正在使用的系统输出流。
     *
     * @return 当前记录器正在使用的系统输出流。
     */
    public PrintStream getSysOut() {
        return sysOut;
    }

    /**
     * 获取当前的系统输出流记录器是否自动更新。
     *
     * @return the autoUpdate 是否自动更新。
     */
    public boolean isAutoUpdate() {
        return autoUpdate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(String message) {
        autoUpdate();
        sysOut.printf("%s\t[INFO]\t%s%n", formatDate(), message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(String message) {
        autoUpdate();
        sysOut.printf("%s\t[TRACE]\t%s%n", formatDate(), message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String message) {
        autoUpdate();
        sysOut.printf("%s\t[WARN]\t%s%n", formatDate(), message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String message, Throwable t) {
        autoUpdate();
        sysOut.printf("%s\t[WARN]\t%s%n", formatDate(), message);
        t.printStackTrace(sysOut);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SysOutLogger [autoUpdate=" + autoUpdate + ", sysOut=" + sysOut + "]";
    }

    private void autoUpdate() {
        if (autoUpdate) {
            sysOut = System.out;
        }
    }

    private String formatDate() {
        DateFormat formatter = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss,SSS]");
        return formatter.format(new Date());
    }
}
