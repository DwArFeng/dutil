package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.develop.cfg.checker.BooleanConfigChecker;
import com.dwarfeng.dutil.develop.cfg.checker.IntegerConfigChecker;
import com.dwarfeng.dutil.develop.cfg.parser.BooleanValueParser;
import com.dwarfeng.dutil.develop.cfg.parser.IntegerValueParser;
import com.dwarfeng.dutil.develop.cfg.struct.ConfigChecker;
import com.dwarfeng.dutil.develop.cfg.struct.ExconfigEntry;
import com.dwarfeng.dutil.develop.cfg.struct.ValueParser;

public enum TestExconfigEntries implements ExconfigEntry, ConfigEntry {

    //
    FAIL_0(null, null, null, null, null),
    //
    FAIL_1(null, new IntegerConfigChecker(), "0", new IntegerValueParser(), "12450"),
    //
    FAIL_2("test.fail.2", null, null, new IntegerValueParser(), "12450"),
    //
    SUCC_0("test.succ.0", new IntegerConfigChecker(), "0", new IntegerValueParser(), "12450"),
    //
    SUCC_1("test.succ.1", new BooleanConfigChecker(), "true", new BooleanValueParser(), "false"),
    //
    SUCC_2("test.succ.2", new IntegerConfigChecker(), "0", new IntegerValueParser(), "NAN"),
    //
    SUCC_3("test.succ.3", new IntegerConfigChecker(), "0", new IntegerValueParser(), "NAN"),

    ;

    private final String keyString;
    private final ConfigChecker checker;
    private final String defaultValue;
    private final ValueParser valueParser;
    private final String currentValue;

    TestExconfigEntries(String keyString, ConfigChecker checker, String defaultValue, ValueParser valueParser,
                        String currentValue) {
        this.keyString = keyString;
        this.checker = checker;
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
        this.currentValue = currentValue;
    }

    @Override
    public ConfigKey getConfigKey() {
        if (keyString == null)
            return null;
        return new ConfigKey(keyString);
    }

    @Override
    public ConfigFirmProps getConfigFirmProps() {
        if (defaultValue == null && checker == null)
            return null;

        return new ConfigFirmProps() {

            @Override
            public String getDefaultValue() {
                return defaultValue;
            }

            @Override
            public ConfigChecker getConfigChecker() {
                return checker;
            }
        };
    }

    @Override
    public ValueParser getValueParser() {
        return valueParser;
    }

    @Override
    public String getCurrentValue() {
        return currentValue;
    }
}
