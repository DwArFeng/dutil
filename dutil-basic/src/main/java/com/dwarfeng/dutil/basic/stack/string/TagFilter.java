package com.dwarfeng.dutil.basic.stack.string;

import com.dwarfeng.dutil.basic.stack.function.Filter;

/**
 * 标签接口。
 *
 * <p>
 * 除了拥有指定的筛选器行为之外，还具有标签的属性。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface TagFilter<T> extends Filter<T>, Tag {
}
