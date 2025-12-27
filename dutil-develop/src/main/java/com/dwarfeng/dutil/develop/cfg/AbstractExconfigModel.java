package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;
import com.dwarfeng.dutil.develop.cfg.obs.ExconfigObserver;
import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 抽象 Ex 配置模型。
 *
 * <p>
 * Ex 配置模型的抽象实现。
 *
 * @author DwArFeng
 * @since 0.1.0-beta
 */
public abstract class AbstractExconfigModel implements ExconfigModel {

    /**
     * 观察器集合
     */
    protected final Set<ExconfigObserver> observers;

    /**
     * 生成一个默认的抽象 Ex 配置模型。
     */
    public AbstractExconfigModel() {
        this(Collections.newSetFromMap(new WeakHashMap<>()));
    }

    /**
     * 生成一个具有指定的侦听器集合的的抽象 Ex 配置模型。
     *
     * @param observers 指定的侦听器集合。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public AbstractExconfigModel(Set<ExconfigObserver> observers) {
        Objects.requireNonNull(observers, DwarfUtil.getExceptionString(ExceptionStringKey.ABSTRACTEXCONFIGMODEL_0));
        this.observers = observers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ExconfigObserver> getObservers() {
        return Collections.unmodifiableSet(observers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addObserver(ExconfigObserver observer) {
        if (Objects.isNull(observer))
            return false;
        return observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(ExconfigObserver observer) {
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
     * 通知配置模型中指定的配置键的当前值发生了改变。
     *
     * @param configKey  指定的配置键。
     * @param oldValue   配置键的旧值。
     * @param newValue   配置键的新值。
     * @param validValue 配置键当前的有效值。
     */
    protected void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue, String validValue) {
        for (ExconfigObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireCurrentValueChanged(configKey, oldValue, newValue, validValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知配置模型中的配置键进行了清除。
     */
    protected void fireConfigKeyCleared() {
        for (ExconfigObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireConfigKeyCleared();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知配置模型中指定的配置键进行了移除。
     *
     * @param configKey       指定的配置键。
     * @param configFirmProps 指定的配置键对应的固定属性。
     * @param valueParser     指定的配置键对应的值解析器。
     * @param currentValue    指定的配置键对应的当前值。
     */
    protected void fireConfigKeyRemoved(ConfigKey configKey, ConfigFirmProps configFirmProps, ValueParser valueParser,
                                        String currentValue) {
        for (ExconfigObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireConfigKeyRemoved(configKey, configFirmProps, valueParser, currentValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知配置模型中指定的配置键进行了添加。
     *
     * @param configKey       指定的配置键。
     * @param configFirmProps 指定的配置键对应的固定属性。
     * @param valueParser     指定的配置键对应的值解析器。
     * @param currentValue    指定的配置键对应的当前值。
     */
    protected void fireConfigKeyAdded(ConfigKey configKey, ConfigFirmProps configFirmProps, ValueParser valueParser,
                                      String currentValue) {
        for (ExconfigObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireConfigKeyAdded(configKey, configFirmProps, valueParser, currentValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知配置模型中指定的配置键的固定属性发生了改变。
     *
     * @param configKey 指定的配置键。
     * @param oldValue  指定配置键的旧的固定属性。
     * @param newValue  指定的配置键的新的固定属性。
     */
    protected void fireConfigFirmPropsChanged(ConfigKey configKey, ConfigFirmProps oldValue, ConfigFirmProps newValue) {
        for (ExconfigObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireConfigFirmPropsChanged(configKey, oldValue, newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 通知配置模型中指定的配置键的值解析器发生了改变。
     *
     * @param configKey 指定的配置键。
     * @param oldValue  指定的配置键对应的旧的值解析器。
     * @param newValue  指定的配置键对应的新的值解析器。
     */
    protected void fireValueParserChanged(ConfigKey configKey, ValueParser oldValue, ValueParser newValue) {
        for (ExconfigObserver observer : observers) {
            if (Objects.nonNull(observer))
                try {
                    observer.fireValueParserChanged(configKey, oldValue, newValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
