package com.dwarfeng.dutil.basic.impl.io;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;
import com.dwarfeng.dutil.basic.stack.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * 基于 JDK 服务提供者机制的插件加载器。
 *
 * <p>
 * 插件目录中的 JAR 通过独立 {@link URLClassLoader} 加载，插件实现通过
 * {@code META-INF/services/com.dwarfeng.dutil.basic.stack.plugin.Plugin} 声明。
 * 该实现不再扫描并尝试实例化 JAR 中的任意类。
 *
 * <p>
 * 加载器拥有其内部类加载器，使用结束后必须调用 {@link #close()}。
 *
 * @param <T> 插件能力的公共上界。
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class PluginLoader<T extends Plugin> implements AutoCloseable {

    public static final String DEFAULT_DIR = "plugins";

    private final Path directory;
    private final URLClassLoader classLoader;
    private final AtomicBoolean closed = new AtomicBoolean();

    /**
     * 创建指向默认插件目录的加载器。
     */
    public PluginLoader() {
        this(Path.of(DEFAULT_DIR));
    }

    /**
     * 创建指向指定插件目录的加载器。
     *
     * @param path 插件目录路径。
     */
    public PluginLoader(String path) {
        this(Path.of(path));
    }

    /**
     * 创建指向指定插件目录的加载器。
     *
     * @param directory 插件目录。
     */
    public PluginLoader(File directory) {
        this(
                Objects.requireNonNull(
                        directory, BasicMessages.message(BasicMessageKey.PLUGIN_LOADER_DIRECTORY_REQUIRED)
                ).toPath()
        );
    }

    /**
     * 创建指向指定插件目录的加载器。
     *
     * @param directory 插件目录。
     */
    public PluginLoader(Path directory) {
        this.directory = Objects.requireNonNull(
                directory, BasicMessages.message(BasicMessageKey.PLUGIN_LOADER_DIRECTORY_REQUIRED)
        ).toAbsolutePath().normalize();
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        classLoader = new URLClassLoader(
                pluginUrls(this.directory), parent == null ? Plugin.class.getClassLoader() : parent
        );
    }

    /**
     * 返回规范化后的插件目录。
     *
     * @return 插件目录。
     */
    public Path directory() {
        return directory;
    }

    /**
     * 返回加载器是否已关闭。
     *
     * @return 是否已关闭。
     */
    public boolean isClosed() {
        return closed.get();
    }

    /**
     * 加载指定服务的提供者类型。
     *
     * @param serviceType 服务类型。
     * @param <U>         服务类型。
     * @return 不可变提供者类型列表。
     */
    public <U extends T> List<Class<? extends U>> loadPluginClass(Class<U> serviceType) {
        ensureOpen();
        Objects.requireNonNull(serviceType, BasicMessages.message(BasicMessageKey.PLUGIN_LOADER_SERVICE_TYPE_REQUIRED));
        List<Class<? extends U>> result = new ArrayList<>();
        providers().map(ServiceLoader.Provider::type)
                .filter(serviceType::isAssignableFrom)
                .forEach(type -> result.add(type.asSubclass(serviceType)));
        return List.copyOf(result);
    }

    /**
     * 加载并实例化指定服务的提供者。
     *
     * @param serviceType 服务类型。
     * @param <U>         服务类型。
     * @return 不可变提供者实例列表。
     */
    public <U extends T> List<U> loadPluginInstance(Class<U> serviceType) {
        ensureOpen();
        Objects.requireNonNull(serviceType, BasicMessages.message(BasicMessageKey.PLUGIN_LOADER_SERVICE_TYPE_REQUIRED));
        return providers()
                .map(ServiceLoader.Provider::get)
                .filter(serviceType::isInstance)
                .map(serviceType::cast)
                .toList();
    }

    /**
     * 关闭内部类加载器。
     *
     * @throws IOException 关闭失败。
     */
    @Override
    public void close() throws IOException {
        if (closed.compareAndSet(false, true)) {
            classLoader.close();
        }
    }

    private void ensureOpen() {
        if (closed.get()) {
            throw new IllegalStateException(BasicMessages.message(BasicMessageKey.PLUGIN_LOADER_CLOSED));
        }
    }

    private Stream<ServiceLoader.Provider<Plugin>> providers() {
        return ServiceLoader.load(Plugin.class, classLoader).stream();
    }

    private static URL[] pluginUrls(Path directory) {
        if (!Files.isDirectory(directory)) {
            return new URL[0];
        }
        try (Stream<Path> paths = Files.list(directory)) {
            return paths.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".jar"))
                    .sorted()
                    .map(PluginLoader::toUrl)
                    .toArray(URL[]::new);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static URL toUrl(Path path) {
        try {
            return path.toUri().toURL();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
