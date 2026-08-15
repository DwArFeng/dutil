package com.dwarfeng.dutil.basic.impl.io;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * ByteBuffer 缓冲输出流。
 *
 * <p>
 * 将数据输入到指定的 {@link ByteBuffer}中。
 *
 * @author DwArFeng
 * @since 0.1.5-beta
 */
public class ByteBufferInputStream extends InputStream {

    private final ByteBuffer byteBuffer;
    private final boolean rewindFlag;

    /**
     * 生成一个具有指定的 ByteBuffer 的 ByteBuffer 缓冲输入流。
     *
     * @param byteBuffer 指定的 ByteBuffer。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public ByteBufferInputStream(ByteBuffer byteBuffer) {
        this(byteBuffer, true);
    }

    /**
     * 生成一个具有指定的 ByteBuffer 的 ByteBuffer 缓冲输入流。
     *
     * @param byteBuffer 指定的 ByteBuffer。
     * @param isRewind   是否重绕指定的 ByteBuffer。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public ByteBufferInputStream(ByteBuffer byteBuffer, boolean isRewind) {
        Objects.requireNonNull(
                byteBuffer, BasicMessages.message(BasicMessageKey.BYTE_BUFFER_INPUT_STREAM_BUFFER_REQUIRED)
        );

        this.byteBuffer = byteBuffer;
        this.rewindFlag = isRewind;

        if (this.rewindFlag) {
            byteBuffer.rewind();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read() {
        if (byteBuffer.remaining() == 0) {
            return -1;
        }
        return byteBuffer.get() & 0xFF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte @NotNull [] b) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte @NotNull [] b, int off, int len) throws IOException {
        if (len == 0)
            return 0;
        if (byteBuffer.remaining() == 0)
            return -1;
        len = Math.min(byteBuffer.remaining(), len);
        try {
            byteBuffer.get(b, off, len);
        } catch (BufferUnderflowException e) {
            throw new IOException(e);
        }
        return len;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long skip(long n) {
        int skip = (int) (n > byteBuffer.remaining() ? byteBuffer.remaining() : n);
        skip = Math.max(0, skip);
        byteBuffer.position(byteBuffer.position() + skip);
        return skip;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int available() {
        return byteBuffer.remaining();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (this.rewindFlag) {
            byteBuffer.rewind();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void mark(int readlimit) {
        byteBuffer.mark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void reset() {
        byteBuffer.reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean markSupported() {
        return true;
    }
}
