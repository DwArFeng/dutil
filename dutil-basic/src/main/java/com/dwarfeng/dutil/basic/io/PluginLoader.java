package com.dwarfeng.dutil.basic.io;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 插件读取类，用于读取指定位置下的指定类型的插件。
 *
 * <p>
 * 插件读取类一经实例化，其读取路径便不能再被更改。
 * <br>插件的加载是通过<code>URLClassLoader</code>来实现的，该方法加载以后，不会主动不关闭。
 * 要想关闭的话请调用{@link PluginLoader#close()} 法，该类被关闭以后，如果继续生成加载的类的实例的话
 * 则可能出现找不到资源的情况。因此，在确保不会有新的实例生成以后，再关闭此类。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class PluginLoader<T> {

    public final static String DEFAULT_DIR = "plugins";

    private final URLClassLoader loader;
    private final File dirFile;
    private final File[] jarFiles;
    private boolean isClose;

    /**
     * 生成一个指向默认路径的插件读取类。
     * <br>默认的读取位置是同目录下的<code>plugins</code>文件夹。
     */
    public PluginLoader() {
        this(new File(DEFAULT_DIR));
    }

    /**
     * 生成一个指向执行文本决定的路径的插件读取类。
     *
     * @param path 指定的路径文本。
     */
    public PluginLoader(String path) {
        this(new File(path));
    }

    /**
     * 生成一个指向指定文件决定的目录的插件读取类。
     *
     * @param dirFile 指定的文件。
     */
    public PluginLoader(File dirFile) {
        // dirFile 不能为 null
        if (dirFile == null) throw new NullPointerException("Dir file can't be null");
        this.dirFile = dirFile;
        File[] tempJarFiles = getDirFile().listFiles(new FileExtensionNameFiliter(".jar"));
        // 搜索目录所有后缀名为.jar 的文件并考虑 null 的特殊情况。
        jarFiles = tempJarFiles == null ? new File[0] : tempJarFiles;
        // 批量转化为 url；
        Set<URL> urls = new HashSet<>();
        for (File jarFile : jarFiles) {
            try {
                urls.add(jarFile.toURI().toURL());
            } catch (MalformedURLException e) {
                CT.trace("Exception occured while transform file to url :" + jarFile.getName());
            }
        }
        // 初始化 loader
        loader = new URLClassLoader(urls.toArray(new URL[0]), Thread.currentThread().getContextClassLoader());
        // 初始化是否关闭的标记。
        isClose = false;
    }

    /**
     * 返回该插件读取类的指向路径。
     *
     * @return 该插件读取类的指向路径。
     */
    public File getDirFile() {
        return this.dirFile;
    }

    /**
     * 关闭当前的额插件加载类，这会使已经读取的类生成新的实例时出现问题。
     *
     * @throws IOException 出现接口异常或者已经关闭时抛出异常。
     */
    public void close() throws IOException {
        try {
            if (isClose) throw new IOException("PluginLoader has already been closed");
            loader.close();
        } finally {
            isClose = true;
        }
    }

    /**
     * 加载所有属于<code>clas</code>子类插件的类。
     *
     * @param clas 指定的父类。
     * @param <U>  泛型。
     * @return 所有被加载的插件类所组成的集合。
     * @throws IOException 当该插件加载对象已经被关闭的时候抛出该异常。
     */
    @SuppressWarnings("unchecked")
    public <U extends T> Collection<Class<U>> loadPluginClass(Class<U> clas) throws IOException {
        if (isClose) throw new IOException("PluginLoader already closed");
        Collection<Class<U>> classCollection = new HashSet<>();
        bk0:
        for (File jarFile : jarFiles) {
            Enumeration<JarEntry> je = null;
            JarFile jf = null;

            try {
                // 寻找 Entry，出现异常直接放弃整个 jar 包，进行下一个 jar 包的调度。
                try {
                    jf = new JarFile(jarFile);
                    je = jf.entries();
                } catch (IOException e) {
                    CT.trace("Exception occured while getting entries : " + jarFile.getName());
                    continue bk0;
                }
                bk1:
                while (je.hasMoreElements()) {
                    JarEntry entry = je.nextElement();
                    if (entry.getName() != null && entry.getName().endsWith(".class")) {
                        Class<?> c = null;
                        // 分别把对应的插件列入注册表。出现异常放弃该 class 文件。
                        try {
                            c = loader.loadClass(entry.getName().replace("/", ".").substring(0, entry.getName().length() - 6));
                            if (clas.isAssignableFrom(c)) classCollection.add((Class<U>) c);
                        } catch (ClassNotFoundException e) {
                            CT.trace("Exception occured while loading class : " + entry.getName());
                            continue bk1;
                        }
                    }
                }
            } finally {
                if (Objects.nonNull(jf)) {
                    try {
                        jf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classCollection;
    }

    /**
     * 读取间接继承指定类的子类，并将其实例化。
     *
     * <p>
     * 注意：该方法只能实现具有默认构造器方法的类，如果其中有的类没有默认的构造器方法，则会提示异常。
     *
     * @param clas 直接或间接继承的类。
     * @param <U>  泛型。
     * @return 所有子类的实例化对象集合。
     * @throws IOException 当该插件加载对象已经被关闭的时候抛出该异常。
     */
    public <U extends T> Collection<U> loadPluginInstance(Class<U> clas) throws IOException {
        Collection<U> instanceCollection = new HashSet<>();
        Collection<Class<U>> classCollection = loadPluginClass(clas);
        Iterator<Class<U>> iterator = classCollection.iterator();
        Class<U> next = null;
        while (iterator.hasNext()) {
            try {
                next = iterator.next();
                instanceCollection.add(next.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                CT.trace("Exception occured when class was initialized" + next.getName());
            }
        }
        return instanceCollection;
    }
}
