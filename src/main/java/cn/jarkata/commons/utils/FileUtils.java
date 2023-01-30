package cn.jarkata.commons.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

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

    /**
     * 将所有的文件对象转换为List
     *
     * @param file 文件集合
     * @return URL集合
     */
    public static List<URL> toURL(File... file) {
        return Arrays.stream(file).map(mapper -> {
            try {
                return mapper.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }


    public static List<File> toFiles(String... locations) {
        return Arrays.stream(locations).map(File::new).collect(Collectors.toList());
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
        return ensureDirectory(file, file.isFile());
    }

    /**
     * 将input输入流复制至output数据流
     *
     * @param inputStream  输入流
     * @param outputStream 输出流
     */
    public static void copy(InputStream inputStream, OutputStream outputStream) {
        try (
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                BufferedOutputStream fos = new BufferedOutputStream(outputStream)
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean ensureDirectory(File file, boolean isFile) {
        if (Objects.isNull(file)) {
            return false;
        }
        File directory = file;
        if (isFile) {
            directory = file.getParentFile();
        }
        if (directory.exists()) {
            return true;
        }
        return directory.mkdirs();

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

    public static void appendData(byte[] data, File targetFile) throws IOException {
        ensureDirectory(targetFile);
        try (RandomAccessFile accessFile = new RandomAccessFile(targetFile, "rw");
             FileChannel fileChannel = accessFile.getChannel()) {
            long size = fileChannel.size();
            fileChannel.position(size);
            ByteBuffer buffer = ByteBuffer.wrap(data);
            fileChannel.write(buffer);
        }
    }


    public static InputStream toInputStream(String str) {
        return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    }


    public static InputStream getStream(String fileName) {
        InputStream resource = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
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
        List<String> lineList = new ArrayList<>();
        String line;
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);
             InputStreamReader isr = new InputStreamReader(bis);
             BufferedReader br = new BufferedReader(isr);) {
            while (Objects.nonNull(line = br.readLine())) {
                lineList.add(line);
            }
        } catch (Exception ignored) {
        }
        return lineList;
    }


    public static ByteArrayOutputStream toByteStream(InputStream fis) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (BufferedInputStream bis = new BufferedInputStream(fis);
             BufferedOutputStream bufferedOs = new BufferedOutputStream(bos)) {
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