package com.dwarfeng.dutil.basic.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.num.NumberUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * ByteBuffer 缓冲输出流。
 *
 * <p>
 * 将数据输出到指定的 {@link ByteBuffer}中。
 *
 * @author DwArFeng
 * @since 0.1.5-beta
 */
public final class ByteBufferOutputStream extends OutputStream {

    private final ByteBuffer byteBuffer;
    private final boolean clearFlag;

    /**
     * 生成一个具有指定的 ByteBuffer 的 ByteBuffer 缓冲输出流。
     *
     * @param byteBuffer 指定的 ByteBuffer。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public ByteBufferOutputStream(ByteBuffer byteBuffer) {
        this(byteBuffer, true);
    }

    /**
     * 生成一个具有指定的 ByteBuffer 的 ByteBuffer 缓冲输出流。
     *
     * @param byteBuffer 指定的 ByteBuffer。
     * @param isClear    是否清除指定的 ByteBuffer，当该参数为 <code>false</code> 的时候，调用
     *                   <code>close()</code> 方法时，指定的 byteBuffer 也不会随之调用
     *                   <code>flip()</code>方法。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public ByteBufferOutputStream(ByteBuffer byteBuffer, boolean isClear) {
        Objects.requireNonNull(byteBuffer, DwarfUtil.getExceptionString(ExceptionStringKey.BYTEBUFFEROUTPUTSTREAM_0));

        this.byteBuffer = byteBuffer;
        this.clearFlag = isClear;

        if (this.clearFlag) {
            byteBuffer.clear();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) throws IOException {
        try {
            byteBuffer.put(NumberUtil.int2Byte(b));
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b) throws IOException {
        try {
            byteBuffer.put(b);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        try {
            byteBuffer.put(b, off, len);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        if (clearFlag) {
            byteBuffer.flip();
        }
    }
}
