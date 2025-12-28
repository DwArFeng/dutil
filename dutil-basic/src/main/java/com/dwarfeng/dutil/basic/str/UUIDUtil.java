package com.dwarfeng.dutil.basic.str;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

/**
 * UUID 工具类。
 *
 * <p>
 * 该类提供了 UUID 的一些实用工具方法，包括对 UUID 进行压缩等方法。
 *
 * <p>
 * 由于是只含有静态方法的工具包，所以该类无法被继承。
 *
 * @author DwArFeng
 * @since 0.2.0-beta
 */
public final class UUIDUtil {

    /**
     * 使用 Base64 对 UUID 进行紧凑型编码。
     *
     * <p>
     * 此种紧凑型编码可以在不丢失 UUID 任何信息的情况下，将 36 位长度的 UUID 文本压缩到 22 位。
     *
     * @param uuid 指定的 UUID。
     * @return 根据指定的 UUID 生成的紧凑型 UUID。
     * @throws NullPointerException 指定的入口参数为 <code> null </code>。
     */
    public static String toDenseString(UUID uuid) {
        Objects.requireNonNull(uuid, DwarfUtil.getExceptionString(ExceptionStringKey.UUIDUTIL_1));

        // 定义变量 msb lsb 和一个 16 个字节的 byte 数组。
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        // 填充 byte 数组，将 UUID 转化为字节数组的表示形式。
        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> (8 * (7 - i)) & 0xff);
            buffer[i + 8] = (byte) (lsb >>> (8 * (7 - i)) & 0xff);
        }

        // 使用 Base64 进行编码，得到紧凑型文本。
        String val = Base64.getEncoder().encodeToString(buffer);
        // 去掉结尾的 == 号。
        return val.substring(0, val.length() - 2);
    }

    private UUIDUtil() {
        throw new IllegalStateException(DwarfUtil.getExceptionString(ExceptionStringKey.UUIDUTIL_0));
    }
}
