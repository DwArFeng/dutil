package com.dwarfeng.dutil.basic.stack.number.unit;

import com.dwarfeng.dutil.basic.stack.number.NumberValue;

/**
 * 质量枚举。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public enum Mass implements NumberValue {

    /**
     * 吨
     */
    T(1d),

    /**
     * 千克
     */
    KG(1000d),

    /**
     * 克
     */
    G(1000000d),

    /**
     * 毫克
     */
    MG(1000000000d),

    /**
     * 盎司
     */
    OZ(35273.96194958d),

    /**
     * 磅
     */
    LB(2204.62262185d),

    /**
     * 打兰
     */
    DR(564383.39119329d);

    private final double val;

    Mass(double val) {
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
