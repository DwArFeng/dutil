package com.dwarfeng.dutil.basic.sdk.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link NumberUtil} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class NumberUtilTest {

    @Test
    public void testPrimitiveByteRoundTrips() {
        assertEquals(0x12345678, NumberUtil.bytesToInt(NumberUtil.intToBytes(0x12345678)));
        assertEquals(0x123456789ABCDEFL, NumberUtil.bytesToLong(NumberUtil.longToBytes(0x123456789ABCDEFL)));
        assertEquals(12.5F, NumberUtil.bytesToFloat(NumberUtil.floatToBytes(12.5F)), 0.0F);
        assertEquals(12.5D, NumberUtil.bytesToDouble(NumberUtil.doubleToBytes(12.5D)), 0.0D);
        assertEquals((short) 0x1234, NumberUtil.bytesToShort(NumberUtil.shortToBytes((short) 0x1234)));
    }

    @Test
    public void testResizeSemanticsAndCut() {
        assertEquals(0x12000000, NumberUtil.bytesToInt(new byte[]{0x12}));
        assertEquals(0x12345678, NumberUtil.bytesToInt(new byte[]{0x12, 0x34, 0x56, 0x78, 0x7F}));
        assertEquals((byte) 0xFF, NumberUtil.cutIntToByte(0x1FF));
    }
}
