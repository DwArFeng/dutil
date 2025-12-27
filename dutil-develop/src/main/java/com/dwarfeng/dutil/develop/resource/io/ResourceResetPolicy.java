package com.dwarfeng.dutil.develop.resource.io;

/**
 * 资源重置策略。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public enum ResourceResetPolicy {

    /**
     * 从不重置资源。
     */
    NEVER,
    /**
     * 当资源不存在的时候，自动重置资源。
     */
    AUTO,
    /**
     * 不管资源是否存在，强行重置资源。
     */
    ALWAYS,
}
