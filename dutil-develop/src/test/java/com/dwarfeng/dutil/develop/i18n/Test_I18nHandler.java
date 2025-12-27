package com.dwarfeng.dutil.develop.i18n;

import com.dwarfeng.dutil.basic.cna.model.DelegateKeySetModel;
import com.dwarfeng.dutil.basic.str.DefaultName;
import com.dwarfeng.dutil.basic.str.Name;
import org.junit.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.WeakHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Test_I18nHandler {

    private final static Locale USED_LOCAL = Locale.ENGLISH;
    private final static String USED_KEY = "hello";
    private final static Name USED_NAME = new DefaultName("hello");
    private final String MISSING_STRING = "missing string!";

    private static I18nHandler handler = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        handler = new DelegateI18nHandler(
                new DelegateKeySetModel<>(new LinkedHashSet<>(), Collections.newSetFromMap(new WeakHashMap<>())));
        handler.add(TestI18nInfo.ENGLISH);
    }

    @After
    public void tearDown() {
        handler = null;
    }

    @Test
    public void getStringString() {
        assertNull(handler.getString(USED_KEY));
        handler.setCurrentLocale(USED_LOCAL);
        assertEquals("hello", handler.getString(USED_KEY));
    }

    @Test
    public void getStringName() {
        assertNull(handler.getString((Name) null));
        assertNull(handler.getString(USED_NAME));
        handler.setCurrentLocale(USED_LOCAL);
        assertEquals("hello", handler.getString(USED_NAME));
    }

    @Test
    public void getStringOrDefaultStringString() {
        assertEquals(MISSING_STRING, handler.getStringOrDefault(USED_KEY, MISSING_STRING));
        handler.setCurrentLocale(USED_LOCAL);
        assertEquals("hello", handler.getStringOrDefault(USED_KEY, MISSING_STRING));
        assertEquals(MISSING_STRING, handler.getStringOrDefault("12450", MISSING_STRING));
    }

    @Test
    public void getStringOrDefaultNameString() {
        assertEquals(MISSING_STRING, handler.getStringOrDefault((Name) null, MISSING_STRING));
        assertEquals(MISSING_STRING, handler.getStringOrDefault(USED_NAME, MISSING_STRING));
        handler.setCurrentLocale(USED_LOCAL);
        assertEquals("hello", handler.getStringOrDefault(USED_NAME, MISSING_STRING));
        assertEquals(MISSING_STRING, handler.getStringOrDefault(new DefaultName("12450"), MISSING_STRING));
    }
}
