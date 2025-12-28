package com.dwarfeng.dutil.develop.reuse;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * 抽象复用池。
 *
 * <p>
 * 复用池的抽象实现。
 *
 * @author DwArFeng
 * @since 0.2.1-beta
 */
public abstract class AbstractReusePool<E> implements ReusePool<E> {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * 该方法为了考虑通用性，效率不是最高，必要时重写该方法。
     */
    @Override
    public boolean contains(Object object) {
        for (E element : this) {
            if (Objects.equals(object, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * 该方法为了考虑通用性，效率不是最高，必要时重写该方法。
     */
    @Override
    public boolean containsAll(Collection<?> collection) throws NullPointerException {
        for (Object object : collection)
            if (!contains(object))
                return false;
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * 该方法为了考虑通用性，效率不是最高，必要时重写该方法。
     */
    @Override
    public boolean remove(Object object) {
        for (Iterator<E> iterator = iterator(); iterator.hasNext(); ) {
            E element = iterator.next();
            if (Objects.equals(object, element)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Objects.requireNonNull(collection, "入口参数 collection 不能为 null。");

        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        Objects.requireNonNull(collection, "入口参数 collection 不能为 null。");

        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * 该方法为了考虑通用性，效率不是最高，必要时重写该方法。
     */
    @Override
    public void clear() {
        for (Iterator<E> iterator = iterator(); iterator.hasNext(); ) {
            iterator.next();
            iterator.remove();
        }
    }
}
