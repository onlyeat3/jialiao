package test;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author liuyuyu
 */
public class ReflectionUtils {
    private ReflectionUtils() {
    }

    @SuppressWarnings("unchecked")
    public static List<JiaLiAo.RowInfo> getAllFieldRowInfoAnnotation(Class clazz, Comparator<JiaLiAo.RowInfo> comparator){
        Field[] declaredFields = clazz.getDeclaredFields();
        List<JiaLiAo.RowInfo> rowInfoList = new ArrayList<>();
        for (Field field : declaredFields) {
            JiaLiAo.ExcelCell an = field.getAnnotation(JiaLiAo.ExcelCell.class);
            JiaLiAo.RowInfo rowInfo = new JiaLiAo.RowInfo();
            rowInfo.setFieldName(field.getName());
            rowInfo.setAnnotation(an);
            rowInfoList.add(rowInfo);
        }
        rowInfoList.sort(comparator);
        return rowInfoList;
    }

    public static boolean hasAnnotation(Class clazz, String fieldName, Class<? extends Annotation> annotation){
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
            return field.getAnnotationsByType(annotation).length > 0;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
