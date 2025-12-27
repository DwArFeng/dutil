package com.dwarfeng.dutil.develop.reuse.condition;

import com.dwarfeng.dutil.develop.reuse.Condition;

import java.util.Objects;

/**
 * 计数情形。
 *
 * <p>
 * 计数情形可以使得对象在进行指定次数计数后判定该对象不应该继续持久化。
 *
 * <p>
 * 计数情形维护着一个私有的计数器。当该情形更新时， 可以通过传入 {@link UpdatePolicy} 对象来决定计数器情形的内部计数器是加一还是清零。
 *
 * <p>
 * 计数器情形维护着一个目标计数，当计数器情形的私有计数器的技术大于等于目标计数时，该情形则认为其指定的对象不适合继续持久化。
 *
 * @author DwArFeng
 * @since 0.2.1-beta
 */
public class CountCondition implements Condition {

    /**
     * 计数情形的更新策略。
     *
     * @author DwArFeng
     * @since 0.2.1-beta
     */
    public enum UpdatePolicy {

        /**
         * 表示计数器情形内部计数器加一的更新策略。
         */
        INCREASE,
        /**
         * 表示计数器情形内部清零的更新策略。
         */
        RESET,

    }

    /**
     * 计数情形的目标计数。
     */
    protected final int aimCount;

    private int currentCount = 0;

    /**
     * 生成一个目标数为指定数字的计数器情形。
     *
     * @param aimCount 指定的数字。
     */
    public CountCondition(int aimCount) {
        this.aimCount = aimCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReuseSatisfy() {
        return !isReuseUnsatisfy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReuseUnsatisfy() {
        return currentCount >= aimCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Object updateObject) throws IllegalArgumentException {
        if (Objects.isNull(updateObject)) {
            // TODO 国际化。
            throw new IllegalArgumentException("入口参数 updateObject 不能为 null。");
        }
        if (!(updateObject instanceof UpdatePolicy)) {
            // TODO 国际化。
            throw new IllegalArgumentException("updateObject 必须是 UpdatePolicy 枚举中的一员");
        }

        switch ((UpdatePolicy) updateObject) {
            case RESET:
                currentCount = 0;
                break;
            case INCREASE:
            default:
                currentCount++;
                break;
        }
    }
}
