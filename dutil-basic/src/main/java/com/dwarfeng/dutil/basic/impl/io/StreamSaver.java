package com.dwarfeng.dutil.basic.impl.io;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;
import com.dwarfeng.dutil.basic.stack.io.Saver;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * 流保存器。
 *
 * <p>
 * 用流实现的保存器。
 *
 * @author DwArFeng
 * @since 0.0.3-beta
 */
public abstract class StreamSaver<T> implements Closeable, Saver<T> {

    /**
     * 保存器中的输出流
     */
    protected final OutputStream out;

    /**
     * 新实例。
     *
     * @param out 指定的输出流。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public StreamSaver(OutputStream out) {
        Objects.requireNonNull(out, BasicMessages.message(BasicMessageKey.STREAM_SAVER_OUTPUT_REQUIRED));
        this.out = out;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        out.close();
    }
}
