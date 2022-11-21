package cn.jarkata.commons.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtils {

    public static File unzip(File file) throws IOException {
        Objects.requireNonNull(file, "ZipFile Null");
        return unzip(file, file.getParentFile()
                .getAbsolutePath(), true);
    }

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
                FileUtils.ensureDirectory(targetZipFile);
                boolean entryDirectory = zipEntry.isDirectory();
                if (entryDirectory) {
                    continue;
                }
                if (clearHistoryFile) {
                    Files.deleteIfExists(targetZipFile.toPath());
                }
                try (InputStream fileInputStream = zipFile.getInputStream(zipEntry)) {
                    ByteArrayOutputStream arrayOutputStream = FileUtils.toByteStream(fileInputStream);
                    Files.write(targetZipFile.toPath(), arrayOutputStream.toByteArray(), StandardOpenOption.CREATE_NEW);
                }
            }
        }

        return outputTargetFile;
    }
}