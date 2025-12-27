package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.develop.cfg.obs.ConfigObserver;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 抽象配置模型实现。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public abstract class AbstractConfigModel implements ConfigModel {

    /**
     * 观察器集合
     */
    protected final Set<ConfigObserver> observers = Collections.newSetFromMap(new WeakHashMap<>());

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ConfigObserver> getObservers() {
        return Collections.unmodifiableSet(observers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addObserver(ConfigObserver observer) {
        if (Objects.isNull(observer))
            return false;
        return observers.add(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeObserver(ConfigObserver observer) {
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
     * 通知观察器模型中的配置键被清除。
     */
    protected void fireConfigKeyCleared() {
        for (ConfigObserver observer : observers) {
            try {
                observer.fireConfigKeyCleared();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通知观察器指定的配置键被添加。
     *
     * @param configKey 指定的配置键。
     */
    protected void fireConfigKeyAdded(ConfigKey configKey) {
        for (ConfigObserver observer : observers) {
            try {
                observer.fireConfigKeyAdded(configKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通知观察器指定的配置键被移除。
     *
     * @param configKey 指定的配置键。
     */
    protected void fireConfigKeyRemoved(ConfigKey configKey) {
        for (ConfigObserver observer : observers) {
            try {
                observer.fireConfigKeyRemoved(configKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通知观察器指定的配置键对应的配置属性被改变。
     *
     * @param configKey 指定的配置键。
     * @param oldValue  指定的配置键对应的旧的配置属性。
     * @param newValue  指定的配置键对应的新的配置属性。
     */
    protected void fireConfigFirmPropsChanged(ConfigKey configKey, ConfigFirmProps oldValue, ConfigFirmProps newValue) {
        for (ConfigObserver observer : observers) {
            try {
                observer.fireConfigFirmPropsChanged(configKey, oldValue, newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 通知观察器指定的当前值被改变。
     *
     * @param configKey  指定的配置键。
     * @param oldValue   指定的配置键对应的旧的当前值。
     * @param newValue   指定的配置键对应的新的当前值。
     * @param validValue 指定的配置键对应的当前值改变后的新的有效值。
     */
    protected void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue, String validValue) {
        for (ConfigObserver observer : observers) {
            try {
                observer.fireCurrentValueChanged(configKey, oldValue, newValue, validValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
