package com.dwarfeng.dutil.basic.io;

import java.util.Set;

/**
 * 保存器。
 *
 * <p>
 * 实现该接口意味着该对象能将指定对象中的信息以某种方式进行存储。
 *
 * <p>
 * 保存器拥有两种行为的保存方式：
 * <ol>
 *     <li>保存器进行保存，当遇到异常的时候，保存过程中止，并抛出异常。</li>
 *     <li>保存器进行保存，当遇到异常的时候，在集合中记录下这个异常，并且继续保存，等到全部保存完毕后，返回异常集合。</li>
 * </ol>
 *
 * <p>
 * 保存器被设计成一次性使用的类，当调用任意一种保存方法之后，该类则失效，因此不能反复调用保存方法。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface Saver<T> {

    /**
     * 将指定的对象中的内容进行保存。
     *
     * @param obj 指定的对象。
     * @throws SaveFailedException   保存失败异常。
     * @throws NullPointerException  入口参数为 <code>null</code>。
     * @throws IllegalStateException 重复调用保存方法时抛出该异常。
     */
    void save(T obj) throws SaveFailedException, IllegalStateException;

    /**
     * 向指定的对象中连续保存数据。
     *
     * <p>
     * 该方法向指定的对象中持续保存数据，当保存过程中遇到任何异常，该方法会将异常记录在异常集合中，
     * 而不立即抛出。当所有的内容全部保存完毕后，该方法将遇到的所有异常以异常集合的形式返回。
     *
     * @param obj 指定的对象。
     * @return 保存过程中遇到的所有异常。
     * @throws NullPointerException  入口参数为 <code>null</code>。
     * @throws IllegalStateException 当重复调用保存方法时抛出该异常。
     */
    Set<SaveFailedException> countinuousSave(T obj) throws IllegalStateException;
}
