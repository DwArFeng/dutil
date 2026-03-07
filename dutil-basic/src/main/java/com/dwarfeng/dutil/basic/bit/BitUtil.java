package com.dwarfeng.dutil.basic.bit;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

/**
 * Bit 工具类。
 *
 * <p>
 * 提供位级别的字节数组操作，包括位复制、位读写、位段提取与写入、位逻辑运算等。
 *
 * <p>
 * 位序约定：字节内从高位到低位（bit 7 → bit 0）。位偏移 0 表示第一个字节的最高位（bit 7）。
 *
 * @author DwArFeng
 * @since 0.4.1-beta
 */
public final class BitUtil {

    /**
     * 每个字节的位数。
     */
    public static final int BITS_PER_BYTE = 8;

    /**
     * 位偏移在字节内的最大值。
     */
    public static final int MAX_BIT_OFFSET_IN_BYTE = BITS_PER_BYTE - 1;

    /**
     * 位偏移在字节内的最小值。
     */
    public static final int MIN_BIT_OFFSET_IN_BYTE = 0;

    /**
     * 复制位。
     *
     * <p>
     * 该方法用于从源数组的指定起始位开始复制指定长度的位到目标数组的指定起始位。<br>
     * 该方法会处理位偏移和字节边界问题，确保正确复制位。
     *
     * <p>
     * 需要注意的是，此处的复制是连续复制，方向是从源数组的起始位向后复制到目标数组的起始位。<br>
     * 在同一字节中，位的方向是高位到低位。<br>
     * 举例：从 source 数组的第 <code>3</code> 位开始复制到目标数组的第 <code>5</code> 位，复制 <code>10</code> 位，
     * 假设目标数组的初始值为 <code>0xFFFF</code>，则复制后的目标数组的值为 <code>0xFC29</code>，示意图如下：
     * <blockquote><pre>
     * |        byte| 0 0 0 0 0 0 0 0 | 1 1 1 1 1 1 1 1 |
     * |         bit| 7 6 5 4 3 2 1 0 | 7 6 5 4 3 2 1 0 |
     * |      source| 0 0 1 1 0 0 0 0 | 1 0 1 0 0 0 1 0 |
     * |source_range|       ^ ^ ^ ^ ^ | ^ ^ ^ ^ ^       |
     * |  dest_value| 1 1 1 1 1 1 0 0 | 0 0 1 0 1 0 0 1 |
     * |  dest_range|           ^ ^ ^ | ^ ^ ^ ^ ^ ^ ^   |
     * </pre></blockquote>
     *
     * @param source         源数组。
     * @param sourceStartBit 源数组起始位。
     * @param dest           目标数组。
     * @param destStartBit   目标数组起始位。
     * @param bitLength      位长度。
     * @throws IllegalArgumentException 若 source 或 dest 为 null、任一参数为负数、或数组长度不足以容纳指定范围。
     */
    public static void copyBits(byte[] source, int sourceStartBit, byte[] dest, int destStartBit, int bitLength) {
        // 参数检查。
        if (source == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_0));
        }
        if (dest == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_1));
        }
        if (sourceStartBit < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_2), sourceStartBit));
        }
        if (destStartBit < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_3), destStartBit));
        }
        if (bitLength < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_4), bitLength));
        }
        long sourceEndBit = (long) sourceStartBit + bitLength;
        if (sourceEndBit > (long) source.length * BITS_PER_BYTE) {
            throw new IllegalArgumentException(String.format(
                    DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_5),
                    (sourceEndBit + BITS_PER_BYTE - 1) / BITS_PER_BYTE, source.length));
        }
        long destEndBit = (long) destStartBit + bitLength;
        if (destEndBit > (long) dest.length * BITS_PER_BYTE) {
            throw new IllegalArgumentException(String.format(
                    DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_6),
                    (destEndBit + BITS_PER_BYTE - 1) / BITS_PER_BYTE, dest.length));
        }
        // 特殊情况：如果 bitLength 为 0，则直接返回。
        if (bitLength == 0) {
            return;
        }
        // 特殊情况：如果 sourceStartBit 能被 BITS_PER_BYTE 整除且 destStartBit 能被 BITS_PER_BYTE 整除。
        if (sourceStartBit % BITS_PER_BYTE == 0 && destStartBit % BITS_PER_BYTE == 0) {
            // 如果 bitLength 能被 BITS_PER_BYTE 整除，则直接使用 System.arraycopy 进行复制。
            if (bitLength % BITS_PER_BYTE == 0) {
                int sourceStartByte = sourceStartBit / BITS_PER_BYTE;
                int destStartByte = destStartBit / BITS_PER_BYTE;
                int byteLength = bitLength / BITS_PER_BYTE;
                System.arraycopy(source, sourceStartByte, dest, destStartByte, byteLength);
            }
            // 否则，左侧完整字节使用 System.arraycopy 进行复制，右侧剩余位使用位运算单独处理。
            else {
                int sourceStartByte = sourceStartBit / BITS_PER_BYTE;
                int destStartByte = destStartBit / BITS_PER_BYTE;
                int byteLength = bitLength / BITS_PER_BYTE;
                System.arraycopy(source, sourceStartByte, dest, destStartByte, byteLength);
                int remainingBits = bitLength % BITS_PER_BYTE;
                int mask = (1 << remainingBits) - 1;
                mask <<= (BITS_PER_BYTE - remainingBits);
                byte lastByte = (byte) (source[sourceStartByte + byteLength] & mask);
                dest[destStartByte + byteLength] &= (byte) ~mask;
                dest[destStartByte + byteLength] |= lastByte;
            }
            return;
        }
        // 循环复制位。
        // 同数组重叠且目标起始位大于源起始位时，需反向复制以避免覆盖未读取的源数据。
        boolean reverse = (source == dest)
                && (sourceStartBit < destStartBit)
                && (destStartBit < sourceStartBit + bitLength);
        for (int j = 0; j < bitLength; j++) {
            int i = reverse ? (bitLength - 1 - j) : j;
            long srcBitPos = (long) sourceStartBit + i;
            long destBitPos = (long) destStartBit + i;
            int srcByteIdx = (int) (srcBitPos / BITS_PER_BYTE);
            int srcBitIdx = MAX_BIT_OFFSET_IN_BYTE - ((int) (srcBitPos % BITS_PER_BYTE));
            int mask = 1 << srcBitIdx;
            int bit = (source[srcByteIdx] & mask) == 0 ? 0 : 1;
            int destByteIdx = (int) (destBitPos / BITS_PER_BYTE);
            int destBitIdx = MAX_BIT_OFFSET_IN_BYTE - ((int) (destBitPos % BITS_PER_BYTE));
            if (bit == 1) {
                dest[destByteIdx] |= (byte) (1 << destBitIdx);
            } else {
                dest[destByteIdx] &= (byte) ~(1 << destBitIdx);
            }
        }
    }

    /**
     * 获取指定偏移处的位值。
     *
     * <p>
     * 位偏移 0 表示第一个字节的最高位（bit 7），依次向后递增。
     *
     * @param data      字节数组。
     * @param bitOffset 位偏移。
     * @return 位值，0 或 1。
     * @throws IllegalArgumentException 若 data 为 null、bitOffset 为负数、或 bitOffset 超出数组位范围。
     */
    public static int getBit(byte[] data, int bitOffset) {
        if (data == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_7));
        }
        if (bitOffset < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_8), bitOffset));
        }
        checkBitOffset(data, bitOffset, 1);
        int byteIdx = bitOffset / BITS_PER_BYTE;
        int bitIdx = MAX_BIT_OFFSET_IN_BYTE - (bitOffset % BITS_PER_BYTE);
        int mask = 1 << bitIdx;
        return (data[byteIdx] & mask) == 0 ? 0 : 1;
    }

    /**
     * 设置指定偏移处的位值。
     *
     * <p>
     * 位偏移 0 表示第一个字节的最高位（bit 7），依次向后递增。
     *
     * @param data      字节数组。
     * @param bitOffset 位偏移。
     * @param value     位值，true 表示 1，false 表示 0。
     * @throws IllegalArgumentException 若 data 为 null、bitOffset 为负数、或 bitOffset 超出数组位范围。
     */
    public static void setBit(byte[] data, int bitOffset, boolean value) {
        if (data == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_7));
        }
        if (bitOffset < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_8), bitOffset));
        }
        checkBitOffset(data, bitOffset, 1);
        int byteIdx = bitOffset / BITS_PER_BYTE;
        int bitIdx = MAX_BIT_OFFSET_IN_BYTE - (bitOffset % BITS_PER_BYTE);
        int mask = 1 << bitIdx;
        if (value) {
            data[byteIdx] |= (byte) mask;
        } else {
            data[byteIdx] &= (byte) ~mask;
        }
    }

    /**
     * 从字节数组的指定位置提取指定位数，作为无符号整数返回。
     *
     * <p>
     * 提取的位从高位到低位依次构成返回值的二进制表示。
     *
     * @param data      字节数组。
     * @param bitOffset 起始位偏移。
     * @param bitLength 位长度，范围 1 至 32。
     * @return 提取的无符号整数值。
     * @throws IllegalArgumentException 若 data 为 null、参数为负数、bitLength 不在 1–32、或范围越界。
     */
    public static int getBitsAsInt(byte[] data, int bitOffset, int bitLength) {
        if (data == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_7));
        }
        if (bitOffset < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_8), bitOffset));
        }
        if (bitLength < 1 || bitLength > 32) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_9), bitLength));
        }
        checkBitOffset(data, bitOffset, bitLength);
        int result = 0;
        for (int i = 0; i < bitLength; i++) {
            result = (result << 1) | getBit(data, bitOffset + i);
        }
        return result;
    }

    /**
     * 从字节数组的指定位置提取指定位数，作为无符号长整数返回。
     *
     * <p>
     * 提取的位从高位到低位依次构成返回值的二进制表示。
     *
     * @param data      字节数组。
     * @param bitOffset 起始位偏移。
     * @param bitLength 位长度，范围 1 至 64。
     * @return 提取的无符号长整数值。
     * @throws IllegalArgumentException 若 data 为 null、参数为负数、bitLength 不在 1–64、或范围越界。
     */
    public static long getBitsAsLong(byte[] data, int bitOffset, int bitLength) {
        if (data == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_7));
        }
        if (bitOffset < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_8), bitOffset));
        }
        if (bitLength < 1 || bitLength > 64) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_10), bitLength));
        }
        checkBitOffset(data, bitOffset, bitLength);
        long result = 0;
        for (int i = 0; i < bitLength; i++) {
            result = (result << 1) | getBit(data, bitOffset + i);
        }
        return result;
    }

    /**
     * 将整数的低指定位数写入字节数组的指定位置。
     *
     * <p>
     * 仅使用 value 的低 bitLength 位，从高位到低位依次写入。
     *
     * @param data      字节数组。
     * @param bitOffset 起始位偏移。
     * @param value     要写入的整数值。
     * @param bitLength 位长度，范围 1 至 32。
     * @throws IllegalArgumentException 若 data 为 null、参数为负数、bitLength 不在 1–32、或范围越界。
     */
    public static void setBitsFromInt(byte[] data, int bitOffset, int value, int bitLength) {
        if (data == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_7));
        }
        if (bitOffset < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_8), bitOffset));
        }
        if (bitLength < 1 || bitLength > 32) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_9), bitLength));
        }
        checkBitOffset(data, bitOffset, bitLength);
        int mask = bitLength == 32 ? 0xFFFFFFFF : (1 << bitLength) - 1;
        int maskedValue = value & mask;
        for (int i = 0; i < bitLength; i++) {
            boolean bit = ((maskedValue >> (bitLength - 1 - i)) & 1) != 0;
            setBit(data, bitOffset + i, bit);
        }
    }

    /**
     * 将长整数的低指定位数写入字节数组的指定位置。
     *
     * <p>
     * 仅使用 value 的低 bitLength 位，从高位到低位依次写入。
     *
     * @param data      字节数组。
     * @param bitOffset 起始位偏移。
     * @param value     要写入的长整数值。
     * @param bitLength 位长度，范围 1 至 64。
     * @throws IllegalArgumentException 若 data 为 null、参数为负数、bitLength 不在 1–64、或范围越界。
     */
    public static void setBitsFromLong(byte[] data, int bitOffset, long value, int bitLength) {
        if (data == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_7));
        }
        if (bitOffset < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_8), bitOffset));
        }
        if (bitLength < 1 || bitLength > 64) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_10), bitLength));
        }
        checkBitOffset(data, bitOffset, bitLength);
        long mask = bitLength == 64 ? 0xFFFFFFFFFFFFFFFFL : (1L << bitLength) - 1;
        long maskedValue = value & mask;
        for (int i = 0; i < bitLength; i++) {
            boolean bit = ((maskedValue >> (bitLength - 1 - i)) & 1) != 0;
            setBit(data, bitOffset + i, bit);
        }
    }

    /**
     * 对两个字节数组按位与，结果写入目标数组。
     *
     * <p>
     * dest[i] = a[i] & b[i]。a、b、dest 长度必须相同。
     *
     * @param a    第一个操作数。
     * @param b    第二个操作数。
     * @param dest 目标数组，用于存储结果。
     * @throws IllegalArgumentException 若 a、b、dest 任一为 null、或三者长度不一致。
     */
    public static void and(byte[] a, byte[] b, byte[] dest) {
        checkBitwiseArrays(a, b, dest);
        for (int i = 0; i < a.length; i++) {
            dest[i] = (byte) (a[i] & b[i]);
        }
    }

    /**
     * 对两个字节数组按位或，结果写入目标数组。
     *
     * <p>
     * dest[i] = a[i] | b[i]。a、b、dest 长度必须相同。
     *
     * @param a    第一个操作数。
     * @param b    第二个操作数。
     * @param dest 目标数组，用于存储结果。
     * @throws IllegalArgumentException 若 a、b、dest 任一为 null、或三者长度不一致。
     */
    public static void or(byte[] a, byte[] b, byte[] dest) {
        checkBitwiseArrays(a, b, dest);
        for (int i = 0; i < a.length; i++) {
            dest[i] = (byte) (a[i] | b[i]);
        }
    }

    /**
     * 对两个字节数组按位异或，结果写入目标数组。
     *
     * <p>
     * dest[i] = a[i] ^ b[i]。a、b、dest 长度必须相同。
     *
     * @param a    第一个操作数。
     * @param b    第二个操作数。
     * @param dest 目标数组，用于存储结果。
     * @throws IllegalArgumentException 若 a、b、dest 任一为 null、或三者长度不一致。
     */
    public static void xor(byte[] a, byte[] b, byte[] dest) {
        checkBitwiseArrays(a, b, dest);
        for (int i = 0; i < a.length; i++) {
            dest[i] = (byte) (a[i] ^ b[i]);
        }
    }

    /**
     * 统计字节数组中置 1 的位数。
     *
     * @param data 字节数组。
     * @return 置 1 的位数。
     * @throws IllegalArgumentException 若 data 为 null。
     */
    public static int countSetBits(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_7));
        }
        return countSetBits(data, 0, data.length * BITS_PER_BYTE);
    }

    /**
     * 统计字节数组指定范围内置 1 的位数。
     *
     * @param data      字节数组。
     * @param bitOffset 起始位偏移。
     * @param bitLength 位长度。
     * @return 置 1 的位数。
     * @throws IllegalArgumentException 若 data 为 null、bitOffset 或 bitLength 为负数、或范围越界。
     */
    public static int countSetBits(byte[] data, int bitOffset, int bitLength) {
        if (data == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_7));
        }
        if (bitOffset < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_8), bitOffset));
        }
        if (bitLength < 0) {
            throw new IllegalArgumentException(
                    String.format(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_4), bitLength));
        }
        checkBitOffset(data, bitOffset, bitLength);
        int count = 0;
        for (int i = 0; i < bitLength; i++) {
            if (getBit(data, bitOffset + i) == 1) {
                count++;
            }
        }
        return count;
    }

    /**
     * 校验位偏移与长度是否在数组范围内。
     */
    private static void checkBitOffset(byte[] data, int bitOffset, int bitLength) {
        long endBit = (long) bitOffset + bitLength;
        if (endBit > (long) data.length * BITS_PER_BYTE) {
            throw new IllegalArgumentException(String.format(
                    DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_11),
                    (endBit + BITS_PER_BYTE - 1) / BITS_PER_BYTE, data.length));
        }
    }

    /**
     * 校验按位运算的数组参数。
     */
    private static void checkBitwiseArrays(byte[] a, byte[] b, byte[] dest) {
        if (a == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_12));
        }
        if (b == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_13));
        }
        if (dest == null) {
            throw new IllegalArgumentException(DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_1));
        }
        if (a.length != b.length || a.length != dest.length) {
            throw new IllegalArgumentException(String.format(
                    DwarfUtil.getExceptionString(ExceptionStringKey.BITUTIL_14), a.length, b.length, dest.length));
        }
    }

    private BitUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
