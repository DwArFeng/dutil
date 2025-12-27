package com.dwarfeng.dutil.develop.logger;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Objects;

/**
 * 写入器记录器。
 *
 * <p>
 * 将记录输出到指定写入器中的记录器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class WriterLogger extends ObjectLogger {

    /**
     * 生成一个具有指定写入器的写入器记录器。
     *
     * @param writer 指定的写入器。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public WriterLogger(Writer writer) {
        super(writer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PrintWriter createWriter(Object writer) throws IllegalArgumentException {
        Objects.requireNonNull(writer, DwarfUtil.getExceptionString(ExceptionStringKey.WRITERLOGGER_0));
        return new PrintWriter((Writer) writer, true);
    }
}
