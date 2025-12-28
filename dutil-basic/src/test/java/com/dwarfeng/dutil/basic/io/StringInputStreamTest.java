package com.dwarfeng.dutil.basic.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringInputStreamTest {

    private final static String TEXT = "0123456789";

    private static StringInputStream in;

    @Before
    public void setUp() {
        in = new StringInputStream(TEXT, StandardCharsets.US_ASCII);
    }

    @After
    public void tearDown() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in = null;
        }
    }

    @Test
    public final void testRead() throws IOException {
        byte[] bs = new byte[1];
        bs[0] = (byte) in.read();
        String str = new String(bs);
        assertEquals("0", str);
    }

    @Test
    public final void testReadByteArray0() throws IOException {
        byte[] bs = new byte[4];
        in.read(bs);
        String str = new String(bs);
        assertEquals("0123", str);
    }

    @Test
    public final void testReadByteArray1() throws IOException {
        byte[] bs = "aaaaaaaaaa".getBytes();
        in.skip(3);
        assertEquals(7, in.read(bs));
        String str = new String(bs);
        assertEquals("3456789aaa", str);
    }

    @Test
    public final void testReadByteArrayIntInt0() throws IOException {
        byte[] bs = new byte[4];
        in.read(bs, 0, 3);
        assertEquals(0, bs[3]);
        bs[3] = "3".getBytes()[0];
        String str = new String(bs);
        assertEquals("0123", str);
    }

    @Test
    public final void testReadByteArrayIntInt1() throws IOException {
        byte[] bs = "aaaaaaaaaa".getBytes();
        in.skip(4);
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
        in.read(bs);
        in.reset();
    }

    @Test
    public final void testMarkAndReset0() throws IOException {
        in.skip(3);
        assertEquals(51, in.read());
        in.mark(10);
        assertEquals(52, in.read());
        in.skip(3);
        assertEquals(56, in.read());
        in.reset();
        assertEquals(52, in.read());
        assertEquals(53, in.read());
        assertEquals(54, in.read());
        assertEquals(55, in.read());
    }

    @Test(expected = IOException.class)
    public final void testMarkAndReset1() throws IOException {
        in.skip(3);
        assertEquals(51, in.read());
        in.mark(2);
        in.skip(3);
        in.reset();
    }

    @Test(expected = IOException.class)
    public final void testMarkAndReset2() throws IOException {
        in.skip(3);
        assertEquals(51, in.read());
        in.mark(2);
        in.read();
        in.read();
        in.read();
        in.reset();
    }

    @Test(expected = IOException.class)
    public final void testMarkAndReset3() throws IOException {
        in.skip(3);
        assertEquals(51, in.read());
        in.mark(2);
        byte[] bs = new byte[3];
        in.read(bs);
        in.reset();
    }

    @Test(expected = IOException.class)
    public final void testMarkAndReset4() throws IOException {
        in.skip(3);
        assertEquals(51, in.read());
        in.mark(2);
        byte[] bs = new byte[4];
        in.read(bs, 1, 3);
        in.reset();
    }
}
