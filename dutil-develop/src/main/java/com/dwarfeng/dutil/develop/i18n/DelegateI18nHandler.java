package com.dwarfeng.dutil.develop.i18n;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.basic.cna.model.KeySetModel;
import com.dwarfeng.dutil.basic.cna.model.MapKeySetModel;
import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;
import com.dwarfeng.dutil.develop.i18n.obs.I18nObserver;

import java.util.*;

/**
 * 代理集合国际化处理器。
 *
 * <p>
 * 通过代理一个 <code>KeySetModel</code>来实现的国际化处理器，并且在此基础上增加了国际化处理器的实现。
 *
 * <p>
 * 在该类初始化后， <code>getCurrentI18n</code> 和 <code>getCurrentLocale</code> 返回的结果都是
 * <code>null</code>， 直到调用 <code>setCurrentLocale</code> 之后，才能正常工作。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public class DelegateI18nHandler implements I18nHandler {

    private class DelegateIterator implements Iterator<I18nInfo> {

        private final Iterator<I18nInfo> delegate;
        private I18nInfo i18nInfo = null;

        public DelegateIterator(Iterator<I18nInfo> delegate) {
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public I18nInfo next() {
            i18nInfo = delegate.next();
            return i18nInfo;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void remove() {
            delegate.remove();
            if (Objects.equals(i18nInfo.getKey(), currentLocale)) {
                resetDefaultLocale();
            }
        }

    }

    /**
     * 该键值集合国际化处理器的键值集合。
     */
    protected final KeySetModel<Locale, I18nInfo> delegate;
    private Locale currentLocale = null;

    private I18n currentI18n = null;

    /**
     * 生成一个默认的代理国际化处理器。
     */
    public DelegateI18nHandler() {
        this(new MapKeySetModel<>());
    }

    /**
     * 生成一个具有指定代理的新的代理国际化处理器。
     *
     * @param delegate 指定的代理。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public DelegateI18nHandler(KeySetModel<Locale, I18nInfo> delegate) {
        Objects.requireNonNull(delegate, DwarfUtil.getExceptionString(ExceptionStringKey.DELEGATEI18NHANDLER_0));
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(I18nInfo e) {
        return delegate.add(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends I18nInfo> c) {
        return delegate.addAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addObserver(SetObserver<I18nInfo> observer) {
        return delegate.addObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        resetDefaultLocale();
        delegate.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearObserver() {
        delegate.clearObserver();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAllKey(Collection<?> c) {
        return delegate.containsAllKey(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        return delegate.equals(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public I18nInfo get(Locale key) {
        return delegate.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public I18n getCurrentI18n() {
        return currentI18n;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SetObserver<I18nInfo>> getObservers() {
        return delegate.getObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<I18nInfo> iterator() {
        return new DelegateIterator(delegate.iterator());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        if (delegate.remove(o)) {
            if (Objects.equals(currentLocale, ((I18nInfo) o).getKey())) {
                resetDefaultLocale();
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        if (delegate.removeAll(c)) {
            boolean aFlag = false;
            for (Object obj : c) {
                if (obj instanceof I18nInfo && Objects.equals(((I18nInfo) obj).getKey(), currentLocale)) {
                    aFlag = true;
                    break;
                }
            }
            if (aFlag) {
                resetDefaultLocale();
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAllKey(Collection<?> c) {
        if (delegate.removeAllKey(c)) {
            if (c.contains(currentLocale)) {
                resetDefaultLocale();
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeKey(Object key) {
        if (delegate.removeKey(key)) {
            if (Objects.equals(key, currentLocale)) {
                resetDefaultLocale();
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(SetObserver<I18nInfo> observer) {
        return delegate.removeObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        if (delegate.retainAll(c)) {
            boolean aFlag = true;
            for (Object obj : c) {
                if (obj instanceof I18nInfo && Objects.equals(((I18nInfo) obj).getKey(), currentLocale)) {
                    aFlag = false;
                    break;
                }
            }
            if (aFlag) {
                resetDefaultLocale();
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAllKey(Collection<?> c) {
        if (delegate.retainAllKey(c)) {
            if (!c.contains(currentLocale)) {
                resetDefaultLocale();
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setCurrentLocale(Locale locale) {
        if (Objects.isNull(locale)) {
            resetDefaultLocale();
            return true;
        } else {
            if (!containsKey(locale))
                return false;

            I18nInfo tempI18nInfo = get(locale);

            I18n tempI18n = null;
            try {
                tempI18n = tempI18nInfo.newI18n();
            } catch (Exception e) {
                return false;
            }

            Locale oldLocale = currentLocale;
            currentLocale = locale;
            currentI18n = tempI18n;

            fireCurrentLocaleChanged(oldLocale, locale, tempI18n);

            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return delegate.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    /**
     * 通知观察器指当前语言发生了改变。
     *
     * @param oldLocale 旧的语言地点。
     * @param newLocale 新的语言地点。
     * @param newI18n   新的国际化接口。
     */
    protected void fireCurrentLocaleChanged(Locale oldLocale, Locale newLocale, I18n newI18n) {
        for (SetObserver<I18nInfo> observer : delegate.getObservers()) {
            if (Objects.nonNull(observer) && observer instanceof I18nObserver) {
                try {
                    ((I18nObserver) observer).fireCurrentLocaleChanged(oldLocale, newLocale, newI18n);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void resetDefaultLocale() {
        Locale oldLocale = currentLocale;

        currentLocale = null;
        I18nInfo i18nInfo = get(null);
        if (Objects.nonNull(i18nInfo)) {
            try {
                currentI18n = i18nInfo.newI18n();
            } catch (Exception e) {
                currentI18n = null;
            }
        } else {
            currentI18n = null;
        }

        fireCurrentLocaleChanged(oldLocale, currentLocale, currentI18n);
    }
}
