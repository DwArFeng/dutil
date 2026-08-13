package com.dwarfeng.dutil.basic.sdk.time;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link TimeMeasurer} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class TimeMeasurerTest {

    @Test
    public void testLifecycle() throws Exception {
        TimeMeasurer measurer = new TimeMeasurer();

        assertTrue(measurer.isNotStarted());
        measurer.start();
        assertTrue(measurer.isTiming());
        assertThrows(IllegalStateException.class, measurer::start);

        Thread.sleep(Duration.ofMillis(1));
        measurer.stop();

        assertTrue(measurer.isStopped());
        assertTrue(measurer.getTimeNs() > 0);
        assertThrows(IllegalStateException.class, measurer::stop);
    }
}
