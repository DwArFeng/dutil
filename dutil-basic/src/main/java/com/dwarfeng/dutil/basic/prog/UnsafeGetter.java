package com.dwarfeng.dutil.basic.prog;

/**
 * 不安全 Getter。
 *
 * <p>
 * 不安全 <code>Getter</code> 能够不安全地提供了一个<code>T</code>类型的对象，提供方法可能会抛出异常。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface UnsafeGetter<T> {

    /**
     * 获取 Getter 中的对象。
     *
     * @return Getter 中的对象。
     * @throws ProcessException 过程异常。
     */
    T get() throws ProcessException;
}
