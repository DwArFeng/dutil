package com.dwarfeng.dutil.base.sdk.i18n;

import com.dwarfeng.dutil.base.stack.i18n.MessageCatalog;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * {@link Messages} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class MessagesTest {

    private static final MessageCatalog TEST_CATALOG = MessageCatalog.of(
            MessagesTest.class, "com.dwarfeng.dutil.base.sdk.i18n.fallback"
    );

    @Test
    public void testExplicitLocaleAndRootFallback() {
        assertEquals("Hello, dutil!", Messages.resolve(TEST_CATALOG, "greeting", Locale.US, "dutil"));
        assertEquals("你好，dutil！", Messages.resolve(TEST_CATALOG, "greeting", Locale.SIMPLIFIED_CHINESE, "dutil"));
        assertEquals("Root only", Messages.resolve(TEST_CATALOG, "root-only", Locale.SIMPLIFIED_CHINESE));
    }

    @Test
    public void testScopedLocaleDoesNotLeak() {
        assertFalse(MessageContext.isBound());
        String message = MessageContext.call(
                Locale.SIMPLIFIED_CHINESE, () -> Messages.resolve(TEST_CATALOG, "greeting", "dutil")
        );

        assertEquals("你好，dutil！", message);
        assertFalse(MessageContext.isBound());
    }

    @Test
    public void testMissingKeyFallback() {
        assertEquals("!missing!", Messages.resolve(TEST_CATALOG, "missing", Locale.ROOT));
    }

}
