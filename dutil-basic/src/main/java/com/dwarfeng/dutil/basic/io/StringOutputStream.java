package com.dwarfeng.dutil.basic.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.num.NumberUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * 字符串构造输出流。
 *
 * <p>
 * 该输出流将输出的数据存储到字符串构造器中。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class StringOutputStream extends OutputStream {

    /**
     * 输出流的默认初始大小。
     */
    protected static final int DEFAULT_INITIAL_CAPACITY = 10;
    /**
     * 输出流的默认读取因子。
     */
    protected static final float DEFAULT_LOAD_FACTOR = 1.5f;

    /**
     * 指定的字符集合。
     */
    protected final Charset charset;
    /**
     * 该输出流是否自动刷新。
     */
    protected final boolean autoFlush;
    /**
     * 输出流缓存的增长因子。
     */
    protected final float loadFactor;

    /**
     * 字符串构造器
     */
    protected final StringBuilder stringBuilder = new StringBuilder();

    /**
     * 输出流的字符串解析器。
     */
    private final CharsetDecoder decoder;

    /**
     * 输出流的字节数据。
     */
    private ByteBuffer byteBuffer;

    /**
     * 生成一个默认的字符串构造输出流。
     */
    public StringOutputStream() {
        this(Charset.defaultCharset());
    }

    /**
     * 生成一个字符集合指定的字符串构造输出流。
     *
     * @param charset 指定的字符集合。
     * @throws NullPointerException 入口参数 charset 为 <code>null</code>。
     */
    public StringOutputStream(Charset charset) {
        this(charset, false);
    }

    /**
     * 生成一个字符集指定，并且指定是否刷新的字符串构造输出流。
     *
     * @param charset   指定的字符集合。
     * @param autoFlush 是否自动刷新。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public StringOutputStream(Charset charset, boolean autoFlush) {
        this(charset, false, DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 生成一个指定字符集，指定是否自动输出，指定字节缓存初始大小和指定读取因子的输出流。
     *
     * <p>
     * 如果指定的字节缓存初始大小小于 0，则缓存大小将设置为 0；如果指定的读取因子小于 1，则读取因子将设置成 1。
     *
     * @param charset         指定的字符集合。
     * @param autoFlush       是否自动刷新。
     * @param initialCapacity 指定的字节缓存初始大小。
     * @param loadFactor      指定的读取因子。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public StringOutputStream(Charset charset, boolean autoFlush, int initialCapacity, float loadFactor)
            throws NullPointerException {
        Objects.requireNonNull(charset, DwarfUtil.getExceptionString(ExceptionStringKey.STRINGOUTPUTSTREAM_0));

        this.charset = charset;
        this.autoFlush = autoFlush;
        this.loadFactor = loadFactor < 1 ? DEFAULT_LOAD_FACTOR : loadFactor;

        this.decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT);

        this.byteBuffer = ByteBuffer.allocate(Math.max(initialCapacity, 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        flushData(true);
        super.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException {
        flushData(false);
    }

    /**
     * 将该输出流接收到的数据以字符串的形式返回。
     *
     * <p>
     * 返回的字符串将包含之前所有被 flush 的数据，但不包括没有被 flush 的数据。
     *
     * @return 接收到的数据转化成的字符串。
     */
    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        ensureRemaining(len);
        byteBuffer.put(b, off, len);
        mayAutoFlushNotEndOfInput();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) throws IOException {
        ensureRemaining(1);
        byteBuffer.put(NumberUtil.cutInt2Byte(b));
        mayAutoFlushNotEndOfInput();
    }

    private void mayAutoFlushNotEndOfInput() throws IOException {
        if (autoFlush) {
            flushData(false);
        }
    }

    private void ensureRemaining(int requiredRemaining) {
        int capacityGrow = requiredRemaining - byteBuffer.remaining();

        if (capacityGrow > 0) {
            byteBuffer.flip();
            ByteBuffer tempByteBuffer = ByteBuffer
                    .allocate((int) ((byteBuffer.capacity() + capacityGrow) * loadFactor));
            tempByteBuffer.put(byteBuffer);
            byteBuffer = tempByteBuffer;
        }
    }

    private void flushData(boolean endOfInput) throws IOException {
        // 翻转字节缓冲，准备读取。
        byteBuffer.flip();

        // 根据解码器定义的每个字符的平均字节来分配字符缓冲。
        int n = (int) (byteBuffer.remaining() * decoder.averageCharsPerByte());
        CharBuffer charBuffer = CharBuffer.allocate(n);

        // 解码循环。
        for (; ; ) {
            CoderResult cr = decoder.decode(byteBuffer, charBuffer, endOfInput);

            if (cr.isUnderflow() && endOfInput)
                cr = decoder.flush(charBuffer);

            // 如果字符缓冲没有被填满，则退出解码循环。
            if (cr.isUnderflow())
                break;
            // 如果字符缓冲被填满了，则扩大字符缓冲的容量，继续解析剩下的字节数据。
            if (cr.isOverflow()) {
                n = 2 * n + 1; // 加 1 是为了保证 n 为 0 的时候还能继续扩大容量。
                CharBuffer tempCharBuffer = CharBuffer.allocate(n);
                charBuffer.flip();
                tempCharBuffer.put(charBuffer);
                charBuffer = tempCharBuffer;
                continue;
            }
            try {
                cr.throwException();
            } catch (CharacterCodingException e) {
                throw new IOException(e);
            }
        }

        // 将字符缓冲中的字符输入到文本构造器中。
        charBuffer.flip();
        stringBuilder.append(Arrays.copyOfRange(charBuffer.array(), charBuffer.position(), charBuffer.limit()));

        // 将解析之后字节缓冲中余下的数据重新输入到字节数据中。
        ByteBuffer tempByteBuffer = ByteBuffer.allocate(byteBuffer.capacity());
        tempByteBuffer.put(byteBuffer);
        byteBuffer = tempByteBuffer;
    }
}
