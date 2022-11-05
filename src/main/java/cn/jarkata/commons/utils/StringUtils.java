package cn.jarkata.commons.utils;

import java.util.Objects;

public final class StringUtils {

    public static boolean isBlank(String str) {
        return Objects.nonNull(str) && str.trim()
                .length() <= 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String defaultIfBlank(String str, String defaultVal) {
        return isBlank(str) ? defaultVal : str;
    }

    public static String trimToEmpty(Object obj) {
        if (obj instanceof String) {
            return ((String) obj).trim();
        }
        String str = Objects.toString(obj, null);
        return trimToEmpty(str);
    }

    public static String trimToEmpty(String str) {
        return isBlank(str) ? "" : str.trim();
    }

    public static String trimToNull(Object obj) {
        if (obj instanceof String) {
            return ((String) obj).trim();
        }
        String str = Objects.toString(obj, null);
        return trimToNull(str);
    }

    public static String trimToNull(String str) {
        return isBlank(str) ? null : str.trim();
    }

    public static String trimJson(String str) {
        if (isBlank(str)) {
            return "";
        }
        str = str.replaceAll("\\\"\\{", "{");
        str = str.replaceAll("\\}\\\"", "}");
        str = str.replaceAll("\\\\", "");
        str = str.replaceAll("\\\n", "");
        str = str.replaceAll("\\\t", "");
        str = str.replaceAll("\\\"\\[\\{", "[{");
        str = str.replaceAll("\\}\\]\\\"", "}]");
        return str;
    }


}