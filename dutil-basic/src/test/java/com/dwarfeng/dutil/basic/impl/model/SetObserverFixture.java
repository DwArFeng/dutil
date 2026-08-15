package com.dwarfeng.dutil.basic.impl.model;

import com.dwarfeng.dutil.basic.stack.model.event.SetObserver;

import java.util.ArrayList;
import java.util.List;

class SetObserverFixture<T> implements SetObserver<T> {

    public final List<T> addedList = new ArrayList<>();
    public final List<T> removedList = new ArrayList<>();
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
