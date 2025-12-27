package com.dwarfeng.dutil.develop.setting;

import com.dwarfeng.dutil.develop.setting.info.BooleanSettingInfo;
import com.dwarfeng.dutil.develop.setting.info.IntegerSettingInfo;

enum Test_SettingEnumItem implements SettingEnumItem {
    ENTRY_1("entry.1", new BooleanSettingInfo("TRUE")), // 
    ENTRY_1F("entry.1", new BooleanSettingInfo("FALSE")), // 
    ENTRY_2("entry.2", new BooleanSettingInfo("FALSE")), // 
    ENTRY_3("entry.3", new IntegerSettingInfo("12")), // 
    ENTRY_4("entry.4", new IntegerSettingInfo("450")), // 
    ENTRY_5("entry.5", new BooleanSettingInfo("TRUE")), // 
    ENTRY_6("entry.6", new BooleanSettingInfo("TRUE")), // 
    ENTRY_7("entry.7", new BooleanSettingInfo("TRUE")), // 
    ENTRY_8("entry.8", new BooleanSettingInfo("TRUE")), // 

    ;

    private final String name;
    private final SettingInfo settingInfo;

    Test_SettingEnumItem(String name, SettingInfo settingInfo) {
        this.name = name;
        this.settingInfo = settingInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SettingInfo getSettingInfo() {
        return settingInfo;
    }
}
