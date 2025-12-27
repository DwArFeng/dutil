package com.dwarfeng.dutil.develop.i18n;

import java.util.Map;

/**
 * 通过 Map 实现的国际化接口。
 *
 * @author DwArFeng
 * @since 0.1.1-beta
 */
public class MapI18n implements I18n {

    protected final Map<String, String> map;

    /**
     * 生成一个具有指定 <code>map</code> 的国际化接口。
     *
     * @param map 指定的 <code>map</code>。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public MapI18n(Map<String, String> map) {
        this.map = map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(String key) {
        return map.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return map.hashCode() * 17;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof MapI18n))
            return false;
        MapI18n that = (MapI18n) obj;
        return this.map.equals(that.map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "MapI18n [map=" + map + "]";
    }
}
