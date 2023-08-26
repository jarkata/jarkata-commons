package cn.jarkata.commons.utils;

import cn.jarkata.commons.exception.FileException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    public static final String EMPTY_STR = "";

    /**
     * 去除路径中多余的字符
     *
     * @param path 路径
     * @return 去除之后的路径
     */
    public static String trimPath(String path) {
        if (StringUtils.isBlank(path)) {
            return EMPTY_STR;
        }
        path = path.replaceAll("\\\\", "/");
        path = path.replaceAll("\\/\\/", "/");
        return path;
    }

    /**
     * 去除路径的后缀
     *
     * @param filePath 路径
     * @param suffix   后缀
     * @return 去除之后的路径
     */
    public static String trimSuffix(String filePath, String suffix) {
        if (StringUtils.isBlank(filePath)) {
            return EMPTY_STR;
        }
        filePath = filePath.substring(0, filePath.length() - suffix.length());
        return filePath;
    }

    public static String trimPrefix(String filePath, String prefix) {
        if (StringUtils.isBlank(filePath)) {
            return EMPTY_STR;
        }
        if (StringUtils.length(filePath) < StringUtils.length(prefix)) {
            throw new IllegalArgumentException("args invalid,filePath=" + filePath + ",prefix=" + prefix);
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


    /**
     * 清理该目录下的文件
     *
     * @param directory 目录文件
     * @throws IOException 执行失败时，抛出次异常
     */
    public static void clearFile(File directory) throws IOException {
        try (Stream<Path> pathStream = Files.walk(directory.toPath()).sorted(Comparator.reverseOrder())) {
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
        } else if (file.isDirectory() && Objects.requireNonNull(file.list()).length == 0) {
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
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);
             BufferedOutputStream fos = new BufferedOutputStream(outputStream)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw new FileException(e, "Copy Stream Failed");
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

    public static void write(File file, String message) {
        if (Objects.isNull(file)) {
            return;
        }
        boolean directory = ensureDirectory(file.getParentFile());
        if (!directory) {
            throw new IllegalArgumentException(file + " Not Exist");
        }
        try (OutputStream outputStream = Files.newOutputStream(file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            throw new FileException(ex, "Save Data Failed,File=" + file);
        }
    }

    public static int appendData(byte[] data, File targetFile) {
        ensureDirectory(targetFile);
        try (RandomAccessFile accessFile = new RandomAccessFile(targetFile, "rw");
             FileChannel fileChannel = accessFile.getChannel()) {
            long size = fileChannel.size();
            fileChannel.position(size);
            ByteBuffer buffer = ByteBuffer.wrap(data);
            return fileChannel.write(buffer);
        } catch (Exception ex) {
            throw new FileException(ex, "AppendData Failed,File=" + targetFile);
        }
    }


    public static InputStream toInputStream(String str) {
        str = StringUtils.trimToEmpty(str);
        return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    }

    public static URL getURL(String fileName) {
        fileName = StringUtils.trimToEmpty(fileName);
        URL resource = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if (Objects.isNull(resource)) {
            resource = FileUtils.class.getClassLoader().getResource(fileName);
        }
        if (Objects.isNull(resource)) {
            resource = ClassLoader.getSystemClassLoader().getResource(fileName);
        }
        return resource;
    }

    public static File getFile(String fileName) {
        URL resource = getURL(fileName);
        Objects.requireNonNull(resource, fileName + " Not Exist");
        return new File(resource.getFile());
    }

    public static InputStream getStream(String fileName) {
        fileName = StringUtils.trimToEmpty(fileName);
        InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        if (Objects.isNull(resource)) {
            resource = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
        }
        if (Objects.isNull(resource)) {
            resource = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
        }
        return resource;
    }

    public static List<String> readLines(InputStream inputStream) {
        List<String> lineList = new ArrayList<>();
        String line;
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);
             InputStreamReader isr = new InputStreamReader(bis); BufferedReader br = new BufferedReader(isr);) {
            while (Objects.nonNull(line = br.readLine())) {
                lineList.add(line);
            }
        } catch (Exception exception) {
            throw new FileException(exception);
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
            throw new FileException(e);
        }
        return bos;
    }

}