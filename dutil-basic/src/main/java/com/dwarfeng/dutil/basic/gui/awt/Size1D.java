package com.dwarfeng.dutil.basic.gui.awt;

import com.dwarfeng.dutil.basic.num.NumberValue;

/**
 * 一维尺寸接口。
 *
 * <p>
 * 该接口表示事物具有一维尺寸。
 *
 * @author DwArFeng
 * @since 0.1.5-beta
 */
public interface Size1D {

    /**
     * 获取对象的长度。
     *
     * @return 对象的长度。
     */
    NumberValue getLength();

    /**
     * 返回对象长度的int 形式。
     *
     * @return 对象长度的int 形式。
     */
    default int getIntLength() {
        return getLength().intValue();
    }

    /**
     * 返回对象长度的double 形式。
     *
     * @return 对象长度的double 形式。
     */
    default double getDoubleValue() {
        return getLength().doubleValue();
    }
}
