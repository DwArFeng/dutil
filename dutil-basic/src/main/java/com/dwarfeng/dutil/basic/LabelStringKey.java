package com.dwarfeng.dutil.basic;

import com.dwarfeng.dutil.basic.str.DefaultName;
import com.dwarfeng.dutil.basic.str.Name;

/**
 * 这个工具包使用的标签文本字段。
 *
 * <p>
 * 与 {@link ExceptionStringKey} 不同的是，标签文本字段是有可能需要经常切换语言的。
 *
 * <p>
 * 此枚举是对内使用的，它的主要作用是返回内部类所需要的标签文本字段。<br>
 * 请不要在外部程序中调用此包的枚举，因为该枚举对内使用，其本身不保证兼容性。
 *
 * <p>
 * <b>注意：</b> 该枚举在设计的时候不考虑兼容性，当发生不向上兼容的改动时，作者没有义务在变更日志中说明。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public enum LabelStringKey implements Name {

    /**
     * JExconsole 类第 0 号文本字段
     */
    JEXCONSOLE_0(new DefaultName("JExconsole.label.0")),
    /**
     * JExconsole 类第 1 号文本字段
     */
    JEXCONSOLE_1(new DefaultName("JExconsole.label.1")),
    /**
     * JExconsole 类第 2 号文本字段
     */
    JEXCONSOLE_2(new DefaultName("JExconsole.label.2")),

    ;

    private final Name name;

    LabelStringKey(Name name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name.getName();
    }
}
