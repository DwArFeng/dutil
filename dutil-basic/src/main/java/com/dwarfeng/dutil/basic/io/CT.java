package com.dwarfeng.dutil.basic.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.str.StringUtil;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * 控制台输出工具
 *
 * <p>
 * 该方法可以在控制台中输出带有时间格式的信息，相当于使用 System.out 输出出当前的时间后，在调用 System.out 输出信息。<br>
 * 该类不能被继承。
 *
 * @author DwArFeng
 * @see PrintStream#println()
 * @since 0.0.2-beta
 */
public final class CT {

    /**
     * 输出工具的输出形式。
     *
     * @author DwArFeng
     * @since 0.0.2-beta
     */
    public enum OutputType {
        /**
         * 不输出系统时间
         */
        NO_DATE,
        /**
         * 输出简要系统时间（时分秒）
         */
        HALF_DATE,
        /**
         * 输出完整的系统时间
         */
        FULL_DATE
    }

    /**
     * 输出工具处理多行文本的方式
     *
     * @author DwArFeng
     * @since 0.2.0-beta
     */
    public enum MultiLineType {
        /**
         * 平铺式。
         *
         * <p>
         * 示例<br>
         * <blockquote> [22:07:03,872] 中国智造，惠及全球。<br>
         * [22:07:03,873] the quick fox jumps over a lazy dog. </blockquote>
         */
        TYPE_1,
        /**
         * 标题式。
         *
         * <p>
         * 示例<br>
         * <blockquote>[2017-07-09 22:07:03,874] 多行文本<br>
         * 中国智造，惠及全球。<br>
         * the quick fox jumps over a lazy dog.</blockquote>
         */
        TYPE_2,
        /**
         * 缩进式。
         *
         * <p>
         * 示例<br>
         * <blockquote>[2017-07-09 22:07:03,874]<br>
         * 中国智造，惠及全球。<br>
         * the quick fox jumps over a lazy dog.</blockquote>
         */
        TYPE_3
    }

    private static OutputType outputType = OutputType.HALF_DATE;
    private static MultiLineType mutiLineType = MultiLineType.TYPE_2;

    /**
     * 返回输出类型。
     *
     * @return 输出类型。
     */
    public static OutputType getOutputType() {
        return outputType;
    }

    /**
     * 设置输出类型。
     *
     * @param outputType 指定的输出类型。
     */
    public static void setOutputType(OutputType outputType) {
        Objects.requireNonNull(outputType, DwarfUtil.getExceptionString(ExceptionStringKey.CT_1));
        CT.outputType = outputType;
    }

    /**
     * 返回多行文本的处理类型。
     *
     * @return 多行文本的处理类型。
     */
    public static MultiLineType getMultiLineType() {
        return mutiLineType;
    }

    /**
     * 设置多行文本的处理类型。
     *
     * @param mutiLineType 指定的多行文本处理类型。
     */
    public static void setMutiLineType(MultiLineType mutiLineType) {
        Objects.requireNonNull(mutiLineType, DwarfUtil.getExceptionString(ExceptionStringKey.CT_2));
        CT.mutiLineType = mutiLineType;
    }

    /**
     * 返回将要输出在控制台中的文本，但是不将其输出在控制台上。
     *
     * @param s 传入的文本。
     * @return 传入文本对应的要输出的文本。
     */
    public static String toString(String s) {
        return toString0(s);
    }

    /**
     * 在控制台中输出一行文本。
     *
     * @param s 需要输出的文本。
     * @return 打印在控制台中的文本回显。
     */
    public static String trace(String s) {
        System.out.println(toString(s));
        return toString(s);
    }

    /**
     * 返回将要输出在控制台中的布尔变量，但是不将其输出在控制台上。
     *
     * @param b 传入的布尔变量。
     * @return 传入布尔变量对应的要输出的文本。
     */
    public static String toString(boolean b) {
        return toString0(Boolean.toString(b));
    }

    /**
     * 在控制台中输出一个布尔变量。
     *
     * @param b 需要输出的布尔变量。
     * @return 打印在控制台中的文本回显。
     */
    public static String trace(boolean b) {
        System.out.println(toString(b));
        return toString(b);
    }

    /**
     * 返回将要输出在控制台中的整型变量，但是不将其输出在控制台上。
     *
     * @param i 传入的整型变量。
     * @return 传入整型变量对应的要输出的文本。
     */
    public static String toString(int i) {
        return toString0(Integer.toString(i));
    }

    /**
     * 在控制台中输出一个整型变量。
     *
     * @param i 需要输出的整型变量。
     * @return 打印在控制台中的文本回显。
     */
    public static String trace(int i) {
        System.out.println(toString(i));
        return toString(i);
    }

    /**
     * 返回将要输出在控制台中的单精度浮点变量，但是不将其输出在控制台上。
     *
     * @param f 传入的单精度浮点变量。
     * @return 传入单精度浮点变量对应的要输出的文本。
     */
    public static String toString(float f) {
        return toString0(Float.toString(f));
    }

    /**
     * 在控制台中输出一个浮点型变量。
     *
     * @param f 需要输出的浮点型变量。
     * @return 打印在控制台中的文本回显。
     */
    public static String trace(float f) {
        System.out.println(toString(f));
        return toString(f);
    }

