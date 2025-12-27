package com.dwarfeng.dutil.basic.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.threads.ExternalThreadSafe;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程安全输入流。
 *
 * <p>
 * 代理执行的输入流，并提供外部的线程安全。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class SyncInputStream extends FilterInputStream implements ExternalThreadSafe {

    /**
     * 同步锁。
     */
    protected final Lock lock = new ReentrantLock();

    /**
     * 生成一个代理指定输入流的线程安全输入流。
     *
     * @param in 指定的输入流。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public SyncInputStream(InputStream in) {
        super(in);
        Objects.requireNonNull(in, DwarfUtil.getExceptionString(ExceptionStringKey.SYNCINPUTSTREAM_0));
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
    public int read() throws IOException {
        lock.lock();
        try {
            return super.read();
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte[] b) throws IOException {
        lock.lock();
        try {
            return super.read(b);
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        lock.lock();
        try {
            return super.read(b, off, len);
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long skip(long n) throws IOException {
        lock.lock();
        try {
            return super.skip(n);
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int available() throws IOException {
        lock.lock();
        try {
            return super.available();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void mark(int readlimit) {
        lock.lock();
        try {
            super.mark(readlimit);
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void reset() throws IOException {
        lock.lock();
        try {
            super.reset();
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean markSupported() {
        lock.lock();
        try {
            return super.markSupported();
        } finally {
            lock.unlock();
        }
    }
}
