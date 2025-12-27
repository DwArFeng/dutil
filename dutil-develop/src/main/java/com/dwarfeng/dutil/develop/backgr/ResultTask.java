package com.dwarfeng.dutil.develop.backgr;

import com.dwarfeng.dutil.develop.backgr.obs.TaskObserver;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 可返回结果的任务。
 *
 * <p>
 * 该任务可以在执行过程中返回一个具体结果，通过方法 {@link #getResult()} 来调用。
 *
 * <p>
 * 在任务的执行过程中，可以通过调用 {@link #setResult(Object)}
 * 来设置任务的结果。任务结果应该在任务被调用的过程中设置完毕，一旦任务结束后，该结果不应继续改变。对于正在运行的任务，可以先调用
 * {@link #awaitFinish()} 方法，等待任务结束后，在获取其得到的结果。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public abstract class ResultTask<V> extends AbstractTask {

    /**
     * 任务的结果。
     */
    private V result = null;

    /**
     * 生成一个默认的可返回结果的任务。
     */
    public ResultTask() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定观察器集合的可返回结果的任务。
     *
     * @param observers 指定的观察器集合。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public ResultTask(Set<TaskObserver> observers) throws NullPointerException {
        super(observers);
    }

    /**
     * 获取该任务的结果。
     *
     * <p>
     * 该方法应该在任务被执行的过程中调用。
     *
     * @return 该任务的结果。
     */
    public V getResult() {
        return result;
    }

    /**
     * 设置该任务结果为指定值。
     *
     * @param result 指定的任务结果。
     */
    protected void setResult(V result) {
        this.result = result;
    }
}
