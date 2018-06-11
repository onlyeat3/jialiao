package io.github.liuyuyu;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                workbook = buildWorkbook03(name);
            }else{
                workbook = buildWorkbook07(name);
            }
            workbook.write(os);
            os.close();
        }

        private HSSFWorkbook buildWorkbook03(String sheetName) throws IOException {
            //创建工作簿
            HSSFWorkbook workBook = new HSSFWorkbook();
            //创建工作表  工作表的名字叫helloWorld
            HSSFSheet sheet = workBook.createSheet(sheetName);

            for (int rowIndex = 0; rowIndex < this.dataList.size(); rowIndex++) {
                //创建行
                HSSFRow row = sheet.createRow(rowIndex);
                Object t = dataList.get(rowIndex);
                String jsonString = OBJECT_MAPPER.writeValueAsString(t);
                Map map = OBJECT_MAPPER.readValue(jsonString, Map.class);
                int columnIndex = 0;
                for (Object key : map.keySet()) {
                    //创建单元格
                    HSSFCell cell = row.createCell(columnIndex, CellType.STRING);
                    cell.setCellValue(map.get(key).toString());
                    columnIndex ++;
                }
            }
            return workBook;
        }
        private XSSFWorkbook buildWorkbook07(String sheetName) throws IOException {
            //创建工作簿
            XSSFWorkbook workBook = new XSSFWorkbook();
            //创建工作表
            XSSFSheet sheet = workBook.createSheet(sheetName);

            for (int rowIndex = 0; rowIndex < this.dataList.size(); rowIndex++) {
                //创建行
                XSSFRow row = sheet.createRow(rowIndex);
                Object t = dataList.get(rowIndex);
                String jsonString = OBJECT_MAPPER.writeValueAsString(t);
                Map map = OBJECT_MAPPER.readValue(jsonString, Map.class);
                int columnIndex = 0;
                for (Object key : map.keySet()) {
                    //创建单元格
                    XSSFCell cell = row.createCell(columnIndex, CellType.STRING);
                    cell.setCellValue(map.get(key).toString());
                    columnIndex ++;
                }
            }
            return workBook;
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

}
