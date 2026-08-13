package com.dwarfeng.dutil.basic.sdk.concurrent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link NumberedThreadFactory} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class NumberedThreadFactoryTest {

    @Test
    public void testPlatformThreadPropertiesAndNumbering() {
        NumberedThreadFactory factory = new NumberedThreadFactory("worker", true, Thread.MAX_PRIORITY);

        Thread firstThread = factory.newThread(() -> {
        });
        Thread secondThread = factory.newThread(() -> {
        });

        assertEquals("worker-1", firstThread.getName());
        assertEquals("worker-2", secondThread.getName());
        assertTrue(firstThread.isDaemon());
        assertEquals(Thread.MAX_PRIORITY, firstThread.getPriority());
        assertFalse(firstThread.isVirtual());
    }

    @Test
    public void testVirtualThreadPropertiesAndNumbering() {
        NumberedThreadFactory factory = NumberedThreadFactory.virtual("virtual-worker");

        Thread firstThread = factory.newThread(() -> {
        });
        Thread secondThread = factory.newThread(() -> {
        });

        assertEquals("virtual-worker-1", firstThread.getName());
        assertEquals("virtual-worker-2", secondThread.getName());
        assertTrue(firstThread.isVirtual());
    }
}
