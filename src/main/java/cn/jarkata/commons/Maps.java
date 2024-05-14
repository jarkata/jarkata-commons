package cn.jarkata.commons;

import cn.jarkata.commons.utils.ReflectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 处理Map的相关工具类
 */
public class Maps {

    /**
     * map类型转换,或者将java复杂对象转换为map结构
     *
     * @param obj 对象
     * @return map
     */
    public static Map<String, Object> toMap(Object obj) {
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        }
        boolean primitive = isPrimitive(obj);
        if (primitive) {
            throw new IllegalArgumentException("Not Support Primitive Type");
        }
        if (obj instanceof String) {
            throw new IllegalArgumentException("Not Support String Type");
        }
        if (obj instanceof Collection) {
            throw new IllegalArgumentException("Not Support Class Type");
        }
        return ReflectionUtils.toObjectMap(obj);
    }

    private static boolean isPrimitive(Object obj) {
        return obj instanceof Integer
                || obj instanceof Long
                || obj instanceof Float
                || obj instanceof Double
                || obj instanceof Byte
                || obj instanceof Boolean
                || obj instanceof Character
                || obj instanceof Short;
    }
}
