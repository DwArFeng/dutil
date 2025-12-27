package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.cna.model.obs.ListObserver;
import com.dwarfeng.dutil.basic.prog.ObserverSet;

import java.util.List;

/**
 * 列表模型。
 *
 * <p>
 * 该模型是一个列表数据模型，所有元素在列表具有一个确切的序号，可以通过查询序号获得。 该模型是 {@link List} 的一个实现。
 *
 * <p>
 * 当列表中的元素被增加、删除、改变、清空的时候，会将必要的信息提供给注册在模型上的侦听器集合。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface ListModel<E> extends List<E>, ObserverSet<ListObserver<E>> {
}
