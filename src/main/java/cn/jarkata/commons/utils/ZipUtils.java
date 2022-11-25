package cn.jarkata.commons.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    /**
     * 解压文件
     *
     * @param file zip文件
     * @return 解压之后的文件目录
     * @throws IOException 文件异常
     */
    public static File unzip(File file) throws IOException {
        Objects.requireNonNull(file, "ZipFile Null");
        return unzip(file, file.getParentFile()
                .getAbsolutePath(), true);
    }

    /**
     * 解压Zip或者Jar文件
     *
     * @param file             zip文件
     * @param outputPath       解压文件的输出目录
     * @param clearHistoryFile 是否清理历时文件
     * @return 解压之后的文件
     * @throws IOException 文件异常
     */
    public static File unzip(File file, String outputPath, boolean clearHistoryFile) throws IOException {
        Objects.requireNonNull(file, "ZipFile Null");
        if (file.isDirectory()) {
            return file;
        }
        String fileName = file.getName();
        if (!fileName.endsWith(".zip") && !fileName.endsWith(".jar")) {
            return file;
        }
        int indexOf = fileName.lastIndexOf(".");
        if (indexOf <= 0) {
            return file;
        }
        String suffix = fileName.substring(indexOf);
        String fileNameTmp = FileUtils.trimSuffix(fileName, suffix);
        File outputTargetFile = new File(outputPath, fileNameTmp);
        try (ZipFile zipFile = new ZipFile(file); ZipInputStream zis = new ZipInputStream(Files.newInputStream(file.toPath()))) {
            ZipEntry zipEntry;
            while (Objects.nonNull(zipEntry = zis.getNextEntry())) {
                String entryName = zipEntry.getName();
                File targetZipFile = new File(outputTargetFile, entryName);
                boolean entryDirectory = zipEntry.isDirectory();
                ensureDirectory(targetZipFile, entryDirectory);
                if (entryDirectory) {
                    continue;
                }
                if (clearHistoryFile) {
                    Files.deleteIfExists(targetZipFile.toPath());
                }
                try (InputStream fileInputStream = zipFile.getInputStream(zipEntry)) {
                    ByteArrayOutputStream arrayOutputStream = FileUtils.toByteStream(fileInputStream);
                    Path path = targetZipFile.toPath();
                    Files.write(path, arrayOutputStream.toByteArray(), StandardOpenOption.CREATE_NEW);
                }
            }
        }

        return outputTargetFile;
    }

    public static void ensureDirectory(File file, boolean entryDirectory) {
        FileUtils.ensureDirectory(file, !entryDirectory);
    }
}