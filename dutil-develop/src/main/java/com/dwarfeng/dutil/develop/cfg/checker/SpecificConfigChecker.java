package com.dwarfeng.dutil.develop.cfg.checker;

import com.dwarfeng.dutil.basic.cna.ArrayUtil;
import com.dwarfeng.dutil.develop.cfg.ConfigChecker;

import java.util.Arrays;
import java.util.Objects;

/**
 * 特定文本检测器。
 *
 * <p>
 * 当待测文本为几个特定值中的一个时，即为有效。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
@SuppressWarnings("deprecation")
public class SpecificConfigChecker implements ConfigChecker {

    private final String[] arr;

    /**
     * 生成特定文本检测器。
     *
     * @param arr 指定值数组。
     */
    public SpecificConfigChecker(String[] arr) {
        this.arr = arr;
    }

    /**
     * 生成特定文本检测器。
     *
     * @param arr 指定的对象数组，取对象的 <code>toString()</code>值。
     */
    public SpecificConfigChecker(Object[] arr) {
        if (Objects.isNull(arr)) {
            this.arr = null;
        } else {
            this.arr = new String[arr.length];
            for (int i = 0; i < arr.length; i++) {
                this.arr[i] = arr[i].toString();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(String value) {
        if (Objects.isNull(this.arr))
            return false;
        return ArrayUtil.contains(arr, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpecificConfigChecker other = (SpecificConfigChecker) obj;
        return Arrays.equals(arr, other.arr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(arr);
        return result;
    }
}
