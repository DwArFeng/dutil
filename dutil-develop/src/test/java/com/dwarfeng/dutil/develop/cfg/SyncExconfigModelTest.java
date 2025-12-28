package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.develop.cfg.obs.ExconfigAdapter;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class SyncExconfigModelTest {

    private final SyncExconfigModel model = ConfigUtil.syncExconfigModel(new DefaultExconfigModel());

    @Before
    public void setUp() {
        model.clearObserver();
        model.clear();
    }

    @Test
    public void testGetLock() {
        model.getLock();
    }

    @Test
    public void testGetObservers() {
        model.getObservers();
    }

    @Test
    public void testAddObserver() {
        model.addObserver(new ExconfigAdapter() {
        });
    }

    @Test
    public void testRemoveObserver() {
        model.removeObserver(null);
    }

    @Test
    public void testClear() {
        model.clear();
    }

    @Test
    public void testClearObserver() {
        model.clearObserver();
    }

    @Test
    public void testContainsKey() {
        model.containsKey(new ConfigKey("foo"));
    }

    @Test
    public void testGetCurrentValue() {
        model.getCurrentValue(new ConfigKey("123"));
    }

    @Test
    public void testGetAllCurrentValue() {
        model.getAllCurrentValue();
    }

    @Test
    public void testIsEmpty() {
        model.isEmpty();
    }

    @Test
    public void testKeySet() {
        model.keySet();
    }

    @Test
    public void testAdd() {
        model.add(TestExconfigEntries.SUCC_0);
    }

    @Test
    public void testAddAll() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
    }

    @Test
    public void testRemove() {
        model.remove(null);
    }

    @Test
    public void testRemoveAll() {
        model.removeAll(new HashSet<>());
    }

    @Test
    public void testRetainAll() {
        model.retainAll(new HashSet<>());
    }

    @Test
    public void testSize() {
        model.size();
    }

    @Test
    public void testIsValueValid() {
        model.isValueValid(null, "");
    }

    @Test
    public void testGetValidValue() {
        model.getValidValue(null);
    }

    @Test
    public void testGetConfigFirmProps() {
        model.getConfigFirmProps(null);
    }

    @Test
    public void testSetConfigFirmProps() {
        model.setConfigFirmProps(null, null);
    }

    @Test
    public void testSetCurrentValue() {
        model.setCurrentValue(null, null);
    }

    @Test
    public void testSetAllCurrentValue() {
        model.setAllCurrentValue(new HashMap<>());
    }

    @Test
    public void testResetCurrentValue() {
        model.resetCurrentValue(null);
    }

    @Test
    public void testResetAllCurrentValue() {
        model.resetAllCurrentValue();
    }

    @Test
    public void testGetValueParser() {
        model.getValueParser(null);
    }

    @Test
    public void testSetValueParser() {
        model.setValueParser(null, null);
    }

    @Test
    public void testGetParsedValueConfigKey() {
        model.getParsedValue(null);
    }

    @Test
    public void testGetParsedValueConfigKeyClassOfT() {
        model.getParsedValue(null, Object.class);
    }
}
