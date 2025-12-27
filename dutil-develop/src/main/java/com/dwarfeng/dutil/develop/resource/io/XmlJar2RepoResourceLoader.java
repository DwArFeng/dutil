package com.dwarfeng.dutil.develop.resource.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.dutil.develop.resource.ResourceHandler;
import com.dwarfeng.dutil.develop.resource.Url2RepoResource;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * XML jar 包资源仓库读取器。
 *
 * <p>
 * 该读取器会读取指定的 XML 文件，并且将其中的每一条内容解析成 {@link Url2RepoResource}。
 *
 * <p>
 * XML 需要满足以下格式
 *
 * <pre>
 * &lt;root dir="repo_dir"&gt;
 * 	&lt;info key="key.name.1" classify="com.dwarfeng.classify.1" filename="file1.extension" default= "jar_path.1"/&gt;
 * 	&lt;info key="key.name.2" classify="com.dwarfeng.classify.2" filename="file2.extension" default= "jar_path.2"/&gt;
 * 	&lt;info key="key.name.3" classify="com.dwarfeng.classify.3" filename="file3.extension" default= "jar_path.3"/&gt;
 * &lt;/root&gt;
 * </pre>
 *
 * <p>
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class XmlJar2RepoResourceLoader extends StreamLoader<ResourceHandler> {

    /**
     * 仓库的根目录。
     */
    protected final File repoDir;

    /**
     * 是否自动复位。
     */
    protected final boolean autoReset;

    private boolean readFlag = false;

    /**
     * 生成一个 XML jar 包资源仓库读取器。
     *
     * @param in 指定的输入流。
     * @throws NullPointerException 入口参数 <code>in</code> 为 <code>null</code>。
     */
    public XmlJar2RepoResourceLoader(InputStream in) {
        this(in, null, false);
    }

    /**
     * 生成一个 XML jar 包资源仓库读取器。
     *
     * @param in        指定的输入流。
     * @param autoReset 是否自动复位。
     * @throws NullPointerException 入口参数 <code>in</code> 为 <code>null</code>。
     */
    public XmlJar2RepoResourceLoader(InputStream in, boolean autoReset) {
        this(in, null, autoReset);
    }

    /**
     * 生成一个 XML jar 包资源仓库读取器，由指定的资源仓库根目录替代 XML 文件中的根目录。
     *
     * @param in      指定的输入流。
     * @param repoDir 指定的资源仓库根目录，如果为 <code>null</code>，则使用 XML 中的根目录。
     * @throws NullPointerException 入口参数 <code>in</code> 为 <code>null</code>。
     */
    public XmlJar2RepoResourceLoader(InputStream in, File repoDir) {
        this(in, repoDir, false);
    }

    /**
     * 生成一个 XML jar 包资源仓库读取器，由指定的资源仓库根目录替代 XML 文件中的根目录。
     *
     * @param in        指定的输入流。
     * @param repoDir   指定的资源仓库根目录，如果为 <code>null</code>，则使用 XML 中的根目录。
     * @param autoReset 是否自动复位。
     * @throws NullPointerException 入口参数 <code>in</code> 为 <code>null</code>。
     */
    public XmlJar2RepoResourceLoader(InputStream in, File repoDir, boolean autoReset) {
        super(in);
        this.repoDir = repoDir;
        this.autoReset = autoReset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(ResourceHandler resourceHandler) throws LoadFailedException, IllegalStateException {
        if (readFlag)
            throw new IllegalStateException(
                    DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_0));

        Objects.requireNonNull(resourceHandler,
                DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_1));

        try {
            readFlag = true;

            SAXReader reader = new SAXReader();
            Element root = reader.read(in).getRootElement();

            File repoDir0;

            if (Objects.nonNull(repoDir)) {
                repoDir0 = repoDir;
            } else {
                repoDir0 = new File(root.attributeValue("dir"));
            }

            /*
             * 根据 dom4j 的相关说明，此处转换是安全的。
             */
            @SuppressWarnings("unchecked")
            List<Element> infos = root.elements("info");

            for (Element info : infos) {
                String defString = info.attributeValue("default");
                String classify = info.attributeValue("classify");
                String fileName = info.attributeValue("filename");
                String key = info.attributeValue("key");

                if (Objects.isNull(defString) || Objects.isNull(classify) || Objects.isNull(fileName)
                        || Objects.isNull(key)) {
                    throw new LoadFailedException(
                            DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_2));
                }

                URL def = DwarfUtil.class.getResource(defString);

                if (Objects.isNull(def)) {
                    throw new LoadFailedException(
                            DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_3));
                }

                Url2RepoResource resource = new Url2RepoResource(key, def, repoDir0, classify, fileName);
                resourceHandler.add(resource);

                if (autoReset && !resource.isValid()) {
                    resource.reset();
                }
            }

        } catch (Exception e) {
            throw new LoadFailedException(DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_4),
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
                    DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_0));

        Objects.requireNonNull(resourceHandler,
                DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_1));

        final Set<LoadFailedException> exceptions = new LinkedHashSet<>();
        try {
            readFlag = true;

            SAXReader reader = new SAXReader();
            Element root = reader.read(in).getRootElement();

            File repoDir0;

            if (Objects.nonNull(repoDir)) {
                repoDir0 = repoDir;
            } else {
                repoDir0 = new File(root.attributeValue("dir"));
            }

            /*
             * 根据 dom4j 的相关说明，此处转换是安全的。
             */
            @SuppressWarnings("unchecked")
            List<Element> infos = root.elements("info");

            for (Element info : infos) {
                try {
                    String defString = info.attributeValue("default");
                    String classify = info.attributeValue("classify");
                    String fileName = info.attributeValue("filename");
                    String key = info.attributeValue("key");

                    if (Objects.isNull(defString) || Objects.isNull(classify) || Objects.isNull(fileName)
                            || Objects.isNull(key)) {
                        throw new LoadFailedException(
                                DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_2));
                    }

                    URL def = DwarfUtil.class.getResource(defString);

                    if (Objects.isNull(def)) {
                        throw new LoadFailedException(
                                DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_3));
                    }

                    Url2RepoResource resource = new Url2RepoResource(key, def, repoDir0, classify, fileName);
                    resourceHandler.add(resource);

                    if (autoReset && !resource.isValid()) {
                        resource.reset();
                    }
                } catch (Exception e) {
                    exceptions.add(new LoadFailedException(
                            DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_4), e));
                }

            }

        } catch (Exception e) {
            exceptions.add(new LoadFailedException(
                    DwarfUtil.getExceptionString(ExceptionStringKey.XMLJAR2REPORESOURCELOADER_4), e));
        }

        return exceptions;

    }
}
