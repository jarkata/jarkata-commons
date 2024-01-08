package cn.jarkata.commons.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 集合类工具类
 */
public class ArrayUtils {

    /**
     * 创建ArrayList集合
     *
     * @param args 参数列表
     * @param <T>  范型
     * @return 集合
     */
    @SafeVarargs
    public static <T> List<T> asList(T... args) {
        return new ArrayList<>(Arrays.asList(args));
    }
}
