package com.dwarfeng.dutil.develop.logger;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.KeySetModel;

import java.util.Collection;
import java.util.Objects;

/**
 * 记录器处理器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface LoggerHandler extends Logger, KeySetModel<String, LoggerInfo> {

    /**
     * 使用指定的记录器信息。
     *
     * <p>
     * 该方法将会调用该记录器信息的 {@link LoggerInfo#newLogger()} 法生成一个新的记录器，
     * 并持有该记录器，该记录器将会成为 {@link #usedLoggers()} 集合的一个元素。
     *
     * <p>
     * 当该记录器信息为 <code>null</code>，或该记录器信息不属于此记录器处理器时，处理器将不会执行任何动作。
     *
     * @param loggerInfo 指定的记录器信息。
     * @return 该操作是否改变了该记录器处理器。
     */
    boolean use(LoggerInfo loggerInfo);

    /**
     * 停止使用指定的信息记录器。
     *
     * <p>
     * 该方法将会使该记录器处理器搜索 {@link #usedLoggers()} 中的每一个记录器，
     * 并且将记录器对应的键与指定记录器信息对应的键比较， 如果两者的键一致， 就从 {@link #usedLoggers()} 中移除该记录器。
     *
     * <p>
     * 当该记录器信息为 <code>null</code>，或该记录器信息不属于此记录器处理器，或者 {@link #usedLoggers()}
     * 中没有对应键的记录器时，处理器将不会执行任何动作。
     *
     * @param loggerInfo 指定的记录器信息。
     * @return 该操作是否改变了该记录器处理器。
     */
    boolean unuse(LoggerInfo loggerInfo);

    /**
     * 使用指定的键对应的记录器信息。
     *
     * <p>
     * 该方法将在该记录器处理器中搜索指定键对应的记录器信息， 并调用该记录器信息的
     * {@link LoggerInfo#newLogger()} 法生成一个新的记录器， 并持有该记录器，该记录器将会成为
     * {@link #usedLoggers()} 集合的一个元素。
     *
     * <p>
     * 当该处理器中不包含该键对应的记录器信息， 或对应的键的记录器信息为 <code>null</code>， 或该记录器信息不属于此记录器处理器时，
     * 处理器将不会执行任何动作。
     *
     * @param key 指定的键。
     * @return 该操作是否改变了该记录器处理器。
     */
    boolean useKey(String key);

    /**
     * 停止使用指定的键对应的信息记录器。
     *
     * <p>
     * 该方法将会使该记录器处理器搜索 {@link #usedLoggers()} 中的每一个记录器，
     * 并且将记录器对应的键与指定记录器信息对应的键比较， 如果两者的键一致， 就从 {@link #usedLoggers()} 中移除该记录器。
     *
     * <p>
     * 当该处理器中不包含该键对应的记录器信息， 或该记录器信息为 <code>null</code>，或该记录器信息不属于此记录器处理器，或者
     * {@link #usedLoggers()} 中没有对应键的记录器时，处理器将不会执行任何动作。
     *
     * @param key 指定的键。
     * @return 该操作是否改变了该记录器处理器。
     */
    boolean unuseKey(String key);

    /**
     * 使用该记录器处理器中的所有记录器。
     */
    void useAll();

    /**
     * 停止使用该记录器处理器中的所有记录器。
     */
    void unuseAll();

    /**
     * 使用指定集合中的所有记录器信息。
     *
     * <p>
     * 该方法将会遍历指定的集合，并尝试对其中所有的记录器信息使用 {@link #use(LoggerInfo)} 法。
     *
     * @param c 指定的记录器信息集合。
     * @return 该操作是否改变了该记录器处理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    boolean useAll(Collection<LoggerInfo> c);

    /**
     * 停止使用指定集合中的所有记录器信息。
     *
     * <p>
     * 该方法将会遍历 {@link #usedLoggers()}， 并将其中指定的键等于属于指定集合中记录器信息的键的记录器移除。
     *
     * @param c 指定的记录器集合。
     * @return 该操作是否改变了该记录器处理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    boolean unuseAll(Collection<LoggerInfo> c);

    /**
     * 使用指定集合中的所有键对应的记录器信息。
     *
     * <p>
     * 该方法将会遍历指定的集合，并尝试对其中所有的键使用 {@link #useKey(String)} 法。
     *
     * @param c 指定的记录器键集合。
     * @return 该操作是否改变了该记录器处理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    boolean useAllKey(Collection<String> c);

    /**
     * 停止使用指定集合中所有的键对应的记录器信息。
     *
     * <p>
     * 该方法将会遍历 {@link #usedLoggers()}， 并将其中指定的键等于属于指定集合中记录器信息的键的记录器移除。
     *
     * @param c 指定的记录器键集合。
     * @return 该操作是否改变了该记录器处理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    boolean unuseAllKey(Collection<String> c);

    /**
     * 停止使用除指定集合中的记录器信息以外的所有记录器信息。
     *
     * <p>
     * 该方法会遍历 {@link #usedLoggers()}， 并将其中指定的键不等于属于指定集合中所有记录器信息的键的记录器移除。
     *
     * @param c 指定的记录器集合。
     * @return 该操作是否改变了该记录器处理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    boolean retainUse(Collection<LoggerInfo> c);

    /**
     * 停止使用除指定集合中的键对应的记录器信息以外的所有记录器信息。
     *
     * <p>
     * 该方法会遍历 {@link #usedLoggers()}， 并将其中指定的键不等于属于指定集合中所有记录器信息的键的记录器移除。
     *
     * @param c 指定的记录器键集合。
     * @return 该操作是否改变了该记录器处理器。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    boolean retainUseKey(Collection<String> c);

    /**
     * 获取该记录器处理中中所有正在使用的 <code>Logger</code>。
     *
     * @return 该记录器处理中中所有正在使用的 <code>Logger</code>。
     */
    Collection<Logger> usedLoggers();

    /**
     * 向正在使用的记录器 <code>trace</code> 一条信息。
     *
     * @param message 指定的信息。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @Override
    default void trace(String message) {
        Objects.requireNonNull(message, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERHANDLER_0));

        usedLoggers().forEach(logger -> logger.trace(message));
    }

    /**
     * 向正在使用的记录器 <code>debug</code> 一条信息。
     *
     * @param message 指定的信息。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @Override
    default void debug(String message) {
        Objects.requireNonNull(message, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERHANDLER_0));

        usedLoggers().forEach(logger -> {
            if (Objects.nonNull(logger)) {
                logger.debug(message);
            }
        });
    }

    /**
     * 向正在使用的记录器 <code>info</code> 一条信息。
     *
     * @param message 指定的信息。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @Override
    default void info(String message) {
        Objects.requireNonNull(message, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERHANDLER_0));

        usedLoggers().forEach(logger -> {
            if (Objects.nonNull(logger)) {
                logger.info(message);
            }
        });
    }

    /**
     * 向正在使用的记录器 <code>warn</code> 一条信息。
     *
     * @param message 指定的信息。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @Override
    default void warn(String message) {
        Objects.requireNonNull(message, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERHANDLER_0));

        usedLoggers().forEach(logger -> {
            if (Objects.nonNull(logger)) {
                logger.warn(message);
            }
        });
    }

    /**
     * 向正在使用的记录器 <code>warn</code> 一条信息。
     *
     * @param message 指定的信息。
     * @param t       指定的可抛出对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @Override
    default void warn(String message, Throwable t) {
        Objects.requireNonNull(message, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERHANDLER_0));
        Objects.requireNonNull(t, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERHANDLER_1));

        usedLoggers().forEach(logger -> {
            if (Objects.nonNull(logger)) {
                logger.warn(message, t);
            }
        });
    }

    /**
     * 向正在使用的记录器 <code>error</code> 一条信息。
     *
     * @param message 指定的信息。
     * @param t       指定的可抛出对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @Override
    default void error(String message, Throwable t) {
        Objects.requireNonNull(message, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERHANDLER_0));
        Objects.requireNonNull(t, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERHANDLER_1));

        usedLoggers().forEach(logger -> {
            if (Objects.nonNull(logger)) {
                logger.error(message, t);
            }
        });
    }

    /**
     * 向正在使用的记录器 <code>fatal</code> 一条信息。
     *
     * @param message 指定的信息。
     * @param t       指定的可抛出对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    @Override
    default void fatal(String message, Throwable t) {
        Objects.requireNonNull(message, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERHANDLER_0));
        Objects.requireNonNull(t, DwarfUtil.getExceptionString(ExceptionStringKey.LOGGERHANDLER_1));

        usedLoggers().forEach(logger -> {
            if (Objects.nonNull(logger)) {
                logger.fatal(message, t);
            }
        });
    }
}
