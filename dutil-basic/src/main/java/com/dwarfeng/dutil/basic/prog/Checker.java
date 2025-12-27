package com.dwarfeng.dutil.basic.prog;

/**
 * 检查器。
 *
 * <p>
 * 用于检查指定的值是否合法。
 *
 * @author DwArFeng
 * @since 0.0.3-beta
 */
public interface Checker<T> {

    /**
     * 判断一个对象是否有效。
     *
     * @param obj 指定的对象。
     * @return 该对象是否有效。
     */
    boolean isValid(T obj);

    /**
     * 判断一个对象是否无效。
     *
     * @param obj 指定的对象。
     * @return 该对象是否无效。
     */
    default boolean nonValid(T obj) {
        return !isValid(obj);
    }
}
