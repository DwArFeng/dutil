package com.dwarfeng.dutil.basic.stack.number;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;

import com.dwarfeng.dutil.basic.stack.function.Filter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Scanner;

/**
 * 区间。
 *
 * <p>
 * 该区间类和数学上的区间是一样的， 具有一个最大值和最小值， 并且可以判断一个数是否在该区间中。
 *
 * <p>
 * 区间的左右边间具有开和闭两种类型，和数学上的定义一样： 开表示区间不包含边界值；闭表示区间包含边界值。
 *
 * @author DwArFeng
 * @since 0.1.3-beta
 */
public record Interval(BoundaryType leftBoundaryType, BoundaryType rightBoundaryType, BigDecimal leftValue,
                       BigDecimal rightValue) implements Filter<NumberValue> {

    /**
     * 区间的边界类型。
     *
     * @author DwArFeng
     * @since 0.1.3-beta
     */
    public enum BoundaryType {
        /**
         * 表示该边界是开。
         */
        OPENED,
        /**
         * 表示该边界是闭。
         */
        CLOSED,
    }

    /**
     * 一个表示所有实数的区间，等于 <code>(-∞,+∞)</code>
     */
    public static final Interval INTERVAL_REAL_NUMBER = new Interval(
            BoundaryType.OPENED, BoundaryType.OPENED, null, null
    );

    /**
     * 一个表示所有正实数的区间，等于<code>(0,+∞)</code>
     */
    public static final Interval INTERVAL_POSITIVE = new Interval(
            BoundaryType.OPENED, BoundaryType.OPENED, BigDecimal.ZERO, null
    );

    /**
     * 一个表示所有非负实数的区间，等于<code>[0,+∞)</code>
     */
    public static final Interval INTERVAL_NOT_NEGATIVE = new Interval(
            BoundaryType.CLOSED, BoundaryType.OPENED, BigDecimal.ZERO, null
    );

    /**
     * 一个表示所有负实数的区间，等于<code>(-∞,0)</code>
     */
    public static final Interval INTERVAL_NEGATIVE = new Interval(
            BoundaryType.OPENED, BoundaryType.OPENED, null, BigDecimal.ZERO
    );

    /**
     * 一个表示所有非正实数的区间，等于<code>(-∞,0]</code>
     */
    public static final Interval INTERVAL_NOT_POSITIVE = new Interval(
            BoundaryType.OPENED, BoundaryType.CLOSED, null, BigDecimal.ZERO
    );

    /**
     * 将指定的文本值转化为区间。
     *
     * <p>
     * 文本必须符合类似于<code>[ 0 , 1 )</code>、
     * <code>( -infinity , 0 ]</code>、<code>( -infinity , infinity )</code>这样的格式（<b>不能省略空格</b>）。
     * <br>
     * 注意事项有： <blockquote> 1、负无穷只能以 "-infinity"的形式出现在逗号左边，
     * 而正无穷只能以"infinity"的形式出现在逗号右边。<br>
     * 2、所有参数直接必须用空格分隔，不能用其它的分隔符分隔。 </blockquote>
     *
     * @param str 指定的字符串。
     * @return 指定的字符串转化而来的区间。
     * @throws IllegalArgumentException 字符串不符合形式。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     */
    @SuppressWarnings("DuplicatedCode")
    public static Interval parseInterval(String str) {
        Objects.requireNonNull(str, BasicMessages.message(BasicMessageKey.INTERVAL_TEXT_REQUIRED));

        // 常量。
        final String leftBoundaryPattern = "[\\[|(]";
        final String leftInfinityPattern = "-infinity";
        final String rightInfinityPattern = "infinity";
        final String rightBoundaryPattern = "[]|)]";

        // 属性。
        final BoundaryType leftBoundaryType;
        final BoundaryType rightBoundaryType;
        final BigDecimal leftValue;
        final BigDecimal rightValue;

        try (Scanner scanner = new Scanner(str)) {
            if (!scanner.hasNext(leftBoundaryPattern)) {
                throw new IllegalArgumentException(BasicMessages.message(BasicMessageKey.INTERVAL_FORMAT_INVALID, str));
            }
            String leftBoundary = scanner.next(leftBoundaryPattern);
            leftBoundaryType = leftBoundary.equals("[") ? BoundaryType.CLOSED : BoundaryType.OPENED;

            if (!scanner.hasNextBigDecimal() && !scanner.hasNext(leftInfinityPattern)) {
                throw new IllegalArgumentException(BasicMessages.message(BasicMessageKey.INTERVAL_FORMAT_INVALID, str));
            }
            if (scanner.hasNextBigDecimal()) {
                leftValue = scanner.nextBigDecimal();
            } else {
                leftValue = null;
                scanner.next(leftInfinityPattern);
            }

            if (!scanner.hasNext(",")) {
                throw new IllegalArgumentException(BasicMessages.message(BasicMessageKey.INTERVAL_FORMAT_INVALID, str));
            }
            scanner.next(",");

            if (!scanner.hasNextBigDecimal() && !scanner.hasNext(rightInfinityPattern)) {
                throw new IllegalArgumentException(BasicMessages.message(BasicMessageKey.INTERVAL_FORMAT_INVALID, str));
            }
            if (scanner.hasNextBigDecimal()) {
                rightValue = scanner.nextBigDecimal();
            } else {
                rightValue = null;
                scanner.next(rightInfinityPattern);
            }
            if (!scanner.hasNext(rightBoundaryPattern)) {
                throw new IllegalArgumentException(BasicMessages.message(BasicMessageKey.INTERVAL_FORMAT_INVALID, str));
            }
            String rightBoundary = scanner.next(rightBoundaryPattern);
            rightBoundaryType = rightBoundary.equals("]") ? BoundaryType.CLOSED : BoundaryType.OPENED;
        }

        return new Interval(leftBoundaryType, rightBoundaryType, leftValue, rightValue);
    }

    /**
     * 生成一个区间。
     *
     * @param leftBoundaryType  左边界的类型，不能为 <code>null</code>。
     * @param rightBoundaryType 右边界的类型，不能为 <code>null</code>。
     * @param leftValue         左边界值，<code>null</code>代表无穷小。
     * @param rightValue        右边界值， <code>null</code>代表无穷大。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 左侧的数大于右侧的数。
     */
    public Interval {
        Objects.requireNonNull(
                leftBoundaryType, BasicMessages.message(BasicMessageKey.INTERVAL_LEFT_BOUNDARY_TYPE_REQUIRED)
        );
        Objects.requireNonNull(
                rightBoundaryType, BasicMessages.message(BasicMessageKey.INTERVAL_RIGHT_BOUNDARY_TYPE_REQUIRED)
        );

        if ((Objects.nonNull(leftValue) && Objects.nonNull(rightValue)) && leftValue.compareTo(rightValue) > 0) {
            throw new IllegalArgumentException(BasicMessages.message(BasicMessageKey.INTERVAL_LEFT_GREATER_THAN_RIGHT));
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(NumberValue value) {
        Objects.requireNonNull(value, BasicMessages.message(BasicMessageKey.INTERVAL_VALUE_REQUIRED));
        return contains(value.doubleValue());
    }

    /**
     * 判断该区间是否包含指定的值。
     *
     * @param value 指定的值。
     * @return 区间是否包含指定的值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public boolean contains(BigDecimal value) {
        if ((Objects.isNull(leftValue) || leftValue.compareTo(value) < 0)
                && (Objects.isNull(rightValue) || rightValue.compareTo(value) > 0))
            return true;
        if (Objects.nonNull(leftValue) && leftValue.compareTo(value) == 0)
            return leftBoundaryType == BoundaryType.CLOSED;
        if (Objects.nonNull(rightValue) && rightValue.compareTo(value) == 0)
            return rightBoundaryType == BoundaryType.CLOSED;
        return false;
    }

    /**
     * 判断该区间是否包含指定的值。
     *
     * @param value 指定的值。
     * @return 区间是否包含指定的值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public boolean contains(double value) {
        return contains(new BigDecimal(value));
    }

    /**
     * 判断该区间是否包含指定的值。
     *
     * @param value 指定的值。
     * @return 区间是否包含指定的值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public boolean contains(float value) {
        return contains(new BigDecimal(value));
    }

    /**
     * 判断该区间是否包含指定的值。
     *
     * @param value 指定的值。
     * @return 区间是否包含指定的值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public boolean contains(byte value) {
        return contains(new BigDecimal(value));
    }

    /**
     * 判断该区间是否包含指定的值。
     *
     * @param value 指定的值。
     * @return 区间是否包含指定的值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public boolean contains(short value) {
        return contains(new BigDecimal(value));
    }

    /**
     * 判断该区间是否包含指定的值。
     *
     * @param value 指定的值。
     * @return 区间是否包含指定的值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public boolean contains(int value) {
        return contains(new BigDecimal(value));
    }

    /**
     * 判断该区间是否包含指定的值。
     *
     * @param value 指定的值。
     * @return 区间是否包含指定的值。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public boolean contains(long value) {
        return contains(new BigDecimal(value));
    }

    /**
     * 获取区间的左边界类型。
     *
     * @return 区间的左边界类型。
     */
    @Override
    public BoundaryType leftBoundaryType() {
        return leftBoundaryType;
    }

    /**
     * 获取区间的左边界值。
     *
     * @return 区间的做边界值，返回 <code>null</code>代表负无穷大。
     */
    @Override
    public BigDecimal leftValue() {
        return leftValue;
    }

    /**
     * 获取区间的右边界类型。
     *
     * @return 区间的右边界类型。
     */
    @Override
    public BoundaryType rightBoundaryType() {
        return rightBoundaryType;
    }

    /**
     * 获取区间的右边界值。
     *
     * @return 区间的右边界值，返回 <code>null</code>代表正无穷大。
     */
    @Override
    public BigDecimal rightValue() {
        return rightValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Interval(
                BoundaryType boundaryType, BoundaryType type, BigDecimal value, BigDecimal rightValue1
        ))) {
            return false;
        }
        if (leftBoundaryType != boundaryType) {
            return false;
        }
        if (leftValue == null) {
            if (value != null) {
                return false;
            }
        } else if (!leftValue.equals(value)) {
            return false;
        }
        if (rightBoundaryType != type) {
            return false;
        }
        if (rightValue == null) {
            return rightValue1 == null;
        } else return rightValue.equals(rightValue1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String toString() {
        return (leftBoundaryType == BoundaryType.CLOSED ? "[ " : "( ") +
                (leftValue == null ? "-∞" : leftValue.toString()) +
                " , " +
                (rightValue == null ? "+∞" : rightValue.toString()) +
                (rightBoundaryType == BoundaryType.CLOSED ? " ]" : " )");
    }

    /**
     * 输出该区间的文本形式，并且左右值具有指定的标度。
     *
     * @param scale        指定的标度。
     * @param roundingMode 指定的取舍模型。
     * @return 该区间的文本形式，左右值具有指定的标度。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public String toString(int scale, RoundingMode roundingMode) {
        Objects.requireNonNull(roundingMode, BasicMessages.message(BasicMessageKey.INTERVAL_ROUNDING_MODE_REQUIRED));

        return (leftBoundaryType == BoundaryType.CLOSED ? "[ " : "( ") +
                (leftValue == null ? "-∞" : leftValue.setScale(scale, roundingMode).toString()) +
                " , " +
                (rightValue == null ? "+∞" : rightValue.setScale(scale, roundingMode).toString()) +
                (rightBoundaryType == BoundaryType.CLOSED ? " ]" : " )");
    }
}
