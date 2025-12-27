package com.dwarfeng.dutil.basic.prog;

/**
 * 构造器抽象的结构，拥有一个对 <code>T</code> 的构造方法。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface Buildable<T> {

    /**
     * 构造新的 T 实例。
     *
     * @return 新的实例。
     */
    T build();
}
