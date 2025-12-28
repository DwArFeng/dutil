package com.dwarfeng.dutil.develop.resource;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.IOUtil;

import java.io.*;
import java.net.URL;
import java.util.Objects;

/**
 * URL 到文件的资源。
 *
 * <p>
 * 该资源的位置在本地，资源文件为磁盘中的 <code>File</code> 文件，而默认资源的地址则以 Url 的形式提供。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public class Url2FileResource extends AbstractResource {

    /**
     * 默认资源的位置。
     */
    protected final URL def;
    /**
     * 资源指向的文件。
     */
    protected final File res;

    /**
     * 新实例。
     *
     * @param key 指定的键。
     * @param def 默认资源的 URL。
     * @param res 资源指向的文件。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public Url2FileResource(String key, URL def, File res) {
        super(key);

        Objects.requireNonNull(def, DwarfUtil.getExceptionString(ExceptionStringKey.URL2FILERESOURCE_0));
        Objects.requireNonNull(res, DwarfUtil.getExceptionString(ExceptionStringKey.URL2FILERESOURCE_1));

        this.def = def;
        this.res = res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream openInputStream() throws IOException {
        return new FileInputStream(res);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutputStream openOutputStream() throws IOException {
        return new FileOutputStream(res);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public void reset() throws IOException {
        FileUtil.createFileIfNotExists(res);

        InputStream in = null;
        OutputStream out = null;

        try {
            in = def.openStream();
            out = new FileOutputStream(res);
            IOUtil.trans(in, out, 8192);
        } finally {
            if (Objects.nonNull(in)) {
                in.close();
            }
            if (Objects.nonNull(out)) {
                out.close();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid() {
        return res.exists();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return key.hashCode() * 177 + def.hashCode() + res.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Url2FileResource))
            return false;
        Url2FileResource that = (Url2FileResource) obj;

        return Objects.equals(this.key, that.key) && Objects.equals(this.def, that.def)
                && Objects.equals(this.res, that.res);
    }
}
