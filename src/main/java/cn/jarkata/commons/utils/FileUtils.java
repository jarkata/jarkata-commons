package cn.jarkata.commons.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static String trimPath(String path) {
        if (StringUtils.isBlank(path)) {
            return "";
        }
        path = path.replaceAll("\\\\", "/");
        path = path.replaceAll("\\/\\/", "/");
        return path;
    }

    public static String trimSuffix(String filePath, String suffix) {
        if (StringUtils.isBlank(filePath)) {
            return "";
        }
        filePath = filePath.substring(0, filePath.length() - suffix.length());
        return filePath;
    }

    public static String trimPrefix(String filePath, String prefix) {
        if (StringUtils.isBlank(filePath)) {
            return "";
        }
        filePath = filePath.substring(prefix.length());
        return filePath;
    }

    public static void clearFile(File directory) throws IOException {
        try (Stream<Path> pathStream = Files.walk(directory.toPath())
                .sorted(Comparator.reverseOrder())) {
            List<Path> pathList = pathStream.collect(Collectors.toList());
            pathList.forEach(path -> {
                File file = path.toFile();
                if (!deleteFile(file)) {
                    throw new IllegalArgumentException("File Type Invalid");
                }
            });
        }
    }

    /**
     * 删除文件,如果文件为目录且为空时，同样删除
     *
     * @param file 文件
     * @return true-删除成功，false-删除失败
     */
    public static boolean deleteFile(File file) {
        if (Objects.isNull(file)) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        } else if (file.isDirectory() && Objects.requireNonNull(file.list()).length <= 0) {
            return file.delete();
        } else if (file.isHidden()) {
            return file.delete();
        }
        return false;
    }

    public static List<String> readFile(File file) throws IOException {
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

    public static ByteArrayOutputStream toByteStream(InputStream fis) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (BufferedInputStream bis = new BufferedInputStream(fis); BufferedOutputStream bufferedOs = new BufferedOutputStream(bos)) {
            byte[] buff = new byte[1024];
            int len;
            while ((len = bis.read(buff)) != -1) {
                bufferedOs.write(buff, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bos;
    }

}