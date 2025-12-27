package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.obs.ReferenceObserver;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 抽象引用模型。
 *
 * <p>
 * 引用模型的抽象实现。
 *
 * <p>
 * 该类实现了模型中侦听器注册与移除的方法，以及通知侦听器的方法。
 *
 * @author DwArFeng
 * @since 0.3.0-beta
 */
public abstract class AbstractReferenceModel<E> implements ReferenceModel<E> {

    /**
     * 抽象列表模型的侦听器集合。
     */
    protected final Set<ReferenceObserver<E>> observers;

    /**
     * 生成一个默认的抽象引用模型。
     */
    public AbstractReferenceModel() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定的侦听器集合的的抽象引用模型。
     *
     * @param observers 指定的侦听器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public AbstractReferenceModel(Set<ReferenceObserver<E>> observers) {
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTREFERENCEMODEL_0));
        this.observers = observers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ReferenceObserver<E>> getObservers() {
        return Collections.unmodifiableSet(observers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addObserver(ReferenceObserver<E> observer) {
        return observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(ReferenceObserver<E> observer) {
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
     * 通知模型中的元素发生了改变。
     *
     * @param oldValue 旧的值。
     * @param newValue 新的值。
     */
    protected void fireSet(E oldValue, E newValue) {
        for (ReferenceObserver<E> observer : observers) {
            try {
                observer.fireSet(oldValue, newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通知模型中的元素被清除。
     */
    protected void fireCleared() {
        for (ReferenceObserver<E> observer : observers) {
            try {
                observer.fireCleared();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
