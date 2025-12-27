package com.dwarfeng.dutil.basic.prog;

import java.util.Objects;

/**
 * 拥有主键的元素接口。
 *
 * <p>
 * 该接口指示实现此接口的类是一个拥有主键的类。如果两个类拥有相同的主键，
 * 那么这两个类显然应该相等（或者能通过冲突测试），如果这两个类不相等（无法通过冲突测试）， 则说明两个类冲突。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface WithKey<K> {

    /**
     * 获取该元素的键。
     *
     * @return 该元素的键。
     */
    K getKey();

    /**
     * 测试指定的对象是否是该元素的键。
     *
     * @param object 指定的对象。
     * @return 指定的对象是否是该元素的键。
     */
    default boolean isKey(Object object) {
        return Objects.equals(getKey(), object);
    }

    /**
     * 测试两个拥有主键的元素是否冲突。
     *
     * <p>
     * 冲突的意思是指两个元素在拥有相同键的情况下表现不一致， 其中的表现由具体类来决定，一种经典的表现不一致是
     * <code> ! this.equals(element)</code>。
     *
     * @param element 指定的另一个拥有主键的元素。
     * @return 两个拥有主键的元素是否冲突。
     */
    default boolean isConflict(WithKey<K> element) {
        if (Objects.isNull(element))
            return false;
        return Objects.equals(getKey(), element.getKey()) && Objects.equals(this, element);
    }
}
