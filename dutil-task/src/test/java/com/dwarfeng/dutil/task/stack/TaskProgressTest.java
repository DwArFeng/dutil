package com.dwarfeng.dutil.task.stack;

import com.dwarfeng.dutil.base.sdk.i18n.MessageContext;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 任务模块国际化消息的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class TaskProgressTest {

    @Test
    public void testPublicApiMessagesUseScopedLocale() {
        IllegalArgumentException english = MessageContext.call(
                Locale.ENGLISH,
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new TaskProgress(-0.1, "", Instant.EPOCH)
                )
        );
        IllegalArgumentException chinese = MessageContext.call(
                Locale.SIMPLIFIED_CHINESE,
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> new TaskProgress(-0.1, "", Instant.EPOCH)
                )
        );

        assertEquals("Progress fraction must be finite and between 0 and 1.", english.getMessage());
        assertEquals("任务进度必须是 0 到 1 之间的有限数值。", chinese.getMessage());
    }
}
