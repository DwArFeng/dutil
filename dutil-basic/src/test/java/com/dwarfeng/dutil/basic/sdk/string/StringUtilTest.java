package com.dwarfeng.dutil.basic.sdk.string;

import com.dwarfeng.dutil.basic.sdk.io.CT;
import org.junit.jupiter.api.Test;

import static com.dwarfeng.dutil.basic.sdk.string.StringUtil.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest {

    @Test
    public void testIsMultiline() {
        assertTrue(isMultiline("123\n456"));
        assertTrue(isMultiline("123\r\n456"));
        assertTrue(isMultiline("123456\n"));
        assertTrue(isMultiline("\n123\n456\n"));
        assertTrue(isMultiline("\n"));
        assertFalse(isMultiline("123"));
        assertFalse(isMultiline(""));
        assertFalse(isMultiline(null));
    }

    @Test
    public void testIsEmailAddress() {
        assertTrue(isEmailAddress("915724865@qq.com"));
        assertTrue(isEmailAddress("dwarfeng@aaa.bbb.com"));
        assertFalse(isEmailAddress("中国智造@163.com"));
        assertFalse(isEmailAddress("dwarfeng@qq@163.com"));
        assertFalse(isEmailAddress("@163.com"));
        assertFalse(isEmailAddress("915724865"));
        assertFalse(isEmailAddress("915724865@"));
        assertTrue(isEmailAddress("9157.24865@qq.com"));
        assertFalse(isEmailAddress("9157,24865@qq.com"));
        assertFalse(isEmailAddress("915724865@qq,com"));
    }

    @Test
    public void testIsInteger() {
        assertTrue(isInteger("0"));
        assertTrue(isInteger("12450"));
        assertTrue(isInteger("+0"));
        assertTrue(isInteger("+12450"));
        assertTrue(isInteger("-0"));
        assertTrue(isInteger("-12450"));
        assertFalse(isInteger("ab"));
        assertFalse(isInteger("12a50"));
        assertFalse(isInteger("1245a"));
        assertFalse(isInteger("+1a"));
        assertFalse(isInteger("-1a"));
        assertFalse(isInteger("+"));
        assertFalse(isInteger("-"));
    }

    @Test
    public void testIsNumeric() {
        CT.trace("+.0".matches("^[-+]?((\\d+\\.?\\d*)|(\\d*\\.?\\d+))$"));

        assertTrue(isNumeric("0"));
        assertTrue(isNumeric("12450"));
        assertTrue(isNumeric("+0"));
        assertTrue(isNumeric("+12450"));
        assertTrue(isNumeric("-0"));
        assertTrue(isNumeric("-12450"));
        assertTrue(isNumeric(".0"));
        assertTrue(isNumeric("12450."));
        assertTrue(isNumeric("+.0"));
        assertTrue(isNumeric("+12450."));
        assertTrue(isNumeric("-.0"));
        assertTrue(isNumeric("-12450."));
        assertFalse(isNumeric("ab"));
        assertFalse(isNumeric("12a50"));
        assertFalse(isNumeric("1245a"));
        assertFalse(isNumeric("+1a"));
        assertFalse(isNumeric("-1a"));
        assertFalse(isNumeric("a.b"));
        assertFalse(isNumeric("12a.50"));
        assertFalse(isNumeric(".1245a"));
        assertFalse(isNumeric("+1.a"));
        assertFalse(isNumeric("-1a."));
        assertFalse(isNumeric("+"));
        assertFalse(isNumeric("-"));
        assertFalse(isNumeric("+."));
        assertFalse(isNumeric("-."));
        assertFalse(isNumeric("."));
    }
}
