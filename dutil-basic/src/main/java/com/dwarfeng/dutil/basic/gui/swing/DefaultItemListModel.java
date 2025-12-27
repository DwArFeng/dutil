package com.dwarfeng.dutil.basic.gui.swing;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.basic.cna.CollectionUtil;

import javax.swing.*;
import java.util.*;

/**
 * 具有默认条目的列表模型。
 *
 * <p>
 * 该模型的使用范围是一些具有默认选项的 Combobox 或者默认条目的 List。例如：有一个人员下拉选单，选单中出了具有各个人员的条目之外，还在
 * 第一项中拥有一个默认条目“选择所有人员（全选）”。<br>
 * 该列表模型由两个部分组成，默认条目和一般条目。虽然该模型不是一个直接的 {@link List} 现，但是其中的默认条目和一般条目均为
 * {@link List} 现。在该模型中，默认条目和一般条目均有序，且默认条目始终在一般条目上方。<br>
 * 默认条目和一般条目均可以作为 {@link List} 回，并分别进行操作；该模型也提供了直接的操作方法，这些直接的操作方法仅能够操作一般条目。
 * 如：添加操作只能对一般元素进行操作，如果向默认元素所在的区域进行插入操作，则会被积极拒绝；清空操作只能将一般元素清空，而默认元素则不会改变。<br>
 * 操作默认元素的方法是，使用 {@link #getDefaultList()} 取默认元素所在的列表，然后对列表进行操作。
 *
 * <p>
 * 该类可以通过指定入口的参数来保证列表的不同实现，如用同步列表作为实现就可以保证其中方法的同步。
 *
 * <p>
 * 如果不指定任何默认元素，该模型等同于 {@link DefaultListModel}。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class DefaultItemListModel<E> extends AbstractListModel<E> implements Iterable<E> {

    private static final long serialVersionUID = 8547542339038946434L;

    /**
     * 默认条目列表
     */
    protected final DefalutItemList defaultItemList;
    /**
     * 一般条目列表
     */
    protected final NormalItemList normalItemList;

    /**
     * 生成一个使用 {@link ArrayList} 现的默认条目列表模型。
     */
    public DefaultItemListModel() {
        this(new ArrayList<E>(), new ArrayList<E>());
    }

    /**
     * 生成一个具有指定实现的默认条目列表模型。
     *
     * @param defalutDelegate 默认条目的列表实现。
     * @param normalDelegate  一般条目的列表实现。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DefaultItemListModel(List<E> defalutDelegate, List<E> normalDelegate) {
        Objects.requireNonNull(defalutDelegate);
        Objects.requireNonNull(normalDelegate);
        this.defaultItemList = new DefalutItemList(defalutDelegate);
        this.normalItemList = new NormalItemList(normalDelegate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return defaultItemList.size() + normalItemList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E getElementAt(int index) {
        if (index < 0 || index >= getSize()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }

        int defaultSize = defaultItemList.size();
        if (index < defaultSize) {
            return defaultItemList.get(index);
        } else {
            return normalItemList.get(index - defaultSize);
        }
    }

    /**
     * 在此列表的指定位置处插入指定的元素。
     *
     * <p>
     * 如果索引超出范围 <code>（index &lt; 0 || index &gt; size()）</code>，则抛出
     * IndexOutOfBoundsException。
     *
     * <p>
     * 如果序号小于第一个一般条目的序号，则会抛出 {@link IllegalArgumentException};
     *
     * @param index   指定元素的插入位置的索引。
     * @param element 要插入的元素。
     * @throws IndexOutOfBoundsException 序号越界。
     * @throws IllegalArgumentException  试图在默认元素区间添加元素。
     */
    public void add(int index, E element) {
        if (index < 0 || index > getSize()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }

        int defalutSize = defaultItemList.size();
        if (index < defalutSize) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.DefaultListItemModel_0));
        }
        normalItemList.delegate.add(index - defalutSize, element);
        fireIntervalAdded(this, index, index);
    }

    /**
     * 将指定组件添加到此类表的末尾。
     *
     * @param element 要添加的组件。
     */
    public void addElement(E element) {
        int index = getSize();
        normalItemList.delegate.add(element);
        fireIntervalAdded(this, index, index);
    }

    /**
     * 返回此列表的当前容量。
     *
     * <p>
     * 该方法与 {@link #getSize()} 法相同，实现这个方法是为了保证与 {@link DefaultListModel} 的方法一致。
     *
     * @return 当前容量。
     */
    public int capacity() {
        return getSize();
    }

    /**
     * 从此列表中移除所有一般元素。此调用返回后，一般元素列表将是空的，而默认元素列表将被保留（除非该调用抛出异常）。
     */
    public void clear() {
        int index0 = defaultItemList.size();
        int index1 = getSize() - 1;
        normalItemList.delegate.clear();
        fireIntervalRemoved(this, index0, index1);
    }

    /**
     * 测试指定对象是否为此类表中的组件。
     *
     * @param elem 对象。
     * @return 如果指定对象是此列表中的组件，则返回 <code>true</code>。
     */
    public boolean contains(Object elem) {
        return defaultItemList.contains(elem) || normalItemList.contains(elem);
    }

    /**
     * 将此列表的组件复制到指定数组中。数组必须足够大，能够保存此列表中的所有对象，否则抛出 IndexOutOfBoundsException。
     *
     * @param anArray 要将组件复制到其中的数组。
     * @throws IndexOutOfBoundsException 数组不够大，以至于不能放下所有对象。
     */
    public void copyInto(Object[] anArray) {
        Objects.requireNonNull(anArray, DwarfUtil.getExceptionString(ExceptionStringKey.DefaultListItemModel_5));
        if (anArray.length < getSize()) {
            throw new IndexOutOfBoundsException(
                    DwarfUtil.getExceptionString(ExceptionStringKey.DefaultListItemModel_6));
        }
        Object[] def = defaultItemList.toArray();
        Object[] nor = normalItemList.toArray();
        System.arraycopy(def, 0, anArray, 0, def.length);
        System.arraycopy(nor, 0, anArray, def.length, nor.length);
    }

    /**
     * 返回指定索引处的组件。如果索引为负或不小于列表的大小，则抛出 IndexOutOfBoundsException。
     *
     * <p>
     * 注：尽管此方法未过时，但首选使用方法是 get(int)，该方法实现 1.2 Collections 框架中定义的 List 接口。
     *
     * @param index 此列表中的一个索引。
     * @return 指定索引处的组件。
     */
    public E elementAt(int index) {
        return getElementAt(index);
    }

    /**
     * 返回此列表的组件枚举。
     *
     * @return 此列表的组件枚举。
     */
    public Enumeration<E> elements() {
        return CollectionUtil.iterator2Enumeration(iterator());
    }

    /**
     * 该方法不实现任何动作，实现这个方法是为了保证与 {@link DefaultListModel} 的方法一致。
     *
     * @param minCapacity the minCapacity.
     */
    public void ensureCapacity(int minCapacity) {
    }

    /**
     * 返回一般列表中的第一个组件。如果此向量没有组件，则抛出 NoSuchElementException。
     *
     * @return 此列表的第一个组件。
     */
    public E firstElement() {
        if (getSize() == 0)
            throw new NoSuchElementException();
        return getElementAt(0);
    }

    /**
     * 返回列表中指定位置处的元素。
     *
     * <p>
     * 如果索引超出范围<code>（index &lt; 0 || index &gt;= size()）</code>，则抛出
     * IndexOutOfBoundsException。
     *
     * @param index 要返回的元素的索引。
     * @return 指定位置处的元素。
     * @throws IndexOutOfBoundsException 下标越界。
     */
    public E get(int index) {
        return getElementAt(index);
    }

    /**
     * 搜索 elem 的第一次出现。
     *
     * @param elem 一个对象。
     * @return 此列表中该参数第一次出现时所在位置上的索引；如果没有找到该对象，则返回 <code>-1</code>。
     */
    public int indexOf(Object elem) {
        int def = defaultItemList.indexOf(elem);
        int nor = normalItemList.indexOf(elem);

        if (def == -1 && nor == -1)
            return -1;
        if (def >= 0)
            return def;

        int defalutSize = defaultItemList.size();
        return defalutSize + nor;
    }

    /**
     * 从 index 开始搜索 elem 的第一次出现。
     *
     * @param elem  所需的组件。
     * @param index 从其所在的位置开始进行搜索的索引。
     * @return 之后第一次出现 elem 处的索引；如果在列表中没有找到 elem，则返回 <code>-1</code>。
     */
    public int indexOf(Object elem, int index) {
        for (int i = index; i < getSize(); i++) {
            Object o = getElementAt(i);
            if (Objects.isNull(o) && Objects.isNull(elem))
                return i;
            if (Objects.nonNull(o) && o.equals(elem))
                return i;
        }
        return -1;
    }

    /**
     * 将指定对象作为此列表中的组件插入到指定的 index 处。
     *
     * <p>
     * 如果索引无效，则抛出 IndexOutOfBoundsException。
     *
     * <p>
     * 如果序号小于第一个一般条目的序号，则会抛出 {@link IllegalArgumentException};
     *
     * <p>
     * 注：尽管此方法未过时，但首选使用方法是 add(int,Object)，该方法实现 1.2 Collections 框架中定义的 List 接口。
     *
     * @param obj   要插入的组件。
     * @param index 插入新组件的位置。
     * @throws IllegalArgumentException  序号小于第一个一般条目的序号。
     * @throws IndexOutOfBoundsException 下标越界。
     */
    public void insertElementAt(E obj, int index) {
        add(index, obj);
    }

    /**
     * 测试此列表中是否有组件。
     *
     * @return 当且仅当此列表中没有组件（也就是说其大小为零）时返回 true；否则返回 false。
     */
    public boolean isEmpty() {
        return defaultItemList.isEmpty() && normalItemList.isEmpty();
    }

    /**
     * 返回列表的最后一个组件。如果此向量没有组件，则抛出 NoSuchElementException。
     *
     * @return 列表的最后一个组件。
     */
    public E lastElement() {
        if (getSize() == 0)
            throw new NoSuchElementException();
        return getElementAt(getSize() - 1);
    }

    /**
     * 返回 elem 最后一次出现处的索引。
     *
     * @param elem 所需的组件。
     * @return 列表中 elem 最后一次出现处的索引；如果没有找到该对象，则返回<code>-1</code>。
     */
    public int lastIndexOf(Object elem) {
        for (int i = getSize() - 1; i >= 0; i--) {
            Object o = getElementAt(i);
            if (Objects.isNull(o) && Objects.isNull(elem))
                return i;
            if (Objects.nonNull(o) && o.equals(elem))
                return i;
        }
        return -1;
    }

    /**
     * 从指定的索引处开始反向搜索 elem，并返回该对象的索引。
     *
     * @param elem  所需的组件。
     * @param index 从其所在的位置开始进行搜索的索引。
     * @return 列表中 index 之前最后一次出现 elem 处的索引；如果在列表中没有找到该对象，则返回 <code>-1</code>。
     */
    public int lastIndexOf(Object elem, int index) {
        for (int i = index; i >= 0; i--) {
            Object o = getElementAt(i);
            if (Objects.isNull(o) && Objects.isNull(elem))
                return i;
            if (Objects.nonNull(o) && o.equals(elem))
                return i;
        }
        return -1;
    }

    /**
     * 移除此列表中指定位置处的元素。返回从列表中移除的元素。
     *
     * <p>
     * 如果索引超出范围<code>（index &lt; 0 || index &gt;= size()）</code>，则抛出
     * IndexOutOfBoundsException。
     *
     * <p>
     * 如果序号小于第一个一般条目的序号，则会抛出 {@link IllegalArgumentException};
     *
     * @param index 要移除的元素的索引。
     * @return 返回的元素。
     * @throws IndexOutOfBoundsException 下标越界。
     */
    public E remove(int index) {
        if (index < 0 || index >= getSize()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        int defaultSize = defaultItemList.size();
        if (index < defaultSize) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.DefaultListItemModel_1));
        }
        E e = normalItemList.delegate.remove(index - defaultSize);
        fireIntervalRemoved(this, index, index);
        return e;
    }

    /**
     * 从此列表中移除所有组件，并将它们的大小设置为零。
     *
     * <p>
     * 注：尽管此方法未过时，但首选使用方法是 clear，该方法实现 1.2 Collections 框架中定义的 List 接口。
     */
    public void removeAllElements() {
        clear();
    }

    /**
     * 从一般列表中移除参数的第一个（索引最小的）匹配项。
     *
     * @param obj 要移除的组件。
     * @return 如果该参数是一般列表的一个组件，则返回 <code>true</code>；否则返回 <code>false</code>。
     */
    public boolean removeElement(Object obj) {
        int index = normalItemList.indexOf(obj);
        boolean flag = normalItemList.delegate.remove(obj);
        if (flag) {
            fireIntervalRemoved(this, index + defaultItemList.size(), index + defaultItemList.size());
        }
        return flag;
    }

    /**
     * 删除指定索引处的组件。
     *
     * <p>
     * 如果索引无效，则抛出 IndexOutOfBoundsException。
     *
     * <p>
     * 如果序号小于第一个一般条目的序号，则会抛出 {@link IllegalArgumentException};
     *
     * <p>
     * 注：尽管此方法未过时，但首选使用方法是 remove(int)，该方法实现 1.2 Collections 框架中定义的 List 接口。
     *
     * @param index 要移除对象的索引
     * @throws IndexOutOfBoundsException 下标越界。
     * @throws IllegalArgumentException  下标落在默认条目区域。
     */
    public void removeElementAt(int index) {
        remove(index);
    }

    /**
     * 删除指定索引范围中的组件。移除组件包括指定范围两个端点处的组件。
     *
     * <p>
     * 如果索引无效，则抛出 IndexOutOfBoundsException。如果
     * <code>fromIndex &gt; toIndex</code>，则抛出 IllegalArgumentException。
     *
     * <p>
     * 如果序号中包含小于第一个一般条目的序号，则会抛出 {@link IllegalArgumentException};
     *
     * @param fromIndex 范围低端点的索引。
     * @param toIndex   范围高端点的索引。
     * @throws IndexOutOfBoundsException 下标越界
     * @throws IllegalArgumentException  下标区间中包含落在默认条目区域的下标。
     */
    public void removeRange(int fromIndex, int toIndex) {
        int defaultSize = defaultItemList.size();
        if (fromIndex < defaultSize) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.DefaultListItemModel_1));
        }
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(fromIndex));
        }
        if (toIndex >= getSize()) {
            throw new IndexOutOfBoundsException(String.valueOf(toIndex));
        }
        for (int i = fromIndex; i <= toIndex; i++) {
            normalItemList.delegate.remove(fromIndex - defaultSize);
        }
        fireIntervalRemoved(this, fromIndex, toIndex);
    }

    /**
     * 使用指定元素替换此列表中指定位置上的元素。
     *
     * <p>
     * 如果索引超出范围<code>（index &lt; 0 || index &gt;= size()）</code>，则抛出
     * IndexOutOfBoundsException。
     *
     * <p>
     * 如果序号小于第一个一般条目的序号，则会抛出 {@link IllegalArgumentException};
     *
     * @param index   要替换的元素的索引。
     * @param element 要存储在指定位置上的元素。
     * @return 以前在指定位置上的元素。
     * @throws IndexOutOfBoundsException 下标越界。
     * @throws IllegalArgumentException  下标落在默认条目区域。
     * @throws IndexOutOfBoundsException 下标越越界。
     */
    public E set(int index, E element) {
        if (index < 0 || index >= getSize()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        int defaultSize = defaultItemList.size();
        if (index < defaultSize) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.DefaultListItemModel_2));
        }
        E e = normalItemList.delegate.set(index - defaultSize, element);
        fireContentsChanged(this, index, index);
        return e;
    }

    /**
     * 将此列表指定 index 处的组件设置为指定的对象。丢弃该位置以前的组件。
     *
     * <p>
     * 如果索引无效，则抛出 IndexOutOfBoundsException。
     *
     * <p>
     * 如果序号小于第一个一般条目的序号，则会抛出 {@link IllegalArgumentException};
     *
     * <p>
     * 注：尽管此方法未过时，但首选使用方法是 set(int,Object)，该方法实现 1.2 Collections 框架中定义的 List 接口。
     *
     * @param obj   组件的设置目标。
     * @param index 指定的索引。
     * @throws IndexOutOfBoundsException 下标越界。
     * @throws IllegalArgumentException  下标落在默认条目区域。
     */
    public void setElementAt(E obj, int index) {
        set(index, obj);
    }

    /**
     * 该方法不实现任何动作，实现这个方法是为了保证与 {@link DefaultListModel} 的方法一致。
     *
     * @param newSize the newSize。
     */
    public void setSize(int newSize) {
    }

    /**
     * 返回此列表中的组件数。
     *
     * @return 此列表中的组件数。
     */
    public int size() {
        return getSize();
    }

    /**
     * 以正确顺序返回包含默认列表以及一般列表中所有元素的数组。
     *
     * @return 包含列表元素的数组。
     */
    public Object[] toArray() {
        Object[] arr1 = defaultItemList.toArray();
        Object[] arr2 = normalItemList.toArray();
        return ArrayUtil.concat(arr1, arr2);
    }

    /**
     * 以正确顺序返回包含默认列表以及一般列表中所有元素的数组。
     * 返回数组的运行时类型是指定数组的运行时类型。如果指定的数组能容纳列表，则将该列表返回此处。
     * 否则，将分配一个具有指定数组的运行时类型和此列表大小的新数组。
     *
     * <p>
     * 如果指定的数组能容纳队列，并有剩余的空间（即数组的元素比队列多），那么会将数组中紧接 collection 尾部的元素设置为 null。 （仅
     * 在调用者知道列表中不包含任何 null 元素时才能用此方法确定列表长度）。
     *
     * @param a   要在其中存储列表元素的数组（如果它足够大）；否则，为此分配一个具有相同运行时类型的新数组。
     * @param <T> 泛型T。
     * @return 包含列表元素的数组。
     */
    public <T> T[] toArray(T[] a) {
        T[] t1 = defaultItemList.toArray(a);
        T[] t2 = normalItemList.toArray(a);
        return ArrayUtil.concat(t1, t2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (; ; ) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return CollectionUtil.contactIterator(defaultItemList.iterator(), normalItemList.iterator());
    }

    /**
     * 该方法不实现任何动作，实现这个方法是为了保证与 {@link DefaultListModel} 的方法一致。
     */
    public void trimToSize() {
    }

    /**
     * 获取第一个一般条目所在的序号。
     *
     * @return 第一个一般条目所在的序号。
     */
    public int getFirstNormalItemIndex() {
        return defaultItemList.size();
    }

    /**
     * 查询一个元素是否是默认元素。
     *
     * <p>
     * 一个元素可能同时在默认列表和一般列表之间，因此存在这种可能性：<br>
     * <code> isDefaultItem(e) == isNormalItem(e) == true </code>。
     *
     * @param element 指定的元素。
     * @return 指定的元素是否是默认元素。
     */
    public boolean isDefaultItem(Object element) {
        return defaultItemList.contains(element);
    }

    /**
     * 查询一个元素是否是默认元素。
     *
     * @param index 元素的序号。
     * @return 序号指向的元素是否是默认元素。
     * @throws IndexOutOfBoundsException 下标越界。
     */
    public boolean isDefaultItem(int index) {
        if (index < 0 || index >= getSize()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        return index < defaultItemList.size();
    }

    /**
     * 查询一个元素是否是一般元素。
     *
     * <p>
     * 一个元素可能同时在默认列表和一般列表之间，因此存在这种可能性：<br>
     * <code> isDefaultItem(e) == isNormalItem(e) == true </code>。
     *
     * @param element 指定的元素。
     * @return 指定的元素是否是一般元素。
     */
    public boolean isNormalItem(Object element) {
        return normalItemList.contains(element);
    }

    /**
     * 获取默认元素列表。
     *
     * <p>
     * 对该列表进行更改可以将效果立即反映在视图中。但是，由该列表通过 {@link List#subList(int, int)} 法返回的
     * 子列表则无法提供这种立即反馈的效果。
     *
     * @return 默认元素列表。
     */
    public List<E> getDefaultList() {
        return this.defaultItemList;
    }

    /**
     * 获取一般元素列表。
     *
     * <p>
     * 对该列表进行更改可以将效果立即反映在视图中。但是，由该列表通过 {@link List#subList(int, int)} 法返回的
     * 子列表则无法提供这种立即反馈的效果。
     *
     * @return 一般元素列表。
     */
    public List<E> getNormalList() {
        return this.normalItemList;
    }

    private final class NormalItemList implements List<E> {

        private final List<E> delegate;

        public NormalItemList(List<E> delegate) {
            this.delegate = delegate;
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
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            return delegate.contains(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<E> iterator() {
            return delegate.iterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            return delegate.toArray();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            return delegate.toArray(a);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            int index = size() + defaultItemList.size();
            boolean flag = delegate.add(e);
            if (flag)
                fireIntervalAdded(DefaultItemListModel.this, index, index);
            return flag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            int index = defaultItemList.size() + indexOf(o);
            boolean flag = delegate.remove(o);
            if (flag)
                fireIntervalRemoved(DefaultItemListModel.this, index, index);
            return flag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            boolean flag = false;
            for (E e : c) {
                if (add(e))
                    flag = true;
            }
            return flag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            int size = size();
            int i = 0;
            for (E e : c) {
                add(index + i, e);
                i++;
            }
            return size() != size;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            boolean flag = false;
            for (Object obj : c) {
                if (remove(obj))
                    flag = true;
            }
            return flag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Set<E> set = new HashSet<E>();
            for (E e : this) {
                if (!c.contains(e)) {
                    set.add(e);
                }
            }
            return removeAll(set);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            int lastIndex = defaultItemList.size() + size() - 1;
            delegate.clear();
            fireIntervalRemoved(DefaultItemListModel.this, 0, lastIndex);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E get(int index) {
            return delegate.get(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E set(int index, E element) {
            int aIndex = index + defaultItemList.size();
            E e = delegate.set(index, element);
            fireContentsChanged(DefaultItemListModel.this, aIndex, aIndex);
            return e;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, E element) {
            int size = size();
            int aIndex = index + defaultItemList.size();
            delegate.add(index, element);
            if (size() != size)
                fireIntervalAdded(DefaultItemListModel.this, aIndex, aIndex);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E remove(int index) {
            int aIndex = index + defaultItemList.size();
            E e = delegate.remove(index);
            fireIntervalRemoved(DefaultItemListModel.this, aIndex, aIndex);
            return e;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int indexOf(Object o) {
            return delegate.indexOf(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int lastIndexOf(Object o) {
            return delegate.lastIndexOf(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator() {
            return delegate.listIterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator(int index) {
            return delegate.listIterator(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return delegate.subList(fromIndex, toIndex);
        }

    }

    private final class DefalutItemList implements List<E> {

        private final List<E> delegate;

        public DefalutItemList(List<E> delegate) {
            this.delegate = delegate;
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
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            return delegate.contains(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<E> iterator() {
            return delegate.iterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            return delegate.toArray();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            return delegate.toArray(a);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            boolean flag = delegate.add(e);
            int index = size();
            if (flag)
                fireIntervalAdded(DefaultItemListModel.this, index, index);
            return flag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            int index = this.indexOf(o);
            boolean flag = delegate.remove(o);
            if (flag)
                fireIntervalRemoved(DefaultItemListModel.this, index, index);
            return flag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return delegate.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            boolean flag = false;
            for (E e : c) {
                if (add(e))
                    flag = true;
            }
            return flag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            int size = size();
            int i = 0;
            for (E e : c) {
                add(index + i, e);
                i++;
            }
            return size() != size;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            boolean flag = false;
            for (Object obj : c) {
                if (remove(obj))
                    flag = true;
            }
            return flag;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            Set<E> set = new HashSet<E>();
            for (E e : this) {
                if (!c.contains(e)) {
                    set.add(e);
                }
            }
            return removeAll(set);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            int lastIndex = size() - 1;
            delegate.clear();
            fireIntervalRemoved(DefaultItemListModel.this, 0, lastIndex);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E get(int index) {
            return delegate.get(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E set(int index, E element) {
            E e = delegate.set(index, element);
            fireContentsChanged(DefaultItemListModel.this, index, index);
            return e;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, E element) {
            delegate.add(index, element);
            fireIntervalAdded(DefaultItemListModel.this, index, index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E remove(int index) {
            E e = delegate.remove(index);
            fireIntervalRemoved(DefaultItemListModel.this, index, index);
            return e;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int indexOf(Object o) {
            return delegate.indexOf(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int lastIndexOf(Object o) {
            return delegate.lastIndexOf(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator() {
            return delegate.listIterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator(int index) {
            return delegate.listIterator(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return delegate.subList(fromIndex, toIndex);
        }

    }
}
