package com.dwarfeng.dutil.basic.prog;

import java.util.Set;

/**
 * 观察器集合。
 *
 * <p>
 * 实现该接口意味着能够向其中添加、移除、清除观察器，也可以遍历集合中的所有观察器。<br>
 * 观察器常常用在 mvc 模型中。模型层常常通过遍历在其注册的观察器来广播一系列事件。而该接口则定义了一个观察器的集合应该拥有的方法。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public interface ObserverSet<T extends Observer> {

    /**
     * 以集合的形式返回该观察器集合。
     *
     * <p>
     * 该集合为只读集合，调用任何修改方法将会抛出 {@link UnsupportedOperationException}。
     *
     * @return 集合形式的观察器集合。
     */
    Set<T> getObservers();

    /**
     * 向属性行模型中添加观察器（可选方法）。
     *
     * @param observer 指定的观察器。
     * @return 该动作是否引起了观察器集合的改变。
     * @throws UnsupportedOperationException 不支持该操作。
     */
    boolean addObserver(T observer) throws UnsupportedOperationException;

    /**
     * 从属性行模型中移除观察器（可选方法）。
     *
     * @param observer 指定的观察器。
     * @return 该动作是否引起了观察器集合的改变。
     * @throws UnsupportedOperationException 不支持该操作。
     */
    boolean removeObserver(T observer) throws UnsupportedOperationException;

    /**
     * 从属性行模型中移除所有观察器（可选方法）。
     *
     * @throws UnsupportedOperationException 不支持该操作。
     */
    void clearObserver() throws UnsupportedOperationException;
}
