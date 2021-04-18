package excel.parse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Работа с Excel-файлом формата XLSX (Excel-2007-2010-2013).
 */
public class ExcelXlsx {

    @Test
    public void readCellValueXlsx() throws IOException {
        var f = ExcelXlsx.class.getResource("Excel-2007-2010-2013.xlsx").getFile();
        try (var wb = new XSSFWorkbook(f)) {
            var sheet1 = wb.getSheetAt(0);
            var row1 = sheet1.getRow(0);
            var cellA1 = row1.getCell(0);
            assertEquals("Header 1", cellA1.getStringCellValue());
        }
    }
}
