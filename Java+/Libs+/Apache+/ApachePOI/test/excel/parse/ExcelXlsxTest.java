package excel.parse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToFile;

/**
 * Работа с Excel-файлом формата XLSX (Excel-2007-2010-2013).
 */
class ExcelXlsxTest {
    @Test
    void readCellValueXlsx() throws IOException, InvalidFormatException {
        var file = resourceToFile(ExcelXlsxTest.class, "Excel-2007-2010-2013.xlsx");
        try (var wb = new XSSFWorkbook(file)) {
            var sheet1 = wb.getSheetAt(0);
            var row1 = sheet1.getRow(0);
            var cellA1 = row1.getCell(0);
            assertThat(cellA1.getStringCellValue()).isEqualTo("Header 1");
        }
    }
}
