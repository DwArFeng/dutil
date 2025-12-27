package com.dwarfeng.dutil.develop.logger;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对象记录器。
 *
 * <p>
 * 将记录指向指定对象的记录器。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public abstract class ObjectLogger implements Logger, Closeable {

    /**
     * 指定的输出流
     */
    protected final PrintWriter writer;

    /**
     * 生成一个指向指定对象的记录器。
     *
     * @param object 指定的对象。
     * @throws IllegalArgumentException 指定的对象非法。
     */
    public ObjectLogger(Object object) throws IllegalArgumentException {
        writer = createWriter(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(String message) {
        writer.println(String.format("%s\t[TRACE]\t%s", formatDate(), message));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(String message) {
        writer.println(String.format("%s\t[DEBUG]\t%s", formatDate(), message));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(String message) {
        writer.println(String.format("%s\t[INFO]\t%s", formatDate(), message));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String message) {
        writer.println(String.format("%s\t[WARN]\t%s", formatDate(), message));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(String message, Throwable t) {
        writer.println(String.format("%s\t[WARN]\t%s", formatDate(), message));
        t.printStackTrace(writer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(String message, Throwable t) {
        writer.println(String.format("%s\t[ERROR]\t%s", formatDate(), message));
        t.printStackTrace(writer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fatal(String message, Throwable t) {
        writer.println(String.format("%s\t[FATAL]\t%s", formatDate(), message));
        t.printStackTrace(writer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        writer.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((writer == null) ? 0 : writer.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ObjectLogger))
            return false;
        ObjectLogger other = (ObjectLogger) obj;
        if (writer == null) {
            return other.writer == null;
        } else return writer.equals(other.writer);
    }

    /**
     * 由指定的对象生成打印流。
     *
     * @param object 指定的对象。
     * @return 生成的打印流。
     * @throws IllegalArgumentException 指定的打印流。
     */
    protected abstract PrintWriter createWriter(Object object) throws IllegalArgumentException;

    private String formatDate() {
        DateFormat formatter = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss,SSS]");
        return formatter.format(new Date());
    }
}
