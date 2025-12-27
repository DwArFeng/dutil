package com.dwarfeng.dutil.basic.io;

import java.util.Set;

/**
 * 读取器。
 *
 * <p>
 * 实现该接口意味着该对象能够将某些数据加载到指定的对象中。
 *
 * <p>
 * 读取器拥有两种行为的读取方式：
 * <ol>
 *     <li>读取器进行读取，当遇到异常的时候，读取过程中止，并抛出异常。</li>
 *     <li>读取器进行读取，当遇到异常的时候，在集合中记录下这个异常，并且继续读取，等到全部读取完毕后，返回异常集合。</li>
 * </ol>
 *
 * <p>
 * 读取器被设计成一次性使用的类，当调用任意一种读取方法之后，该类则失效，因此不能反复调用读取方法。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface Loader<T> {

    /**
     * 向指定的对象中读取数据。
     *
     * <p>
     * 该方法向指定的对象中读取数据，当数据全部读取完毕，或者遇到第一个异常时，则过程停止。
     *
     * @param obj 指定的对象。
     * @throws LoadFailedException   读取失败异常。
     * @throws NullPointerException  入口参数为 <code>null</code>。
     * @throws IllegalStateException 重复调用读取方法时抛出该异常。
     */
    void load(T obj) throws LoadFailedException, IllegalStateException;

    /**
     * 向指定的对象中连续读取数据。
     *
     * <p>
     * 该方法向指定的对象中持续读取数据，当读取过程中遇到任何异常，该方法会将异常记录在异常集合中，
     * 而不立即抛出。当所有的内容全部读取完毕后，该方法将遇到的所有异常以异常集合的形式返回。
     *
     * @param obj 指定的对象。
     * @return 读取过程中遇到的所有异常。
     * @throws NullPointerException  入口参数为 <code>null</code>。
     * @throws IllegalStateException 当重复调用读取方法时抛出该异常。
     */
    Set<LoadFailedException> countinuousLoad(T obj) throws IllegalStateException;
}
