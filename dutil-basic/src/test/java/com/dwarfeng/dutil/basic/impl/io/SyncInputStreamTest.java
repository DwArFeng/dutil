package com.dwarfeng.dutil.basic.impl.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SyncInputStreamTest {

    private final static String TEXT = "0123456";

    private static StringInputStream in;
    private static SyncInputStream syncIn;

    @BeforeEach
    public void setUp() {
        in = new StringInputStream(TEXT);
        syncIn = new SyncInputStream(in);
    }

    @AfterEach
    public void tearDown() throws IOException {
        try {
            syncIn.close();
        } finally {
            syncIn = null;
            in = null;
        }
    }

    @Test
    public final void testRead() throws IOException {
        byte[] bs = new byte[1];
        bs[0] = (byte) syncIn.read();
        String str = new String(bs);
        assertEquals("0", str);
    }

    @Test
    public final void testReadByteArray() throws IOException {
        byte[] bs = new byte[4];
        int _ = syncIn.read(bs);
        String str = new String(bs);
        assertEquals("0123", str);
    }

    @Test
    public final void testReadByteArrayIntInt() throws IOException {
        byte[] bs = new byte[4];
        int _ = syncIn.read(bs, 0, 3);
        assertEquals(0, bs[3]);
        bs[3] = "3".getBytes()[0];
        String str = new String(bs);
        assertEquals("0123", str);
    }

    @Test
    public final void testSkip() throws IOException {
        long _ = syncIn.skip(3);
        byte[] bs = new byte[4];
        int _ = syncIn.read(bs);
        String str = new String(bs);
        assertEquals("3456", str);
    }

    @Test
    public final void testAvailable() throws IOException {
        assertEquals(7, syncIn.available());
    }

    @Test
    public final void testMarkAndReset() {
        assertThrows(IOException.class, () -> {
            try {
                syncIn.skip(3);
                syncIn.mark(0);

                byte[] bs = new byte[4];
                syncIn.read(bs);
                String str = new String(bs);
                assertEquals("3456", str);
            } catch (IOException e) {
                fail("在意外的地方发生异常。");
            }
            syncIn.reset();
        });
    }

    @Test
    public final void testMarkSupported() {
        assertTrue(syncIn.markSupported());
    }

    @Test
    public final void testGetLock() {
        assertNotNull(syncIn.getLock());
    }
}
