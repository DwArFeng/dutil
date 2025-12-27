package com.dwarfeng.dutil.develop.resource;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.develop.resource.io.XmlJar2FileResourceLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class Test_XmlJar2FileResourceLoader {

    private static ResourceHandler handler = null;
    private static XmlJar2FileResourceLoader loader = null;

    @Before
    public void setUp() throws Exception {
        handler = new DelegateResourceHandler();
        loader = new XmlJar2FileResourceLoader(
                this.getClass().getResourceAsStream("/com/dwarfeng/dutil/resources/test/develop/resource/paths.xml"));

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
        File file = new File("test\\");
        if (file.exists())
            FileUtil.deleteFile(file);
    }

    @Test
    public void testLoad() {
        try {
            loader.load(handler);
        } catch (LoadFailedException | IllegalStateException ignore) {
        }

        assertEquals(0, handler.size());
    }

    @Test(expected = LoadFailedException.class)
    public void testLoadException() throws LoadFailedException, IllegalStateException {
        loader.load(handler);
    }

    @Test
    public void testCountinuousLoad() throws IOException {
        Set<LoadFailedException> exceptions = loader.countinuousLoad(handler);
        assertEquals(1, exceptions.size());
        assertEquals(1, handler.size());
        Resource resource = handler.get("test");
        Scanner scanner = new Scanner(resource.openInputStream());
        assertEquals("HelloWorld!", scanner.nextLine());
        scanner.close();
        Writer writer = new OutputStreamWriter(resource.openOutputStream());
        writer.write("你好");
        writer.close();
        scanner = new Scanner(resource.openInputStream());
        assertEquals("你好", scanner.nextLine());
        scanner.close();
        resource.reset();
        scanner = new Scanner(resource.openInputStream());
        assertEquals("HelloWorld!", scanner.nextLine());
        scanner.close();
    }
}
