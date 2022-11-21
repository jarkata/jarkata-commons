package cn.jarkata.commons.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtils {

    public static InputStream toInputStream(String str) {
        return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    }

    public static void write(File file, String message) throws IOException {
        boolean directory = ensureDirectory(file);
        if (!directory) {
            throw new IllegalArgumentException(file + " Not Exist");
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(message.getBytes(StandardCharsets.UTF_8));
        }
    }

    public List<String> readFile(File file) throws IOException {
        Objects.requireNonNull(file);
        return readLines(Files.newInputStream(file.toPath()));
    }

    public static boolean ensureDirectory(File file) {
        if (Objects.isNull(file)) {
            return false;
        }
        if (file.isFile()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                return parentFile.mkdirs();
            }
        }
        if (file.isDirectory()) {
            if (file.exists()) {
                return true;
            }
            return file.mkdirs();
        }
        return false;
    }

    public static InputStream getStream(String fileName) {
        InputStream resource = FileUtils.class.getClassLoader()
                .getResourceAsStream(fileName);
        if (Objects.isNull(resource)) {
            resource = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(fileName);
        }
        if (Objects.isNull(resource)) {
            resource = ClassLoader.getSystemClassLoader()
                    .getResourceAsStream(fileName);
        }
        return resource;
    }

    public static List<String> readLines(InputStream inputStream) {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        InputStreamReader isr = new InputStreamReader(bis);
        BufferedReader br = new BufferedReader(isr);
        List<String> lineList = new ArrayList<>();
        String line;
        try {
            while (Objects.nonNull(line = br.readLine())) {
                lineList.add(line);
            }
        } catch (Exception ignored) {
        }
        return lineList;
    }
}