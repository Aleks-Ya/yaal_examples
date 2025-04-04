package excel.parse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static util.ResourceUtil.resourceToFile;

/**
 * Работа с Excel-файлом формата XLS (Excel-97-2000-XP-2003).
 */
class ExcelXlsTest {
    @Test
    void readCellValue() throws IOException {
        var file = resourceToFile(ExcelXlsTest.class, "Excel-97-2000-XP-2003.xls");
        try (var is = new FileInputStream(file);
             var wb = new HSSFWorkbook(is)) {
            var sheet1 = wb.getSheetAt(0);
            var row1 = sheet1.getRow(0);
            var cellA1 = row1.getCell(0);
            assertThat(cellA1.getStringCellValue()).isEqualTo("Header 1");
        }
    }
}
