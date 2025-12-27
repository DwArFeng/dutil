package com.dwarfeng.dutil.develop.reuse.condition;

import com.dwarfeng.dutil.develop.reuse.Condition;

import java.util.Objects;

/**
 * 时间情形。
 *
 * <p>
 * 时间情形可以使得超过指定的时间之后仍未更新的对象被判定不应该继续持久化。
 *
 * <p>
 * 时间情形维护着一个私有的最后更新时间。 当该情形更新时，可以通过传入 {@link UpdatePolicy}
 * 对象来决定内部的最后更新时间是保持之前的时间还是更新到当前的系统时间。
 *
 * <p>
 * 时间情形维护着一个目标更新时间，当系统时间减去时间情形维护的最后更新时间大于等于目标更新时间时， 该情形则认为其指定的对象不适合继续持久化。
 *
 * @author DwArFeng
 * @since 0.2.1-beta
 */
public class TimeCondition implements Condition {

    /**
     * 时间情形的更新策略。
     *
     * @author DwArFeng
     * @since 0.2.1-beta
     */
    public enum UpdatePolicy {

        /**
         * 表示时间情形的内部最后更新时间保持不变。
         */
        KEEP_UP,
        /**
         * 表示时间情形的内部最后更新时间同步为系统时间。
         */
        SET_CURRENT_TIME,

    }

    /**
     * 时间情形的目标时间。
     */
    protected final long aimTime;

    private long lastUpdateTime = System.currentTimeMillis();

    public TimeCondition(long aimTime) {
        this.aimTime = aimTime;
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
        return System.currentTimeMillis() - lastUpdateTime >= aimTime;
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
            case SET_CURRENT_TIME:
                lastUpdateTime = System.currentTimeMillis();
                break;
            case KEEP_UP:
            default:
                break;
        }

    }
}
