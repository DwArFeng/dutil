package com.dwarfeng.dutil.develop.cfg;

import com.dwarfeng.dutil.develop.cfg.parser.IntegerValueParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test_IntegerValueParser {

    private static final IntegerValueParser dec_paraser = new IntegerValueParser(10);
    private static final IntegerValueParser bin_paraser = new IntegerValueParser(2);
    private static final IntegerValueParser hex_paraser = new IntegerValueParser(16);

    @Test
    public void testParseString() {
        assertEquals(255, dec_paraser.parseValue("255"));
        assertEquals(255, hex_paraser.parseValue("ff"));
        assertEquals(255, bin_paraser.parseValue("11111111"));
    }

    @Test
    public void testParseObject() {
        assertEquals("255", dec_paraser.parseObject(255));
        assertEquals("11111111", bin_paraser.parseObject(255));
        assertEquals("ff", hex_paraser.parseObject(255));
    }
}
