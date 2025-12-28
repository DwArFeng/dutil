package com.dwarfeng.dutil.develop.resource.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.dutil.develop.resource.ResourceHandler;
import com.dwarfeng.dutil.develop.resource.Url2FileResource;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;

/**
 * XML jar 包到文件读取器。
 *
 * <p>
 * 通过 XML 读取 jar 包到文件的资源。
 *
 * <p>
 * XML 需要满足以下格式
 *
 * <pre>
 * &lt;root&gt;
 * 	&lt;info key="key.name.1" path="file_pathl.1" default= "jar_path.1"/&gt;
 * 	&lt;info key="key.name.2" path="file_pathl.2" default= "jar_path.2"/&gt;
 * 	&lt;info key="key.name.3" path="file_pathl.3" default= "jar_path.3"/&gt;
 * &lt;/root&gt;
 * </pre>
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public class XmlJar2FileResourceLoader extends StreamLoader<ResourceHandler> {

    protected static final String MARK_INFO = "info";

    protected static final String MARK_DEFAULT = "default";
    protected static final String MARK_RESOURCE = "path";
    protected static final String MARK_KEY = "key";

    protected static final Supplier<? extends IllegalArgumentException> EXCEPTION_SUPPLIER_LOSSING_PROPERTY = () -> new IllegalArgumentException(
            DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2FILERESOURCELOADER_3));
    protected static final Supplier<? extends IllegalArgumentException> EXCEPTION_SUPPLIER_INVALID_DEF_PATH = () -> new IllegalArgumentException(
            DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2FILERESOURCELOADER_4));

    /**
     * 是否自动复位。
     */
    @Deprecated
    protected final boolean autoReset;
    /**
     * 重置资源的策略。
     */
    protected final ResourceResetPolicy resourceResetPolicy;

    private boolean readFlag = false;

    /**
     * 生成一个 XML jar 包资源文件读取器。
     *
     * @param in 指定的输入流。
     * @throws NullPointerException 入口参数 <code>in</code> 为 <code>null</code>。
     */
    public XmlJar2FileResourceLoader(InputStream in) {
        this(in, ResourceResetPolicy.NEVER);
    }

    /**
     * 生成一个 XML jar 包资源文件读取器。
     *
     * @param in        指定的输入流。
     * @param autoReset 是否自动复位。
     * @throws NullPointerException 入口参数 <code>in</code> 为 <code>null</code>。
     * @see #XmlJar2FileResourceLoader(InputStream, ResourceResetPolicy)
     * @deprecated 该方法被更合理的方法
     * {@link #XmlJar2FileResourceLoader(InputStream, ResourceResetPolicy)} 代。
     */
    public XmlJar2FileResourceLoader(InputStream in, boolean autoReset) {
        this(in, ResourceResetPolicy.AUTO);
    }

    /**
     * 生成一个 XML jar 包资源文件读取器。
     *
     * @param in                  指定的输入流。
     * @param resourceResetPolicy 复位的策略。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public XmlJar2FileResourceLoader(InputStream in, ResourceResetPolicy resourceResetPolicy) {
        super(in);

        Objects.requireNonNull(resourceResetPolicy,
                DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2FILERESOURCELOADER_5));

        this.resourceResetPolicy = resourceResetPolicy;
        autoReset = resourceResetPolicy == ResourceResetPolicy.AUTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(ResourceHandler resourceHandler) throws LoadFailedException, IllegalStateException {
        if (readFlag)
            throw new IllegalStateException(
                    DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2FILERESOURCELOADER_0));

        Objects.requireNonNull(resourceHandler,
                DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2FILERESOURCELOADER_1));

        readFlag = true;

        try {
            SAXReader reader = new SAXReader();
            Element root = reader.read(in).getRootElement();

            /*
             * 根据 dom4j 的相关说明，此处转换是安全的。
             */
            List<Element> infos = root.elements(MARK_INFO);

            for (Element info : infos) {
                loadInfo(resourceHandler, info);
            }

        } catch (Exception e) {
            throw new LoadFailedException(DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2FILERESOURCELOADER_2),
                    e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<LoadFailedException> countinuousLoad(ResourceHandler resourceHandler) throws IllegalStateException {
        if (readFlag)
            throw new IllegalStateException(
                    DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2FILERESOURCELOADER_0));

        Objects.requireNonNull(resourceHandler,
                DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2FILERESOURCELOADER_1));

        readFlag = true;

        final Set<LoadFailedException> exceptions = new LinkedHashSet<>();

        try {
            SAXReader reader = new SAXReader();
            Element root = reader.read(in).getRootElement();

            /*
             * 根据 dom4j 的相关说明，此处转换是安全的。
             */
            List<Element> infos = root.elements(MARK_INFO);

            for (Element info : infos) {
                try {
                    loadInfo(resourceHandler, info);
                } catch (Exception e) {
                    exceptions.add(new LoadFailedException(
                            DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2FILERESOURCELOADER_2), e));
                }

            }

        } catch (Exception e) {
            exceptions.add(new LoadFailedException(
                    DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2FILERESOURCELOADER_2), e));
        }

        return exceptions;

    }

    private void loadInfo(ResourceHandler resourceHandler, Element info) throws Exception {
        String defString = Optional.ofNullable(info.attributeValue(MARK_DEFAULT))
                .orElseThrow(EXCEPTION_SUPPLIER_LOSSING_PROPERTY);
        String resString = Optional.ofNullable(info.attributeValue(MARK_RESOURCE))
                .orElseThrow(EXCEPTION_SUPPLIER_LOSSING_PROPERTY);
        String key = Optional.ofNullable(info.attributeValue(MARK_KEY))
                .orElseThrow(EXCEPTION_SUPPLIER_LOSSING_PROPERTY);

        URL def = Optional.ofNullable(DwarfUtil.class.getResource(defString))
                .orElseThrow(EXCEPTION_SUPPLIER_INVALID_DEF_PATH);

        File res = new File(resString);

        Url2FileResource resource = new Url2FileResource(key, def, res);
        resourceHandler.add(resource);

        switch (resourceResetPolicy) {
            case AUTO:
                if (resource.isValid())
                    break;
            case ALWAYS:
                resource.reset();
                break;
            case NEVER:
            default:
                break;
        }
    }
}
