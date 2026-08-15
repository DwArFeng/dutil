package com.dwarfeng.dutil.basic.sdk.concurrent;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;

/**
 * 编号线程工厂。
 *
 * <p>
 * 该线程工厂提供流水编号的线程，其内部维护着一个编号。 当新的线程被请求时，该工厂返回的线程的名称由编号和前缀组成。
 *
 * @author DwArFeng
 * @since 0.0.3-beta
 */
public final class NumberedThreadFactory implements ThreadFactory {

    private final ThreadFactory delegate;

    /**
     * 生成一个默认的编号线程。
     *
     * <p>
     * 线程不是守护线程，且具有标准的优先级。
     *
     * @param prefix 指定的前缀。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public NumberedThreadFactory(String prefix) {
        this(prefix, false, Thread.NORM_PRIORITY);
    }

    /**
     * 生成一个具有指定前缀，指定守护线程标志，指定优先级的编号线程工厂。
     *
     * @param prefix     指定的前缀。
     * @param daemonFlag 指定的守护线程标志。
     * @param priority   指定的优先级。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public NumberedThreadFactory(String prefix, boolean daemonFlag, int priority) {
        Objects.requireNonNull(prefix, BasicMessages.message(BasicMessageKey.NUMBERED_THREAD_FACTORY_PREFIX_REQUIRED));
        delegate = Thread.ofPlatform()
                .name(prefix + "-", 1)
                .daemon(daemonFlag)
                .priority(priority)
                .factory();
    }

    private NumberedThreadFactory(ThreadFactory delegate) {
        this.delegate = delegate;
    }

    /**
     * 创建使用虚拟线程的编号线程工厂。
     *
     * @param prefix 线程名称前缀。
     * @return 虚拟线程工厂。
     */
    public static NumberedThreadFactory virtual(String prefix) {
        Objects.requireNonNull(prefix, BasicMessages.message(BasicMessageKey.NUMBERED_THREAD_FACTORY_PREFIX_REQUIRED));
        return new NumberedThreadFactory(Thread.ofVirtual().name(prefix + "-", 1).factory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Thread newThread(@NotNull Runnable r) {
        return delegate.newThread(Objects.requireNonNull(r, "r"));
    }
}
