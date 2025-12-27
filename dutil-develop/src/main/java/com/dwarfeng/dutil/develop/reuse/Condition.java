package com.dwarfeng.dutil.develop.reuse;

/**
 * 复用条件。
 *
 * <p>
 * 复用条件用来确认条件对应的对象是否适合继续持久化。其本身的 {@link #isReuseSatisfy()} 和
 * {@link #isReuseUnsatisfy()} 分别用来判断其对应的对象是否适合继续持久化以及是否不是花继续持久化。
 *
 * <p>
 * 复用条件的 {@link #update(Object)} 方法用于更新条件的状态。该方法通过传入对象的不同，对复用条件进行不同类型的更新。
 * 绝大多数复用条件都会对其方法的入口参数做出限制。
 *
 * <p>
 * 复用条件不是线程安全的，应避免多线程访问，尤其是其调用更新方法的时候。
 *
 * @author DwArFeng
 * @since 0.2.1-beta
 */
public interface Condition {

    /**
     * 判断指定的复用条件是否仍然适合复用。
     *
     * @return 指定的复用条件是否仍然适合复用。
     */
    boolean isReuseSatisfy();

    /**
     * 判断指定的复用条件是否已经不适合复用。
     *
     * <p>
     * 该方法的返回结果必须与 {@link #isReuseSatisfy()} 相反。
     *
     * @return 指定的复用条件是否已经不适合复用。
     */
    boolean isReuseUnsatisfy();

    /**
     * 更新此复用条件。
     *
     * <p>
     * 在更新复用条件时，应该指定一个对象，作为更新时的额外信息，这个对象可以是 <code>null</code>。
     *
     * @param updateObject 指定的更新对象。
     * @throws IllegalArgumentException 传入的对象不符合要求。
     */
    void update(Object updateObject) throws IllegalArgumentException;
}
