package com.dwarfeng.dutil.develop.reuse;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.prog.Filter;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 映射复用池。
 *
 * <p>
 * 通过代理 Map 实现的复用池。
 *
 * @author DwArFeng
 * @since 0.2.1-beta
 */
public class MapReusePool<E> extends AbstractReusePool<E> implements ReusePool<E> {

    protected final Map<E, Condition> delegate;
    /**
     * 用于快速失败的编辑次数统计。
     */
    private int modCount;

    /**
     * 新实例。
     */
    public MapReusePool() {
        this(new HashMap<>());
    }

    /**
     * 通过指定的映射代理生成的映射复用池。
     *
     * @param delegate 指定的代理。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public MapReusePool(Map<E, Condition> delegate) throws NullPointerException {
        Objects.requireNonNull(delegate, DwarfUtil.getExceptionString(ExceptionStringKey.MAPREUSEPOOL_0));
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return new MapReusePoolIterator();
    }

    private final class MapReusePoolIterator implements Iterator<E> {

        private final Iterator<E> iterator = delegate.keySet().iterator();
        private int exceptedModCount = modCount;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            checkModCount();
            return iterator.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            checkModCount();
            return iterator.next();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            checkModCount();
            iterator.remove();
            // 此处移除对象应该增加操作数，并且预期操作数应该与操作数同步。
            modCount++;
            exceptedModCount = modCount;
        }

        private void checkModCount() throws ConcurrentModificationException {
            if (modCount != exceptedModCount) {
                throw new ConcurrentModificationException();
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return delegate.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Condition getCondition(Object object) throws NullPointerException {
        return delegate.get(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean put(E element, Condition condition) {
        if (Objects.isNull(element)) {
            return false;
        }
        if (Objects.isNull(condition)) {
            return false;
        }
        if (delegate.containsKey(element)) {
            return false;
        }

        delegate.put(element, condition);
        // 添加操作数。
        modCount++;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object object) {
        return delegate.containsKey(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> collection) throws NullPointerException {
        Objects.requireNonNull(collection, DwarfUtil.getExceptionString(ExceptionStringKey.MAPREUSEPOOL_1));
        return delegate.keySet().containsAll(collection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object object) {
        if (!delegate.containsKey(object)) {
            return false;
        }

        delegate.remove(object);
        // 添加操作数。
        modCount++;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> collection) throws NullPointerException {
        Objects.requireNonNull(collection, DwarfUtil.getExceptionString(ExceptionStringKey.MAPREUSEPOOL_1));

        boolean aFlag = delegate.keySet().removeAll(collection);
        // 如果该操作对复用池造成了任何改变，则添加操作数。
        if (aFlag) {
            modCount++;
        }

        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> collection) throws NullPointerException {
        Objects.requireNonNull(collection, DwarfUtil.getExceptionString(ExceptionStringKey.MAPREUSEPOOL_1));

        boolean aFlag = delegate.keySet().retainAll(collection);
        // 如果该操作对复用池造成了任何改变，则添加操作数。
        if (aFlag) {
            modCount++;
        }

        return aFlag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        delegate.clear();
        // 添加操作数。
        modCount++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BatchOperator<E> batchOperator() {
        return new MapReusePoolBatchOperator();
    }

    private final class MapReusePoolBatchOperator implements BatchOperator<E> {

        private final Collection<E> updatedElements = new HashSet<>();
        private int expectedModCount = modCount;

        /**
         * {@inheritDoc}
         */
        @Override
        public BatchOperator<E> update(E element, Object updateObject)
                throws ConcurrentModificationException, NullPointerException {
            checkModCount();

            if (!delegate.containsKey(element)) {
                return this;
            }
            if (updatedElements.contains(element)) {
                return this;
            }

            delegate.get(element).update(updateObject);
            updatedElements.add(element);

            modCount++;
            expectedModCount++;

            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public BatchOperator<E> updateAll(Collection<E> collection, Object updateObject)
                throws ConcurrentModificationException, NullPointerException {
            Objects.requireNonNull(collection, DwarfUtil.getExceptionString(ExceptionStringKey.MAPREUSEPOOL_1));

            for (E element : collection) {
                update(element, updateObject);
            }

            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public BatchOperator<E> updateAll(Filter<E> filter, Object updateObject)
                throws ConcurrentModificationException, NullPointerException {
            Objects.requireNonNull(filter, DwarfUtil.getExceptionString(ExceptionStringKey.MAPREUSEPOOL_2));

            for (E element : delegate.keySet()) {
                if (filter.accept(element)) {
                    update(element, updateObject);
                }
            }

            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public BatchOperator<E> updateRemain(Object updateObject)
                throws ConcurrentModificationException, NullPointerException {
            for (E element : delegate.keySet()) {
                if (!updatedElements.contains(element)) {
                    update(element, updateObject);
                }
            }

            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<E> getUnsatisfyElements() throws ConcurrentModificationException {
            return delegate.entrySet().stream().filter(entry -> entry.getValue().isReuseUnsatisfy())
                    .map(Entry::getKey).collect(Collectors.toSet());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<E> removeUnsatisfyElements() throws ConcurrentModificationException {
            Set<E> elements2Remove = delegate.entrySet().stream().filter(entry -> entry.getValue().isReuseUnsatisfy())
                    .map(Entry::getKey).collect(Collectors.toSet());
            delegate.keySet().removeAll(elements2Remove);
            return elements2Remove;
        }

        private void checkModCount() throws ConcurrentModificationException {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

    }
}
