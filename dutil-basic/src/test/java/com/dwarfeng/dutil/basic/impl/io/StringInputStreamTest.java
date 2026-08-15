package com.dwarfeng.dutil.basic.impl.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class StringInputStreamTest {

    private final static String TEXT = "0123456789";

    private static StringInputStream in;

    @BeforeEach
    public void setUp() {
        in = new StringInputStream(TEXT, StandardCharsets.US_ASCII);
    }

    @AfterEach
    public void tearDown() throws IOException {
        try {
            in.close();
        } finally {
            in = null;
        }
    }

    @Test
    public final void testRead() {
        byte[] bs = new byte[1];
        bs[0] = (byte) in.read();
        String str = new String(bs);
        assertEquals("0", str);
    }

    @Test
    public final void testReadByteArray0() {
        byte[] bs = new byte[4];
        int _ = in.read(bs);
        String str = new String(bs);
        assertEquals("0123", str);
    }

    @Test
    public final void testReadByteArray1() {
        @SuppressWarnings("SpellCheckingInspection")
        byte[] bs = "aaaaaaaaaa".getBytes();
        long _ = in.skip(3);
        assertEquals(7, in.read(bs));
        String str = new String(bs);
        assertEquals("3456789aaa", str);
    }

    @Test
    public final void testReadByteArrayIntInt0() {
        byte[] bs = new byte[4];
        int _ = in.read(bs, 0, 3);
        assertEquals(0, bs[3]);
        bs[3] = "3".getBytes()[0];
        String str = new String(bs);
        assertEquals("0123", str);
    }

    @Test
    public final void testReadByteArrayIntInt1() {
        @SuppressWarnings("SpellCheckingInspection")
        byte[] bs = "aaaaaaaaaa".getBytes();
        long _ = in.skip(4);
        assertEquals(6, in.read(bs, 2, 8));
        String str = new String(bs);
        assertEquals("aa456789aa", str);
    }

    @Test
    public void testAvailable() {
        assertEquals(10, in.available());
    }

    @Test
    public void testMarkSupported() {
        assertTrue(in.markSupported());
    }

    @Test
    public final void testMark() throws IOException {
        in.mark(3);
        byte[] bs = new byte[4];
        int _ = in.read(bs);
        in.reset();
    }

    @Test
    public final void testMarkAndReset0() throws IOException {
        long _ = in.skip(3);
        assertEquals(51, in.read());
        in.mark(10);
        assertEquals(52, in.read());
        long _ = in.skip(3);
        assertEquals(56, in.read());
        in.reset();
        assertEquals(52, in.read());
        assertEquals(53, in.read());
        assertEquals(54, in.read());
        assertEquals(55, in.read());
    }

    @Test
    public final void testMarkAndReset1() {
        assertThrows(IOException.class, () -> {
            in.skip(3);
            assertEquals(51, in.read());
            in.mark(2);
            in.skip(3);
            in.reset();
        });
    }

    @Test
    public final void testMarkAndReset2() {
        assertThrows(IOException.class, () -> {
            in.skip(3);
            assertEquals(51, in.read());
            in.mark(2);
            in.read();
            in.read();
            in.read();
            in.reset();
        });
    }

    @Test
    public final void testMarkAndReset3() {
        assertThrows(IOException.class, () -> {
            in.skip(3);
            assertEquals(51, in.read());
            in.mark(2);
            byte[] bs = new byte[3];
            in.read(bs);
            in.reset();
        });
    }

    @Test
    public final void testMarkAndReset4() {
        assertThrows(IOException.class, () -> {
            in.skip(3);
            assertEquals(51, in.read());
            in.mark(2);
            byte[] bs = new byte[4];
            in.read(bs, 1, 3);
            in.reset();
        });
    }
}
