package com.dwarfeng.dutil.develop.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TestResource extends AbstractResource {

    public static final TestResource A = new TestResource("A");
    public static final TestResource B = new TestResource("B");
    public static final TestResource C = new TestResource("C");

    public TestResource(String key) {
        super(key);
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return null;
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return null;
    }

    @Override
    public void reset() throws IOException {
    }
}
