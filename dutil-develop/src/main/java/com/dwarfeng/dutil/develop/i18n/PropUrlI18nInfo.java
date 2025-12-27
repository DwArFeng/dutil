package com.dwarfeng.dutil.develop.i18n;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

/**
 * Properties URL 国际化信息。
 *
 * <p>
 * 通过 <code>.properties</code> 文件确定的国际化信息接口。 <br>
 * 该国际化信息接口通过一个 URL 文件来实现。
 *
 * <p>
 * <code>.properties</code> 文件的形式为：
 *
 * <pre>
 * key = value
 * </pre>
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public class PropUrlI18nInfo extends AbstractI18nInfo {

    /**
     * 该国际化信息的文件。
     */
    protected final URL url;

    /**
     * 生成一个具有指定语言，指定名称，指定文件的 Properties URL 国际化信息。
     *
     * @param key  指定的语言。
     * @param name 指定的名称。
     * @param url  指定的 URL。
     */
    public PropUrlI18nInfo(Locale key, String name, URL url) {
        super(key, name);

        Objects.requireNonNull(url, DwarfUtil.getExceptionString(ExceptionStringKey.PROPURLI18NINFO_0));
        this.url = url;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public I18n newI18n() throws Exception {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = url.openStream();
            properties.load(in);
            return new PropertiesI18n(properties);
        } finally {
            if (Objects.nonNull(in)) {
                in.close();
            }
        }
    }
}
