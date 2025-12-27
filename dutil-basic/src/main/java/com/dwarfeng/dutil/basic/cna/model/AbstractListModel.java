package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.obs.ListObserver;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 抽象列表模型。
 *
 * <p>
 * 列表模型的抽象实现。
 *
 * <p>
 * 该类实现了模型中侦听器注册与移除的方法，以及通知侦听器的方法。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public abstract class AbstractListModel<E> implements ListModel<E> {

    /**
     * 抽象列表模型的侦听器集合。
     */
    protected final Set<ListObserver<E>> observers;

    /**
     * 生成一个默认的抽象列表模型。
     */
    public AbstractListModel() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定的侦听器集合的的抽象列表模型。
     *
     * @param observers 指定的侦听器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public AbstractListModel(Set<ListObserver<E>> observers) {
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTLISTMODEL_0));
        this.observers = observers;
    }

    @Override
    public Set<ListObserver<E>> getObservers() {
        return Collections.unmodifiableSet(observers);
    }

    @Override
    public boolean addObserver(ListObserver<E> observer) {
        return observers.add(observer);
    }

    @Override
    public boolean removeObserver(ListObserver<E> observer) {
        return observers.remove(observer);
    }

    @Override
    public void clearObserver() {
        observers.clear();
    }

    /**
     * 通知观察器该模型在指定的位置添加了指定的元素。
     *
     * @param index   指定的位置。
     * @param element 指定的的元素。
     */
    protected void fireAdded(int index, E element) {
        for (ListObserver<E> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireAdded(index, element);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器该模型在指定的位置移除了元素。
     *
     * @param index   指定的位置。
     * @param element 被移除的元素。
     */
    protected void fireRemoved(int index, E element) {
        for (ListObserver<E> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireRemoved(index, element);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器该模型指定位置处的元素发生了改变。
     *
     * @param index      指定的位置。
     * @param oldElement 改变前的旧元素。
     * @param newElement 改变后的新元素。
     */
    protected void fireChanged(int index, E oldElement, E newElement) {
        for (ListObserver<E> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireChanged(index, oldElement, newElement);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器该模型移除了所有的元素。
     */
    protected void fireCleared() {
        for (ListObserver<E> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireCleared();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
