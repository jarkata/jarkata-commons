package cn.jarkata.commons;

import cn.jarkata.commons.utils.ReflectionUtils;

import java.util.Map;

/**
 * 处理Map的相关工具类
 */
public class Maps {

    public static Map<String, Object> toMap(Object obj) {
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        }
        return ReflectionUtils.toObjectMap(obj);
    }
}
