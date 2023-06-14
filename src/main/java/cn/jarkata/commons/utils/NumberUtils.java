package cn.jarkata.commons.utils;

import java.util.Objects;

/**
 * 数字类转换工具
 */
public final class NumberUtils {

    public static Long toLong(Object obj, Long defaultVal) {
        if (obj instanceof Long) {
            return (Long) obj;
        }
        String str = Objects.toString(obj, null);
        str = StringUtils.trimToEmpty(str);
        if (Objects.isNull(str)) {
            return defaultVal;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    public static Integer toInt(Object obj, Integer defaultVal) {
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        String str = Objects.toString(obj, null);
        str = StringUtils.trimToEmpty(str);
        if (Objects.isNull(str)) {
            return defaultVal;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    public static Float toFloat(Object obj, Float defaultVal) {
        if (obj instanceof Float) {
            return (Float) obj;
        }
        String str = Objects.toString(obj, null);
        str = StringUtils.trimToEmpty(str);
        if (Objects.isNull(str)) {
            return defaultVal;
        }
        try {
            return Float.parseFloat(str);
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    public static Double toDouble(Object obj, Double defaultVal) {
        if (obj instanceof Double) {
            return (Double) obj;
        }
        String str = Objects.toString(obj, null);
        str = StringUtils.trimToEmpty(str);
        if (Objects.isNull(str)) {
            return defaultVal;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception ex) {
            return defaultVal;
        }
    }
}