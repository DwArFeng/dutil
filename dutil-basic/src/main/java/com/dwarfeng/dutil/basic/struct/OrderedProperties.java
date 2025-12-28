package com.dwarfeng.dutil.basic.struct;

import java.util.*;

/**
 * 能够按照指定的顺序存储内部条目的 Properties 文件结构。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public class OrderedProperties extends Properties {

    private static final long serialVersionUID = 5604182371001690789L;

    private final LinkedHashSet<Object> keys = new LinkedHashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<Object> keys() {
        return Collections.enumeration(keys);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object put(Object key, Object value) {
        keys.add(key);
        return super.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Object> keySet() {
        return keys;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> stringPropertyNames() {
        Set<String> set = new LinkedHashSet<>();

        for (Object key : this.keys) {
            set.add((String) key);
        }

        return set;
    }
}
