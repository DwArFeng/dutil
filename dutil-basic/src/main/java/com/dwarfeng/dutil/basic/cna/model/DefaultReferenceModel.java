package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.cna.model.obs.ReferenceObserver;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 默认引用模型。
 *
 * <p>
 * 引用模型的默认实现。
 *
 * @author DwArFeng
 * @since 0.1.5-beta
 */
public class DefaultReferenceModel<E> extends AbstractReferenceModel<E> implements ReferenceModel<E> {

    /**
     * 引用模型中的元素。
     */
    protected E value;

    /**
     * 生成一个默认的引用模型。
     */
    public DefaultReferenceModel() {
        this(null, Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定初始值的引用模型。
     *
     * @param initialValue 指定的初始值。
     */
    public DefaultReferenceModel(E initialValue) {
        this(initialValue, Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定初始值，指定侦听器集合的引用模型。
     *
     * @param initialValue 指定的初始值。
     * @param observers    指定的侦听器集合。
     * @throws NullPointerException observers 为 <code>null</code>。
     */
    public DefaultReferenceModel(E initialValue, Set<ReferenceObserver<E>> observers) {
        super(observers);
        this.value = initialValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E set(E element) {
        E oldValue = value;
        value = element;
        fireSet(oldValue, element);
        return oldValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        value = null;
        fireCleared();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof DefaultReferenceModel))
            return false;
        DefaultReferenceModel<?> other = (DefaultReferenceModel<?>) obj;
        if (value == null) {
            return other.value == null;
        } else {
            return value.equals(other.value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DefaultReferenceModel [value=" + value + "]";
    }
}
