package cn.jarkata.commons.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 反射工具类
 */
public class ReflectionUtils {
    private static final ConcurrentHashMap<String, List<Field>> ALL_FIELD_CACHE_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Field> FIELD_CACHE_MAP = new ConcurrentHashMap<>();

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
        List<Field> fieldList = ALL_FIELD_CACHE_MAP.get(objClazz.getName());
        if (Objects.isNull(fieldList)) {
            Field[] allField = getAllField(objClazz);
            fieldList = new ArrayList<>(Arrays.asList(allField));
        }
        Class<?> superclass = objClazz.getSuperclass();
        if (Objects.nonNull(superclass) && !(superclass.isAssignableFrom(Object.class))) {
            List<Field> fieldList1 = getFieldList(superclass);
            fieldList.addAll(fieldList1);
        }
        ALL_FIELD_CACHE_MAP.put(objClazz.getName(), fieldList);
        return fieldList;
    }


    public static Method getDeclaredMethod(Object obj, String methodName, Class<?>... parameterType) {
        if (Objects.isNull(obj)) {
            return null;
        }
        try {
            return obj.getClass().getDeclaredMethod(methodName, parameterType);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 调用指定的方法
     *
     * @param obj        对象
     * @param method     方法名
     * @param parameters 参数集合
     * @return 方法执行结果
     */
    public static Object invokeMethod(Object obj, String method, Object[] parameters) {
        if (Objects.isNull(obj) || Objects.isNull(parameters)) {
            return null;
        }
        Method declaredMethod = getDeclaredMethod(obj, method, Arrays.stream(parameters).map(Object::getClass).toArray(Class[]::new));
        if (Objects.isNull(declaredMethod)) {
            return null;
        }
        try {
            return declaredMethod.invoke(obj, parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据方法名查找对应的方法
     *
     * @param obj        对象
     * @param methodName 方法名
     * @return 方法集合
     */
    public static List<Method> getDeclaredMethod(Object obj, String methodName) {
        if (Objects.isNull(obj)) {
            return new ArrayList<>(0);
        }
        return getDeclaredMethod(obj.getClass(), methodName);
    }

    /**
     * 根据Class对象查找对应的方法
     *
     * @param clazz      clazz对象
     * @param methodName 方法名
     * @return 方法集合
     */
    public static List<Method> getDeclaredMethod(Class<?> clazz, String methodName) {
        if (Objects.isNull(clazz)) {
            return new ArrayList<>(0);
        }
        List<Method> methodList = Arrays.asList(clazz.getDeclaredMethods());
        return methodList.stream().filter(method -> method.getName().equals(methodName)).collect(Collectors.toList());
    }


    public static Field[] getAllField(Object obj) {
        if (Objects.isNull(obj)) {
            return new Field[0];
        }
        return getAllField(obj.getClass());
    }

    public static void setFieldValue(Field distDeclaredField, Object distObj, Object fieldValue) {
        if (Objects.isNull(distDeclaredField) || Objects.isNull(fieldValue)) {
            return;
        }
        try {
            distDeclaredField.setAccessible(true);
            distDeclaredField.set(distObj, fieldValue);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
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
        Field field = FIELD_CACHE_MAP.get(objClazz.getName() + fieldName);
        if (Objects.nonNull(field)) {
            return field;
        }
        Field declaredField;
        try {
            declaredField = objClazz.getDeclaredField(fieldName);
        } catch (Exception ex) {
            declaredField = getDeclaredField(objClazz.getSuperclass(), fieldName);
        }
        if (Objects.nonNull(declaredField)) {
            FIELD_CACHE_MAP.put(objClazz.getName() + fieldName, declaredField);
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
        List<Field> fieldList = Arrays.stream(allField).filter(field -> field.getModifiers() != Modifier.STATIC && field.getModifiers() != Modifier.FINAL).collect(Collectors.toList());
        Map<String, Object> dataMap = new HashMap<>(allField.length);
        for (Field field : fieldList) {
            Object fieldValue = getFieldValue(field, obj);
            dataMap.put(field.getName(), fieldValue);
        }
        return dataMap;
    }

}