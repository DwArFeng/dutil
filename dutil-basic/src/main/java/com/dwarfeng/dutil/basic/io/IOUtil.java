package com.dwarfeng.dutil.basic.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 与 IO 接口有关的一些功能。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class IOUtil {

    /**
     * 数据传送方法。
     *
     * <p>
     * 将一个输入流中的所有数据传递给一个输出流。<br>
     * 注意：该方法不会主动关闭 <code>in</code> 或者 <code>out</code>，需要调用额外的关闭方法关闭指定的输入流和输出流。
     *
     * @param in         指定的输入流。
     * @param out        指定的输出流。
     * @param bufferSize 指定的缓冲的大小。
     * @throws IOException 当数据无法读取或数据无法写入时抛出的异常。
     */
    public static void trans(InputStream in, OutputStream out, int bufferSize) throws IOException {
        if (in == null || out == null) {
            throw new NullPointerException("InputStream or OutputStream can't be null");
        }
        byte[] buffer = new byte[bufferSize];
        int i;
        while ((i = in.read(buffer, 0, buffer.length)) >= 0) {
            out.write(buffer, 0, i);
        }
    }

    // 禁止外部生成实例。
    private IOUtil() {
    }
}
