package com.dwarfeng.dutil.basic.impl.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SyncOutputStreamTest {

    private final static byte[] BYTES = "0123456789".getBytes(StandardCharsets.US_ASCII);

    private static StringOutputStream out;
    private static SyncOutputStream syncOut;

    @BeforeEach
    public void setUp() {
        out = new StringOutputStream(StandardCharsets.US_ASCII, false);
        syncOut = new SyncOutputStream(out);
    }

    @AfterEach
    public void tearDown() {
        try {
            syncOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            syncOut = null;
            out = null;
        }
    }

    @Test
    public final void testWriteInt() throws IOException {
        syncOut.write(49);
        syncOut.write(50);
        syncOut.write(52);
        syncOut.write(53);
        syncOut.write(48);
        syncOut.flush();
        assertEquals("12450", out.toString());
    }

    @Test
    public final void testWriteByteArray() throws IOException {
        syncOut.write(BYTES);
        syncOut.flush();
        assertEquals("0123456789", out.toString());
    }

    @Test
    public final void testWriteByteArrayIntInt() throws IOException {
        syncOut.write(BYTES, 2, 6);
        syncOut.flush();
        assertEquals("234567", out.toString());
    }

    @Test
    public final void testFlush() throws IOException {
        syncOut.write(49);
        syncOut.write(50);
        syncOut.write(52);
        syncOut.write(53);
        syncOut.write(48);
        assertEquals("", out.toString());
        syncOut.flush();
        assertEquals("12450", out.toString());
    }

    @Test
    public final void testGetLock() {
        assertNotNull(syncOut.getLock());
    }
}
