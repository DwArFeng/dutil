package com.dwarfeng.dutil.basic.internal.i18n;

import com.dwarfeng.dutil.base.sdk.i18n.Messages;
import com.dwarfeng.dutil.base.stack.i18n.MessageCatalog;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link BasicMessages} 的单元测试。
 *
 * <p>
 * 该测试验证模块消息键、职责目录和多语言资源之间的一致性，并确认消息入口使用缓存目录完成解析。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class BasicMessagesTest {

    private static final Pattern ARGUMENT_INDEX_PATTERN = Pattern.compile("\\{(\\d+)");
    private static final Object[] FORMAT_ARGUMENTS = {"alpha", "beta", "gamma"};
    private static final String MISSING_KEY = "missing";

    @Test
    public void testCatalogResourcesAndLocalizedMessages() throws IOException {
        for (BasicMessages.Catalog catalog : BasicMessages.Catalog.values()) {
            Map<String, String> rootMessages = loadMessages(catalog, Locale.ROOT);
            Map<String, String> chineseMessages = loadMessages(catalog, Locale.SIMPLIFIED_CHINESE);
            Set<String> expectedKeys = Arrays.stream(BasicMessageKey.values())
                    .filter(key -> key.catalog() == catalog)
                    .map(BasicMessageKey::key)
                    .collect(Collectors.toSet());

            assertFalse(expectedKeys.isEmpty(), catalog.name());
            assertEquals(expectedKeys, rootMessages.keySet(), catalog.name() + " root keys");
            assertEquals(expectedKeys, chineseMessages.keySet(), catalog.name() + " zh_CN keys");
            MessageCatalog messageCatalog = catalog.messageCatalog();
            assertSame(messageCatalog, catalog.messageCatalog(), catalog.name());

            for (BasicMessageKey key : BasicMessageKey.values()) {
                if (key.catalog() != catalog) {
                    continue;
                }
                String rootPattern = rootMessages.get(key.key());
                String chinesePattern = chineseMessages.get(key.key());
                assertEquals(argumentIndexes(rootPattern), argumentIndexes(chinesePattern), key.name());
                assertEquals(
                        format(rootPattern, Locale.ROOT),
                        BasicMessages.message(Locale.ROOT, key, FORMAT_ARGUMENTS),
                        key.name() + " root"
                );
                assertEquals(
                        format(chinesePattern, Locale.SIMPLIFIED_CHINESE),
                        BasicMessages.message(Locale.SIMPLIFIED_CHINESE, key, FORMAT_ARGUMENTS),
                        key.name() + " zh_CN"
                );
            }
        }
    }

    @Test
    public void testMissingKeyFallback() {
        for (BasicMessages.Catalog catalog : BasicMessages.Catalog.values()) {
            assertEquals(
                    "!" + MISSING_KEY + "!",
                    Messages.resolve(catalog.messageCatalog(), MISSING_KEY, Locale.ROOT),
                    catalog.name()
            );
        }
    }

    private static Map<String, String> loadMessages(BasicMessages.Catalog catalog, Locale locale)
            throws IOException {
        String resourceName = resourceName(catalog, locale);
        InputStream inputStream = catalog.messageCatalog().module().getResourceAsStream(resourceName);
        assertNotNull(inputStream, resourceName);
        try (inputStream; InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            Properties properties = new Properties();
            properties.load(reader);
            Map<String, String> messages = new LinkedHashMap<>();
            properties.stringPropertyNames().forEach(key -> messages.put(key, properties.getProperty(key)));
            return Map.copyOf(messages);
        }
    }

    private static String resourceName(BasicMessages.Catalog catalog, Locale locale) {
        String localeSuffix = Locale.ROOT.equals(locale) ? "" : "_" + locale;
        return catalog.messageCatalog().baseName().replace('.', '/') + localeSuffix + ".properties";
    }

    private static Set<Integer> argumentIndexes(String pattern) {
        Set<Integer> indexes = new java.util.HashSet<>();
        Matcher matcher = ARGUMENT_INDEX_PATTERN.matcher(pattern);
        while (matcher.find()) {
            indexes.add(Integer.parseInt(matcher.group(1)));
        }
        return Set.copyOf(indexes);
    }

    private static String format(String pattern, Locale locale) {
        return new MessageFormat(pattern, locale).format(FORMAT_ARGUMENTS);
    }
}
