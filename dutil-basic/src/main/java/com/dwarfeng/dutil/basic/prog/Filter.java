package com.dwarfeng.dutil.basic.prog;

/**
 * 筛选器接口。
 *
 * <p>
 * 筛选器接提供是否接受某个指定对象的方法。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface Filter<T> {

    /**
     * 是否接受指定的对象。
     *
     * @param t 指定的对象。
     * @return 是否接受指定的对象。
     */
    boolean accept(T t);
}
