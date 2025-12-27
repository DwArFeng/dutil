package com.dwarfeng.dutil.develop.cfg;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test_DefaultConfigModel {

    private final ConfigModel model = new DefaultConfigModel();

    @Before
    public void setUp() throws Exception {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
    }

    @Test
    public final void testRemoveAll() {
        model.removeAll(Arrays.asList(TestExconfigEntries.SUCC_1.getConfigKey(), TestExconfigEntries.SUCC_2.getConfigKey()));

        assertTrue(model.containsKey(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertFalse(model.containsKey(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertFalse(model.containsKey(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertTrue(model.containsKey(TestExconfigEntries.SUCC_3.getConfigKey()));
    }

    @Test
    public final void testRetainAll() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        model.retainAll(Arrays.asList(TestExconfigEntries.SUCC_1.getConfigKey(), TestExconfigEntries.SUCC_2.getConfigKey()));

        assertFalse(model.containsKey(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertTrue(model.containsKey(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertTrue(model.containsKey(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertFalse(model.containsKey(TestExconfigEntries.SUCC_3.getConfigKey()));
    }
}
