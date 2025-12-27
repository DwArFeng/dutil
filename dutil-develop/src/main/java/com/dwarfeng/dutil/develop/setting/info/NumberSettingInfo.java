package com.dwarfeng.dutil.develop.setting.info;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.num.Interval;
import com.dwarfeng.dutil.develop.setting.AbstractSettingInfo;

import java.util.Objects;

/**
 * 数字配置信息。
 *
 * <p>
 * 该配置信息是所有与数字有关的配置信息的父类，本身有一个区间变量， 只有当文本值是一个数字，且这个数字在指定的区间时，配置信息才会判定该值有效。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public abstract class NumberSettingInfo extends AbstractSettingInfo {

    /**
     * 配置信息使用的区间。
     */
    protected final Interval interval;

    /**
     * 生成一个新的数字配置信息。
     *
     * <p>
     * 信息数字配置信息默认全区间数字可用。
     *
     * @param defaultValue 指定的默认值。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 指定的默认值不能通过自身检查。
     */
    public NumberSettingInfo(String defaultValue) throws NullPointerException, IllegalArgumentException {
        this(defaultValue, Interval.INTERVAL_REALNUMBER);
    }

    /**
     * 生成一个具有指定区间的数字配置信息。
     *
     * @param defaultValue 指定的默认值。
     * @param interval     指定的区间。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 指定的默认值不能通过自身检查。
     */
    public NumberSettingInfo(String defaultValue, Interval interval)
            throws NullPointerException, IllegalArgumentException {
        super(defaultValue);
        Objects.requireNonNull(interval, DwarfUtil.getExceptionString(ExceptionStringKey.NUMBERSETTINGINFO_0));
        this.interval = interval;
    }
}
