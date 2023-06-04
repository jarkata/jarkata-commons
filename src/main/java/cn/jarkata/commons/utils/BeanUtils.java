package cn.jarkata.commons.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class BeanUtils {


    public static void copyBeans(Object target, Object dist) throws IllegalAccessException {
        if (Objects.isNull(target)) {
            return;
        }
        List<Field> fieldList = ReflectionUtils.getFieldList(dist.getClass());
        for (Field declaredField : fieldList) {
            Object targetValue = ReflectionUtils.getFieldValue(target, declaredField.getName());
            ReflectionUtils.setFieldValue(declaredField, dist, targetValue);
        }

    }


}
