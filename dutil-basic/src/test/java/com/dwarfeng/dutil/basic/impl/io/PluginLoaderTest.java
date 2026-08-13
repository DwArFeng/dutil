package com.dwarfeng.dutil.basic.impl.io;

import com.dwarfeng.dutil.base.sdk.i18n.MessageContext;
import com.dwarfeng.dutil.basic.stack.plugin.Plugin;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link PluginLoader} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class PluginLoaderTest {

    @Test
    public void testEmptyDirectoryAndLifecycle() throws Exception {
        Path directory = Path.of("target", "missing-plugin-directory");

        PluginLoader<Plugin> loader = new PluginLoader<>(directory);
        assertEquals(directory.toAbsolutePath().normalize(), loader.directory());
        assertFalse(loader.isClosed());
        assertTrue(loader.loadPluginClass(TestPlugin.class).isEmpty());
        assertTrue(loader.loadPluginInstance(TestPlugin.class).isEmpty());

        loader.close();
        loader.close();
        assertTrue(loader.isClosed());
        IllegalStateException exception = MessageContext.call(
                Locale.SIMPLIFIED_CHINESE,
                () -> assertThrows(
                        IllegalStateException.class, () -> loader.loadPluginClass(TestPlugin.class)
                )
        );
        assertEquals("插件加载器已经关闭。", exception.getMessage());
    }

    private interface TestPlugin extends Plugin {
    }
}
