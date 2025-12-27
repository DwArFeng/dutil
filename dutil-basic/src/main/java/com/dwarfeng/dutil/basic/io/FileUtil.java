package com.dwarfeng.dutil.basic.io;

import com.dwarfeng.dutil.basic.DwarfUtil;
import com.dwarfeng.dutil.basic.ExceptionStringKey;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
     * @deprecated 该方法不符合命名规范，已经被 {@link #fileCopy(File, File)} 代替。
     */
    @Deprecated
    public static void FileCopy(File source, File target) throws IOException {
        fileCopy(source, target);
    }

    /**
     * 将一个文件复制到另一个文件。
     *
     * @param source 需要复制的源文件。
     * @param target 需要复制到的目标文件。
     * @throws IOException          复制过程中 IO 发生异常时抛出的异常。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void fileCopy(File source, File target) throws IOException {
        Objects.requireNonNull(source, DwarfUtil.getExceptionString(ExceptionStringKey.FILEUTIL_1));
        Objects.requireNonNull(target, DwarfUtil.getExceptionString(ExceptionStringKey.FILEUTIL_2));

        // 如果 target 不存在，则创建 target 以及其目录（有必要的话）。
        createFileIfNotExists(target);
        // 定义 2MB 的缓冲大小
        int bufferSize = 2097152;
        int length = 0;
        // 创建输入输出流
        FileInputStream in = new FileInputStream(source);
        FileOutputStream out = new FileOutputStream(target);
        // 获取通道
        FileChannel inC = in.getChannel();
        FileChannel outC = out.getChannel();
        // 定义字节缓冲
        ByteBuffer buffer = null;
        while (true) {
            // 判断完成
            if (inC.position() == inC.size()) {
                in.close();
                out.close();
                inC.close();
                outC.close();
                return;
            }
            // 开辟字节缓冲
            length = (int) (inC.size() - inC.position() < bufferSize ? inC.size() - inC.position() : bufferSize);
            buffer = ByteBuffer.allocateDirect(length);
            // 复制数据
            inC.read(buffer);
            buffer.flip();
            outC.write(buffer);
        }
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
     */
    public static boolean deleteFile(File file) {
        Objects.requireNonNull(file, DwarfUtil.getExceptionString(ExceptionStringKey.FILEUTIL_0));

        if (file.isDirectory()) {
            String[] children = file.list();
            // 递归删除该目录的子目录文件
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFile(new File(file, children[i]));
                if (!success)
                    return false;
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
     * @throws IOException          文件无法创建或者通信错误时抛出的异常。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void createFileIfNotExists(File file) throws IOException {
        Objects.requireNonNull(file, DwarfUtil.getExceptionString(ExceptionStringKey.FILEUTIL_0));

        // 如果文件存在，则什么事也不做。
        if (file.exists())
            return;
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists())
            parentFile.mkdirs();
        file.createNewFile();
    }

    /**
     * 如果指定的目录不存在，则尝试新建目录的方法。
     *
     * <p>
     * 该方法在建立目录时，会将其根目录一同创建（如果具有根目录的话）。
     *
     * @param file 指定的目录。
     * @throws IOException 目录无法创建或者通信错误时抛出的异常。
     */
    public static void createDirIfNotExists(File file) throws IOException {
        Objects.requireNonNull(file, DwarfUtil.getExceptionString(ExceptionStringKey.FILEUTIL_0));

        // 如果目录存在，则什么事也不做。
        if (file.exists())
            return;
        file.mkdirs();
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
        Objects.requireNonNull(file, DwarfUtil.getExceptionString(ExceptionStringKey.FILEUTIL_0));

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

        File[] currFolder = file.listFiles();
        for (File currFile : currFolder) {
            scanFile(currFile, filter, list);
        }
    }

    // 不允许实例化
    private FileUtil() {
    }
}
