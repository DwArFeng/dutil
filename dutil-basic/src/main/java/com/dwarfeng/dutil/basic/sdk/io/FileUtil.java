package com.dwarfeng.dutil.basic.sdk.io;

import com.dwarfeng.dutil.basic.internal.i18n.BasicMessageKey;
import com.dwarfeng.dutil.basic.internal.i18n.BasicMessages;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 实现文件操作的类。
 *
 * <p>
 * 实现的文件操作目前为复制。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public final class FileUtil {

    /**
     * 将一个文件复制到另一个文件。
     *
     * @param source 需要复制的源文件。
     * @param target 需要复制到的目标文件。
     * @throws IOException          复制过程中 IO 发生异常时抛出的异常。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void fileCopy(File source, File target) throws IOException {
        Objects.requireNonNull(source, BasicMessages.message(BasicMessageKey.FILE_SOURCE_REQUIRED));
        Objects.requireNonNull(target, BasicMessages.message(BasicMessageKey.FILE_TARGET_REQUIRED));

        createFileIfNotExists(target);
        Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 文件/文件夹的删除方法。
     *
     * <p>
     * 该方法会对目标文件进行删除，如果目标文件是标准文件的话，则删除该文件。<br>
     * 如果目标文件是文件夹的话，则会删除该文件夹（包括文件夹中的所有文件、子文件夹、子文件夹中的文件等等）。
     *
     * @param file 目标文件或文件夹。
     * @return 文件或文件夹是否删除。
     * @throws NullPointerException  入口参数为 <code>null</code>。
     * @throws IllegalStateException 如果在删除目录时，目录的子文件列表为 null 时抛出该异常。
     */
    public static boolean deleteFile(File file) {
        Objects.requireNonNull(file, BasicMessages.message(BasicMessageKey.FILE_REQUIRED));

        if (file.isDirectory()) {
            String[] children = file.list();
            // 如果 children 为 null，说明方法逻辑有误。
            if (Objects.isNull(children)) {
                throw new IllegalStateException("children should not be null");
            }
            // 递归删除该目录的子目录文件
            for (String child : children) {
                boolean success = deleteFile(new File(file, child));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return file.delete();
    }

    /**
     * 如果指定的文件不存在，则尝试新建文件的方法。
     *
     * <p>
     * 该方法在建立文件时，会将其根目录一同创建（如果具有根目录的话）。
     *
     * @param file 指定的文件。
     * @throws IOException           文件无法创建或者通信错误时抛出的异常。
     * @throws NullPointerException  入口参数为 <code>null</code>。
     * @throws IllegalStateException 父目录创建失败时抛出的异常。
     * @throws IllegalStateException 文件创建失败时抛出的异常。
     */
    public static void createFileIfNotExists(File file) throws IOException {
        Objects.requireNonNull(file, BasicMessages.message(BasicMessageKey.FILE_REQUIRED));

        // 如果文件存在，则什么事也不做。
        if (file.exists()) {
            return;
        }
        // 获取父目录。
        File parentFile = file.getParentFile();
        // 如果父目录不存在，则创建父目录。
        if (Objects.nonNull(parentFile) && !parentFile.exists()) {
            boolean flag = parentFile.mkdirs();
            if (!flag) {
                throw new IllegalStateException("flag should be true");
            }
        }
        // 创建文件。
        boolean flag = file.createNewFile();
        if (!flag) {
            throw new IllegalStateException("flag should be true");
        }
    }

    /**
     * 如果指定的目录不存在，则尝试新建目录的方法。
     *
     * <p>
     * 该方法在建立目录时，会将其根目录一同创建（如果具有根目录的话）。
     *
     * @param file 指定的目录。
     * @throws NullPointerException  入口参数为 <code>null</code>。
     * @throws IllegalStateException 目录创建失败时抛出的异常。
     */
    public static void createDirIfNotExists(File file) {
        Objects.requireNonNull(file, BasicMessages.message(BasicMessageKey.FILE_REQUIRED));

        // 如果目录存在，则什么事也不做。
        if (file.exists()) {
            return;
        }
        boolean flag = file.mkdirs();
        if (!flag) {
            throw new IllegalStateException("flag should be true");
        }
    }

    /**
     * 列出一个文件夹中的所有文件，包括子文件夹下的文件。
     *
     * <p>
     * 如果指定的文件不是文件夹，则返回自己。
     *
     * @param file 指定的文件。
     * @return 指定的文件下的所有文件。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static File[] listAllFile(File file) {
        return listAllFile(file, null);
    }

    /**
     * 列出一个文件夹中的所有符合过滤器文件，包括子文件夹下的文件。
     *
     * <p>
     * 如果指定的文件不是文件夹，则返回自己。
     *
     * @param file   指定的文件。
     * @param filter 指定的过滤器，如果为 <code>null</code>，则接受所有文件。
     * @return 指定文件夹下的符合要求的所有文件。
     */
    public static File[] listAllFile(File file, FileFilter filter) {
        Objects.requireNonNull(file, BasicMessages.message(BasicMessageKey.FILE_REQUIRED));

        if (!file.isDirectory()) {
            return Objects.isNull(filter) || filter.accept(file) ? new File[]{file} : new File[0];
        }

        List<File> list = new ArrayList<>();
        scanFile(file, filter, list);
        return list.toArray(new File[0]);
    }

    private static void scanFile(File file, FileFilter filter, List<File> list) {
        if (Objects.isNull(filter) || filter.accept(file))
            list.add(file);

        if (!file.isDirectory()) {
            return;
        }

        File[] currentFolder = file.listFiles();
        // 如果 currentFolder 为 null，说明方法逻辑有误。
        if (Objects.isNull(currentFolder)) {
            throw new IllegalStateException("currentFolder should not be null");
        }
        // 递归扫描子文件夹。
        for (File currFile : currentFolder) {
            scanFile(currFile, filter, list);
        }
    }

    private FileUtil() {
        throw new IllegalStateException(BasicMessages.message(BasicMessageKey.FILE_INSTANTIATION_FORBIDDEN));
    }
}
