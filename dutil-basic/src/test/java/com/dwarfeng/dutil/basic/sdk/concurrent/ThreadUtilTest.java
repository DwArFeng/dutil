package com.dwarfeng.dutil.basic.sdk.concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThreadUtilTest {

    @Test
    public void testUnmodifiableLock_0() {
        ThreadUtil.unmodifiableLock(new ReentrantLock());
    }

    @Test
    public void testUnmodifiableLock_1() {
        assertThrows(NullPointerException.class, () -> {
            ThreadUtil.unmodifiableLock(null);
        });
    }
}
