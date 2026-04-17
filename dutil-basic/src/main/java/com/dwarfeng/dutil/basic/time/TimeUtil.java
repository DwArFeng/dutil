package com.dwarfeng.dutil.basic.time;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * 时间工具类。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.4.2.a-beta
 */
public final class TimeUtil {

    /**
     * 毫秒内纳秒偏移量允许的最小值。
     */
    public static final int MIN_NANO_OFFSET = 0;

    /**
     * 毫秒内纳秒偏移量允许的最大值。
     */
    public static final int MAX_NANO_OFFSET = 999999;

    /**
     * 将日期与毫秒内纳秒偏移量转换为瞬时时间。
     *
     * <p>
     * 该方法将入口参数中的毫秒时间与纳秒偏移量进行合并。
     *
     * @param date       日期。
     * @param nanoOffset 毫秒内纳秒偏移量，取值范围为 [MIN_NANO_OFFSET, MAX_NANO_OFFSET]。
     * @return 转换后的瞬时时间。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 入口参数不合法。
     * @see #MIN_NANO_OFFSET
     * @see #MAX_NANO_OFFSET
     */
    public static Instant toInstant(Date date, int nanoOffset) {
        Objects.requireNonNull(date, DwarfUtil.getExceptionString(ExceptionStringKey.TIMEUTIL_0));
        checkNanoOffset(nanoOffset);

        return Instant.ofEpochMilli(date.getTime()).plusNanos(nanoOffset);
    }

    /**
     * 将瞬时时间拆分为日期。
     *
     * <p>
     * 拆分后保留毫秒部分，纳秒偏移量通过 {@link #toNanoOffset(Instant)} 获取。
     *
     * @param instant 瞬时时间。
     * @return 拆分后的日期。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static Date toDate(Instant instant) {
        Objects.requireNonNull(instant, DwarfUtil.getExceptionString(ExceptionStringKey.TIMEUTIL_1));

        return new Date(instant.toEpochMilli());
    }

    /**
     * 获取瞬时时间在毫秒内的纳秒偏移量。
     *
     * @param instant 瞬时时间。
     * @return 毫秒内纳秒偏移量，取值范围为 [MIN_NANO_OFFSET, MAX_NANO_OFFSET]。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     * @see #MIN_NANO_OFFSET
     * @see #MAX_NANO_OFFSET
     */
    public static int toNanoOffset(Instant instant) {
        Objects.requireNonNull(instant, DwarfUtil.getExceptionString(ExceptionStringKey.TIMEUTIL_1));

        return instant.getNano() % 1000000;
    }

    /**
     * 比较两个 <code>Date + nanoOffset</code> 所表示时间点的先后关系。
     *
     * @param date1        第一个日期。
     * @param nanoOffset1  第一个毫秒内纳秒偏移量，取值范围为 [MIN_NANO_OFFSET, MAX_NANO_OFFSET]。
     * @param date2        第二个日期。
     * @param nanoOffset2  第二个毫秒内纳秒偏移量，取值范围为 [MIN_NANO_OFFSET, MAX_NANO_OFFSET]。
     * @return 比较结果，小于 0 表示前者早于后者，等于 0 表示相等，大于 0 表示前者晚于后者。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 入口参数不合法。
     * @see #MIN_NANO_OFFSET
     * @see #MAX_NANO_OFFSET
     */
    public static int compare(Date date1, int nanoOffset1, Date date2, int nanoOffset2) {
        Objects.requireNonNull(date1, DwarfUtil.getExceptionString(ExceptionStringKey.TIMEUTIL_2));
        Objects.requireNonNull(date2, DwarfUtil.getExceptionString(ExceptionStringKey.TIMEUTIL_3));
        checkNanoOffset(nanoOffset1);
        checkNanoOffset(nanoOffset2);

        int dateCompareResult = Long.compare(date1.getTime(), date2.getTime());
        if (dateCompareResult != 0) {
            return dateCompareResult;
        }
        return Integer.compare(nanoOffset1, nanoOffset2);
    }

    /**
     * 判断毫秒内纳秒偏移量是否合法。
     *
     * @param nanoOffset 毫秒内纳秒偏移量。
     * @return 指定的偏移量是否合法。
     */
    public static boolean isNanoOffsetLegal(int nanoOffset) {
        return nanoOffset >= MIN_NANO_OFFSET && nanoOffset <= MAX_NANO_OFFSET;
    }

    /**
     * 检查毫秒内纳秒偏移量是否合法。
     *
     * @param nanoOffset 毫秒内纳秒偏移量。
     * @throws IllegalArgumentException 入口参数不合法。
     */
    public static void checkNanoOffset(int nanoOffset) {
        if (isNanoOffsetLegal(nanoOffset)) {
            return;
        }
        throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.TIMEUTIL_4));
    }

    // 禁止外部实例化。
    private TimeUtil() {
    }
}
