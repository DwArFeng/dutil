package com.dwarfeng.dutil.basic.prog;

/**
 * 运行时状态。
 *
 * <p>
 * 指示程序的运行状态的枚举。
 *
 * @author DwArFeng
 * @since 0.0.3-beta
 */
public enum RuntimeState {

    /**
     * 程序还未启动
     */
    NOT_START,
    /**
     * 程序正在运行
     */
    RUNNING,
    /**
     * 程序已经结束
     */
    ENDED,
}