    /**
     * 返回将要输出在控制台中的双精度浮点变量，但是不将其输出在控制台上。
     *
     * @param d 传入的双精度浮点变量。
     * @return 传入双精度浮点变量对应的要输出的文本。
     */
    public static String toString(double d) {
        return toString0(Double.toString(d));
    }

    /**
     * 在控制台中输出一个双精度浮点变量。
     *
     * @param d 需要输出的双精度浮点变量。
     * @return 打印在控制台中的文本回显。
     */
    public static String trace(double d) {
        System.out.println(toString(d));
        return toString(d);
    }

    /**
     * 返回将要输出在控制台中的长整型变量，但是不将其输出在控制台上。
     *
     * @param l 传入的长整型变量。
     * @return 传入长整型变量对应的要输出的文本。
     */
    public static String toString(long l) {
        return toString0(Long.toString(l));
    }

    /**
     * 在控制台中输出一个长整形变量。
     *
     * @param l 需要输出的长整形变量。
     * @return 打印在控制台中的文本回显。
     */
    public static String trace(long l) {
        System.out.println(toString(l));
        return toString(l);
    }

    /**
     * 返回将要输出在控制台中的字符变量，但是不将其输出在控制台上。
     *
     * @param c 传入的字符变量。
     * @return 传入字符变量对应的要输出的文本。
     */
    public static String toString(char c) {
        return toString0(Character.toString(c));
    }

    /**
     * 在控制台中输出一个字符变量。
     *
     * @param c 需要输出的字符变量。
     * @return 打印在控制台中的文本回显。
     */
    public static String trace(char c) {
        System.out.println(toString(c));
        return toString(c);
    }

    /**
     * 返回将要输出在控制台中的对象，但是不将其输出在控制台上。
     *
     * @param o 传入的对象。
     * @return 传入对象对应的要输出的文本。
     */
    public static String toString(Object o) {
        if (Objects.isNull(o)) {
            return toString0("null");
        }
        return toString0(o.toString());
    }

    /**
     * 在控制台中输出一个对象。
     *
     * @param o 需要输出的对象。
     * @return 打印在控制台中的文本回显。
     */
    public static String trace(Object o) {
        System.out.println(toString(o));
        return toString(o);
    }

    /**
     * 处理有可能输入的多行文本，以及空的时间前缀。
     */
    private static String toString0(String string) {
        String prefix = getTimePrefix();

        // 如果没有前缀，那么一切如常。
        if (Objects.isNull(string)) {
            string = "null";
        }
        string = string.replace("\r", "");
        if (prefix.length() == 0) {
            return string;
        }

        /**
         * 如果有前缀，则需要：<br>
         * 1. 先判断指定的文本是否是多行文本。<br>
         * 1.1 如果不是多行文本，则按照单行输出的样式进行输出。<br>
         * 1.2 如果是多行文本，则按照 mutiLineType 中的样式分类进行输出。<br>
         */

        if (!StringUtil.isMultiline(string)) {
            return String.format("%s\t%s", prefix, string);
        }

        switch (mutiLineType) {
            case TYPE_1:
                return multiLine1(prefix, string);
            case TYPE_2:
                return multiLine2(prefix, string);
            case TYPE_3:
                return multiLine3(prefix, string);
            default:
                return String.format("%s\t%s", prefix, string);
        }

    }

    /**
     * 输出年月日时分秒或者时分秒格式的系统时间
     */
    private static String getTimePrefix() {
        SimpleDateFormat formatter;

        switch (outputType) {
            case FULL_DATE:
                formatter = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss,SSS]");
                return formatter.format(new Date());
            case HALF_DATE:
                formatter = new SimpleDateFormat("[HH:mm:ss,SSS]");
                return formatter.format(new Date());
            case NO_DATE:
                return "";
            default:
                return "";
        }

    }

    private static String multiLine1(String prefix, String string) {
        StringTokenizer st = new StringTokenizer(string, "\n");
        StringBuilder sb = new StringBuilder();

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            // sb.append(String.format("%s\t%s", prefix, token));
            sb.append(prefix).append("\t").append(token);

            if (st.hasMoreTokens()) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private static String multiLine2(String prefix, String string) {
        StringTokenizer st = new StringTokenizer(string, "\n");
        StringBuilder sb = new StringBuilder();

        // sb.append(String.format("%s\t%s\n", prefix,
        // DwarfUtil.getExecptionString(ExceptionStringKey.CT_0)));
        sb.append(prefix).append("\t").append(DwarfUtil.getExceptionString(ExceptionStringKey.CT_0)).append("\n");

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            sb.append("\t").append(token);

            if (st.hasMoreTokens()) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private static String multiLine3(String prefix, String string) {
        StringTokenizer st = new StringTokenizer(string, "\n");
        StringBuilder sb = new StringBuilder();

        // sb.append(String.format("%s\t%s\n", prefix,
        // DwarfUtil.getExecptionString(ExceptionStringKey.CT_0)));
        sb.append(prefix).append("\n");

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            sb.append("\t").append(token);

            if (st.hasMoreTokens()) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    // 不可见的构造器方法
    private CT() {
    }
}
