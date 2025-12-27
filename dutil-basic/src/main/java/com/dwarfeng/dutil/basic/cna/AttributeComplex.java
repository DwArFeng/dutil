package com.dwarfeng.dutil.basic.cna;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.str.Name;

import java.util.*;
import java.util.Map.Entry;

/**
 * 属性复合体。
 *
 * <p>
 * 属性复合体用来存储一系列<b>数量已知</b>、<b>类型明确</b>的属性，将每一个属性与一个 <b>明确的</b> 键值对应起来用于查询。
 * 属性复合体的内部使用字符串-对象映射的方式存储一系列的属性。 同时，属性复合体拥有一个非常简单，易于书写的工厂方法。
 *
 * <p>
 * 属性复合体可以在需要返回或存储多余一个属性时使用，比如在一个方法需要返回多个参数时使用属性复合体——这样可以替代繁琐、一次性的自定义类结构。
 *
 * <p>
 * 与 Map 不一样的是，试图返回属性复合体中不存在的键对应的值不会返回
 * <code>null</code>，而是会抛出异常。其原因是该类的使用场合所决定的：一个方法的返回值，即便是有多个，也应该是明确的。
 * 如果返回键不存在的值，这是不合理的。
 *
 * <p>
 * 属性复合体不允许使用 <code>null</code> 值作为键，该任何尝试使用 <code>null</code> 值作为键的行为（比如使用含有
 * <code>null</code>值作为键的工厂方法）都会抛出异常。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class AttributeComplex {

    /**
     * 通过数组生成一个新的属性复合体。
     *
     * <p>
     * 使用指定格式的数组，快速的生成一个新的属性复合体。指定的数组需要遵循以下约束：
     *
     * <pre>
     * 1. 数组的元素格式必须是偶数。
     * 2. 数组的偶数下标对应的元素（第奇数个元素）必须是 String 类型的元素。
     * </pre>
     *
     * <p>
     * 该数组中的元素两个为一对，分别代表属性的键和属性的值。以下为一个例子：
     *
     * <blockquote><pre>
     * void example() {
     *     // 第 1,3,5 元素为属性的键；第 2,4,6 元素为 1,3,5 元素键对应的值。
     *     Object[] objects = new Object[] { "key.a", true, "key.b", false, "key.c", 12450 };
     *     AttributeComplex ac = AttributeComplex.newInstance(objects);
     *     CT.tract(ac.get("key.a", Boolean.class));// 输出 true。
     *     CT.tract(ac.get("key.b"));// 输出 false。
     *     CT.tract(ac.get("key.c", Integer.class));// 输出 12450。
     * }
     * </pre></blockquote>
     *
     * @param objects 指定的对象数组。
     * @return 通过指定的对象数组返回的属性复合体。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 对象数组的元素个数是奇数，或对象数组的奇数个元素为 <code>null</code>，或对象的奇数个元素不是
     *                                  <code>String</code> 或者 <code>Name</code> 对象。
     */
    public static AttributeComplex newInstance(Object[] objects)
            throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(objects, DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_0));

        // 确保数组中元素的个数是偶数。
        if (objects.length % 2 != 0) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_1));
        }

        Map<String, Object> delegate = new HashMap<>(objects.length / 2);

        for (int i = 0; i < objects.length; i += 2) {
            Object keyObject = objects[i];
            if (Objects.isNull(keyObject))
                throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_2));

            String key;
            if (keyObject instanceof String) {
                key = (String) keyObject;
            } else if (keyObject instanceof Name) {
                key = ((Name) keyObject).getName();
            } else {
                throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_3));
            }
            delegate.put(key, objects[i + 1]);
        }

        return new AttributeComplex(delegate);
    }

    /**
     * 属性复合体代理的映射，该映射是不可更改的。
     */
    protected final Map<String, Object> delegate;

    /**
     * 新实例。
     *
     * @param delegate 指定的代理映射。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 指定的代理映射含有 <code>null</code> 元素。
     */
    protected AttributeComplex(Map<String, Object> delegate) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(delegate, DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_4));
        if (delegate.containsKey(null)) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_5));
        }

        this.delegate = Collections.unmodifiableMap(delegate);
    }

    /**
     * 返回该属性复合体中拥有的属性的个数。
     *
     * @return 该属性复合体中拥有的属性的个数。
     */
    public int size() {
        return delegate.size();
    }

    /**
     * 返回该属性复合体是否是空的。
     *
     * @return 该属性复合体是否是空的。
     */
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * 返回该属性复合体是否含有指定的键值。
     *
     * @param key 指定的键值。
     * @return 该属性复合体是否含有指定的键值。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public boolean containsKey(String key) throws NullPointerException {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_6));
        return delegate.containsKey(key);
    }

    /**
     * 返回该属性复合体是否含有指定的键值。
     *
     * @param key 指定的键值对应的名称。
     * @return 该属性复合体是否含有指定的键值。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public boolean containsKey(Name key) throws NullPointerException {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_6));
        return containsKey(key.getName());
    }

    /**
     * 获取该属性复合体中指定键值对应的属性。
     *
     * @param key 指定的键值。
     * @return 指定的键值对应的属性。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 该属性复合体不包含指定的键值。
     */
    public Object get(String key) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_6));
        if (!containsKey(key)) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_7));
        }
        return delegate.get(key);
    }

    /**
     * 获取该属性复合体中指定键值对应的属性。
     *
     * @param key 指定的键值对应的名称。
     * @return 指定的键值对应的属性。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 该属性复合体不包含指定的键值。
     */
    public Object get(Name key) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_6));
        if (!containsKey(key)) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_7));
        }
        return get(key.getName());
    }

    /**
     * 获取该属性复合体中指定键值对应的属性。
     *
     * @param key  指定的键值。
     * @param clas 对应的属性被转换成的类型。
     * @return 该属性复合体中指定键值对应的属性。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 该属性复合体不包含指定的键值。
     * @throws ClassCastException       类型转换异常。
     */
    public <T> T get(String key, Class<T> clas)
            throws NullPointerException, IllegalArgumentException, ClassCastException {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_6));
        Objects.requireNonNull(clas, DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_8));
        if (!containsKey(key)) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_7));
        }
        return clas.cast(delegate.get(key));
    }

    /**
     * 获取该属性复合体中指定键值对应的属性。
     *
     * @param key  指定的键值对应的名称。
     * @param clas 对应的属性被转换成的类型。
     * @return 该属性复合体中指定键值对应的属性。
     * @throws NullPointerException     指定的入口参数为 <code> null </code>。
     * @throws IllegalArgumentException 该属性复合体不包含指定的键值。
     * @throws ClassCastException       类型转换异常。
     */
    public <T> T get(Name key, Class<T> clas)
            throws NullPointerException, IllegalArgumentException, ClassCastException {
        Objects.requireNonNull(key, DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_6));
        Objects.requireNonNull(clas, DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_8));
        if (!containsKey(key)) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.ATTRIBUTECOMPLEX_7));
        }
        return get(key.getName(), clas);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AttributeComplex))
            return false;

        AttributeComplex that = (AttributeComplex) o;
        return Objects.equals(this.delegate, that.delegate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return delegate.hashCode() + 17;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Iterator<Entry<String, Object>> i = delegate.entrySet().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            Entry<String, Object> e = i.next();
            String key = e.getKey();
            Object value = e.getValue();
            sb.append(key);
            sb.append('=');
            sb.append(value == this ? "(this AttributeComplex)" : value);
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }
}
