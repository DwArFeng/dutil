package com.dwarfeng.dutil.develop.resource;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.IOUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class Test_Url2FileResource {

    private static Url2FileResource resource = null;

    @Before
    public void setUp() throws Exception {
        resource = new Url2FileResource("test.foo",
                this.getClass().getResource("/com/dwarfeng/dutil/resources/test/develop/resource/hello.txt"),
                new File("test/hello.txt"));
        InputStream in = this.getClass()
                .getResourceAsStream("/com/dwarfeng/dutil/resources/test/develop/resource/hello.txt");
        File file = new File("test/hello.txt");
        FileUtil.createFileIfNotExists(file);
        OutputStream out = new FileOutputStream(file);
        IOUtil.trans(in, out, 4096);
        in.close();
        out.close();
    }

    @After
    public void tearDown() throws Exception {
        File file = new File("test/");
        if (file.exists())
            FileUtil.deleteFile(file);
    }

    @Test
    public void testHashCode() {
        Url2FileResource anotherResource = new Url2FileResource("test.foo",
                this.getClass().getResource("/com/dwarfeng/dutil/resources/test/develop/resource/hello.txt"),
                new File("test/hello.txt"));
        assertEquals(anotherResource.hashCode(), resource.hashCode());
    }

    @Test
    public void testOpenInputStream() throws IOException {
        Scanner scanner = new Scanner(resource.openInputStream());
        assertEquals("HelloWorld!", scanner.nextLine());
        scanner.close();
    }

    @Test
    public void testOpenOutputStream() throws IOException {
        OutputStream out = resource.openOutputStream();
        Writer writer = new OutputStreamWriter(out);
        writer.write("你好");
        writer.close();
        Scanner scanner = new Scanner(resource.openInputStream());
        assertEquals("你好", scanner.nextLine());
        scanner.close();
    }

    @Test
    public void testReset() throws IOException {
        OutputStream out = resource.openOutputStream();
        Writer writer = new OutputStreamWriter(out);
        writer.write("你好");
        writer.close();
        Scanner scanner = new Scanner(resource.openInputStream());
        assertEquals("你好", scanner.nextLine());
        scanner.close();
        resource.reset();
        scanner = new Scanner(resource.openInputStream());
        assertEquals("HelloWorld!", scanner.nextLine());
        scanner.close();
    }

    @Test
    public void testEqualsObject() {
        Url2FileResource anotherResource = new Url2FileResource("test.foo",
                this.getClass().getResource("/com/dwarfeng/dutil/resources/test/develop/resource/hello.txt"),
                new File("test/hello.txt"));
        assertEquals(anotherResource.hashCode(), resource.hashCode());
    }
}
