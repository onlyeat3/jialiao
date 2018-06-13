package io.github.liuyuyu;


import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author liuyuyu
 */
public class ReflectionUtils {
    private ReflectionUtils() {
    }

    @SuppressWarnings("unchecked")
    public static Map<String, JiaLiAo.ExcelCell> getAllFieldAnnotation(Class clazz, Comparator comparator){
        Field[] declaredFields = clazz.getDeclaredFields();
        Map<String,JiaLiAo.ExcelCell> fieldNameAnnotationMap = new TreeMap<>(comparator);
        for (Field field : declaredFields) {
            JiaLiAo.ExcelCell an = field.getAnnotation(JiaLiAo.ExcelCell.class);
            fieldNameAnnotationMap.put(field.getName(),an);
        }
        return fieldNameAnnotationMap;
    }
}
