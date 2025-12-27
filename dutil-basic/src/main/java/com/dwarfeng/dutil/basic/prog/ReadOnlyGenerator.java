package com.dwarfeng.dutil.basic.prog;

/**
 * 只读生成器。
 *
 * <p>
 * 该接口提供一个方法：将指定的对象 <code>T</code>转换为只读对象。
 *
 * <p>
 * 该接口是集合（数组）工具返回只读集合（数组）的重要元素。
 *
 * <p>
 * 什么是只读集合（数组）？ <blockquote> 只读集本身是集合（数组）的一种，它的特性是只读，并且不能做任何更改；<br>
 * 区别于不可编辑（unmodifiable），不可编辑集合（数组）不能对集合（数组）本身调用编辑方法， 但是可以对从集合（数组）中查询来的元素调用编辑方法；
 * 只读集合（数组）不仅仅不能对集合（数组）本身调用编辑方法， 也不能对通过查询集合（数组）得到的元素调用编辑方法。</blockquote>
 *
 * @author DwArFeng
 * @since 0.1.3-beta
 */
public interface ReadOnlyGenerator<T> {

    /**
     * 获取对象 <code>t</code> 的只读版本。
     *
     * @param t 指定的对象。
     * @return 对象的只读版本。
     */
    T readOnly(T t);
}
