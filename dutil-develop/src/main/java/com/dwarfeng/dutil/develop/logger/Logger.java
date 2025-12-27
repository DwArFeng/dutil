package com.dwarfeng.dutil.develop.logger;

/**
 * 记录器。
 *
 * <p>
 * 用来处理与记录有关的方法。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface Logger {

    /**
     * 调用记录站点的 trace 方法。
     *
     * @param message 指定的信息。
     */
    void trace(String message);

    /**
     * 调用记录站点的 debug 方法。
     *
     * @param message 指定的信息。
     */
    void debug(String message);

    /**
     * 调用记录站点的 info 方法。
     *
     * @param message 指定的信息。
     */
    void info(String message);

    /**
     * 调用记录站点的 warn 方法。
     *
     * @param message 指定的信息。
     */
    void warn(String message);

    /**
     * 调用记录站点的 warn 方法。
     *
     * @param message 指定的信息。
     * @param t       指定的可抛出对象，一般是线程或异常的跟踪堆栈。
     */
    void warn(String message, Throwable t);

    /**
     * 调用记录站点的 error 方法。
     *
     * @param message 指定的信息。
     * @param t       指定的可抛出对象，一般是线程或异常的跟踪堆栈。
     */
    void error(String message, Throwable t);

    /**
     * 调用记录站点的 fatal 方法。
     *
     * @param message 指定的信息。
     * @param t       指定的可抛出对象，一般是线程或异常的跟踪堆栈。
     */
    void fatal(String message, Throwable t);
}
