package com.dwarfeng.dutil.develop.cfg.struct;

/**
 * 配置值检查器。
 *
 * <p>
 * 用于检查配置值是否合法。 <br>
 * 由于检测值是否无效和检测值是否有效的使用频率几乎同样高，因此，专门定义检测值是否无效的默认方法 {@link #nonValid(String)}，
 * 如果重写这个方法，需要保证：对于任意的<code>String value</code> 值（包括 <code>null</code>
 * 值），需要<code>isValid(value) == ! nonValid(value)</code>。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public interface ConfigChecker {

    /**
     * 检查指定的值是否有效。
     *
     * <p>
     * 该方法需要遵守约定： <code>null</code>值在任何情况下都返回 <code>false</code>。
     *
     * @param value 指定的值。
     * @return 指定的值是否有效。
     */
    boolean isValid(String value);

    /**
     * 检查指定的值是否无效。
     *
     * <p>
     * 该方法需要遵守约定： <code>null</code>值在任何情况下都返回 <code>true</code>。
     *
     * @param value 指定的值。
     * @return 指定的值是否无效。
     */
    default boolean nonValid(String value) {
        return !isValid(value);
    }

    /**
     * 对于配置值检查器 A 和 B 来说，当无论任何值 value， 都有
     * <code>A.isValid(value) == B.isValid(value)</code> 时，则可以认为 A 和 B 相等。
     *
     * <p>
     * 为了保险起见，符合上述等式的两个对象可以不相等（也就是说可以不重写 equals 方法）， 但不满足上述等式的对象一定要不相等。
     *
     * @param obj 指定的对象。
     * @return 该配置值检查器是否与该对象相等。
     */
    @Override
    boolean equals(Object obj);

    /**
     * {@inheritDoc}
     */
    @Override
    int hashCode();
}
