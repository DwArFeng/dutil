package com.dwarfeng.dutil.develop.i18n;

import com.dwarfeng.dutil.basic.prog.WithKey;
import com.dwarfeng.dutil.basic.str.Name;

import java.util.Locale;

/**
 * 国际化信息接口。
 *
 * <p>
 * 记录着国际化接口的有关信息，并且能否返回对应的国际化接口。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public interface I18nInfo extends WithKey<Locale>, Name {

    /**
     * 由该国际化信息接口中的信息生成一个新的国际化接口。
     *
     * @return 新的国际化接口。
     * @throws Exception 生成新的国际化接口时发生异常。
     */
    I18n newI18n() throws Exception;
}
