package com.dwarfeng.dutil.demo.basic;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.io.StringInputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * {@link StringInputStream} 的示例。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class StringInputStreamDemo {

    public static void main(String[] args) throws IOException {
        String testString = "这是第一行\n" +
                "This is the second line\n" +
                "这是第三行\n" +
                "This is the forth line";
        StringInputStream in = new StringInputStream(testString, StandardCharsets.UTF_8);

        Scanner scanner = new Scanner(in, "UTF-8");

        try {
            while (scanner.hasNextLine()) {
                CT.trace(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
    }
}
