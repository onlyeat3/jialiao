package io.github.liuyuyu;


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
    public static List<JiaLiAo.RowInfo> getAllFieldAnnotation(Class clazz, Comparator<JiaLiAo.RowInfo> comparator){
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
}
