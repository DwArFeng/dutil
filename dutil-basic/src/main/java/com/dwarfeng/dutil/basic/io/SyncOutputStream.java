package com.dwarfeng.dutil.basic.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.threads.ExternalThreadSafe;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程安全输出流。
 *
 * <p>
 * 代理执行的输出流，并提供外部的线程安全。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class SyncOutputStream extends FilterOutputStream implements ExternalThreadSafe {

    /**
     * 同步锁。
     */
    protected final Lock lock = new ReentrantLock();

    /**
     * 生成一个代理指定输出流的线程安全输出流。
     *
     * @param out 指定的输出流。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public SyncOutputStream(OutputStream out) {
        super(out);
        Objects.requireNonNull(out, DwarfUtil.getExceptionString(ExceptionStringKey.SYNCOUTPUTSTREAM_0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lock getLock() {
        return lock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) throws IOException {
        lock.lock();
        try {
            super.write(b);
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b) throws IOException {
        lock.lock();
        try {
            super.write(b);
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        lock.lock();
        try {
            super.write(b, off, len);
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException {
        lock.lock();
        try {
            super.flush();
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        lock.lock();
        try {
            super.close();
        } finally {
            lock.unlock();
        }
    }
}
