package com.dwarfeng.dutil.basic;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.io.CT.OutputType;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * DwarfUtil 工具包的主类。
 *
 * <p>
 * 该类是 DwarfUtil 工具包的主类与信息类。该工具包中的各种根级信息（如版本信息）都可以在这个类中获得；
 * 同时，该工具包的所用包级设置也通过该类的方法来设置。
 *
 * <p>
 * 该包是工具包，所有的方法皆为静态方法，由于该包的性质，该包不允许外部实例化，不允许继承。
 *
 * <p>
 * 该包中的方法都是线程不安全的，如果需要多线程调用该包中的方法，请使用外部同步代码。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class DwarfUtil {

    /**
     * 图片资源的根目录路径。
     */
    public static final String IMAGE_ROOT = "/com/dwarfeng/dutil/resources/image/";

    /**
     * 标签文本的路径。
     */
    public static final String LF_PATH = "com/dwarfeng/dutil/resources/i18n/label_string";

    /**
     * 异常文本的路径。
     */
    public static final String EXCEPTION_STRING_PATH = "com/dwarfeng/dutil/resources/i18n/exception_string";

    private static final Map<Locale, ResourceBundle> LABEL_FIELD_MAP = new HashMap<>();

    private static ResourceBundle sf = ResourceBundle.getBundle(EXCEPTION_STRING_PATH, Locale.getDefault(),
            CT.class.getClassLoader());

    private static final Version version = new DefaultVersion.Builder().setType(VersionType.BETA)
            .setFirstVersion((byte) 0).setSecondVersion((byte) 2).setThirdVersion((byte) 1).setBuildDate("")
            .setBuildVersion('A').setSnapshot(true).build();

    /**
     * 根据异常文本字段主键枚举返回其主键对应的文本。
     *
     * <p>
     * 如果入口参数 <code>key</code> 为 <code>null</code>，则返回空字符串<code>""</code>。
     *
     * <p>
     * 此方法是对内使用的，它的主要作用是返回内部类所需要的异常文本。<br>
     * 请不要在外部程序中调用此包的方法，因为该方法对内使用，其本身不保证兼容性。
     *
     * <p>
     * <b>注意：</b> 该方法在设计的时候不考虑兼容性，当发生不向上兼容的改动时，作者没有义务在变更日志中说明。
     *
     * @param key 异常文本字段主键枚举。
     * @return 主键对应的文本。
     */
    public static String getExceptionString(ExceptionStringKey key) {
        if (Objects.isNull(key))
            return "";
        return sf.getString(key.getName());
    }

    /**
     * 获得指定的图片键对应的图片。
     *
     * <p>
     * 如果入口参数为 <code>null</code>，或是找不到指定的图片，则返回 <code>null</code>。
     *
     * <p>
     * 此方法是对内使用的，它的主要作用是返回内部类所需要的图片。<br>
     * 请不要在外部程序中调用此包的方法，因为该方法对内使用，其本身不保证兼容性。
     *
     * <p>
     * <b>注意：</b> 该方法在设计的时候不考虑兼容性，当发生不向上兼容的改动时，作者没有义务在变更日志中说明。
     *
     * @param imageKey 指定的图片键。
     * @return 指定的图片键对应的图片。
     */
    public static Image getImage(ImageKey imageKey) {
        if (Objects.isNull(imageKey)) {
            return null;
        }

        try {
            BufferedImage image = ImageIO.read(DwarfUtil.class.getResource(IMAGE_ROOT + imageKey.getName()));
            return image;
        } catch (Exception e) {
            try {
                BufferedImage image = ImageIO.read(DwarfUtil.class.getResource(ImageKey.IMG_LOAD_FAILED.getName()));
                return image;
            } catch (Exception e1) {
                return null;
            }
        }
    }

    /**
     * 获取指定语言环境下的标签字段。
     *
     * <p>
     * 如果入口参数 <code>key</code> 为 <code>null</code>，则返回空字符串<code>""</code>。
     *
     * <p>
     * 如果 <code>local</code> 为 <code>null</code>，则使用 {@link Locale#getDefault()}
     *
     * <p>
     * 此方法是对内使用的，它的主要作用是返回内部类所需要的标签字段。<br>
     * 请不要在外部程序中调用此包的方法，因为该方法对内使用，其本身不保证兼容性。
     *
     * <p>
     * <b>注意：</b> 该方法在设计的时候不考虑兼容性，当发生不向上兼容的改动时，作者没有义务在变更日志中说明。
     *
     * @param key    指定的标签键。
     * @param locale 指定的语言环境。
     * @return 指定标签键和语言环境下的标签字段。
     */
    public static String getLabelString(LabelStringKey key, Locale locale) {
        if (Objects.isNull(key))
            return "";
        if (Objects.isNull(locale))
            locale = Locale.getDefault();

        ResourceBundle rb = LABEL_FIELD_MAP.get(locale);
        // 延迟加载
        if (Objects.isNull(rb)) {
            rb = ResourceBundle.getBundle(LF_PATH, locale, DwarfUtil.class.getClassLoader());
            LABEL_FIELD_MAP.put(locale, rb);
        }

        return rb.getString(key.toString());
    }

    /**
     * 返回该工具包的版本。
     *
     * @return 该工具包的版本。
     */
    public static Version getVersion() {
        return version;
    }

    /**
     * 返回该包的欢迎文本。
     *
     * @return 该包的欢迎
     */
    public static String getWelcomeString() {
        return getExceptionString(ExceptionStringKey.WELCOME_STRING) + getVersion().getLongName();
    }

    public static void main(String[] args) {
        CT.setOutputType(OutputType.NO_DATE);
        CT.trace(DwarfUtil.getWelcomeString());
    }

    /**
     * 将异常的文本字段语言设置为指定语言。
     *
     * <p>
     * 如果 <code>local</code> 为 <code>null</code>，则使用 {@link Locale#getDefault()}
     *
     * @param locale 指定的语言。
     */
    public static void setLocale(Locale locale) {
        if (Objects.isNull(locale))
            locale = Locale.getDefault();
        sf = ResourceBundle.getBundle(EXCEPTION_STRING_PATH, locale, DwarfUtil.class.getClassLoader());
    }

    // 禁止外部实例化
    private DwarfUtil() {
    }
}
