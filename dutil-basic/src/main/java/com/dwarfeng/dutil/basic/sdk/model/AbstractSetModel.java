package com.dwarfeng.dutil.basic.sdk.model;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;
import com.dwarfeng.dutil.basic.stack.model.SetModel;

import com.dwarfeng.dutil.basic.stack.model.event.SetObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 抽象集合模型。
 *
 * <p>
 * 集合模型的抽象实现。
 *
 * <p>
 * 该类实现了模型中侦听器注册与移除的方法，以及通知侦听器的方法。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public abstract class AbstractSetModel<E> implements SetModel<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSetModel.class);

    /**
     * 抽象集合模型的侦听器集合。
     */
    protected final Set<SetObserver<E>> observers;

    /**
     * 生成一个默认的抽象集合模型。
     */
    public AbstractSetModel() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定的侦听器集合的的抽象集合模型。
     *
     * @param observers 指定的侦听器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public AbstractSetModel(Set<SetObserver<E>> observers) {
        Objects.requireNonNull(observers, BasicMessages.message(BasicMessageKey.ABSTRACT_SET_MODEL_OBSERVERS_REQUIRED));
        this.observers = observers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<SetObserver<E>> getObservers() {
        return Collections.unmodifiableSet(observers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addObserver(SetObserver<E> observer) {
        return observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(SetObserver<E> observer) {
        return observers.remove(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearObserver() {
        observers.clear();
    }

    /**
     * 通知观察器该模型添加了指定的元素。
     *
     * @param element 指定的元素。
     */
    protected void fireAdded(E element) {
        for (SetObserver<E> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireAdded(element);
                } catch (Exception e) {
                    logObserverException("fireAdded", e);
                }
        }
    }

    /**
     * 通知观察器该模型移除了指定的元素。
     *
     * @param element 指定的元素。
     */
    protected void fireRemoved(E element) {
        for (SetObserver<E> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireRemoved(element);
                } catch (Exception e) {
                    logObserverException("fireRemoved", e);
                }
        }
    }

    /**
     * 通知观察器该模型清除了元素。
     */
    protected void fireCleared() {
        for (SetObserver<E> observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireCleared();
                } catch (Exception e) {
                    logObserverException("fireCleared", e);
                }
        }
    }

    private void logObserverException(String eventName, Exception exception) {
        LOGGER.warn(
                BasicMessages.message(
                        BasicMessageKey.MODEL_OBSERVER_NOTIFICATION_FAILED, getClass().getName(), eventName
                ),
                exception
        );
    }
}
