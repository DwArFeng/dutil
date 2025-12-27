package com.dwarfeng.dutil.basic.str;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本工具类。
 *
 * <p>
 * 文本工具类提供了一系列文本的扩展方法，包括判断文本是否具有某种属性以及对文本进行特定格式的操作等等。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public final class StringUtil {

    /**
     * 电子邮件的正则表达式。
     */
    private static final String REGEX_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    /**
     * 整数的正则表达式。
     */
    private static final String REGEX_INTEGER = "^[-\\+]?\\d+$";
    /**
     * 数字（包括浮点数）的正则表达式
     */
    private static final String REGEX_NUMERIC = "^[-\\+]?((\\d+\\.?\\d*)|(\\d*\\.?\\d+))$";

    // 不能进行实例化
    private StringUtil() {
    }

    /**
     * 判断指定的文本是否是多行文本。
     *
     * <p>
     * 如果入口参数 <code>string</code> 为 <code>null</code>，则返回 <code>false</code>。
     *
     * @param string 指定的文本。
     * @return 指定的文本是否是多行文本。
     */
    public static boolean isMultiline(String string) {
        if (Objects.isNull(string)) {
            return false;
        }
        return string.indexOf('\n') >= 0;
    }

    /**
     * 判断指定的字符串是否是电子邮件地址。
     *
     * <p>
     * 如果入口参数 <code>string</code> 为 <code>null</code>，则返回 <code>false</code>。
     *
     * @param string 指定的文本。
     * @return 指定的文本是否是电子邮件地址。
     */
    public static boolean isEmailAddress(String string) {
        return isRegexMatches(string, REGEX_EMAIL);
    }

    /**
     * 判断指定的字符串是否是整数。
     *
     * <p>
     * 该方法能判断绝大多数的浮点数格式，包括省不省略正号（例如<code>+12450</code>）；
     * 不省略前导零（例如<code>0012450</code>）的数字。
     *
     * <p>
     * 如果入口参数 <code>string</code> 为 <code>null</code>，则返回 <code>false</code>。
     *
     * @param string 指定的文本。
     * @return 指定的文本是否是整数。
     */
    public static boolean isInteger(String string) {
        return isRegexMatches(string, REGEX_INTEGER);
    }

    /**
     * 判断指定的字符串是否是数字（包括浮点数）
     *
     * <p>
     * 该方法能判断绝大多数的浮点数格式，包括省不省略正号（例如<code>+12.450</code>）；
     * 略前导零（例如<code>+.12450</code>）； 省略后置零（例如<code>-12450.</code>）的数字。
     *
     * <p>
     * 如果入口参数 <code>string</code> 为 <code>null</code>，则返回 <code>false</code>。
     *
     * @param string 指定的文本。
     * @return 指定的文本是否是数字（包括浮点数）
     */
    public static boolean isNumeric(String string) {
        return isRegexMatches(string, REGEX_NUMERIC);
    }

    private static boolean isRegexMatches(String string, String regex) {
        if (Objects.isNull(string)) {
            return false;
        }

        // 正则表达式的模式
        Pattern p = Pattern.compile(regex);
        // 正则表达式的匹配器
        Matcher m = p.matcher(string);
        // 进行正则匹配
        return m.matches();
    }
}
