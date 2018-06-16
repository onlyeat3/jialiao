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

            List<RowInfo> rowInfoList = ReflectionUtils.getAllFieldAnnotation(this.jiaLiAo.clazz,new ExcelCellComparator());

            //创建工作表
            Sheet sheet = workbook.createSheet(name);
            //表头
            Row headerRow = sheet.createRow(0);
            int headerColumnIndex = 0;
            for (RowInfo rowInfo : rowInfoList) {
                Cell cell = headerRow.createCell(headerColumnIndex, CellType.STRING);
                ExcelCell excelCell = rowInfo.getAnnotation();
                String fieldName = rowInfo.getFieldName();
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

                for (int columnIndex = 0; columnIndex < rowInfoList.size(); columnIndex++) {
                    RowInfo rowInfo = rowInfoList.get(columnIndex);
                    String key = rowInfo.getFieldName();
                    Object value = map.get(key);
                    //创建单元格
                    ExcelCell excelCell = rowInfo.getAnnotation();
                    CellType cellType = CellType.STRING;
                    if(excelCell != null){
                        cellType = excelCell.cellType();
                    }

                    Cell cell = row.createCell(columnIndex, cellType);
                    cell.setCellValue(value.toString());
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
         * 顺序，从小到大
         */
        int order() default -1;

        /**
         * 单元格数据类型
         */
        CellType cellType() default CellType.STRING;
    }

    /**
     * 顺序信息
     */
    static class RowInfo {
        /**
         * 字段名
         */
        private String fieldName;

        private ExcelCell annotation;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public ExcelCell getAnnotation() {
            return annotation;
        }

        public void setAnnotation(ExcelCell annotation) {
            this.annotation = annotation;
        }
    }

    static class ExcelCellComparator implements Comparator<RowInfo> {

        @Override
        public int compare(RowInfo o1, RowInfo o2) {
            //默认值-1尽量不被覆盖
            if(o1 == null || o2 == null
               || o1.getAnnotation() == null || o2.getAnnotation() == null
               ){
                return 0;
            }
            if(o1.getAnnotation().order() == -1){
                return 1;
            }
            return o1.getAnnotation().order() - o2.getAnnotation().order();
        }
    }
}
