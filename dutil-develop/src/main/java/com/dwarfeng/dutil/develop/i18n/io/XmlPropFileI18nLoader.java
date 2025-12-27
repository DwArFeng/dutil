package com.dwarfeng.dutil.develop.i18n.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.dutil.basic.str.FactoriesByString;
import com.dwarfeng.dutil.develop.i18n.I18nHandler;
import com.dwarfeng.dutil.develop.i18n.PropUrlI18nInfo;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;

/**
 * Xml 属性文件国际化读取器。
 *
 * <p>
 * 通过 Xml 文件和 properties 文件向国际化处理器中读取数据的读取器。
 *
 * <p>
 * Xml 文件的格式如下：
 *
 * <pre>
 * &lt;root&gt;
 * 	 &lt;info locale="en_US" name="English" file="directory/en_US.properties"/&gt;
 * 	 &lt;info locale="zh_CN" name="简体中文" file="directory/zh_CN.properties"/&gt;
 * 	 &lt;info locale="ja_JP" name="日本語" file="directory/ja_JP.properties"/&gt;
 * &lt;/root&gt;
 * </pre>
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public class XmlPropFileI18nLoader extends StreamLoader<I18nHandler> {

    protected static final String MARK_INFO_DEFAULT = "info-default";
    protected static final String MARK_INFO = "info";

    protected static final String MARK_LOCALE = "locale";
    protected static final String MARK_NAME = "name";
    protected static final String MARK_FILE = "file";

    protected static final Supplier<? extends IllegalArgumentException> EXCEPTION_SUPPLIER_LOSSING_PROPERTY = () -> new IllegalArgumentException(
            DwarfUtil.getExceptionString(ExceptionStringKey.XMLPROPFILEI18NLOADER_3));

    private boolean readFlag = false;

    /**
     * 生成一个具有指定输入流的 xml 属性文件国际化读取器。
     *
     * @param in 指定的输入流。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public XmlPropFileI18nLoader(InputStream in) {
        super(in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(I18nHandler i18nHandler) throws LoadFailedException, IllegalStateException {
        if (readFlag)
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.XMLPROPFILEI18NLOADER_0));

        Objects.requireNonNull(i18nHandler, DwarfUtil.getExceptionString(ExceptionStringKey.XMLPROPFILEI18NLOADER_1));

        try {
            readFlag = true;

            SAXReader reader = new SAXReader();
            Element root = reader.read(in).getRootElement();

            loadDefaultInfo(i18nHandler, root);

            /*
             * 根据 dom4j 的相关说明，此处转换是安全的。
             */
            @SuppressWarnings("unchecked")
            List<Element> infos = root.elements(MARK_INFO);

            for (Element info : infos) {
                loadInfo(i18nHandler, info);
            }

        } catch (Exception e) {
            throw new LoadFailedException(DwarfUtil.getExceptionString(ExceptionStringKey.XMLPROPFILEI18NLOADER_2));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<LoadFailedException> countinuousLoad(I18nHandler i18nHandler) throws IllegalStateException {

        if (readFlag)
            throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.XMLPROPFILEI18NLOADER_0));

        Objects.requireNonNull(i18nHandler, DwarfUtil.getExceptionString(ExceptionStringKey.XMLPROPFILEI18NLOADER_1));

        final Set<LoadFailedException> exceptions = new LinkedHashSet<>();
        try {
            readFlag = true;

            SAXReader reader = new SAXReader();
            Element root = reader.read(in).getRootElement();

            try {
                loadDefaultInfo(i18nHandler, root);
            } catch (Exception e) {
                exceptions.add(new LoadFailedException(
                        DwarfUtil.getExceptionString(ExceptionStringKey.XMLPROPFILEI18NLOADER_2), e));
            }

            /*
             * 根据 dom4j 的相关说明，此处转换是安全的。
             */
            @SuppressWarnings("unchecked")
            List<Element> infos = root.elements(MARK_INFO);

            for (Element info : infos) {
                try {
                    loadInfo(i18nHandler, info);
                } catch (Exception e) {
                    exceptions.add(new LoadFailedException(
                            DwarfUtil.getExceptionString(ExceptionStringKey.XMLPROPFILEI18NLOADER_2), e));
                }

            }

        } catch (Exception e) {
            exceptions.add(new LoadFailedException(
                    DwarfUtil.getExceptionString(ExceptionStringKey.XMLPROPFILEI18NLOADER_2), e));
        }

        return exceptions;

    }

    private void loadDefaultInfo(I18nHandler i18nHandler, Element root)
            throws LoadFailedException, MalformedURLException {
        Element defaultInfo;

        // 默认信息存在判断。
        // 由于默认信息不是必须存在的，所以应该首先判断默认信息是否存在，如果存在，执行相应逻辑；如果不存在，直接退出。
        if (Objects.nonNull(defaultInfo = root.element(MARK_INFO_DEFAULT))) {
            // 信息存在，执行相应逻辑。
            String defaultNameString = Optional.ofNullable(defaultInfo.attributeValue(MARK_NAME))
                    .orElseThrow(EXCEPTION_SUPPLIER_LOSSING_PROPERTY);
            String defaultFileString = Optional.ofNullable(defaultInfo.attributeValue(MARK_FILE))
                    .orElseThrow(EXCEPTION_SUPPLIER_LOSSING_PROPERTY);

            URL defaultUrl = new File(defaultFileString).toURI().toURL();

            i18nHandler.add(new PropUrlI18nInfo(null, defaultNameString, defaultUrl));
        }
    }

    private void loadInfo(I18nHandler i18nHandler, Element info) throws LoadFailedException, MalformedURLException {
        String localeString = Optional.ofNullable(info.attributeValue(MARK_LOCALE))
                .orElseThrow(EXCEPTION_SUPPLIER_LOSSING_PROPERTY);
        String nameString = Optional.ofNullable(info.attributeValue(MARK_NAME))
                .orElseThrow(EXCEPTION_SUPPLIER_LOSSING_PROPERTY);
        String fileString = Optional.ofNullable(info.attributeValue(MARK_FILE))
                .orElseThrow(EXCEPTION_SUPPLIER_LOSSING_PROPERTY);

        URL url = new File(fileString).toURI().toURL();
        Locale locale = FactoriesByString.newLocale(localeString);

        i18nHandler.add(new PropUrlI18nInfo(locale, nameString, url));
    }
}
