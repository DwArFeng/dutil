package com.dwarfeng.dutil.basic.num;

/**
 * 数字值接口。
 *
 * <p>
 * 该接口意味着实现类能够转化为某个值，并且可以将该值以任何一种基本数据类型返回。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface NumberValue {

    /**
     * 返回该值的 double 形式。
     *
     * @return 该值的 double 形式。
     */
    double doubleValue();

    /**
     * 返回该值的 float 形式。
     *
     * <p>
     * 注意，该默认方法在强制转换时存在精度丢失的隐患。
     *
     * @return 该值的 float 形式。
     */
    default float floatValue() {
        return (float) doubleValue();
    }

    /**
     * 返回该值的 long 形式。
     *
     * <p>
     * 注意，该默认方法在强制转换时存在精度丢失的隐患。
     *
     * @return 该值的 long 形式。
     */
    default long longValue() {
        return (long) doubleValue();
    }

    /**
     * 返回该值的 int 形式。
     *
     * <p>
     * 注意，该默认方法在强制转换时存在精度丢失的隐患。
     *
     * @return 该值的 int 形式。
     */
    default int intValue() {
        return (int) doubleValue();
    }

    /**
     * 返回该值的 short 形式。
     *
     * <p>
     * 注意，该默认方法在强制转换时存在精度丢失的隐患。
     *
     * @return 该值的 short 形式。
     */
    default short shortValue() {
        return (short) doubleValue();
    }

    /**
     * 返回该值的 byte 形式。
     *
     * <p>
     * 注意，该默认方法在强制转换时存在精度丢失的隐患。
     *
     * @return 该值的 byte 形式。
     */
    default byte byteValue() {
        return (byte) doubleValue();
    }
}
