package com.dwarfeng.dutil.basic.cna;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.prog.ReadOnlyGenerator;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 有关于数组的工具包。
 *
 * <p>
 * 该包中包含关于对数组进行操作的常用方法。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class ArrayUtil {

    private ArrayUtil() {
        // 不允许实例化。
    }

    /**
     * 获取一个指定类型的空数组。
     *
     * @param clas 指定的类型。
     * @return 指定类型的空数组。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static <T> T[] empty(Class<T> clas) throws NullPointerException {
        Objects.requireNonNull(clas, DwarfUtil.getExceptionString(ExceptionStringKey.ARRAYUTIL_5));
        // 根据 Array 的相关说明，此处类型转换是安全的。
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(clas, 0);
        return array;
    }

    /**
     * 判断数组中是否包含某个元素。
     *
     * <p>
     * 该方法会将目标数组中的每一个对象拿出来与目标对象进行 equals 比对，如果为 true，就返回 true；如果全部元素为 false
     * 则返回 false。<br>
     * 由于该方法利用 equals 方法，而有些对象会重写该方法，因此该比对的结果不以内存中的位置为准。
     *
     * <p>
     * 如果目标数组为 <code>null</code>，则返回 <code>false</code>
     *
     * @param objects 目标数组。
     * @param object  目标元素。
     * @return 目标数组中是否含有目标元素。
     */
    public static boolean contains(Object[] objects, Object object) {
        if (Objects.isNull(objects))
            return false;
        for (Object o : objects) {
            if (Objects.equals(o, object))
                return true;
        }
        return false;
    }

    /**
     * 判断源数组是否包含目标数组的所有对象。
     *
     * <p>
     * 该方法会对目标数组中的每一个元 o 素执行<code>contains(source,o)</code>，直到全部测试完或者发现其中一个 o 不在源数组中。
     *
     * <p>
     * 如果数组为 <code>null</code>，则默认其为不含有元素的空数组。
     *
     * @param source 源数组。
     * @param target 目标数组。
     * @return 源数组是否包含目标数组的全部元素。
     * @see ArrayUtil#contains(Object[], Object)
     */
    public static boolean containsAll(Object[] source, Object[] target) {
        if (Objects.isNull(target))
            return false;
        for (Object o : target) {
            if (!contains(source, o))
                return false;
        }
        return true;
    }

    /**
     * 在一个数组剔除其中所有的 null 元素，并把不是 null 的元素按照原有的顺序以数组的形式返回。
     *
     * <p>
     * 如果元素数组为 <code>null</code> 则返回一个空的数组。
     *
     * @param objects 元素数组。
     * @param t       返回的数组类型，比如<code> new Object[0]</code>。
     * @param <T>     泛型 T
     * @return 返回的数组泛型。
     * @deprecated 该方法性能太差，应该优先使用 {@link #getNonNull(Object[])};
     */
    public static <T> T[] getNotNull(Object[] objects, T[] t) {
        ArrayList<Object> col = new ArrayList<Object>(objects.length);
        if (objects != null) {
            for (Object o : objects) {
                if (o != null)
                    col.add(o);
            }
        }
        return col.toArray(t);
    }

    /**
     * 在一个数组剔除其中所有的 null 元素，并把不是 null 的元素按照原有的顺序以集合的形式返回。
     *
     * <p>
     * 如果元素数组为 <code>null</code> 则返回一个空的集合。
     *
     * @param object 元素数组。
     * @param <T>    泛型 T
     * @return 返回的集合。
     * @deprecated 该方法性能太差，且不符合 ArrayUtil 工具包的功能。
     */
    public static <T> Collection<T> getNotNull(T[] object) {
        Collection<T> col = new Vector<T>(object.length);
        if (object != null) {
            for (T o : object) {
                if (o != null)
                    col.add(o);
            }
        }
        return col;
    }

    /**
     * 在一个数组剔除其中所有的 null 元素，并把不是 null 的元素按照原有的顺序以数组的形式返回。
     *
     * <p>
     * 如果元素数组为 <code>null</code> 则返回一个空的数组。
     *
     * @param array 指定数组。
     * @return 返回的不含有 <code>null</code> 元素的数组。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static <T> T[] getNonNull(T[] array) throws NullPointerException {
        Objects.requireNonNull(array, DwarfUtil.getExceptionString(ExceptionStringKey.ARRAYUTIL_3));

        Object[] target = new Object[array.length];
        int j = 0;

        for (int i = 0; i < array.length; i++) {
            T t = array[i];
            if (Objects.nonNull(t))
                target[j++] = t;
        }

        // 由于 target 数组中只含有 T 类型元素，因此该类型转换安全。
        @SuppressWarnings("unchecked")
        T[] dejavu = (T[]) Arrays.copyOf(target, j, array.getClass());
        return dejavu;
    }

    /**
     * 将两个数组合并。
     *
     * <p>
     * 两个数组均不能为 null。
     *
     * @param first  第一个数组。
     * @param second 第二个数组。
     * @param <T>    泛型 T。
     * @return 两个数组按照先后顺序合并后得到的数组。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <T> T[] concat(T[] first, T[] second) {
        Objects.requireNonNull(first, DwarfUtil.getExceptionString(ExceptionStringKey.ARRAYUTIL_0));
        Objects.requireNonNull(second, DwarfUtil.getExceptionString(ExceptionStringKey.ARRAYUTIL_1));

        int totalLength = first.length + second.length;
        T[] result = Arrays.copyOf(first, totalLength);
        System.arraycopy(second, 0, result, first.length, second.length);

        return result;
    }

    /**
     * 将多个数组合并。
     *
     * <p>
     * 如果数组为 <code>null</code>，则被当做不含有任何元素的数组。 首个数组不能为 <code>null</code>。
     *
     * @param first 第一个数组。
     * @param rest  第二个或更多个数组。
     * @param <T>   泛型 T。
     * @return 所有数组按先后顺序合并后得到的数组。
     * @throws NullPointerException 入口参数 <code>rest</code>为 <code>null</code>。
     */
    public static <T> T[] concat(T[] first, T[][] rest) {
        Objects.requireNonNull(first, DwarfUtil.getExceptionString(ExceptionStringKey.ARRAYUTIL_0));

        if (Objects.isNull(rest))
            return first;

        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            if (Objects.nonNull(array)) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }
        return result;
    }

    /**
     * 判断数组是否含有空元素。
     *
     * <p>
     * 当且仅当数组本身不为 <code>null</code>且不含有任何 <code>null</code>元素是返回
     * <code>true</code>。
     *
     * @param objs 待判断的数组。
     * @return 数组是否含有空元素。
     */
    public static boolean isContainsNull(Object[] objs) {
        if (Objects.isNull(objs))
            return false;
        for (Object obj : objs) {
            if (Objects.isNull(obj))
                return false;
        }
        return true;
    }

    /**
     * 确保指定的数组不含有任何 <code>null</code>元素，如果有，则抛出异常。
     *
     * @param objs 指定的数组。
     * @throws NullPointerException 指定数组本身是 <code>null</code>或其中含有 <code>null</code>元素时 抛出该异常。
     */
    public static void requireNotContainsNull(Object[] objs) throws NullPointerException {
        Objects.requireNonNull(objs);
        for (Object obj : objs) {
            Objects.requireNonNull(obj);
        }
    }

    /**
     * 确保指定的数组不含有任何 <code>null</code>元素，如果有，则抛出异常。
     *
     * @param objs    指定的数组。
     * @param message 抛出异常时的信息。
     * @throws NullPointerException 指定数组本身是 <code>null</code>或其中含有 <code>null</code>元素时
     *                              抛出该信息为指定字符串的异常。
     */
    public static void requireNotContainsNull(Object[] objs, String message) throws NullPointerException {
        Objects.requireNonNull(objs, message);
        for (Object obj : objs) {
            Objects.requireNonNull(obj, message);
        }
    }

    /**
     * 对 Byte 数组拆包。
     *
     * @param target 指定的 Byte 数组。
     * @return 拆包后得到的基本类型数组。
     */
    public static byte[] unpack(Byte[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        target = getNotNull(target, new Byte[0]);

        byte[] bytes = new byte[target.length];
        for (int i = 0; i < target.length; i++) {
            bytes[i] = target[i];
        }
        return bytes;
    }

    /**
     * 对 Short 数组拆包。
     *
     * @param target 指定的 Short 数组。
     * @return 拆包后得到的基本类型数组。
     */
    public static short[] unpack(Short[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        target = getNotNull(target, new Short[0]);

        short[] shorts = new short[target.length];
        for (int i = 0; i < target.length; i++) {
            shorts[i] = target[i];
        }
        return shorts;
    }

    /**
     * 对 Integer 数组拆包。
     *
     * @param target 指定的 Integer 数组。
     * @return 拆包后得到的基本类型数组。
     */
    public static int[] unpack(Integer[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        target = getNotNull(target, new Integer[0]);

        int[] ints = new int[target.length];
        for (int i = 0; i < target.length; i++) {
            ints[i] = target[i];
        }
        return ints;
    }

    /**
     * 对 Float 数组拆包。
     *
     * @param target 指定的 Float 数组。
     * @return 拆包后得到的基本类型数组。
     */
    public static float[] unpack(Float[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        target = getNotNull(target, new Float[0]);

        float[] floats = new float[target.length];
        for (int i = 0; i < target.length; i++) {
            floats[i] = target[i];
        }
        return floats;
    }

    /**
     * 对 Long 数组拆包。
     *
     * @param target 指定的 Long 数组。
     * @return 拆包后得到的基本类型数组。
     */
    public static long[] unpack(Long[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        target = getNotNull(target, new Long[0]);

        long[] longs = new long[target.length];
        for (int i = 0; i < target.length; i++) {
            longs[i] = target[i];
        }
        return longs;
    }

    /**
     * 对 Character 数组进行拆包。
     *
     * @param target 指定的 Character 数组。
     * @return 拆包后得到的基本数组。
     */
    public static char[] unpack(Character[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        target = getNotNull(target, new Character[0]);

        char[] charss = new char[target.length];
        for (int i = 0; i < target.length; i++) {
            charss[i] = target[i];
        }
        return charss;
    }

    /**
     * 对 Boolean 数组进行拆包。
     *
     * @param target 指定的 Boolean 数组。
     * @return 拆包后得到的基本数组。
     */
    public static boolean[] unpack(Boolean[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        target = getNotNull(target, new Boolean[0]);

        boolean[] booleans = new boolean[target.length];
        for (int i = 0; i < target.length; i++) {
            booleans[i] = target[i];
        }
        return booleans;
    }

    /**
     * 对 byte 数组进行打包。
     *
     * @param target 指定的 byte 数组。
     * @return 打包后得到的封包数组。
     */
    public static Byte[] pack(byte[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        Byte[] bytes = new Byte[target.length];
        for (int i = 0; i < target.length; i++) {
            bytes[i] = target[i];
        }
        return bytes;
    }

    /**
     * 对 short 数组进行打包。
     *
     * @param target 指定的 short 数组。
     * @return 打包后得到的封包数组。
     */
    public static Short[] pack(short[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        Short[] shorts = new Short[target.length];
        for (int i = 0; i < target.length; i++) {
            shorts[i] = target[i];
        }
        return shorts;
    }

    /**
     * 对 int 数组进行打包。
     *
     * @param target 指定的 int 数组。
     * @return 打包后得到的封包数组。
     */
    public static Integer[] pack(int[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        Integer[] integers = new Integer[target.length];
        for (int i = 0; i < target.length; i++) {
            integers[i] = target[i];
        }
        return integers;
    }

    /**
     * 对 long 数组进行打包。
     *
     * @param target 指定的 long 数组。
     * @return 打包后得到的封包数组。
     */
    public static Long[] pack(long[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        Long[] longs = new Long[target.length];
        for (int i = 0; i < target.length; i++) {
            longs[i] = target[i];
        }
        return longs;
    }

    /**
     * 对 float 数组进行打包。
     *
     * @param target 指定的 float 数组。
     * @return 打包后得到的封包数组。
     * @see #pack(float[])
     * @deprecated 该方法由于不符合命名规范，已经用 {@link #pack(float[])} 代替。
     */
    @Deprecated
    public static Float[] Pack(float[] target) {
        return pack(target);
    }

    /**
     * 对 float 数组进行打包。
     *
     * @param target 指定的 float 数组。
     * @return 打包后得到的封包数组。
     */
    public static Float[] pack(float[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        Float[] floats = new Float[target.length];
        for (int i = 0; i < target.length; i++) {
            floats[i] = target[i];
        }
        return floats;

    }

    /**
     * 对 double 数组进行打包。
     *
     * @param target 指定的 double 数组。
     * @return 打包后得到的封包数组。
     * @see #pack(double[])
     * @deprecated 由于该方法不符合命名规范，已经用 {@link #pack(double[])} 代替。
     */
    @Deprecated
    public static Double[] Pack(double[] target) {
        return pack(target);
    }

    /**
     * 对 double 数组进行打包。
     *
     * @param target 指定的 double 数组。
     * @return 打包后得到的封包数组。
     */
    public static Double[] pack(double[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        Double[] doubles = new Double[target.length];
        for (int i = 0; i < target.length; i++) {
            doubles[i] = target[i];
        }
        return doubles;
    }

    /**
     * 对 char 数组进行打包。
     *
     * @param target 指定的 char 数组。
     * @return 打包后得到的封包数组。
     * @see #pack(char[])
     * @deprecated 由于该方法不符合命名规范，已经用 {@link #pack(char[])} 代替。
     */
    @Deprecated
    public static Character[] Pack(char[] target) {
        return pack(target);
    }

    /**
     * 对 char 数组进行打包。
     *
     * @param target 指定的 char 数组。
     * @return 打包后得到的封包数组。
     */
    public static Character[] pack(char[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        Character[] characters = new Character[target.length];
        for (int i = 0; i < target.length; i++) {
            characters[i] = target[i];
        }
        return characters;
    }

    /**
     * 对 boolean 数组进行打包。
     *
     * @param target 指定的 boolean 数组。
     * @return 打包后得到的封包数组。
     * @see #pack(boolean[])
     * @deprecated 由于该方法不符合命名规范，已经用 {@link #pack(boolean[])} 代替。
     */
    @Deprecated
    public static Boolean[] Pack(boolean[] target) {
        return pack(target);
    }

    /**
     * 对boolean 数组进行打包。
     *
     * @param target 指定的boolean 数组。
     * @return 打包后得到的封包数组。
     */
    public static Boolean[] pack(boolean[] target) {

        if (target == null)
            throw new NullPointerException(DwarfUtil.getExceptionString(ExceptionStringKey.COLLECTIONUTIL_11));

        Boolean[] booleans = new Boolean[target.length];
        for (int i = 0; i < target.length; i++) {
            booleans[i] = target[i];
        }
        return booleans;
    }

    /**
     * 检测指定的序号是否落在数组的边界中。
     *
     * @param objs  指定的数组。
     * @param index 指定的序号。
     * @return 如果序号落在数组的边界中，则返回 <code>true</code>。
     */
    public static boolean checkBounds(Object[] objs, int index) {
        return index >= 0 && index < objs.length;
    }

    /**
     * 要求指定的需要落在指定数组的边界内。
     *
     * @param objs  指定的数组。
     * @param index 指定的序号。
     * @throws IndexOutOfBoundsException 如果指定的序号没有落在指定的数组中。
     */
    public static void requireInBounds(Object[] objs, int index) {
        if (index >= 0 && index < objs.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * 要求指定的需要落在指定数组的边界内。
     *
     * @param objs    指定的数组。
     * @param index   指定的序号。
     * @param message 异常的信息。
     * @throws IndexOutOfBoundsException 如果指定的序号没有落在指定的数组中。
     */
    public static void requireInBounds(Object[] objs, int index, String message) {
        if (index >= 0 && index < objs.length) {
            throw new IndexOutOfBoundsException(message);
        }
    }

    private static final class ArrayIterable<T> implements Iterable<T> {

        private final T[] arr;

        public ArrayIterable(T[] arr) {
            this.arr = arr;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Iterator<T> iterator() {
            return new ArrayIterator();
        }

        private final class ArrayIterator implements Iterator<T> {

            private int index = 0;

            /**
             * {@inheritDoc}
             */
            @Override
            public boolean hasNext() {
                return index < arr.length;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public T next() {
                return arr[index++];
            }

        }
    }

    /**
     * 将一个数组转化为一个可迭代对象。
     *
     * <p>
     * 虽然数组可以使用 for-each 循环，但是数组不可以作为 {@link Iterable} 对象进行参数传递，该方法为了解决这一问题，
     * 可以将一个数组转化为一个 {@link Iterable} 象，方便某些需要传入可迭代对象的场合。
     *
     * @param array 指定的数组。
     * @param <T>   泛型T。
     * @return 由指定的数组转化而成的可迭代对象。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <T> Iterable<T> array2Iterable(T[] array) {
        Objects.requireNonNull(array, DwarfUtil.getExceptionString(ExceptionStringKey.ARRAYUTIL_2));
        return new ArrayIterable<>(array);
    }

    /**
     * 根据指定的数组和指定的只读生成器生成一个只读的数组。
     *
     * @param array     指定的数组。
     * @param generator 指定的只读生成器。
     * @param <T>       数组的类型。
     * @return 根据指定的数组和指定的只读生成器生成一个只读的数组。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static <T> T[] readOnlyArray(T[] array, ReadOnlyGenerator<T> generator) {
        Objects.requireNonNull(array, DwarfUtil.getExceptionString(ExceptionStringKey.ARRAYUTIL_3));
        Objects.requireNonNull(generator, DwarfUtil.getExceptionString(ExceptionStringKey.ARRAYUTIL_4));

        @SuppressWarnings("unchecked")
        T[] tarArr = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), array.length);

        System.arraycopy(array, 0, tarArr, 0, array.length);

        return tarArr;
    }
}
