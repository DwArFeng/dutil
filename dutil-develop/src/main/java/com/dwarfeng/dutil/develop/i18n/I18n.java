package com.dwarfeng.dutil.develop.i18n;

import java.util.Objects;

/**
 * 国际化接口。
 *
 * <p>
 * 负责通过指定的键返回相应的文本，以直接实现国际化的接口。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public interface I18n {

    /**
     * 获取由指定的键对应的文本。
     *
     * <p>
     * 如果该国际化接口不含有指定的键的话，则返回 <code>null</code>。
     *
     * @param key 指定的键。
     * @return 由指定的键对应的文本。
     */
    String getString(String key);

    /**
     * 获取由指定的键对应的文本，或者是默认值。
     *
     * <p>
     * 如果该国际化接口含有指定的键的话，则返回指定的键对应的文本；否则，返回默认的文本。
     *
     * @param key           指定的键。
     * @param defaultString 指定的默认文本。
     * @return 指定的键对应的文本，或者是默认文本。
     */
    default String getStringOrDefault(String key, String defaultString) {
        String string;
        if (Objects.isNull((string = getString(key)))) {
            string = defaultString;
        }
        return string;
    }
}
