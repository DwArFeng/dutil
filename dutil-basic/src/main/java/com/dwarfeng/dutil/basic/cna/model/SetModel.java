package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;
import com.dwarfeng.dutil.basic.prog.ObserverSet;

import java.util.Set;

/**
 * 集合模型。
 *
 * <p>
 * 该模型是一个集合数据模型，集合模型可以存放若干个不相等的元素，不保证元素的顺序。 该模型是 {@link Set} 的一个实现。
 *
 * <p>
 * 当列表中的元素被增加、删除、清空的时候，会将必要的信息提供给注册在模型上的侦听器集合。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface SetModel<E> extends Set<E>, ObserverSet<SetObserver<E>> {
}
