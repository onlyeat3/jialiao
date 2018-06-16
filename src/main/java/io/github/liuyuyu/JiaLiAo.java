package io.github.liuyuyu;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.*;
import java.util.*;

/**
 * @author liuyuyu
 */
public class JiaLiAo<T> {
    private Class<T> clazz;
    private Boolean is03;
    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public JiaLiAo(Class<T> clazz,Boolean is03) {
        this.clazz = clazz;
        this.is03 = is03;
    }

    public static void renascence(ObjectMapper objectMapper){
        OBJECT_MAPPER = objectMapper;
    }

    public static <T> JiaLiAo r(Class<T> clazz,Boolean is03){
        return new JiaLiAo<>(clazz,is03);
    }

    public E<T> e(List<Object> dataList){
        return new E<>(this,dataList);
    }

    public W<T> w(){
        return new W<>(this);
    }

    /**
     * E技能，击飞（写）
     */
    static class E<T>{
        private JiaLiAo<T> jiaLiAo;
        private List<Object> dataList = new ArrayList<>();

        public E(JiaLiAo<T> jiaLiAo, List<Object> dataList) {
            this.jiaLiAo = jiaLiAo;
            this.dataList = dataList;
        }

        public void q(String name,OutputStream os) throws IOException {
            Workbook workbook;
            if(this.jiaLiAo.is03){
                workbook = new HSSFWorkbook();
            }else{
                workbook = new XSSFWorkbook();
            }

            Map<String, ExcelCell> fieldNameAnnotationMap = ReflectionUtils.getAllFieldAnnotation(this.jiaLiAo.clazz,Comparator.reverseOrder());

            //创建工作表
            Sheet sheet = workbook.createSheet(name);
            //表头
            Row headerRow = sheet.createRow(0);
            int headerColumnIndex = 0;
            for (String key : fieldNameAnnotationMap.keySet()) {
                Cell cell = headerRow.createCell(headerColumnIndex,CellType.STRING);
                ExcelCell excelCell = fieldNameAnnotationMap.get(key);
                String fieldName = key;
                if(excelCell != null){
                    if(!"".equals(excelCell.value())){
                        fieldName = excelCell.value();
                    }
                }
                cell.setCellValue(fieldName);
                headerColumnIndex ++;
            }
            for (int rowIndex = 1; rowIndex < this.dataList.size(); rowIndex++) {
                //创建行
                Row row = sheet.createRow(rowIndex);
                Object t = dataList.get(rowIndex);
                String jsonString = OBJECT_MAPPER.writeValueAsString(t);
                Map<String,Object> map = OBJECT_MAPPER.readValue(jsonString, HashMap.class);
                TreeMap<String, Object> treeMap = new TreeMap<>(Comparator.reverseOrder());
                treeMap.putAll(map);

                int columnIndex = 0;
                for (String key : treeMap.keySet()) {
                    //创建单元格
                    ExcelCell excelCell = fieldNameAnnotationMap.get(key);
                    CellType cellType = CellType.STRING;
                    if(excelCell != null){
                        cellType = excelCell.cellType();
                    }

                    Cell cell = row.createCell(columnIndex, cellType);
                    cell.setCellValue(treeMap.get(key).toString());
                    columnIndex ++;
                }
            }
            workbook.write(os);
            os.close();
        }
    }

    /**
     * W技能，吸收伤害并嘲讽敌人（读）
     */
    static class W<T>{
        private JiaLiAo jiaLiAo;

        W(JiaLiAo jiaLiAo) {
            this.jiaLiAo = jiaLiAo;
        }

        public T q(){
            return null;
        }
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ExcelCell {
        /**
         * 表头名
         */
        String value();

        /**
         * 单元格数据类型
         */
        CellType cellType() default CellType.STRING;
    }

    /**
     * 顺序信息
     */
    static class OrderInfo{
        /**
         * 字段名
         */
        private String fieldName;
        /**
         * 顺序
         */
        private Long order;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public Long getOrder() {
            return order;
        }

        public void setOrder(Long order) {
            this.order = order;
        }
    }
}
