package com.dwarfeng.dutil.basic.cna;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.prog.ReadOnlyGenerator;

import java.util.*;
import java.util.Map.Entry;

/**
 * 有关于集合的工具包。
 *
 * <p>
 * 该工具包中包含对集合进行的常见的操作
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class CollectionUtil {

    // 禁止外部实例化。
    private CollectionUtil() {
    }

    /**
     * 在指定集合的基础上获得不允许含有 <code>null</code> 元素的集合。
     *
     * <p>
     * 获得的集合会转运指定的集合中的方法，因此，获得的集合的表现与指定的集合是一致的。
     *
     * <p>
     * 请注意，入口参数必须是空的，因为非空的参数可能已经包含了 <code>null</code>元素。<br>
     * 获得的集合不允许其中含有 null 元素，因此，任何试图向其中添加 <code>null</code>元素 的方法都将抛出异常。
     *
     * @param set 转运的集合。
     * @param <T> 泛型 T。
     * @return 不允许含有 <code>null</code> 元素的集合。
     * @throws NullPointerException     当入口参数为 <code>null</code> 时抛出该异常。
     * @throws IllegalArgumentException 当入口的参数不是空的时候抛出该异常。
     */
    public static <T> Set<T> nonNullSet(Set<T> set) {
        Objects.requireNonNull(set, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_0));
        if (!set.isEmpty()) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_8));
        }
        return new NonNullSet<>(set);
    }

    private static final class NonNullSet<E> implements Set<E> {

        private final Set<E> set;

        public NonNullSet(Set<E> set) {
            this.set = set;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return set.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return set.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            return set.contains(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<E> iterator() {
            return set.iterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            return set.toArray();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            return set.toArray(a);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            Objects.requireNonNull(e, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_1));
            return set.add(e);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            return set.remove(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return set.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_1));
            if (CollectionUtil.conatinsNull(c)) {
                throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_3));
            }
            return set.addAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            return set.retainAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            return set.removeAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            set.clear();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return set.toString();
        }
    }

    /**
     * 在指定列表的基础上获得不允许含有 <code>null</code> 元素的列表。
     *
     * <p>
     * 获得的列表会转运指定的列表中的方法，因此，获得的列表的表现与指定的列表是一致的。
     *
     * <p>
     * 请注意，入口参数必须是空的，因为非空的参数可能已经包含了 <code>null</code>元素。<br>
     * 获得的列表不允许其中含有 null 元素，因此，任何试图向其中添加 <code>null</code>元素 的方法都将抛出异常。
     *
     * @param list 转运的列表。
     * @param <T>  泛型 T。
     * @return 不允许含有 <code>null</code> 元素的列表。
     * @throws NullPointerException     当入口参数为 <code>null</code> 时抛出该异常。
     * @throws IllegalArgumentException 当入口的参数不是空的时候抛出该异常。
     */
    public static <T> List<T> nonNullList(List<T> list) {
        Objects.requireNonNull(list, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_4));
        if (!list.isEmpty()) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_8));
        }
        return new NonNullList<>(list);
    }

    private static final class NonNullList<E> implements List<E> {

        private final List<E> list;

        public NonNullList(List<E> list) {
            this.list = list;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return list.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(Object o) {
            return list.contains(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<E> iterator() {
            return list.iterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            return list.toArray();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public <T> T[] toArray(T[] a) {
            return list.toArray(a);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            Objects.requireNonNull(e, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_1));
            return list.add(e);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            return list.remove(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return list.containsAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_1));
            if (CollectionUtil.conatinsNull(c)) {
                throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_3));
            }
            return list.addAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_1));
            if (CollectionUtil.conatinsNull(c)) {
                throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_3));
            }
            return list.addAll(index, c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            return list.removeAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            return list.retainAll(c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            list.clear();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E get(int index) {
            return list.get(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E set(int index, E element) {
            Objects.requireNonNull(element, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_1));
            return list.set(index, element);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, E element) {
            Objects.requireNonNull(element, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_1));
            list.add(index, element);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E remove(int index) {
            return list.remove(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int indexOf(Object o) {
            return list.indexOf(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int lastIndexOf(Object o) {
            return list.lastIndexOf(o);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator() {
            return list.listIterator();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator(int index) {
            return list.listIterator(index);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return new NonNullList<>(list.subList(fromIndex, toIndex));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return list.toString();
        }

    }

    /**
     * 在指定映射的基础上获得不允许含有 <code>null</code> 元素的映射。
     *
     * <p>
     * 获得的映射会转运指定的映射中的方法，因此，获得的映射的表现与指定的映射是一致的。
     *
     * <p>
     * 请注意，入口参数必须是空的，因为非空的参数可能已经包含了 <code>null</code>键。<br>
     * 获得的映射不允许其中含有 null 键，因此，任何试图向其中添加 <code>null</code>键 的方法都将抛出异常。
     *
     * @param map 转运的映射。
     * @param <K> 泛型 K。
     * @param <V> 泛型 V。
     * @return 不允许含有 <code>null</code> 键的映射。
     * @throws NullPointerException     当入口参数为 <code>null</code> 时抛出该异常。
     * @throws IllegalArgumentException 当入口的参数不是空的时候抛出该异常。
     */
    public static <K, V> Map<K, V> nonNullMap(Map<K, V> map) {
        Objects.requireNonNull(map, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_5));
        if (!map.isEmpty()) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_8));
        }
        return new NonNullMap<>(map);
    }

    private static final class NonNullMap<K, V> implements Map<K, V> {

        private final Map<K, V> map;

        public NonNullMap(Map<K, V> map) {
            this.map = map;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int size() {
            return map.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsKey(Object key) {
            return map.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsValue(Object value) {
            return map.containsValue(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(Object key) {
            return map.get(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V put(K key, V value) {
            Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_6));
            return map.put(key, value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(Object key) {
            return map.remove(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            Objects.requireNonNull(m, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_7));
            if (CollectionUtil.conatinsNull(m.keySet())) {
                throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_6));
            }
            map.putAll(m);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            map.clear();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<K> keySet() {
            return map.keySet();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<V> values() {
            return map.values();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<java.util.Map.Entry<K, V>> entrySet() {
            return map.entrySet();
        }

    }

    /**
     * 检查指定的集合中是否含有 <code>null</code>元素。
     *
     * @param collection 指定的集合。
     * @return 是否含有 <code>null</code>元素。
     * @throws NullPointerException 当入口参数为 <code>null</code>时。
     */
    public static boolean conatinsNull(Collection<?> collection) {
        Objects.requireNonNull(collection, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_2));
        for (Object obj : collection) {
            if (Objects.isNull(obj))
                return true;
        }
        return false;
    }

    /**
     * 要求指定的集合不能含有 <code>null</code>元素。
     *
     * <p>
     * 入口指定的 <code>collection</code>中含有 <code>null</code>元素， 则抛出
     * {@link NullPointerException}。
     *
     * @param collection 指定的集合元素。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     * @throws NullPointerException <code>collection</code> 中含有 <code>null</code>元素。
     */
    public static void requireNotContainsNull(Collection<?> collection) {
        Objects.requireNonNull(collection, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_2));
        if (conatinsNull(collection))
            throw new NullPointerException();
    }

    /**
     * 要求指定的集合不能含有 <code>null</code>元素。
     *
     * <p>
     * 入口指定的 <code>collection</code>中含有 <code>null</code>元素， 则抛出拥有指定异常信息的
     * {@link NullPointerException}。
     *
     * @param collection 指定的集合元素。
     * @param message    指定的异常信息。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     * @throws NullPointerException <code>collection</code> 中含有 <code>null</code>元素。
     */
    public static void requireNotContainsNull(Collection<?> collection, String message) {
        Objects.requireNonNull(collection, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_2));
        if (conatinsNull(collection))
            throw new NullPointerException(message);
    }

    private static final class EnumerationIterator<T> implements Iterator<T> {

        private final Enumeration<T> enumeration;

        public EnumerationIterator(Enumeration<T> enumeration) {
            this.enumeration = enumeration;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return enumeration.hasMoreElements();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T next() {
            return enumeration.nextElement();
        }

    }

    /**
     * 通过指定的 {@link Enumeration} 生成的 {@link Iterator}。
     *
     * @param enumeration 指定的枚举。
     * @param <T>         泛型T。
     * @return 通过指定的枚举生成的迭代器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <T> Iterator<T> enumeration2Iterator(Enumeration<T> enumeration) {
        Objects.requireNonNull(enumeration, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_9));
        return new EnumerationIterator<>(enumeration);
    }

    private static final class IteratorEnumeration<T> implements Enumeration<T> {

        private final Iterator<T> iterator;

        public IteratorEnumeration(Iterator<T> iterator) {
            this.iterator = iterator;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasMoreElements() {
            return iterator.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public T nextElement() {
            return iterator.next();
        }

    }

    /**
     * 通过指定的 {@link Iterator} 生成的 {@link Enumeration}。
     *
     * @param iterator 指定的迭代器。
     * @param <T>      泛型T。
     * @return 通过指定的迭代器生成的枚举。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <T> Enumeration<T> iterator2Enumeration(Iterator<T> iterator) {
        Objects.requireNonNull(iterator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_10));
        return new IteratorEnumeration<>(iterator);
    }

    /**
     * <p>
     * 将一个数组转化为一个迭代器。
     *
     * <p>
     * 虽然数组可以使用 for-each 循环，但是数组不可以作为 {@link Iterable} 对象进行参数传递，该方法为了解决这一问题，
     * 可以将一个数组转化为一个 {@link Iterator} 象，方便某些需要传入迭代器的场合。
     *
     * @param array 指定的数组。
     * @param <T>   泛型T。
     * @return 由指定的数组转化而成的迭代器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     * @deprecated 该方法的功能与该工具包的功能不符，已经停止使用，可以用类似的方法
     * {@link ArrayUtil#array2Iterable(Object[])} 替。
     */
    @Deprecated
    public static <T> Iterator<T> array2Iterator(T[] array) {
        Objects.requireNonNull(array, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));
        return ArrayUtil.array2Iterable(array).iterator();
    }

    /**
     * 用于连接两个迭代器。
     *
     * <p>
     * 新的迭代器首先迭代 <code>firstIterator</code>中的元素，当其中的元素迭代完之后，继续迭代
     * <code>secondIterator</code>中的元素，直至两个迭代器中的元素全部迭代完成。
     *
     * @param firstIterator  第一个迭代器。
     * @param secondIterator 第二个迭代器。
     * @param <T>            泛型T。
     * @return 两个迭代器连接形成的迭代器。
     */
    public static <T> Iterator<T> contactIterator(Iterator<T> firstIterator, Iterator<T> secondIterator) {
        Objects.requireNonNull(firstIterator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_13));
        Objects.requireNonNull(secondIterator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_14));
        return new JointIterator.Builder<T>().append(firstIterator).append(secondIterator).build();
    }

    /**
     * 将指定的对象按照顺序插入到指定的表中。
     *
     * <p>
     * 该方法将用指定的比较器逐个比较指定的对象与列表中的对象，并将指定的对象插入到列表中<b>第一个</b>大于等于其的元素之前， 并返回插入的位置。
     * <br>
     * 如果指定的列表在之前已经按照比较器的顺序排列好，那么调用该方法之后，此列表依然遵循比较器的顺序，
     * 事实上，该方法就是为此设计的——对一个没有排序的列表调用此方法是没有意义的。<br>
     * 有些列表允许 <code>null</code>元素，有些不允许。对于那些允许 <code>null</code>元素的的列表，请注意：
     * 指定的比较器也需要支持 <code>null</code>元素。
     *
     * @param <T>  列表中的元素的类。
     * @param list 指定的列表。
     * @param obj  指定的对象，允许为 <code>null</code>，但是需要列表和比较器支持 <code>null</code>元素。
     * @param c    指定的比较器。
     * @return 对象的插入位置。
     */
    public static <T> int insertByOrder(List<T> list, T obj, Comparator<? super T> c) {
        Objects.requireNonNull(list, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_15));
        Objects.requireNonNull(c, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_16));

        // TODO 效率不高，换成二分查找。
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            if (c.compare(obj, t) <= 0) {
                list.add(i, obj);
                return i;
            }
        }
        list.add(obj);
        return list.size() - 1;
    }

    /**
     * 根据指定的迭代器生成一个不可编辑的迭代器。
     *
     * @param iterator 指定的迭代器。
     * @param <E>      泛型E。
     * @return 不可编辑的迭代器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> Iterator<E> unmodifiableIterator(Iterator<E> iterator) {
        Objects.requireNonNull(iterator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_10));
        return new UnmodifiableIterator<>(iterator);
    }

    private static class UnmodifiableIterator<E> implements Iterator<E> {

        private final Iterator<E> delegate;

        public UnmodifiableIterator(Iterator<E> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            return delegate.next();
        }

    }

    /**
     * 根据指定的迭代器和指定的只读生成器生成一个只读的迭代器。
     *
     * @param iterator  指定的迭代器。
     * @param generator 指定的只读生成器。
     * @param <E>       迭代器的泛型。
     * @return 根据指定的迭代器和指定的只读生成器生成的只读迭代器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> Iterator<E> readOnlyIterator(Iterator<E> iterator, ReadOnlyGenerator<E> generator) {
        Objects.requireNonNull(iterator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_10));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_17));
        return new ReadOnlyIterator<>(iterator, generator);
    }

    private final static class ReadOnlyIterator<E> implements Iterator<E> {

        private final Iterator<E> delegate;
        private final ReadOnlyGenerator<E> generator;

        public ReadOnlyIterator(Iterator<E> delegate, ReadOnlyGenerator<E> generator) {
            this.delegate = delegate;
            this.generator = generator;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            return generator.readOnly(delegate.next());
        }

    }

    /**
     * 根据指定的列表迭代器生成一个不可编辑的列表迭代器。
     *
     * @param listIterator 指定的列表迭代器。
     * @param <E>          列表迭代器的泛型。
     * @return 根据指定的列表迭代器生成的不可编辑的列表迭代器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> ListIterator<E> unmodifiableListIterator(ListIterator<E> listIterator) {
        Objects.requireNonNull(listIterator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_18));
        return new UnmodifiableListIterator<>(listIterator);
    }

    private final static class UnmodifiableListIterator<E> implements ListIterator<E> {

        private final ListIterator<E> delegate;

        public UnmodifiableListIterator(ListIterator<E> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            return delegate.next();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasPrevious() {
            return delegate.hasPrevious();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E previous() {
            return delegate.previous();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int nextIndex() {
            return delegate.nextIndex();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int previousIndex() {
            return delegate.previousIndex();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("set");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("add");
        }

    }

    /**
     * 根据指定的列表迭代器和指定的只读生成器生成一个只读的列表迭代器。
     *
     * @param listIterator 指定的列表迭代器。
     * @param generator    指定的只读生成器。
     * @param <E>          列表迭代器的泛型。
     * @return 根据指定的列表迭代器和指定的只读生成器生成的只读的列表迭代器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> ListIterator<E> readOnlyListIterator(ListIterator<E> listIterator,
                                                           ReadOnlyGenerator<E> generator) {
        Objects.requireNonNull(listIterator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_18));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_17));
        return new ReadOnlyListIterator<>(listIterator, generator);
    }

    private static final class ReadOnlyListIterator<E> implements ListIterator<E> {

        private final ListIterator<E> delegate;
        private final ReadOnlyGenerator<E> generator;

        public ReadOnlyListIterator(ListIterator<E> delegate, ReadOnlyGenerator<E> generator) {
            this.delegate = delegate;
            this.generator = generator;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E next() {
            return generator.readOnly(delegate.next());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasPrevious() {
            return delegate.hasPrevious();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E previous() {
            return generator.readOnly(delegate.previous());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int nextIndex() {
            return delegate.nextIndex();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int previousIndex() {
            return delegate.previousIndex();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("set");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("add");
        }

    }

    /**
     * 根据指定的集合和指定的只读生成器生成一个只读的集合。
     *
     * @param collection 指定的集合。
     * @param generator  指定的只读生成器。
     * @param <E>        只读集合的泛型。
     * @return 根据指定的集合和指定的只读生成器生成的只读集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> Collection<E> readOnlyCollection(Collection<E> collection, ReadOnlyGenerator<E> generator) {
        Objects.requireNonNull(collection, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_2));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_17));
        return new ReadOnlyCollection<>(collection, generator);
    }

    private static final class ReadOnlyCollection<E> implements Collection<E> {

        private final Collection<E> delegate;
        private final ReadOnlyGenerator<E> generator;

        public ReadOnlyCollection(Collection<E> delegate, ReadOnlyGenerator<E> generator) {
            this.delegate = delegate;
            this.generator = generator;
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
            return readOnlyIterator(delegate.iterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            @SuppressWarnings("unchecked")
            E[] eArray = (E[]) delegate.toArray();
            return ArrayUtil.readOnlyArray(eArray, generator);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            T[] tArray = delegate.toArray(a);
            return (T[]) ArrayUtil.readOnlyArray(((E[]) tArray), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
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
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegate.toString();
        }

    }

    /**
     * 根据指定的集合和指定的只读生成器生成一个只读的集合。
     *
     * @param set       指定的集合。
     * @param generator 指定的只读生成器。
     * @param <E>       只读集合的泛型。
     * @return 根据指定的集合和指定的只读生成器生成的只读集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> Set<E> readOnlySet(Set<E> set, ReadOnlyGenerator<E> generator) {
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_0));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_17));
        return new ReadOnlySet<>(set, generator);
    }

    private final static class ReadOnlySet<E> implements Set<E> {

        private final Set<E> delegate;
        private final ReadOnlyGenerator<E> generator;

        public ReadOnlySet(Set<E> delegate, ReadOnlyGenerator<E> generator) {
            this.delegate = delegate;
            this.generator = generator;
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
            return readOnlyIterator(delegate.iterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            @SuppressWarnings("unchecked")
            E[] eArray = (E[]) delegate.toArray();
            return ArrayUtil.readOnlyArray(eArray, generator);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            T[] tArray = delegate.toArray(a);
            return (T[]) ArrayUtil.readOnlyArray(((E[]) tArray), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
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
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return delegate.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == delegate)
                return true;
            if (obj == this)
                return true;
            return delegate.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegate.toString();
        }

    }

    /**
     * 根据指定的列表和指定的只读生成器生成一个只读的列表。
     *
     * @param list      指定的列表。
     * @param generator 指定的只读生成器。
     * @param <E>       只读列表的泛型。
     * @return 根据指定的列表和指定的只读生成器生成的只读列表。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <E> List<E> readOnlyList(List<E> list, ReadOnlyGenerator<E> generator) {
        Objects.requireNonNull(list, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_4));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_17));
        return new ReadOnlyList<>(list, generator);
    }

    private final static class ReadOnlyList<E> implements List<E> {

        private final List<E> delegate;
        private final ReadOnlyGenerator<E> generator;

        public ReadOnlyList(List<E> delegate, ReadOnlyGenerator<E> generator) {
            this.delegate = delegate;
            this.generator = generator;
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
            return CollectionUtil.readOnlyIterator(delegate.iterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object[] toArray() {
            @SuppressWarnings("unchecked")
            E[] eArray = (E[]) delegate.toArray();
            return ArrayUtil.readOnlyArray(eArray, generator);
        }

        /**
         * {@inheritDoc}
         */
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            T[] tArray = delegate.toArray(a);
            return (T[]) ArrayUtil.readOnlyArray(((E[]) tArray), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
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
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E get(int index) {
            return generator.readOnly(delegate.get(index));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E set(int index, E element) {
            throw new UnsupportedOperationException("set");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void add(int index, E element) {
            throw new UnsupportedOperationException("add");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E remove(int index) {
            throw new UnsupportedOperationException("remove");
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
            return readOnlyListIterator(delegate.listIterator(), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ListIterator<E> listIterator(int index) {
            return readOnlyListIterator(delegate.listIterator(index), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return new ReadOnlyList<>(delegate.subList(fromIndex, toIndex), generator);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return delegate.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == delegate)
                return true;
            if (obj == this)
                return true;
            return delegate.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegate.toString();
        }

    }

    /**
     * 根据指定的映射入口和指定的只读生成器生成一个只读的映射入口。
     *
     * @param entry    指定的映射入口。
     * @param keyGen   指定的键只读生成器。
     * @param valueGen 指定的值只读生成器
     * @param <K>      只读映射的键的泛型。
     * @param <V>      只读映射的值的泛型。
     * @return 根据指定的映射入口和指定的只读生成器生成的只读映射入口。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <K, V> Map.Entry<K, V> readOnlyMapEntry(Map.Entry<K, V> entry, ReadOnlyGenerator<K> keyGen,
                                                          ReadOnlyGenerator<V> valueGen) {
        Objects.requireNonNull(entry, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_21));
        Objects.requireNonNull(keyGen, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_19));
        Objects.requireNonNull(valueGen, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_20));
        return new ReadOnlyMapEntry<>(entry, keyGen, valueGen);
    }

    private static final class ReadOnlyMapEntry<K, V> implements Map.Entry<K, V> {

        private final Map.Entry<K, V> delegate;
        private final ReadOnlyGenerator<K> keyGen;
        private final ReadOnlyGenerator<V> valueGen;

        public ReadOnlyMapEntry(Entry<K, V> delegate, ReadOnlyGenerator<K> keyGen, ReadOnlyGenerator<V> valueGen) {
            this.delegate = delegate;
            this.keyGen = keyGen;
            this.valueGen = valueGen;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public K getKey() {
            return keyGen.readOnly(delegate.getKey());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V getValue() {
            return valueGen.readOnly(delegate.getValue());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("setValue");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return delegate.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == delegate)
                return true;
            if (obj == this)
                return true;
            return delegate.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegate.toString();
        }

    }

    /**
     * 根据指定的映射和指定的只读生成器生成一个只读的映射。
     *
     * @param map      指定的映射。
     * @param keyGen   指定的键只读生成器。
     * @param valueGen 指定的值只读生成器
     * @param <K>      只读映射的键的泛型。
     * @param <V>      只读的映射的值的泛型。
     * @return 根据指定的映射和指定的只读生成器生成的只读映射。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <K, V> Map<K, V> readOnlyMap(Map<K, V> map, ReadOnlyGenerator<K> keyGen,
                                               ReadOnlyGenerator<V> valueGen) {
        Objects.requireNonNull(map, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_5));
        Objects.requireNonNull(keyGen, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_19));
        Objects.requireNonNull(valueGen, DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_20));
        return new ReadOnlyMap<>(map, keyGen, valueGen);
    }

    private static final class ReadOnlyMap<K, V> implements Map<K, V> {

        private final Map<K, V> delegate;
        private final ReadOnlyGenerator<K> keyGen;
        private final ReadOnlyGenerator<V> valueGen;

        public ReadOnlyMap(Map<K, V> delegate, ReadOnlyGenerator<K> keyGen, ReadOnlyGenerator<V> valueGen) {
            this.delegate = delegate;
            this.keyGen = keyGen;
            this.valueGen = valueGen;
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
        public boolean containsKey(Object key) {
            return delegate.containsKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsValue(Object value) {
            return delegate.containsValue(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V get(Object key) {
            return valueGen.readOnly(delegate.get(key));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V put(K key, V value) {
            throw new UnsupportedOperationException("put");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V remove(Object key) {
            throw new UnsupportedOperationException("remove");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            throw new UnsupportedOperationException("putAll");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<K> keySet() {
            return readOnlySet(delegate.keySet(), keyGen);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Collection<V> values() {
            return readOnlyCollection(delegate.values(), valueGen);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<Entry<K, V>> entrySet() {
            return readOnlySet(delegate.entrySet(), element -> readOnlyMapEntry(element, keyGen, valueGen));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return delegate.hashCode();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == delegate)
                return true;
            if (obj == this)
                return true;
            return delegate.equals(obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return delegate.toString();
        }

    }
}
