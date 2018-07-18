package test.jialiao;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author liuyuyu
 */
public class TestPOI07 {
    public static final String FILE_PATH="out/test.xlsx";

    @Test
    public void writeExcel07() throws IOException {
        //创建工作簿
        XSSFWorkbook workBook = new XSSFWorkbook();
        //创建工作表
        XSSFSheet sheet = workBook.createSheet("helloWorld");
        //创建行
        XSSFRow row = sheet.createRow(2);
        //创建单元格，操作第三行第三列
        XSSFCell cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("helloWorld");

        FileOutputStream outputStream = new FileOutputStream(new File(FILE_PATH));
        workBook.write(outputStream);

        workBook.close();//记得关闭工作簿
    }
    @Test
    public void readExcel07() throws IOException{
        FileInputStream inputStream = new FileInputStream(new File(FILE_PATH));
        //读取工作簿
        XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
        //读取工作表
        XSSFSheet sheet = workBook.getSheetAt(0);
        //读取行
        XSSFRow row = sheet.getRow(2);
        //读取单元格
        XSSFCell cell = row.getCell(2);
        String value = cell.getStringCellValue();

        System.out.println(value);

        inputStream.close();//关闭工作簿
        workBook.close();
    }
}
