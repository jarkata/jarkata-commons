package cn.jarkata.commons.utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        str = str.trim();
        return length(str) <= 0;
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

    /**
     * 计算字符串的长度
     *
     * @param str 字符串
     * @return 字符串长度
     */
    public static int length(String str) {
        if (Objects.isNull(str)) {
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
        final int padLen = length(padStr);

        final int strLen = length(str);
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
        if (isBlank(str)) {
            return null;
        }
        return str.trim();
    }

    private static final Pattern compile = Pattern.compile("\\\"\\{");
    private static final Pattern compile2 = Pattern.compile("\\}\\\"");
    private static final Pattern compile3 = Pattern.compile("\\\\");
    private static final Pattern compile4 = Pattern.compile("\\\n");
    private static final Pattern compile5 = Pattern.compile("\\\t");
    private static final Pattern compile6 = Pattern.compile("\\\"\\[\\{");
    private static final Pattern compile7 = Pattern.compile("\\}\\]\\\"");

    private static final Pattern compile8 = Pattern.compile("  ");


    /**
     * 去除空格,\n、\t,及转换字符
     * <pre>"{ 转换为 {</pre>
     * <pre>}" 转换为 }</pre>
     * <pre>"[{ 转换为 [{</pre>
     * <pre>}]" 转换为 }]</pre>
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String trimJson(String str) {
        if (isBlank(str)) {
            return "";
        }
        Matcher matchered8 = compile8.matcher(str);
        if (matchered8.find()) {
            str = matchered8.replaceAll(" ");
        }

        Matcher matchered = compile.matcher(str);
        if (matchered.find()) {
            str = matchered.replaceAll("{");
        }
        Matcher matchered2 = compile2.matcher(str);
        if (matchered2.find()) {
            str = matchered2.replaceAll("}");
        }
        Matcher matchered3 = compile3.matcher(str);
        if (matchered3.find()) {
            str = matchered3.replaceAll("");
        }
        Matcher matchered4 = compile4.matcher(str);
        if (matchered4.find()) {
            str = matchered4.replaceAll("");
        }
        Matcher matchered5 = compile5.matcher(str);
        if (matchered5.find()) {
            str = matchered5.replaceAll("");
        }
        Matcher matchered6 = compile6.matcher(str);
        if (matchered6.find()) {
            str = matchered6.replaceAll("[{");
        }
        Matcher matchered7 = compile7.matcher(str);
        if (matchered7.find()) {
            str = matchered7.replaceAll("}]");
        }
        return str;
    }


}