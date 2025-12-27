package com.dwarfeng.dutil.basic.cna.model;

import com.dwarfeng.dutil.basic.cna.model.obs.SetObserver;

import java.util.ArrayList;
import java.util.List;

class TestSetObserver<T> implements SetObserver<T> {

    public List<T> addedList = new ArrayList<>();
    public List<T> removedList = new ArrayList<>();
    public int cleared = 0;

    @Override
    public void fireAdded(T element) {
        addedList.add(element);
    }

    @Override
    public void fireRemoved(T element) {
        removedList.add(element);
    }

    @Override
    public void fireCleared() {
        cleared++;
    }

    public void reset() {
        addedList.clear();
        removedList.clear();
        cleared = 0;
    }
}
