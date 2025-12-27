package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.cna.model.obs.MapObserver;

import java.util.ArrayList;
import java.util.List;

class TestMapObserver implements MapObserver<String, String> {

    public final List<String> putKeyList = new ArrayList<>();
    public final List<String> putValueList = new ArrayList<>();
    public final List<String> changedKeyList = new ArrayList<>();
    public final List<String> changedOldValueList = new ArrayList<>();
    public final List<String> changedNewValueList = new ArrayList<>();
    public final List<String> removeKeyList = new ArrayList<>();
    public final List<String> removeValueList = new ArrayList<>();
    public int cleared = 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public void firePut(String key, String value) {
        putKeyList.add(key);
        putValueList.add(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireChanged(String key, String oldValue, String newValue) {
        changedKeyList.add(key);
        changedOldValueList.add(oldValue);
        changedNewValueList.add(newValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireRemoved(String key, String value) {
        removeKeyList.add(key);
        removeValueList.add(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fireCleared() {
        cleared++;
    }

    public void reset() {
        putKeyList.clear();
        putValueList.clear();
        changedKeyList.clear();
        changedOldValueList.clear();
        changedNewValueList.clear();
        removeKeyList.clear();
        removeValueList.clear();
        cleared = 0;
    }
}
