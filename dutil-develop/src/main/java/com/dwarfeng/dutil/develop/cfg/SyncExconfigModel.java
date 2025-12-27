package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * 同步 Ex 配置模型。
 *
 * <p>
 * 提供一个线程安全的键值 Ex 配置模型，该模型的线程安全是用同步读写锁（ {@link ReadWriteLock} 实现的。
 * 对于大部分方法，可以直接调用而不用担心线程安全的方法；对于一小部分需要对返回的对象进行连续的，不可预知的操作的时候，则需要获取模型中的锁用以外部同步。
 * 比如使用 {@link #keySet()} 法进行遍历。实例代码如下：
 *
 * <pre>
 *
 * static void inspect(SyncExconfigModel model) {
 * 	model.getLock().readLock().lock();
 * 	try {
 * 		for (ConfigKey key : model.keySet()) {
 * 			// Do Something
 *        }
 *    } finally {
 * 		model.getLock().readLock().unLock();
 *    }
 * }
 * </pre>
 *
 * <p>
 * 该模型是线程安全的。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public interface SyncExconfigModel extends ExconfigModel, ExternalReadWriteThreadSafe {
}
