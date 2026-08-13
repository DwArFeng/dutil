package com.dwarfeng.dutil.basic.impl.io;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * 字符串输入流。
 *
 * <p>
 * 该输入流从指定的字符串中读入数据。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class StringInputStream extends InputStream {

    /**
     * 保存文本的数组。
     */
    protected final byte[] bytes;
    /**
     * 当前读取到的位置。
     */
    protected int pos = 0;
    /**
     * 标记的位置。
     */
    protected int markPos = -1;
    /**
     * 标记的限制。
     */
    protected int markLimit;

    /**
     * 构造一个基于指定字符串的字符串输入流。
     *
     * @param string 指定的字符串。
     * @throws NullPointerException 入口参数 <code>string</code>为 <code>null</code>。
     */
    public StringInputStream(String string) {
        this(string, Charset.defaultCharset());
    }

    /**
     * 构造一个基于指定字符串的，使用指定字符集的字符串输入流。
     *
     * @param string  指定的字符串。
     * @param charset 指定的字符集。
     * @throws NullPointerException 入口参数 <code>string</code>为 <code>null</code>。
     * @throws NullPointerException 入口参数 <code>charset</code> 为 <code>null</code>。
     */
    public StringInputStream(String string, Charset charset) {
        Objects.requireNonNull(string, BasicMessages.message(BasicMessageKey.STRING_INPUT_STREAM_TEXT_REQUIRED));
        Objects.requireNonNull(charset, BasicMessages.message(BasicMessageKey.STRING_INPUT_STREAM_CHARSET_REQUIRED));
        this.bytes = string.getBytes(charset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int available() {
        return bytes.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void mark(int readlimit) {
        markPos = pos;
        markLimit = readlimit < 0 ? bytes.length - pos : readlimit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean markSupported() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read() {
        if (pos >= bytes.length) {
            return -1;
        }
        int data = bytes[pos++];

        if ((markPos > 0) && (pos > markPos + markLimit)) {
            // 如果读取之后，读取位置超过了标记的限制，则清空标记。
            markPos = -1;
        }

        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte[] b) {
        Objects.requireNonNull(b, BasicMessages.message(BasicMessageKey.STRING_INPUT_STREAM_BUFFER_REQUIRED));

        int length = b.length + pos > bytes.length ? bytes.length - pos : b.length;
        System.arraycopy(bytes, pos, b, 0, length);

        pos += length;

        if ((markPos > 0) && (pos > markPos + markLimit)) {
            // 如果读取之后，读取位置超过了标记的限制，则清空标记。
            markPos = -1;
        }

        return length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte[] b, int off, int len) {
        Objects.requireNonNull(b, BasicMessages.message(BasicMessageKey.STRING_INPUT_STREAM_BUFFER_REQUIRED));

        if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException(
                    BasicMessages.message(BasicMessageKey.STRING_INPUT_STREAM_BUFFER_RANGE_INVALID)
            );
        }

        int length = len + pos > bytes.length ? bytes.length - pos : len;
        System.arraycopy(bytes, pos, b, off, length);

        pos += length;

        if ((markPos > 0) && (pos > markPos + markLimit)) {
            // 如果读取之后，读取位置超过了标记的限制，则清空标记。
            markPos = -1;
        }

        return length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void reset() throws IOException {
        if (markPos < 0) {
            throw new IOException(BasicMessages.message(BasicMessageKey.STRING_INPUT_STREAM_MARK_INVALID));
        }

        pos = markPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long skip(long n) {
        // 如果跳过的长度小于等于 0，什么也不做，直接返回 0。
        if (n <= 0)
            return 0;

        // 定义常用的参考数据。
        long tp = pos + n;
        long tm = markPos + markLimit;

        if ((markPos >= 0) && (tp > tm)) {
            // 如果该输入流被标记且当前位置加跳过的长度超过了标记的限制，则清除标记。
            markPos = -1;
        }

        if (tp > bytes.length) {
            // 如果跳过的长度超过了剩余数据的长度，直接跳过剩下的所有内容。
            pos = bytes.length;
            return n - pos;
        } else {
            // 如果跳过的长度小于剩余数据的长度，正常跳转。
            pos = (int) tp;
            return n;
        }
    }
}
