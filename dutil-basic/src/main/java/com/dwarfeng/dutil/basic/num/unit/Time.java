package com.dwarfeng.dutil.basic.num.unit;

import com.dwarfeng.dutil.basic.num.NumberValue;

/**
 * 时间枚举。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public enum Time implements NumberValue {

    /**
     * 纳秒。
     */
    NS(86400000000000d),

    /**
     * 微秒。
     */
    US(86400000000d),

    /**
     * 毫秒。
     */
    MS(86400000d),

    /**
     * 秒。
     */
    SEC(86400d),

    /**
     * 分钟。
     */
    MIN(1440d),

    /**
     * 小时。
     */
    HOR(24d),

    /**
     * 天。
     */
    DAY(1d);

    private final double val;

    Time(double val) {
        this.val = val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double doubleValue() {
        return this.val;
    }
}
