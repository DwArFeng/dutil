package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.develop.cfg.checker.BooleanConfigChecker;
import com.dwarfeng.dutil.develop.cfg.obs.ExconfigObserver;
import com.dwarfeng.dutil.develop.cfg.parser.BooleanValueParser;
import com.dwarfeng.dutil.develop.cfg.struct.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DefaultExconfigModelTest {

    private static final class Obs1 implements ExconfigObserver {

        public final List<ConfigKey> currentValueChangedList = new ArrayList<>();
        public final List<ConfigKey> removedList = new ArrayList<>();
        public final List<ConfigKey> addedList = new ArrayList<>();
        public final List<ConfigKey> configFirmPropsChangedList = new ArrayList<>();
        public final List<ConfigKey> valueParserChangedList = new ArrayList<>();

        @Override
        public void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue, String validValue) {
            currentValueChangedList.add(configKey);
        }

        @Override
        public void fireConfigKeyCleared() {
        }

        @Override
        public void fireConfigKeyRemoved(ConfigKey configKey, ConfigFirmProps configFirmProps, ValueParser valueParser,
                                         String currentValue) {
            removedList.add(configKey);
        }

        @Override
        public void fireConfigKeyAdded(ConfigKey configKey, ConfigFirmProps configFirmProps, ValueParser valueParser,
                                       String currentValue) {
            addedList.add(configKey);
        }

        @Override
        public void fireConfigFirmPropsChanged(ConfigKey configKey, ConfigFirmProps oldValue,
                                               ConfigFirmProps newValue) {
            configFirmPropsChangedList.add(configKey);
        }

        @Override
        public void fireValueParserChanged(ConfigKey configKey, ValueParser oldValue, ValueParser newValue) {
            valueParserChangedList.add(configKey);
        }

        public void reset() {
            currentValueChangedList.clear();
            removedList.clear();
            addedList.clear();
            configFirmPropsChangedList.clear();
            valueParserChangedList.clear();
        }

    }

    private static final class Obs2 implements ExconfigObserver {

        public int currentValueChanged = 0;
        public int cleared = 0;
        public int removed = 0;
        public int added = 0;
        public int configFirmProps = 0;
        public int valueParser = 0;

        @Override
        public void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue, String validValue) {
            currentValueChanged++;
        }

        @Override
        public void fireConfigKeyCleared() {
            cleared++;
        }

        @Override
        public void fireConfigKeyRemoved(ConfigKey configKey, ConfigFirmProps configFirmProps, ValueParser valueParser,
                                         String currentValue) {
            removed++;
        }

        @Override
        public void fireConfigKeyAdded(ConfigKey configKey, ConfigFirmProps configFirmProps, ValueParser valueParser,
                                       String currentValue) {
            added++;
        }

        @Override
        public void fireConfigFirmPropsChanged(ConfigKey configKey, ConfigFirmProps oldValue,
                                               ConfigFirmProps newValue) {
            configFirmProps++;
        }

        @Override
        public void fireValueParserChanged(ConfigKey configKey, ValueParser oldValue, ValueParser newValue) {
            valueParser++;
        }

        public void reset() {
            currentValueChanged = 0;
            cleared = 0;
            removed = 0;
            added = 0;
            configFirmProps = 0;
            valueParser = 0;
        }
    }

    private final Obs1 obs1 = new Obs1();
    private final Obs2 obs2 = new Obs2();

    private final DefaultExconfigModel model = new DefaultExconfigModel();

    @Before
    public void setUp() {
        model.clearObserver();
        model.clear();
        obs1.reset();
        obs2.reset();
        model.addObserver(obs1);
        model.addObserver(obs2);
    }

    @Test
    public final void testConstructors() {
        ExconfigModel foo = new DefaultExconfigModel(Arrays.asList(TestExconfigEntries.values()));
        assertEquals(4, foo.size());
    }

    @Test
    public final void testClear() {
        model.add(TestExconfigEntries.SUCC_0);
        model.clear();
        assertEquals(0, model.size());
        assertEquals(1, obs2.cleared);
    }

    @Test
    public final void testContainsKey() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertTrue(model.containsKey(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertFalse(model.containsKey(TestExconfigEntries.FAIL_2.getConfigKey()));
    }

    @Test
    public final void testGetCurrentValue() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertEquals("12450", model.getCurrentValue(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals("false", model.getCurrentValue(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertEquals("NAN", model.getCurrentValue(TestExconfigEntries.SUCC_2.getConfigKey()));
    }

    @Test
    public final void testGetAllCurrentValue() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        Map<ConfigKey, String> map = model.getAllCurrentValue();

        assertTrue(map.containsKey(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertTrue(map.containsKey(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertTrue(map.containsKey(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertTrue(map.containsKey(TestExconfigEntries.SUCC_3.getConfigKey()));
        assertFalse(map.containsKey(TestExconfigEntries.FAIL_2.getConfigKey()));

        assertTrue(map.containsValue("12450"));
        assertTrue(map.containsValue("false"));
        assertTrue(map.containsValue("NAN"));
        assertFalse(map.containsValue("power overwhelming"));

        assertEquals("12450", map.get(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals("false", map.get(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertEquals("NAN", map.get(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertEquals("NAN", map.get(TestExconfigEntries.SUCC_3.getConfigKey()));
    }

    @Test
    public final void testIsEmpty() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertFalse(model.isEmpty());
        model.clear();
        assertTrue(model.isEmpty());
    }

    @Test
    public final void testKeySet() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        Set<ConfigKey> keySet = model.keySet();

        assertTrue(keySet.contains(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertTrue(keySet.contains(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertTrue(keySet.contains(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertTrue(keySet.contains(TestExconfigEntries.SUCC_3.getConfigKey()));
        assertFalse(keySet.contains(TestExconfigEntries.FAIL_2.getConfigKey()));
    }

    @Test
    public final void testAdd() {
        assertFalse(model.add(TestExconfigEntries.FAIL_1));
        assertTrue(model.add(TestExconfigEntries.SUCC_1));
        assertEquals(1, obs2.added);
    }

    @Test
    public final void testAddAll() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertEquals(4, obs2.added);
        assertTrue(obs1.addedList.contains(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertTrue(obs1.addedList.contains(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertTrue(obs1.addedList.contains(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertTrue(obs1.addedList.contains(TestExconfigEntries.SUCC_3.getConfigKey()));
    }

    @Test
    public final void testRemove() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertTrue(model.remove(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertFalse(model.remove(TestExconfigEntries.FAIL_0.getConfigKey()));
        assertEquals(1, obs2.removed);
    }

    @Test
    public final void testRemoveAll() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        model.removeAll(
                Arrays.asList(TestExconfigEntries.SUCC_1.getConfigKey(), TestExconfigEntries.SUCC_2.getConfigKey()));

        assertEquals(2, obs2.removed);
        assertTrue(obs1.removedList.contains(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertTrue(obs1.removedList.contains(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertFalse(obs1.removedList.contains(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertFalse(obs1.removedList.contains(TestExconfigEntries.SUCC_3.getConfigKey()));

        assertTrue(model.containsKey(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertFalse(model.containsKey(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertFalse(model.containsKey(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertTrue(model.containsKey(TestExconfigEntries.SUCC_3.getConfigKey()));

    }

    @Test
    public final void testRetainAll() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        model.retainAll(
                Arrays.asList(TestExconfigEntries.SUCC_1.getConfigKey(), TestExconfigEntries.SUCC_2.getConfigKey()));

        assertEquals(2, obs2.removed);
        assertFalse(obs1.removedList.contains(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertFalse(obs1.removedList.contains(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertTrue(obs1.removedList.contains(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertTrue(obs1.removedList.contains(TestExconfigEntries.SUCC_3.getConfigKey()));

        assertFalse(model.containsKey(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertTrue(model.containsKey(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertTrue(model.containsKey(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertFalse(model.containsKey(TestExconfigEntries.SUCC_3.getConfigKey()));
    }

    @Test
    public final void testIsValueValid() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));

        assertTrue(model.isValueValid(TestExconfigEntries.SUCC_0.getConfigKey(), "1"));
        assertTrue(model.isValueValid(TestExconfigEntries.SUCC_0.getConfigKey(), "NAN"));
        assertTrue(model.isValueValid(TestExconfigEntries.SUCC_1.getConfigKey(), "true"));
        assertTrue(model.isValueValid(TestExconfigEntries.SUCC_1.getConfigKey(), "treu"));
    }

    @Test
    public final void testGetValidValue() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));

        assertEquals("12450", model.getValidValue(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals("false", model.getValidValue(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertEquals("0", model.getValidValue(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertEquals("0", model.getValidValue(TestExconfigEntries.SUCC_3.getConfigKey()));
    }

    @Test
    public final void testGetConfigFirmProps() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertEquals("0", model.getConfigFirmProps(TestExconfigEntries.SUCC_0.getConfigKey()).getDefaultValue());
        assertEquals("true", model.getConfigFirmProps(TestExconfigEntries.SUCC_1.getConfigKey()).getDefaultValue());
        assertEquals("0", model.getConfigFirmProps(TestExconfigEntries.SUCC_2.getConfigKey()).getDefaultValue());
        assertEquals("0", model.getConfigFirmProps(TestExconfigEntries.SUCC_3.getConfigKey()).getDefaultValue());
    }

    @Test
    public final void testSetConfigFirmProps() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertTrue(model.setConfigFirmProps(TestExconfigEntries.SUCC_0.getConfigKey(), new ConfigFirmProps() {

            final BooleanConfigChecker checker = new BooleanConfigChecker();
            final String defaultValue = "true";

            @Override
            public String getDefaultValue() {
                return defaultValue;
            }

            @Override
            public ConfigChecker getConfigChecker() {
                return checker;
            }
        }));
        assertEquals(1, obs2.configFirmProps);
        assertTrue(obs1.configFirmPropsChangedList.contains(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals("true", model.getConfigFirmProps(TestExconfigEntries.SUCC_0.getConfigKey()).getDefaultValue());
        assertFalse(model.setConfigFirmProps(TestExconfigEntries.SUCC_1.getConfigKey(), new ConfigFirmProps() {

            final BooleanConfigChecker checker = new BooleanConfigChecker();
            final String defaultValue = "0";

            @Override
            public String getDefaultValue() {
                return defaultValue;
            }

            @Override
            public ConfigChecker getConfigChecker() {
                return checker;
            }
        }));
        assertEquals(1, obs2.configFirmProps);
        assertFalse(obs1.configFirmPropsChangedList.contains(TestExconfigEntries.SUCC_1.getConfigKey()));
    }

    @Test
    public final void testSetCurrentValue() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertFalse(model.setCurrentValue(TestExconfigEntries.FAIL_2.getConfigKey(), "foo"));
        assertTrue(model.setCurrentValue(TestExconfigEntries.SUCC_0.getConfigKey(), "foo"));
        assertTrue(model.setCurrentValue(TestExconfigEntries.SUCC_1.getConfigKey(), "foo"));
        assertTrue(model.setCurrentValue(TestExconfigEntries.SUCC_2.getConfigKey(), "foo"));
        assertEquals(3, obs2.currentValueChanged);
        assertEquals("foo", model.getCurrentValue(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals("foo", model.getCurrentValue(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertEquals("foo", model.getCurrentValue(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertEquals("NAN", model.getCurrentValue(TestExconfigEntries.SUCC_3.getConfigKey()));
        assertTrue(obs1.currentValueChangedList.contains(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertTrue(obs1.currentValueChangedList.contains(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertTrue(obs1.currentValueChangedList.contains(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertFalse(obs1.currentValueChangedList.contains(TestExconfigEntries.SUCC_3.getConfigKey()));
    }

    @Test
    public final void testSetAllCurrentValue() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        Map<ConfigKey, String> map = new HashMap<>();

        map.put(TestExconfigEntries.FAIL_2.getConfigKey(), "foo");
        assertFalse(model.setAllCurrentValue(map));
        assertEquals(0, obs2.currentValueChanged);

        map.put(TestExconfigEntries.SUCC_0.getConfigKey(), "foo");
        map.put(TestExconfigEntries.SUCC_1.getConfigKey(), "foo");
        map.put(TestExconfigEntries.SUCC_2.getConfigKey(), "foo");
        assertTrue(model.setAllCurrentValue(map));
        assertEquals(3, obs2.currentValueChanged);
        assertEquals("foo", model.getCurrentValue(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals("foo", model.getCurrentValue(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertEquals("foo", model.getCurrentValue(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertEquals("NAN", model.getCurrentValue(TestExconfigEntries.SUCC_3.getConfigKey()));
        assertTrue(obs1.currentValueChangedList.contains(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertTrue(obs1.currentValueChangedList.contains(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertTrue(obs1.currentValueChangedList.contains(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertFalse(obs1.currentValueChangedList.contains(TestExconfigEntries.SUCC_3.getConfigKey()));
    }

    @Test
    public final void testResetCurrentValue() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertTrue(model.resetCurrentValue(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertTrue(model.resetCurrentValue(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertTrue(model.resetCurrentValue(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertTrue(model.resetCurrentValue(TestExconfigEntries.SUCC_3.getConfigKey()));
        assertTrue(model.resetCurrentValue(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals("0", model.getCurrentValue(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals("true", model.getCurrentValue(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertEquals("0", model.getCurrentValue(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertEquals("0", model.getCurrentValue(TestExconfigEntries.SUCC_3.getConfigKey()));
        assertEquals(5, obs2.currentValueChanged);
    }

    @Test
    public final void testResetAllCurrentValue() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertTrue(model.resetAllCurrentValue());
        assertEquals("0", model.getCurrentValue(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals("true", model.getCurrentValue(TestExconfigEntries.SUCC_1.getConfigKey()));
        assertEquals("0", model.getCurrentValue(TestExconfigEntries.SUCC_2.getConfigKey()));
        assertEquals("0", model.getCurrentValue(TestExconfigEntries.SUCC_3.getConfigKey()));
        assertEquals(4, obs2.currentValueChanged);
    }

    @Test
    public final void testGetValueParser() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertEquals(12450, model.getValueParser(TestExconfigEntries.SUCC_0.getConfigKey()).parseValue("12450"));
        assertEquals(false, model.getValueParser(TestExconfigEntries.SUCC_1.getConfigKey()).parseValue("false"));
    }

    @Test
    public final void testSetValueParser() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertTrue(model.setValueParser(TestExconfigEntries.SUCC_0.getConfigKey(), new BooleanValueParser()));
        assertTrue(model.setValueParser(TestExconfigEntries.SUCC_0.getConfigKey(), new BooleanValueParser()));
        assertEquals(2, obs2.valueParser);
        assertEquals(false, model.getValueParser(TestExconfigEntries.SUCC_0.getConfigKey()).parseValue("12450"));
        assertEquals(false, model.getValueParser(TestExconfigEntries.SUCC_0.getConfigKey()).parseValue("false"));
    }

    @Test
    public final void testGetParsedValueConfigKey() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertEquals(12450, model.getParsedValue(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals(false, model.getParsedValue(TestExconfigEntries.SUCC_1.getConfigKey()));
    }

    @Test
    public final void testGetParsedValueConfigKeyClassOfT() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertEquals((Integer) 12450, model.getParsedValue(TestExconfigEntries.SUCC_0.getConfigKey(), Integer.class));
        assertEquals(false, model.getParsedValue(TestExconfigEntries.SUCC_1.getConfigKey(), Boolean.class));
    }

    @Test
    public final void testSetParsedValue() {
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertFalse(model.setParsedValue(TestExconfigEntries.SUCC_0.getConfigKey(), null));
        assertTrue(model.setParsedValue(TestExconfigEntries.SUCC_0.getConfigKey(), 3306));
        assertEquals("3306", model.getCurrentValue(TestExconfigEntries.SUCC_0.getConfigKey()));
        assertEquals(TestExconfigEntries.SUCC_0.getConfigKey(), obs1.currentValueChangedList.get(0));
    }

    @Test
    public final void testRemoveObserver() {
        model.removeObserver(obs1);
        model.addAll(Arrays.asList(TestExconfigEntries.values()));
        assertEquals(0, obs1.addedList.size());
        assertEquals(4, obs2.added);
    }
}
