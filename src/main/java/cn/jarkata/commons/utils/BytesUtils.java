package cn.jarkata.commons.utils;

public class BytesUtils {

    public static String toBinaryString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            builder.append(StringUtils.leftPad(Integer.toUnsignedString(aByte, 2), 8, "0"));
        }
        return builder.toString();
    }

    

}
