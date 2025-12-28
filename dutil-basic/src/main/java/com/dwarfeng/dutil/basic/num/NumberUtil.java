package com.dwarfeng.dutil.basic.num;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.num.unit.Angle;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * 数据转换类。
 *
 * <p>
 * 提供常用的数据转换方法与常用的数据结构与 byte 数组相互转换的方法。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * <p>
 * 该类提供单位换算相关的方法。<br>
 * 请注意，换算方法由于涉及浮点运算，精度会稍有误差。<br>
 * 该包定义了部分常用的换算枚举，这样就避免了用户在常见的单位换算中花费时间。比如 {@linkplain Angle}<br>
 * 请注意，虽然换算方法中，<code>u1</code>和<code>u2</code>可以用不同枚举中的 字段，但是这样是没有意义的，请不要这样做。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class NumberUtil {

    // 不能进行实例化。
    private NumberUtil() {
    }

    /**
     * 将 int 数据类型转化为 byte 数组。
     *
     * @param i 指定的 int 数据类型。
     * @return 转换后的 byte 数组。
     */
    public static byte[] int2Byte(int i) {

        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(i);
        return buffer.array();
    }

    /**
     * 将指定的 byte 数组转换为 int 数据类型。
     *
     * <p>
     * 指定的 byte 数组将会自动的被裁剪或添加到 4 位。
     *
     * @param bytes 指定的 byte 数组。
     * @return 转换后的 int 数据类型。
     */
    public static int byte2Int(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(TrimToSize(bytes, 4)).flip();
        return buffer.getInt();
    }

    /**
     * 将 long 数据类型转换为 byte 数组。
     *
     * @param l 指定的 long 数据类型。
     * @return 转换后的 byte 数组。
     */
    public static byte[] long2Byte(long l) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(l);
        return buffer.array();
    }

    /**
     * 将 byte 数组转换为 long 数据类型。
     *
     * <p>
     * 指定的 byte 数组将会自动被裁剪或添加到 8 位。
     *
     * @param bytes 指定的 byte 数组。
     * @return 转换后的 long 数据类型。
     */
    public static long byte2Long(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(TrimToSize(bytes, 8)).flip();
        return buffer.getLong();
    }

    /**
     * 将 float 数据类型转换为 byte 数组。
     *
     * @param f 指定的 float 数据类型。
     * @return 转换后的 byte 数组。
     */
    public static byte[] float2Byte(float f) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putFloat(f);
        return buffer.array();
    }

    /**
     * 将 byte 数组转换为 float 数据类型。
     *
     * <p>
     * 指定的 byte 数组将会自动被裁剪或添加到 4 位。
     *
     * @param bytes 指定的 byte 数组。
     * @return 转换后的 float 数据类型。
     */
    public static float byte2Float(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(TrimToSize(bytes, 4)).flip();
        return buffer.getFloat();
    }

    /**
     * 将 double 类型转换为 byte 数组。
     *
     * @param d 指定的 double 数据类型。
     * @return 转换后的 byte 数组。
     */
    public static byte[] double2Byte(double d) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putDouble(d);
        return buffer.array();
    }

    /**
     * 将 byte 数组转换为 double 数据类型。
     *
     * <p>
     * 指定的 byte 数组将会自动被裁剪或添加到 8 位。
     *
     * @param bytes 指定的 byte 数组。
     * @return 转换后的 double 数据类型。
     */
    public static double byte2Double(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(TrimToSize(bytes, 8)).flip();
        return buffer.getDouble();
    }

    /**
     * 将 short 数据类型转换为 byte 数组。
     *
     * @param s 指定的 short 数据类型。
     * @return 转换后的 byte 数组。
     */
    public static byte[] short2Double(short s) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putShort(s);
        return buffer.array();
    }

    /**
     * 将 byte 数组转换为 short 数据类型。
     *
     * <p>
     * 指定的 byte 数组将会自动的被裁剪或添加到 2 位。
     *
     * @param bytes 指定的 byte 数组。
     * @return 转换后的 short 数据类型。
     */
    public static short byte2Short(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put(TrimToSize(bytes, 2)).flip();
        return buffer.getShort();
    }

    /**
     * 将 byte 数组进行裁剪到达指定的位数。 如果数组的位数多余指定的位数，则进行裁剪；如果数组的位数少于指定的位数，则进行补 0。
     *
     * @param bytes  指定的数组。
     * @param length 需要裁剪到的长度。
     * @return 裁剪后的数组。
     */
    private static byte[] TrimToSize(byte[] bytes, int length) {
        // 特殊情况：裁剪数组的长度正好为所需的长度。
        if (bytes.length == length) {
            return bytes;
        }
        // 一般情况：裁剪或者补 0。
        byte[] target = new byte[length];
        if (bytes.length > length) {
            System.arraycopy(bytes, 0, target, 0, length);
        } else {
            System.arraycopy(bytes, 0, target, 0, bytes.length);
            for (int i = bytes.length; i < length; i++) {
                target[i] = 0;
            }
        }
        // 返回裁剪后的数组。
        return target;
    }

    /**
     * 将一个整型数截取低八位后，输出其 byte 形式。
     *
     * @param b 指定的整型数。
     * @return 截取低八位后的byte 形式。
     */
    public static byte cutInt2Byte(int b) {
        return (byte) (b & 0xFF);
    }

    /**
     * 将指定的值由一个单位转换为另一个单位。
     *
     * @param val 待转换的值。
     * @param u1  该值的现有单位权重。
     * @param u2  目标单位的权重。
     * @return 该值在目标单位下的值。
     */
    public static NumberValue unitTrans(NumberValue val, NumberValue u1, NumberValue u2) {

        return new NumberValue() {

            /**
             * {@inheritDoc}
             */
            @Override
            public double doubleValue() {
                return (val.doubleValue()) / (u1.doubleValue()) * (u2.doubleValue());
            }
        };
    }

    /**
     * 将指定的值由一个单位转换为另一个单位。
     *
     * @param val 待转换的值。
     * @param u1  该值的现有单位权重。
     * @param u2  目标单位的权重。
     * @return 该值在目标单位下的值。
     */
    public static NumberValue unitTrans(float val, NumberValue u1, NumberValue u2) {

        return new NumberValue() {

            /**
             * {@inheritDoc}
             */
            @Override
            public double doubleValue() {
                return ((double) val) / (u1.doubleValue()) * (u2.doubleValue());
            }
        };
    }

    /**
     * 将指定的值由一个单位转换为另一个单位。
     *
     * @param val 待转换的值。
     * @param u1  该值的现有单位权重。
     * @param u2  目标单位的权重。
     * @return 该值在目标单位下的值。
     */
    public static NumberValue unitTrans(double val, NumberValue u1, NumberValue u2) {

        return new NumberValue() {

            /**
             * {@inheritDoc}
             */
            @Override
            public double doubleValue() {
                return val / (u1.doubleValue()) * (u2.doubleValue());
            }
        };
    }

    /**
     * 将指定的值由一个单位转换为另一个单位。
     *
     * @param val 待转换的值。
     * @param u1  该值的现有单位权重。
     * @param u2  目标单位的权重。
     * @return 该值在目标单位下的值。
     */
    public static NumberValue unitTrans(long val, NumberValue u1, NumberValue u2) {

        return new NumberValue() {

            /**
             * {@inheritDoc}
             */
            @Override
            public double doubleValue() {
                return ((double) val) / (u1.doubleValue()) * (u2.doubleValue());
            }
        };
    }

    /**
     * 将指定的值由一个单位转换为另一个单位。
     *
     * @param val 待转换的值。
     * @param u1  该值的现有单位权重。
     * @param u2  目标单位的权重。
     * @return 该值在目标单位下的值。
     */
    public static NumberValue unitTrans(short val, NumberValue u1, NumberValue u2) {

        return new NumberValue() {

            /**
             * {@inheritDoc}
             */
            @Override
            public double doubleValue() {
                return ((double) val) / (u1.doubleValue()) * (u2.doubleValue());
            }
        };
    }

    /**
     * 将指定的值由一个单位转换为另一个单位。
     *
     * @param val 待转换的值。
     * @param u1  该值的现有单位权重。
     * @param u2  目标单位的权重。
     * @return 该值在目标单位下的值。
     */
    public static NumberValue unitTrans(int val, NumberValue u1, NumberValue u2) {

        return new NumberValue() {

            /**
             * {@inheritDoc}
             */
            @Override
            public double doubleValue() {
                return ((double) val) / (u1.doubleValue()) * (u2.doubleValue());
            }
        };
    }

    /**
     * 将指定的值由一个单位转换为另一个单位。
     *
     * @param val 待转换的值。
     * @param u1  该值的现有单位权重。
     * @param u2  目标单位的权重。
     * @return 该值在目标单位下的值。
     */
    public static NumberValue unitTrans(byte val, NumberValue u1, NumberValue u2) {

        return new NumberValue() {

            /**
             * {@inheritDoc}
             */
            @Override
            public double doubleValue() {
                return ((double) val) / (u1.doubleValue()) * (u2.doubleValue());
            }
        };
    }

    /**
     * 返回一组整型数据中的最大值。
     *
     * <p>
     * 如果不传入任何参数，则返回 <code>0</code>。
     *
     * @param is 整型数组。
     * @return 最大值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static int max(int... is) {
        Objects.requireNonNull(is, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_0));
        if (is.length == 0)
            return 0;
        if (is.length == 1)
            return is[0];
        if (is.length == 2)
            return Math.max(is[0], is[1]);
        int max = is[0];
        for (int i = 1; i < is.length; i++) {
            max = Math.max(max, is[i]);
        }
        return max;
    }

    /**
     * 返回一组整型数据中的最小值。
     *
     * <p>
     * 如果不传入任何参数，则返回 <code>0</code>。
     *
     * @param is 整型数组。
     * @return 最小值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static int min(int... is) {
        Objects.requireNonNull(is, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_0));
        if (is.length == 0)
            return 0;
        if (is.length == 1)
            return is[0];
        if (is.length == 2)
            return Math.min(is[0], is[1]);
        int min = is[0];
        for (int i = 1; i < is.length; i++) {
            min = Math.min(min, is[i]);
        }
        return min;
    }

    /**
     * 返回一组双精度浮点数据中的最大值。
     *
     * <p>
     * 如果不传入任何参数，则返回 <code>0</code>。
     *
     * @param ds 双精度浮点数组。
     * @return 最大值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static double max(double... ds) {
        Objects.requireNonNull(ds, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_1));
        if (ds.length == 0)
            return 0;
        if (ds.length == 1)
            return ds[0];
        if (ds.length == 2)
            return Math.max(ds[0], ds[1]);
        double max = ds[0];
        for (int i = 1; i < ds.length; i++) {
            max = Math.max(max, ds[i]);
        }
        return max;
    }

    /**
     * 返回一组双精度浮点数据中的最小值。
     *
     * <p>
     * 如果不传入任何参数，则返回 <code>0</code>。
     *
     * @param ds 双精度浮点数组。
     * @return 最小值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static double min(double... ds) {
        Objects.requireNonNull(ds, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_1));
        if (ds.length == 0)
            return 0;
        if (ds.length == 1)
            return ds[0];
        if (ds.length == 2)
            return Math.min(ds[0], ds[1]);
        double min = ds[0];
        for (int i = 1; i < ds.length; i++) {
            min = Math.min(min, ds[i]);
        }
        return min;
    }

    /**
     * 判断一个数是否在一个区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间。
     * @return 指定的数值是否在指定的区间之内。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static boolean isInInterval(int value, Interval interval) {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        return interval.contains(value);
    }

    /**
     * 判断一个数是否在一个区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间。
     * @return 指定的数值是否在指定的区间之内。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static boolean isInInterval(double value, Interval interval) {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        return interval.contains(value);
    }

    /**
     * 判断一个数是否在一个区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间。
     * @return 指定的数值是否在指定的区间之内。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static boolean isInInterval(BigDecimal value, Interval interval) {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        return interval.contains(value);
    }

    /**
     * 判断一个数是否在一个区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间的字符串形式。
     * @return 指定的数值是否在指定的区间之内。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 参数 <code>interval</code>不符区间的合格式要求。
     * @see Interval#parseInterval(String)
     */
    public static boolean isInInterval(int value, String interval) {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        return isInInterval(value, Interval.parseInterval(interval));
    }

    /**
     * 判断一个数是否在一个区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间的字符串形式。
     * @return 指定的数值是否在指定的区间之内。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 参数 <code>interval</code>不符区间的合格式要求。
     * @see Interval#parseInterval(String)
     */
    public static boolean isInInterval(double value, String interval) {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        return isInInterval(value, Interval.parseInterval(interval));
    }

    /**
     * 判断一个数是否在一个区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间的字符串形式。
     * @return 指定的数值是否在指定的区间之内。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 参数 <code>interval</code>不符区间的合格式要求。
     * @see Interval#parseInterval(String)
     */
    public static boolean isInInterval(BigDecimal value, String interval) {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        return isInInterval(value, Interval.parseInterval(interval));
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间的文本形式。
     * @throws OutOfIntervalException 指定的数值不在指定的区间之内。
     * @throws NullPointerException   入口参数为 <code>null</code>。
     */
    public static void requireInInterval(int value, Interval interval) throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        requireInInterval(value, interval, null);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间。
     * @throws OutOfIntervalException 指定的数值不在指定的区间之内。
     * @throws NullPointerException   入口参数为 <code>null</code>。
     */
    public static void requireInInterval(double value, Interval interval) throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        requireInInterval(value, interval, null);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间。
     * @throws OutOfIntervalException 指定的数值不在指定的区间之内。
     * @throws NullPointerException   入口参数为 <code>null</code>。
     */
    public static void requireInInterval(BigDecimal value, Interval interval) throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        requireInInterval(value, interval, null);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间的文本形式。
     * @throws OutOfIntervalException 指定的数值不在指定的区间之内。
     * @throws NullPointerException   入口参数为 <code>null</code>。
     */
    public static void requireInInterval(int value, String interval) throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        requireInInterval(value, interval, null);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间的文本形式。
     * @throws OutOfIntervalException 指定的数值不在指定的区间之内。
     * @throws NullPointerException   入口参数为 <code>null</code>。
     */
    public static void requireInInterval(double value, String interval) throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        requireInInterval(value, interval, null);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间的文本形式。
     * @throws OutOfIntervalException 指定的数值不在指定的区间之内。
     * @throws NullPointerException   入口参数为 <code>null</code>。
     */
    public static void requireInInterval(BigDecimal value, String interval) throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        requireInInterval(value, interval, null);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间。
     * @param message  指定的异常信息。
     * @throws OutOfIntervalException 指定的数值不在指定的区间之内。
     * @throws NullPointerException   入口参数为 <code>null</code>。
     */
    public static void requireInInterval(int value, Interval interval, String message) throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        if (!isInInterval(value, interval))
            throw new OutOfIntervalException(message);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间。
     * @param message  指定的异常信息。
     * @throws OutOfIntervalException 指定的数值不在指定的区间之内。
     * @throws NullPointerException   入口参数为 <code>null</code>。
     */
    public static void requireInInterval(double value, Interval interval, String message)
            throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        if (!isInInterval(value, interval))
            throw new OutOfIntervalException(message);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间。
     * @param message  指定的异常信息。
     * @throws OutOfIntervalException 指定的数值不在指定的区间之内。
     * @throws NullPointerException   入口参数为 <code>null</code>。
     */
    public static void requireInInterval(BigDecimal value, Interval interval, String message)
            throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        if (!isInInterval(value, interval))
            throw new OutOfIntervalException(message);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间的文本形式。
     * @param message  指定的异常信息。
     * @throws OutOfIntervalException   指定的数值不在指定的区间之内。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 参数 <code>interval</code>不符区间的合格式要求。
     * @see Interval#parseInterval(String)
     */
    public static void requireInInterval(int value, String interval, String message) throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        requireInInterval(value, Interval.parseInterval(interval), message);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间的文本形式。
     * @param message  指定的异常信息。
     * @throws OutOfIntervalException   指定的数值不在指定的区间之内。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 参数 <code>interval</code>不符区间的合格式要求。
     * @see Interval#parseInterval(String)
     */
    public static void requireInInterval(double value, String interval, String message) throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        requireInInterval(value, Interval.parseInterval(interval), message);
    }

    /**
     * 要求指定的数值在指定的区间之内。
     *
     * @param value    指定的数值。
     * @param interval 指定的区间的文本形式。
     * @param message  指定的异常信息。
     * @throws OutOfIntervalException   指定的数值不在指定的区间之内。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 参数 <code>interval</code>不符区间的合格式要求。
     * @see Interval#parseInterval(String)
     */
    public static void requireInInterval(BigDecimal value, String interval, String message)
            throws OutOfIntervalException {
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERUTIL_2));
        requireInInterval(value, Interval.parseInterval(interval), message);
    }
}
