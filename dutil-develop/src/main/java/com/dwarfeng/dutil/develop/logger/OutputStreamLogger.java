package com.dwarfeng.dutil.develop.logger;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * 输出流记录器。
 *
 * <p>
 * 将记录输出到指定输出流中的记录器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class OutputStreamLogger extends ObjectLogger {

    /**
     * 生成一个具有指定输出流的输出流记录器。
     *
     * @param out 指定的输出流。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public OutputStreamLogger(OutputStream out) {
        super(out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PrintWriter createWriter(Object out) throws IllegalArgumentException {
        Objects.requireNonNull(out, DwarfUtil.getExceptionString(ExceptionStringKey.OUTPUTSTREAMLOGGER_0));
        return new PrintWriter((OutputStream) out, true);
    }
}
