package com.dwarfeng.dutil.basic.bit;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * {@link BitUtil} 的单元测试。
 *
 * @author DwArFeng
 * @since 0.4.1-beta
 */
public class BitUtilTest {

    // region constants

    @Test
    public void testConstants() {
        assertEquals(8, BitUtil.BITS_PER_BYTE);
        assertEquals(7, BitUtil.MAX_BIT_OFFSET_IN_BYTE);
        assertEquals(0, BitUtil.MIN_BIT_OFFSET_IN_BYTE);
    }

    // endregion

    // region constructor

    @Test(expected = InvocationTargetException.class)
    public void testConstructorPrivate() throws Exception {
        Constructor<BitUtil> constructor = BitUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    // endregion

    // region copyBits_parameterValidation

    @Test(expected = IllegalArgumentException.class)
    public void testCopyBits_sourceNull() {
        BitUtil.copyBits(null, 0, new byte[1], 0, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyBits_destNull() {
        BitUtil.copyBits(new byte[1], 0, null, 0, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyBits_sourceStartBitNegative() {
        BitUtil.copyBits(new byte[1], -1, new byte[1], 0, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyBits_destStartBitNegative() {
        BitUtil.copyBits(new byte[1], 0, new byte[1], -1, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyBits_bitLengthNegative() {
        BitUtil.copyBits(new byte[1], 0, new byte[1], 0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyBits_sourceLengthInsufficient() {
        BitUtil.copyBits(new byte[1], 0, new byte[2], 0, 16);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyBits_destLengthInsufficient() {
        BitUtil.copyBits(new byte[2], 0, new byte[1], 0, 16);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyBits_sourceLengthExceedByOne() {
        BitUtil.copyBits(new byte[1], 0, new byte[2], 0, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyBits_destLengthExceedByOne() {
        BitUtil.copyBits(new byte[2], 0, new byte[1], 0, 9);
    }

    // endregion

    // region copyBits_specialCases

    @Test
    public void testCopyBits_bitLengthZero() {
        byte[] dest = new byte[]{(byte) 0xFF, (byte) 0xFF};
        BitUtil.copyBits(new byte[]{0x30, (byte) 0xA2}, 3, dest, 5, 0);
        assertArrayEquals(new byte[]{(byte) 0xFF, (byte) 0xFF}, dest);
    }

    // endregion

    // region copyBits_byteAligned

    @Test
    public void testCopyBits_byteAlignedFullBytes() {
        byte[] source = new byte[]{(byte) 0xAB, (byte) 0xCD};
        byte[] dest = new byte[]{0x00, 0x00};
        BitUtil.copyBits(source, 0, dest, 0, 16);
        assertArrayEquals(new byte[]{(byte) 0xAB, (byte) 0xCD}, dest);
    }

    @Test
    public void testCopyBits_byteAlignedWithRemainingBits() {
        byte[] source = new byte[]{(byte) 0xAB, (byte) 0xCD};
        byte[] dest = new byte[]{(byte) 0xFF, (byte) 0xFF};
        BitUtil.copyBits(source, 0, dest, 0, 12);
        assertArrayEquals(new byte[]{(byte) 0xAB, (byte) 0xCF}, dest);
    }

    @Test
    public void testCopyBits_byteAlignedRemainingBits1() {
        byte[] source = new byte[]{(byte) 0x80, 0x00};
        byte[] dest = new byte[]{(byte) 0xFF, (byte) 0xFF};
        BitUtil.copyBits(source, 0, dest, 0, 9);
        assertArrayEquals(new byte[]{(byte) 0x80, (byte) 0x7F}, dest);
    }

    @Test
    public void testCopyBits_byteAlignedRemainingBits7() {
        byte[] source = new byte[]{(byte) 0xAB, (byte) 0x80};
        byte[] dest = new byte[]{0x00, (byte) 0xFF};
        BitUtil.copyBits(source, 0, dest, 0, 15);
        assertArrayEquals(new byte[]{(byte) 0xAB, (byte) 0x81}, dest);
    }

    @Test
    public void testCopyBits_byteAlignedSameArrayOverlap() {
        byte[] arr = new byte[]{(byte) 0x12, (byte) 0x34, 0x00};
        BitUtil.copyBits(arr, 0, arr, 8, 8);
        assertArrayEquals(new byte[]{(byte) 0x12, (byte) 0x12, 0x00}, arr);
    }

    // endregion

    // region copyBits_loop

    @Test
    public void testCopyBits_javadoc() {
        byte[] source = new byte[]{0x30, (byte) 0xA2};
        byte[] dest = new byte[]{(byte) 0xFF, (byte) 0xFF};
        BitUtil.copyBits(source, 3, dest, 5, 10);
        assertArrayEquals(new byte[]{(byte) 0xFC, 0x29}, dest);
    }

    @Test
    public void testCopyBits_singleBit() {
        byte[] source = new byte[]{(byte) 0x80};
        byte[] dest = new byte[]{0x00};
        BitUtil.copyBits(source, 0, dest, 0, 1);
        assertArrayEquals(new byte[]{(byte) 0x80}, dest);
    }

    @Test
    public void testCopyBits_singleBitZero() {
        byte[] source = new byte[]{(byte) 0x7F};
        byte[] dest = new byte[]{(byte) 0xFF};
        BitUtil.copyBits(source, 0, dest, 0, 1);
        assertArrayEquals(new byte[]{(byte) 0x7F}, dest);
    }

    @Test
    public void testCopyBits_crossByteBoundary() {
        byte[] source = new byte[]{(byte) 0xF3, (byte) 0xCF};
        byte[] dest = new byte[]{0x00, 0x00};
        BitUtil.copyBits(source, 4, dest, 0, 8);
        assertArrayEquals(new byte[]{(byte) 0x3C, 0x00}, dest);
    }

    @Test
    public void testCopyBits_sameArrayNoOverlap() {
        byte[] arr = new byte[]{(byte) 0x12, (byte) 0x34, (byte) 0x56};
        BitUtil.copyBits(arr, 0, arr, 16, 8);
        assertArrayEquals(new byte[]{(byte) 0x12, (byte) 0x34, (byte) 0x12}, arr);
    }

    @Test
    public void testCopyBits_sameArrayOverlap() {
        byte[] arr = new byte[]{0x30, (byte) 0xA2, 0x00};
        BitUtil.copyBits(arr, 0, arr, 5, 10);
        assertArrayEquals(new byte[]{0x31, (byte) 0x84, 0x00}, arr);
    }

    @Test
    public void testCopyBits_destBeforeSource() {
        byte[] arr = new byte[]{(byte) 0x12, (byte) 0x34};
        BitUtil.copyBits(arr, 8, arr, 0, 8);
        assertArrayEquals(new byte[]{(byte) 0x34, (byte) 0x34}, arr);
    }

    @Test
    public void testCopyBits_loopAllZeros() {
        byte[] source = new byte[]{0x00, 0x00};
        byte[] dest = new byte[]{(byte) 0xFF, (byte) 0xFF};
        BitUtil.copyBits(source, 0, dest, 0, 16);
        assertArrayEquals(new byte[]{0x00, 0x00}, dest);
    }

    // endregion

    // region copyBits_boundary

    @Test
    public void testCopyBits_sourceLengthExactBoundary() {
        byte[] source = new byte[]{(byte) 0xAB};
        byte[] dest = new byte[]{(byte) 0x00};
        BitUtil.copyBits(source, 0, dest, 0, 8);
        assertArrayEquals(new byte[]{(byte) 0xAB}, dest);
    }

    @Test
    public void testCopyBits_destLengthExactBoundary() {
        byte[] source = new byte[]{(byte) 0xAB};
        byte[] dest = new byte[]{(byte) 0x00};
        BitUtil.copyBits(source, 0, dest, 0, 8);
        assertArrayEquals(new byte[]{(byte) 0xAB}, dest);
    }

    // endregion

    // region getBit

    @Test
    public void testGetBit_basicBit1() {
        byte[] data = new byte[]{(byte) 0x80};
        assertEquals(1, BitUtil.getBit(data, 0));
    }

    @Test
    public void testGetBit_basicBit0() {
        byte[] data = new byte[]{(byte) 0x7F};
        assertEquals(0, BitUtil.getBit(data, 0));
    }

    @Test
    public void testGetBit_middleByte() {
        byte[] data = new byte[]{(byte) 0xAB, (byte) 0x80};
        assertEquals(1, BitUtil.getBit(data, 8));
        assertEquals(0, BitUtil.getBit(data, 15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBit_dataNull() {
        BitUtil.getBit(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBit_bitOffsetNegative() {
        BitUtil.getBit(new byte[1], -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBit_bitOffsetOutOfRange() {
        BitUtil.getBit(new byte[1], 8);
    }

    // endregion

    // region setBit

    @Test
    public void testSetBit_setToOne() {
        byte[] data = new byte[]{0x00};
        BitUtil.setBit(data, 0, true);
        assertEquals((byte) 0x80, data[0]);
    }

    @Test
    public void testSetBit_setToZero() {
        byte[] data = new byte[]{(byte) 0xFF};
        BitUtil.setBit(data, 0, false);
        assertEquals((byte) 0x7F, data[0]);
    }

    @Test
    public void testSetBit_noChange() {
        byte[] data = new byte[]{(byte) 0x80};
        BitUtil.setBit(data, 0, true);
        assertEquals((byte) 0x80, data[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetBit_dataNull() {
        BitUtil.setBit(null, 0, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetBit_bitOffsetNegative() {
        BitUtil.setBit(new byte[1], -1, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetBit_bitOffsetOutOfRange() {
        BitUtil.setBit(new byte[1], 8, true);
    }

    // endregion

    // region getBitsAsInt

    @Test
    public void testGetBitsAsInt_singleBit() {
        byte[] data = new byte[]{(byte) 0x80};
        assertEquals(1, BitUtil.getBitsAsInt(data, 0, 1));
    }

    @Test
    public void testGetBitsAsInt_fullByte() {
        byte[] data = new byte[]{(byte) 0xAB};
        assertEquals(0xAB, BitUtil.getBitsAsInt(data, 0, 8));
    }

    @Test
    public void testGetBitsAsInt_crossByte() {
        byte[] data = new byte[]{(byte) 0xAB, (byte) 0xCD};
        assertEquals(0xABCD, BitUtil.getBitsAsInt(data, 0, 16));
    }

    @Test
    public void testGetBitsAsInt_32Bits() {
        byte[] data = new byte[]{(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78};
        assertEquals(0x12345678, BitUtil.getBitsAsInt(data, 0, 32));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBitsAsInt_dataNull() {
        BitUtil.getBitsAsInt(null, 0, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBitsAsInt_bitOffsetNegative() {
        BitUtil.getBitsAsInt(new byte[1], -1, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBitsAsInt_bitLengthZero() {
        BitUtil.getBitsAsInt(new byte[4], 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBitsAsInt_bitLengthExceeds32() {
        BitUtil.getBitsAsInt(new byte[4], 0, 33);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBitsAsInt_rangeExceeded() {
        BitUtil.getBitsAsInt(new byte[1], 0, 16);
    }

    // endregion

    // region getBitsAsLong

    @Test
    public void testGetBitsAsLong_basic() {
        byte[] data = new byte[]{(byte) 0xAB, (byte) 0xCD};
        assertEquals(0xABCDL, BitUtil.getBitsAsLong(data, 0, 16));
    }

    @Test
    public void testGetBitsAsLong_64Bits() {
        byte[] data = new byte[]{
                (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78,
                (byte) 0x9A, (byte) 0xBC, (byte) 0xDE, (byte) 0xF0
        };
        assertEquals(0x123456789ABCDEF0L, BitUtil.getBitsAsLong(data, 0, 64));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBitsAsLong_dataNull() {
        BitUtil.getBitsAsLong(null, 0, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBitsAsLong_bitLengthZero() {
        BitUtil.getBitsAsLong(new byte[8], 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBitsAsLong_bitLengthExceeds64() {
        BitUtil.getBitsAsLong(new byte[8], 0, 65);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBitsAsLong_rangeExceeded() {
        BitUtil.getBitsAsLong(new byte[1], 0, 16);
    }

    // endregion

    // region setBitsFromInt

    @Test
    public void testSetBitsFromInt_basic() {
        byte[] data = new byte[]{0x00, 0x00};
        BitUtil.setBitsFromInt(data, 0, 0xABCD, 16);
        assertArrayEquals(new byte[]{(byte) 0xAB, (byte) 0xCD}, data);
    }

    @Test
    public void testSetBitsFromInt_crossByte() {
        byte[] data = new byte[]{0x00, 0x00, 0x00};
        BitUtil.setBitsFromInt(data, 4, 0xFFF, 12);
        assertArrayEquals(new byte[]{(byte) 0x0F, (byte) 0xFF, 0x00}, data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetBitsFromInt_dataNull() {
        BitUtil.setBitsFromInt(null, 0, 0, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetBitsFromInt_bitLengthInvalid() {
        BitUtil.setBitsFromInt(new byte[4], 0, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetBitsFromInt_rangeExceeded() {
        BitUtil.setBitsFromInt(new byte[1], 0, 0, 16);
    }

    // endregion

    // region setBitsFromLong

    @Test
    public void testSetBitsFromLong_basic() {
        byte[] data = new byte[]{0x00, 0x00};
        BitUtil.setBitsFromLong(data, 0, 0xABCDL, 16);
        assertArrayEquals(new byte[]{(byte) 0xAB, (byte) 0xCD}, data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetBitsFromLong_dataNull() {
        BitUtil.setBitsFromLong(null, 0, 0L, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetBitsFromLong_rangeExceeded() {
        BitUtil.setBitsFromLong(new byte[1], 0, 0L, 16);
    }

    // endregion

    // region and

    @Test
    public void testAnd_basic() {
        byte[] a = new byte[]{(byte) 0xFF, (byte) 0xF0};
        byte[] b = new byte[]{(byte) 0x0F, (byte) 0xFF};
        byte[] dest = new byte[2];
        BitUtil.and(a, b, dest);
        assertArrayEquals(new byte[]{(byte) 0x0F, (byte) 0xF0}, dest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnd_aNull() {
        BitUtil.and(null, new byte[1], new byte[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnd_bNull() {
        BitUtil.and(new byte[1], null, new byte[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnd_destNull() {
        BitUtil.and(new byte[1], new byte[1], null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnd_lengthMismatch() {
        BitUtil.and(new byte[1], new byte[2], new byte[1]);
    }

    // endregion

    // region or

    @Test
    public void testOr_basic() {
        byte[] a = new byte[]{(byte) 0xF0, (byte) 0x0F};
        byte[] b = new byte[]{(byte) 0x0F, (byte) 0xF0};
        byte[] dest = new byte[2];
        BitUtil.or(a, b, dest);
        assertArrayEquals(new byte[]{(byte) 0xFF, (byte) 0xFF}, dest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOr_aNull() {
        BitUtil.or(null, new byte[1], new byte[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOr_lengthMismatch() {
        BitUtil.or(new byte[1], new byte[2], new byte[1]);
    }

    // endregion

    // region xor

    @Test
    public void testXor_basic() {
        byte[] a = new byte[]{(byte) 0xFF, (byte) 0xFF};
        byte[] b = new byte[]{(byte) 0xF0, (byte) 0x0F};
        byte[] dest = new byte[2];
        BitUtil.xor(a, b, dest);
        assertArrayEquals(new byte[]{(byte) 0x0F, (byte) 0xF0}, dest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testXor_aNull() {
        BitUtil.xor(null, new byte[1], new byte[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testXor_lengthMismatch() {
        BitUtil.xor(new byte[1], new byte[2], new byte[1]);
    }

    // endregion

    // region countSetBits

    @Test
    public void testCountSetBits_fullArray() {
        byte[] data = new byte[]{(byte) 0xFF, (byte) 0xFF};
        assertEquals(16, BitUtil.countSetBits(data));
    }

    @Test
    public void testCountSetBits_range() {
        byte[] data = new byte[]{(byte) 0xFF, (byte) 0x0F};
        assertEquals(8, BitUtil.countSetBits(data, 0, 8));
        assertEquals(4, BitUtil.countSetBits(data, 8, 8));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCountSetBits_dataNull() {
        BitUtil.countSetBits(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCountSetBits_bitOffsetNegative() {
        BitUtil.countSetBits(new byte[1], -1, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCountSetBits_bitLengthNegative() {
        BitUtil.countSetBits(new byte[1], 0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCountSetBits_rangeExceeded() {
        BitUtil.countSetBits(new byte[1], 0, 16);
    }

    // endregion
}
