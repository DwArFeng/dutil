package com.dwarfeng.dutil.basic.gui.swing;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Objects;

/**
 * swing 工具包。
 *
 * <p>
 * 该包中包含关于对 swing 组件进行操作的常用方法。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class SwingUtil {

    /**
     * 设置指定的文件选择器的语言环境。
     *
     * <p>
     * 允许参数 <code>locale</code>为 <code>null</code>。
     *
     * @param fileChooser 指定的文件选择器。
     * @param locale      指定的语言环境。
     * @throws NullPointerException 入口参数 <code>fileChooser</code> 为 <code>null</code>。
     */
    public static void setJFileChooserLocal(JFileChooser fileChooser, Locale locale) {
        Objects.requireNonNull(fileChooser, DwarfUtil.getExceptionString(ExceptionStringKey.SWINGUTIL_0));
        fileChooser.setLocale(locale);
        fileChooser.updateUI();
    }

    /**
     * 向事件队列中添加一个指定的可运行对象。
     *
     * @param runnable 指定的可运行对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void invokeInEventQueue(Runnable runnable) {
        Objects.requireNonNull(runnable, DwarfUtil.getExceptionString(ExceptionStringKey.SWINGUTIL_1));

        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeLater(runnable);
        }
    }

    /**
     * 向实践队列中添加一个指定的可运行对象。
     *
     * <p>
     * 在指定的可运行对象运行结束之前，当前线程将处于阻塞状态。
     *
     * @param runnable 指定的可运行对象。
     * @throws NullPointerException      入口参数为 <code>null</code>。
     * @throws InvocationTargetException <code>runnable</code> 运行时抛出异常。
     * @throws InterruptedException      如果等待事件指派线程执完成执行 <code>runnable.run()</code>时被中断
     */
    public static void invokeAndWaitInEventQueue(Runnable runnable)
            throws InvocationTargetException, InterruptedException {
        Objects.requireNonNull(runnable, DwarfUtil.getExceptionString(ExceptionStringKey.SWINGUTIL_1));

        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeAndWait(runnable);
        }
    }

    // 禁止外部实例化。
    private SwingUtil() {
    }
}
