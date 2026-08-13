package com.dwarfeng.dutil.basic.impl.model;

import com.dwarfeng.dutil.basic.stack.model.WithKey;

import java.util.Objects;

public class WithKeyFixture implements WithKey<String> {

    public static final WithKeyFixture ELE_1 = new WithKeyFixture("A", "1");
    public static final WithKeyFixture ELE_2 = new WithKeyFixture("B", "2");
    public static final WithKeyFixture ELE_3 = new WithKeyFixture("C", "3");
    public static final WithKeyFixture ELE_4 = new WithKeyFixture("D", "4");
    public static final WithKeyFixture ELE_5 = new WithKeyFixture("E", "5");
    public static final WithKeyFixture ELE_6 = new WithKeyFixture("F", "6");
    public static final WithKeyFixture ELE_7 = new WithKeyFixture("G", "7");
    public static final WithKeyFixture ELE_8 = new WithKeyFixture("H", "8");

    public static final WithKeyFixture FAIL_ELE = new WithKeyFixture("A", "5");

    private final String key;
    private final String value;

    private WithKeyFixture(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConflict(WithKey<String> element) {
        if (Objects.isNull(element))
            return false;
        if (!element.getKey().equals(key))
            return false;
        return !Objects.equals(element, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof WithKeyFixture that))
            return false;
        return that.key.equals(this.key) && that.value.equals(this.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return key.hashCode() * 17 + value.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TestWithKey [key=" + key + ", value=" + value + "]";
    }
}
