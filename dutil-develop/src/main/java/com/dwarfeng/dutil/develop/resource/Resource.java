package com.dwarfeng.dutil.develop.resource;

import com.dwarfeng.dutil.basic.prog.WithKey;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 程序中使用的资源。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Resource extends WithKey<String> {

    /**
     * 打开资源的输入流。
     *
     * @return 资源的输入流。
     * @throws IOException IO 异常。
     */
    InputStream openInputStream() throws IOException;

    /**
     * 打开资源的输出流。
     *
     * @return 资源的输出流。
     * @throws IOException IO 异常。
     */
    OutputStream openOutputStream() throws IOException;

    /**
     * 将文件资源置换成默认的资源。
     *
     * @throws IOException IO 异常。
     */
    void reset() throws IOException;

    /**
     * 返回该资源是否有效。
     *
     * @return 该资源是否有效。
     */
    boolean isValid();

    /**
     * 自动复位。
     *
     * <p>
     * 该方法是 Resource 接口的一个快捷使用方法，目的是为不使用过多的代码，方便的在必要的时候重置资源。<br>
     * 该方法运行时，会检查此资源接口的 {@link #isValid()} 方法，如果返回 <code>false</code>， 则自动执行
     * {@link #reset()} 方法，并返回自身。
     *
     * <p>
     * 常见的用法如下：
     *
     * <pre>
     * resource.autoRest().openInputStream();
     * </pre>
     *
     * <p>
     * 在此之前，该方法只能写作:
     *
     * <pre>
     * try {
     * 	resource.openInputStream();
     * } catch (IOException e) {
     * 	resource.reset();
     * 	resource.openInputStream();
     * }
     * </pre>
     *
     * @return 资源自身。
     * @throws IOException 复位是发生异常。
     */
    default Resource autoReset() throws IOException {
        if (!isValid()) {
            reset();
        }
        return this;
    }
}
