package com.dwarfeng.dutil.basic.impl.io;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;
import com.dwarfeng.dutil.basic.stack.io.Loader;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 流记读取器。
 *
 * <p>
 * 用流实现的读取器。
 *
 * @author DwArFeng
 * @since 0.0.3-beta
 */
public abstract class StreamLoader<T> implements Closeable, Loader<T> {

    /**
     * 输入流。
     */
    protected final InputStream in;

    /**
     * 新实例。
     *
     * @param in 指定的输入流。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public StreamLoader(InputStream in) {
        Objects.requireNonNull(in, BasicMessages.message(BasicMessageKey.STREAM_LOADER_INPUT_REQUIRED));
        this.in = in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        this.in.close();
    }
}
