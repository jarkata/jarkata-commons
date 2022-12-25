package cn.jarkata.commons.utils;

import java.util.Objects;

/**
 * 字符串工具类
 */
public final class StringUtils {
    public static final String SPACE = " ";

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return true-为空，false-不为空
     */
    public static boolean isBlank(String str) {
        if (Objects.isNull(str)) {
            return true;
        }
        return str.trim()
                .length() <= 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String defaultString(String str, String defaultVal) {
        return defaultIfBlank(str, defaultVal);
    }

    public static String defaultIfBlank(String str, String defaultVal) {
        return isBlank(str) ? defaultVal : str;
    }

    /**
     * 会去掉前后的空格
     *
     * @param obj 数据
     * @return 去掉空格之后的数据
     */
    public static String trimBlankToEmpty(Object obj) {
        return trimToEmpty(obj).trim();
    }

    /**
     * 如果为null时，返回空字符串<code>""</code>,如果非null，则返回实际值
     *
     * @param obj 数据
     * @return 处理之后的值
     */
    public static String trimToEmpty(Object obj) {
        if (obj instanceof String) {
            return ((String) obj);
        }
        String str = Objects.toString(obj, null);
        return trimToEmpty(str);
    }

    public static int length(String str) {
        if (isBlank(str)) {
            return 0;
        }
        return str.length();
    }

    /**
     * 右侧追加数据
     *
     * @param str    字符串
     * @param len    长度
     * @param padStr 追加的字符
     * @return 字符串
     */
    public static String rightPad(String str, int len, String padStr) {
        if (isBlank(str)) {
            return str;
        }
        if (isBlank(padStr)) {
            padStr = SPACE;
        }
        final int padLen = padStr.length();

        final int strLen = str.length();
        int subLen = len - strLen;

        if (subLen <= 0) {
            return str; // returns original String when possible
        }
        if (subLen == padLen) {
            return str + padStr;
        } else if (subLen < padLen) {
            return str + padStr.substring(0, subLen);
        } else {
            return str + paddingStr(subLen, padStr);
        }
    }

    /**
     * 左侧追加字符串
     *
     * @param str    字符串
     * @param len    目标字符串长度
     * @param padStr 追加的字符串
     * @return 结果字符串
     */
    public static String leftPad(String str, int len, String padStr) {
        if (isBlank(str)) {
            return str;
        }
        if (isBlank(padStr)) {
            padStr = SPACE;
        }
        final int padLen = padStr.length();

        final int strLen = str.length();
        int subLen = len - strLen;

        if (subLen <= 0) {
            return str; // returns original String when possible
        }
        if (subLen == padLen) {
            return padStr + str;
        } else if (subLen < padLen) {
            return padStr.substring(0, subLen) + str;
        } else {
            return paddingStr(subLen, padStr) + str;
        }
    }

    public static String paddingStr(int len, String padChar) {
        StringBuilder builder = new StringBuilder();
        char[] padChars = padChar.toCharArray();
        int padLen = padChars.length;
        for (int index = 0; index < len; index = index + padLen) {
            builder.append(padChar);
        }
        return builder.substring(0, len);
    }

    public static String trimToEmpty(String str) {
        return Objects.isNull(str) ? "" : str;
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