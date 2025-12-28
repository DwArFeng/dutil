package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 抽象集合模型。
 *
 * <p>
 * 集合模型的抽象实现。
 *
 * <p>
 * 该类实现了模型中侦听器注册与移除的方法，以及通知侦听器的方法。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public abstract class AbstractSetModel<E> implements SetModel<E> {

    /**
     * 抽象集合模型的侦听器集合。
     */
    protected final Set<SetObserver<E>> observers;

    /**
     * 生成一个默认的抽象集合模型。
     */
    public AbstractSetModel() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定的侦听器集合的的抽象集合模型。
     *
     * @param observers 指定的侦听器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public AbstractSetModel(Set<SetObserver<E>> observers) {
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTSETMODEL_0));
        this.observers = observers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SetObserver<E>> getObservers() {
        return Collections.unmodifiableSet(observers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addObserver(SetObserver<E> observer) {
        return observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(SetObserver<E> observer) {
        return observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearObserver() {
        observers.clear();
    }

    /**
     * 通知观察器该模型添加了指定的元素。
     *
     * @param element 指定的元素。
     */
    protected void fireAdded(E element) {
        for (SetObserver<E> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireAdded(element);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器该模型移除了指定的元素。
     *
     * @param element 指定的元素。
     */
    // 由于早期开发未使用日志框架，故保留 printStackTrace 方法，忽略相关警告。
    @SuppressWarnings("CallToPrintStackTrace")
    protected void fireRemoved(E element) {
        for (SetObserver<E> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireRemoved(element);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知观察器该模型清除了元素。
     */
    // 由于早期开发未使用日志框架，故保留 printStackTrace 方法，忽略相关警告。
    @SuppressWarnings("CallToPrintStackTrace")
    protected void fireCleared() {
        for (SetObserver<E> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireCleared();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
