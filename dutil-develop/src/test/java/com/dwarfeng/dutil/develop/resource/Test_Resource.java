package com.dwarfeng.dutil.develop.resource;

import com.dwarfeng.dutil.basic.io.StringInputStream;
import org.junit.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import static org.junit.Assert.*;

public class Test_Resource {

    private static final class TestResource implements Resource {

        String str = null;

        /**
         * {@inheritDoc}
         */
        @Override
        public String getKey() {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public InputStream openInputStream() throws IOException {
            if (Objects.isNull(str)) {
                throw new IOException("指定目标为 null。");
            }
            return new StringInputStream(str);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public OutputStream openOutputStream() {
            throw new UnsupportedOperationException("openOutputStream");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void reset() {
            str = "123456";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isValid() {
            return Objects.nonNull(str);
        }

    }

    private static TestResource resource;

    @BeforeClass
    public static void setUpBeforeClass() {
    }

    @AfterClass
    public static void tearDownAfterClass() {
    }

    @Before
    public void setUp() {
        resource = new TestResource();
    }

    @After
    public void tearDown() {
        resource = null;
    }

    @Test
    public final void testIsValid() throws IOException {
        assertFalse(resource.isValid());
        resource.reset();
        assertTrue(resource.isValid());
    }

    @Test
    public final void testAutoReset0() throws IOException {
        InputStream in = resource.autoReset().openInputStream();
        byte[] bs = new byte[6];
        in.read(bs);
        String string = new String(bs);
        assertEquals("123456", string);
    }

    @Test
    public final void testAutoReset1() throws IOException {
        resource.str = "654321";
        InputStream in = resource.autoReset().openInputStream();
        byte[] bs = new byte[6];
        in.read(bs);
        String string = new String(bs);
        assertEquals("654321", string);
    }
}
