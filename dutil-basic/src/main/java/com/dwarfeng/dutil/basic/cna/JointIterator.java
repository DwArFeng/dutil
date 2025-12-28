package com.dwarfeng.dutil.basic.cna;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.prog.Buildable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 连接迭代器。用于连接多个独立 {@link Iterator} 的 {@link Iterator}。
 *
 * <p>
 * 该迭代器连接多个迭代器，这些被连接的迭代器对顺序敏感。该连接迭代器的迭代顺序是：首先对其中独立的迭代器中的第一个
 * 中的元素进行迭代，当第一个迭代器中没有更多的元素时，进而迭代第二个迭代器的元素，以此类推，直到所有迭代器中的元素 都完成迭代。
 *
 * <p>
 * 迭代器不接受 <code>null</code>元素，如果在构造器中使用 <code>null</code>元素进行构造，则会被积极的拒绝。
 *
 * <p>
 * 为了防止潜在的堆污染，该类的构造方式由构造器完成。
 *
 * <p>
 * 如果只是合并两个迭代器，则 {@link CollectionUtil} 供了便捷方法，请查看
 * {@link CollectionUtil#contactIterator(Iterator, Iterator)}。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class JointIterator<T> implements Iterator<T> {

    public static class Builder<T> implements Buildable<JointIterator<T>> {

        private final List<Iterator<T>> iteratorList = CollectionUtil.nonNullList(new ArrayList<>());

        /**
         * 向构造器中添加一个迭代器。
         *
         * <p>
         * 该迭代器位于构造器中的最后方，直到新的迭代器被添加。
         *
         * @param iterator 指定的可迭代对象。
         * @return 构造器自身。
         * @throws NullPointerException 入口参数为 <code>null</code>。
         */
        public Builder<T> append(Iterator<T> iterator) {
            Objects.requireNonNull(iterator, DwarfUtil.getExceptionString(ExceptionStringKey.JointIterator_0));
            iteratorList.add(iterator);
            return this;
        }

        /**
         * 向构造器中添加一个可迭代对象。
         *
         * @param iterable 指定的可迭代对象。
         * @return 构造器自身。
         * @throws NullPointerException 入口参数为 <code>null</code>。
         */
        public Builder<T> append(Iterable<T> iterable) {
            Objects.requireNonNull(iterable, DwarfUtil.getExceptionString(ExceptionStringKey.JointIterator_1));
            iteratorList.add(iterable.iterator());
            return this;
        }

        /**
         * 向构造器中添加一个数组。
         *
         * <p>
         * 该数组将转换为迭代器添加进构造器中，其迭代顺序和数组的顺序一致。
         *
         * @param array 指定的数组。
         * @return 构造器自身。
         * @throws NullPointerException 入口参数为 <code>null</code>。
         */
        public Builder<T> append(T[] array) {
            Objects.requireNonNull(array, DwarfUtil.getExceptionString(ExceptionStringKey.JointIterator_2));
            iteratorList.add(ArrayUtil.array2Iterable(array).iterator());
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public JointIterator<T> build() {
            return new JointIterator<>(iteratorList);
        }

    }

    /**
     * 存放独立迭代器的列表
     */
    protected final List<Iterator<T>> iteratorList;
    /**
     * 用于记录当前迭代位置的变量
     */
    protected int index = 0;

    private JointIterator(List<Iterator<T>> iteratorList) {
        this.iteratorList = iteratorList;
    }

    private boolean hasNextBehind(int index) {
        for (int i = index; i < iteratorList.size(); i++) {
            if (iteratorList.get(i).hasNext())
                return true;
        }
        return false;
    }

    private int getNextIndexBehind(int index) {
        for (int i = index; i < iteratorList.size(); i++) {
            if (iteratorList.get(i).hasNext())
                return i;
        }
        throw new IllegalArgumentException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        Iterator<T> currentIterator = iteratorList.get(index);
        if (currentIterator.hasNext()) {
            return true;
        } else {
            if (hasNextBehind(index)) {
                this.index = getNextIndexBehind(index);
                return true;
            } else {
                this.index = iteratorList.size() - 1;
                return false;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T next() {
        Iterator<T> currentIterator = iteratorList.get(index);
        return currentIterator.next();
    }
}
