package excel.create;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class CellStyle {

    @Test
    public void style() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet();
            var row = sheet.createRow(0);
            var cellString = row.createCell(0);
            var cellNumeric = row.createCell(1);

            var fontString = wb.createFont();
            fontString.setBold(true);
            fontString.setFontHeightInPoints((short) 14);
            fontString.setUnderline(Font.U_DOUBLE);
            var styleString = wb.createCellStyle();
            styleString.setFont(fontString);
            cellString.setCellStyle(styleString);

            var fontNumeric = wb.createFont();
            fontNumeric.setBold(true);
            fontNumeric.setFontHeightInPoints((short) 14);
            fontNumeric.setUnderline(Font.U_DOUBLE);
            var styleNumeric = wb.createCellStyle();
            styleNumeric.setFont(fontNumeric);
            cellNumeric.setCellStyle(styleNumeric);

            cellString.setCellValue("abc");
            cellNumeric.setCellValue(222);


            var file = Files.createTempFile(CellStyle.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            wb.write(new FileOutputStream(file));
        }
    }
}
