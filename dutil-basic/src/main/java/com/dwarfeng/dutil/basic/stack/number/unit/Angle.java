package com.dwarfeng.dutil.basic.stack.number.unit;

import com.dwarfeng.dutil.basic.stack.number.NumberValue;

/**
 * 角度枚举。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public enum Angle implements NumberValue {

    /**
     * 角度
     */
    DEG(180),

    /**
     * 弧度
     */
    RAD(Math.PI),

    /**
     * 百分度
     */
    GRAD(200),

    /**
     * 分
     */
    MIN(10800),

    /**
     * 秒
     */
    SEC(648000);

    private final double val;

    Angle(double val) {
        this.val = val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double doubleValue() {
        return val;
    }
}
