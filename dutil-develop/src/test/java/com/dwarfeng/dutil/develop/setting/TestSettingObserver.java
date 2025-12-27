package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.develop.setting.obs.SettingAdapter;
import com.dwarfeng.dutil.develop.setting.obs.SettingObserver;

import java.util.ArrayList;
import java.util.List;

public final class TestSettingObserver extends SettingAdapter implements SettingObserver {

    public final List<String> putList = new ArrayList<>();
    public final List<String> removedKey = new ArrayList<>();
    public final List<String> settingInfoChangedKey = new ArrayList<>();
    public final List<String> currentValueChangedKey = new ArrayList<>();

    public int clearCount = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireKeyPut(String key, SettingInfo settingInfo, String currentValue) {
        putList.add(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireKeyRemoved(String key) {
        removedKey.add(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireKeyCleared() {
        clearCount++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireSettingInfoChanged(String key, SettingInfo oldValue, SettingInfo newValue) {
        settingInfoChangedKey.add(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireCurrentValueChanged(String key, String oldValue, String newValue) {
        currentValueChangedKey.add(key);
    }
}
