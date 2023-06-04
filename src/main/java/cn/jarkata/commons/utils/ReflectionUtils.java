package cn.jarkata.commons.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 反射工具类
 */
public class ReflectionUtils {
    private static final ConcurrentHashMap<String, List<Field>> fieldCacheMap = new ConcurrentHashMap<>();

    /**
     * 获取clazz类的所有字段
     *
     * @param clazz clazz对象
     * @return 所有的属性值
     */
    public static Field[] getAllField(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return new Field[0];
        }
        return clazz.getDeclaredFields();
    }

    public static List<Field> getFieldList(Class<?> objClazz) {
        if (Objects.isNull(objClazz)) {
            return new ArrayList<>(0);
        }
        List<Field> fieldList = fieldCacheMap.get(objClazz.getName());
        if (Objects.isNull(fieldList)) {
            Field[] allField = getAllField(objClazz);
            fieldList = new ArrayList<>(Arrays.asList(allField));
            fieldCacheMap.put(objClazz.getName(), fieldList);
        }
        Class<?> superclass = objClazz.getSuperclass();
        if (Objects.nonNull(superclass) && !(superclass.isAssignableFrom(Object.class))) {
            List<Field> fieldList1 = getFieldList(superclass);
            fieldList.addAll(fieldList1);
        }
        return fieldList;
    }

    public static Field[] getAllField(Object obj) {
        if (Objects.isNull(obj)) {
            return new Field[0];
        }
        return getAllField(obj.getClass());
    }

    public static void setFieldValue(Field distDeclaredField, Object distObj, Object fieldValue) throws IllegalAccessException {
        if (Objects.isNull(distDeclaredField) || Objects.isNull(fieldValue)) {
            return;
        }
        distDeclaredField.setAccessible(true);
        distDeclaredField.set(distObj, fieldValue);
    }

    public static Field getDeclaredField(Object obj, String fieldName) {
        if (Objects.isNull(obj)) {
            return null;
        }
        return getDeclaredField(obj.getClass(), fieldName);
    }

    public static Field getDeclaredField(Class<?> objClazz, String fieldName) {
        if (Objects.isNull(objClazz)) {
            return null;
        }
        Field declaredField;
        try {
            declaredField = objClazz.getDeclaredField(fieldName);
        } catch (Exception ex) {
            declaredField = getDeclaredField(objClazz.getSuperclass(), fieldName);
        }
        return declaredField;
    }

    /**
     * @param declaredField 字段
     * @param obj           对象
     * @return 字段值
     */
    public static Object getFieldValue(Field declaredField, Object obj) {
        try {
            if (Objects.isNull(declaredField)) {
                return null;
            }
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * 获取obj对象中fieldName字段的值
     *
     * @param obj       对象
     * @param fieldName 属性名称
     * @return 该{fieldName}属性的值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field declaredField = getDeclaredField(obj, fieldName);
            return getFieldValue(declaredField, obj);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    /**
     * 将对象数据转换为Map结构
     *
     * @param obj 对象
     * @return key/value形式的数据
     */
    public static Map<String, Object> toObjectMap(Object obj) {
        Field[] allField = getAllField(obj);
        List<Field> fieldList = Arrays.stream(allField)
                                      .filter(field -> field.getModifiers() != Modifier.STATIC && field.getModifiers() != Modifier.FINAL)
                                      .collect(Collectors.toList());
        Map<String, Object> dataMap = new HashMap<>(allField.length);
        for (Field field : fieldList) {
            Object fieldValue = getFieldValue(field, obj);
            dataMap.put(field.getName(), fieldValue);
        }
        return dataMap;
    }

}