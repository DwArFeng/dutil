package com.dwarfeng.dutil.develop.i18n.obs;

import com.dwarfeng.dutil.develop.i18n.I18n;
import com.dwarfeng.dutil.develop.i18n.I18nInfo;

import java.util.Locale;

/**
 * 国际化处理器侦听器适配器。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public abstract class I18nAdapter implements I18nObserver {

    @Override
    public void fireAdded(I18nInfo element) {
    }

    @Override
    public void fireRemoved(I18nInfo element) {
    }

    @Override
    public void fireCleared() {
    }

    @Override
    public void fireCurrentLocaleChanged(Locale oldLocale, Locale newLocale, I18n newI18n) {
    }
}
