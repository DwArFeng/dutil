package com.dwarfeng.dutil.develop.backgr;

/**
 * 有关后台的工具包。
 *
 * <p>
 * 该包中包含后台的常用方法。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 * @deprecated 由于该类不符合命名规范，已经由 {@link BackgroundUtil} 替。
 */
@Deprecated
public final class Backgrounds {

    /**
     * 从指定的 {@link Runnable} 中生成一个新的任务。
     *
     * @param runnable 指定的 {@link Runnable}。
     * @return 从指定的 {@link Runnable} 中生成的新任务。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Task newTaskFromRunnable(Runnable runnable) {
        return BackgroundUtil.newTaskFromRunnable(runnable);
    }

    private Backgrounds() {
    }
}
