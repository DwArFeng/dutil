package com.dwarfeng.dutil.basic.sdk.io;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link FileUtil} 的单元测试。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FileUtilTest {

    @Test
    public void testCopyCreatesParentAndReplacesTarget() throws Exception {
        Path directory = Path.of("target", "file-util-test");
        FileUtil.deleteFile(directory.toFile());
        Files.createDirectories(directory);
        Path source = directory.resolve("source.txt");
        Path target = directory.resolve("nested/target.txt");
        Files.writeString(source, "first", StandardCharsets.UTF_8);

        FileUtil.fileCopy(source.toFile(), target.toFile());
        assertEquals("first", Files.readString(target, StandardCharsets.UTF_8));

        Files.writeString(source, "second", StandardCharsets.UTF_8);
        FileUtil.fileCopy(source.toFile(), target.toFile());
        assertEquals("second", Files.readString(target, StandardCharsets.UTF_8));
        assertTrue(FileUtil.deleteFile(directory.toFile()));
    }
}
