package com.dwarfeng.dutil.basic.num;

import com.dwarfeng.dutil.basic.prog.Filter;

/**
 * 数字值过滤器。
 *
 * <p>
 * 能够过滤数字值的接口。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 * @deprecated 该类可以通过使用 lambda 表达式构造<code>Filter&ltNumberValue&gt</code>匿名类快速实现，因此没必要保留这个类。
 */
public interface NumberValueFilter extends Filter<NumberValue> {

    @Override
    boolean accept(NumberValue value);
}
