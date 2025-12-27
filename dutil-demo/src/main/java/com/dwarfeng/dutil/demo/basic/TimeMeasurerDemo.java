package com.dwarfeng.dutil.demo.basic;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.dutil.basic.num.unit.Time;

/**
 * {@link TimeMeasurer} 类的示例。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class TimeMeasurerDemo {

    public static void main(String[] args) throws InterruptedException {
        TimeMeasurer tm = new TimeMeasurer();
        CT.trace("time start");
        tm.start();
        // Wait 2 second approximately.
        Thread.sleep(2000);
        tm.stop();
        CT.trace("time stop");
        CT.trace(tm.getTimeNs() + "ns");
        CT.trace(tm.getTimeMs() + "ms");
        CT.trace(tm.getTimeSec() + "s");
        CT.trace(tm.getTime(Time.US) + "us");
        CT.trace(tm.formatStringNs());
        CT.trace(tm.formatStringMs());
        CT.trace(tm.formatStringSec());
    }
}
