package com.dwarfeng.dutil.demo.basic;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.io.StringOutputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {@link StringOutputStream} 的示例。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class StringOutputStreamDemo {

    public static void main(String[] args) throws IOException {
        byte[] bs0 = new byte[]{84, 104, 101, 32, 113, 117, 105, 99, 107, 32, 98, 114, 111, 119, 110, 32, 102, 111, 120, 32, 106, 117, 109, 112, 115, 32, 111, 118, 101, 114, 32, 116, 104, 101, 32, 108, 97, 122};
        byte[] bs1 = new byte[]{121, 32, 100, 111};
        byte[] bs2 = new byte[]{127, 103, 46, -128};

        try (StringOutputStream out = new StringOutputStream(StandardCharsets.UTF_8)) {
            for (byte b : bs0) {
                out.write(b);
            }
            out.write(bs1);
            out.write(bs2, 1, 2);

            out.flush();
            CT.trace(out);
        }

    }
}
