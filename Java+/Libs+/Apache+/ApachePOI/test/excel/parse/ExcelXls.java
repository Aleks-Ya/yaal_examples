package excel.parse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Работа с Excel-файлом формата XLS (Excel-97-2000-XP-2003).
 */
public class ExcelXls {

    @Test
    public void readCellValue() throws IOException {
        var file = ExcelXls.class.getResource("Excel-97-2000-XP-2003.xls").getFile();
        try (var is = new FileInputStream(file);
             var wb = new HSSFWorkbook(is)) {
            var sheet1 = wb.getSheetAt(0);
            var row1 = sheet1.getRow(0);
            var cellA1 = row1.getCell(0);
            assertEquals("Header 1", cellA1.getStringCellValue());
        }
    }
}
