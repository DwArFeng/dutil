package com.dwarfeng.dutil.develop.i18n;

import com.dwarfeng.dutil.basic.io.CT;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Test_PropertiesI18n {

    private final static Properties properties = new Properties();
    private final static PropertiesI18n i18n = new PropertiesI18n(properties);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        InputStream in = Test_PropertiesI18n.class
                .getResourceAsStream("/com/dwarfeng/dutil/resources/test/develop/i18n/zh_CN.properties");
        CT.trace(in);
        properties.load(in);
    }

    @Test
    public void testGetString() {
        assertEquals("你好", i18n.getString("hello"));
        assertNull(i18n.getString("bye"));
    }

    @Test
    public void testGetStringOrDefault() {
        assertEquals("你好", i18n.getStringOrDefault("hello", "再见"));
        assertEquals("再见", i18n.getStringOrDefault("bye", "再见"));
    }
}
