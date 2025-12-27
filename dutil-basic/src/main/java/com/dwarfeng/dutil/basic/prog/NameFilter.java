package com.dwarfeng.dutil.basic.prog;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * 带有名称的筛选器接口。
 *
 * <p>
 * 除了拥有指定的筛选器行为之外，还具有名称的属性。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public interface NameFilter<T> extends Filter<T>, Name {
}
