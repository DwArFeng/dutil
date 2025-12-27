package com.dwarfeng.dutil.basic.cls;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.*;

/**
 * 类功能包。
 *
 * <p>
 * 该包封装了一些 {@link Class} 包中没有的功能，比如列出一个包的所有直到 {@link Object} 的所有父类。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class ClassUtil {

    /**
     * 获取一个类的所有父类。
     *
     * @param cl 指定的类。
     * @return 该类的所有父类组成的集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Collection<Class<?>> getSuperClasses(Class<?> cl) {
        Objects.requireNonNull(cl, DwarfUtil.getExceptionString(ExceptionStringKey.CLASSUTIL_0));
        Collection<Class<?>> collection = new HashSet<Class<?>>();
        Class<?> clas = cl.getSuperclass();
        while (Objects.nonNull(clas)) {
            collection.add(clas);
            clas = clas.getSuperclass();
        }
        return collection;
    }

    /**
     * 获取一个类的所有父类。
     *
     * @param o 指定的实例。
     * @return 该类的所有父类组成的集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Collection<Class<?>> getSuperClasses(Object o) {
        Objects.requireNonNull(o, DwarfUtil.getExceptionString(ExceptionStringKey.CLASSUTIL_1));
        return getSuperClasses(o.getClass());
    }

    /**
     * 获取一个实例的类中的所有父类。
     *
     * @param o 指定的实例。
     * @return 该实例的所有父类组成的数组。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Class<?>[] getSuperClassArray(Object o) {
        Objects.requireNonNull(o, DwarfUtil.getExceptionString(ExceptionStringKey.CLASSUTIL_1));
        return getSuperClasses(o).toArray(new Class<?>[0]);
    }

    /**
     * 获取一个类中的所有父类。
     *
     * @param cl 指定的类。
     * @return 该类的所有父类组成的数组。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Class<?>[] getSuperClassArray(Class<?> cl) {
        Objects.requireNonNull(cl, DwarfUtil.getExceptionString(ExceptionStringKey.CLASSUTIL_0));
        return getSuperClasses(cl).toArray(new Class<?>[0]);
    }

    /**
     * 获取一个类所实现的所有接口，包括自身以及父类实现的所有接口和实现接口的父接口。
     *
     * @param cl 指定的类。
     * @return 该类以及父类实现的所有接口组成的集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Collection<Class<?>> getImplInterfaces(Class<?> cl) {
        Objects.requireNonNull(cl, DwarfUtil.getExceptionString(ExceptionStringKey.CLASSUTIL_0));

        Collection<Class<?>> superIntr = new HashSet<Class<?>>();
        Set<Class<?>> intrPool = new HashSet<Class<?>>();

        intrPool.addAll(Arrays.asList(cl.getInterfaces()));
        for (Class<?> clas : getSuperClasses(cl)) {
            intrPool.addAll(Arrays.asList(clas.getInterfaces()));
        }

        while (intrPool.size() > 0) {
            Class<?> clas = intrPool.iterator().next();
            intrPool.remove(clas);

            if (clas.isInterface()) {
                superIntr.add(clas);
            }
            intrPool.addAll(Arrays.asList(clas.getInterfaces()));
        }

        return superIntr;
    }

    /**
     * 获取一个实例的类所实现的所有接口，包括自身以及父类实现的所有接口和实现接口的父接口。
     *
     * @param o 指定的实例。
     * @return 该实例的类以及父类实现的所有接口组成的集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Collection<Class<?>> getImplInterfaces(Object o) {
        Objects.requireNonNull(o, DwarfUtil.getExceptionString(ExceptionStringKey.CLASSUTIL_1));
        return getImplInterfaces(o.getClass());
    }

    /**
     * 获取一个类所实现的所有接口，包括自身以及父类实现的所有接口和实现接口的父接口。
     *
     * @param cl 指定的类。
     * @return 该类以及父类实现的所有接口组成的数组。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Class<?>[] getImplInterfacesArray(Class<?> cl) {
        Objects.requireNonNull(cl, DwarfUtil.getExceptionString(ExceptionStringKey.CLASSUTIL_0));
        return getImplInterfaces(cl).toArray(new Class<?>[0]);
    }

    /**
     * 获取一个实例的类所实现的所有接口，包括自身以及父类实现的所有接口和实现接口的父接口。
     *
     * @param o 指定的实例。
     * @return 该实例的类以及父类实现的所有接口组成的数组。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Class<?>[] getImplInterfacesArray(Object o) {
        Objects.requireNonNull(o, DwarfUtil.getExceptionString(ExceptionStringKey.CLASSUTIL_1));
        return getImplInterfaces(o).toArray(new Class<?>[0]);
    }

    /**
     * 获取指定类的包装类。
     *
     * <p>
     * 如果指定的类是基础类型的话，则返回其包装类型；否则，则返回其自身。
     *
     * @param cl 指定的类。
     * @return 指定的类的包装类。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 无法识别的基础类。
     */
    public static Class<?> getPackedClass(Class<?> cl) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(cl, DwarfUtil.getExceptionString(ExceptionStringKey.CLASSUTIL_0));

        if (!cl.isPrimitive()) {
            return cl;
        }

        if (cl == byte.class) {
            return Byte.class;
        } else if (cl == short.class) {
            return Short.class;
        } else if (cl == int.class) {
            return Integer.class;
        } else if (cl == long.class) {
            return Long.class;
        } else if (cl == float.class) {
            return Float.class;
        } else if (cl == double.class) {
            return Double.class;
        } else if (cl == char.class) {
            return Character.class;
        } else if (cl == boolean.class) {
            return Boolean.class;
        } else if (cl == void.class) {
            return Void.class;
        }

        throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.CLASSUTIL_4));
    }

    // 该类无法实例化
    private ClassUtil() {
    }
}
