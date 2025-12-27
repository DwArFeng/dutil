package com.dwarfeng.dutil.develop.cfg.struct;

import com.dwarfeng.dutil.develop.cfg.ExconfigModel;

/**
 * 值解析器。
 *
 * <p>
 * 该解析器作为 {@link ExconfigEntry} 的一部分，被存放在 {@link ExconfigModel} 中， 在配置模型中，
 * 每个配置键都与一个值解析器相对应。 由于该解析器能实现下述的功能，可以对配置模型中对象与文本的相互转换方法提供便利。
 *
 * <p>
 * 该结构允许将一个 {@link String} 解析为指示的类型， 使用解析器可以将配置模型中的有效值直接解析成用户希望使用的类型，
 * 进而省去了对配置模型进行复杂的重写。 同时，该结构也定义了将一个对象转换为文本的方法， 这样，在用户设置当前值时，
 * 可以直接将当前值设置为对象，
 * 而不用在外部代码中编写对象转换成文本的方法。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface ValueParser {

    /**
     * 将一个 String 类型值解析成 {@link Object} 对象。
     *
     * <p>
     * 注意：该方法不应该抛出异常，因为在经过 {@link ConfigChecker} 的筛选之后，应该能保证传入该接口的值都是正确无误的。
     *
     * @param value 指定的 String 值。
     * @return 解析成的 {@link Object} 对象，如果值无效，则返回 <code>null</code>。
     */
    Object parseValue(String value);

    /**
     * 将一个对象解析成字符串。
     *
     * <p>
     * 由于一个配置键通常只对应一个对象类型， 因此在转化值的过程中， 如果传入的对象的类型不是期望的类型时， 返回的字符串应该是无效的，
     * 即返回的字符串不应该通过该值解析器在配置模型中对应的配置键对应的值检查器。
     *
     * @param object 指定的对象。
     * @return 由指定对象解析成的字符串， 如果对象无效，则返回 <code>null</code>。
     */
    String parseObject(Object object);

    /**
     * 对于配置值检查器 A 和 B 来说，当无论任何字符串 value 与对象 obj， 都有
     * <code>A.parseValue(value) == B.parseValue(value) 且 A.parseObject(obj) == B.parseObject(obj)</code>
     * 时，则可以认为 A 和 B 相等。
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
