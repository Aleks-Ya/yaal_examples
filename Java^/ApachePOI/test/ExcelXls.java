import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Работа с Excel-файлом формата XLS (Excel-97-2000-XP-2003).
 */
public class ExcelXls {

    @Test
    public void readCellValue() throws IOException, InvalidFormatException {
        InputStream is = new FileInputStream(ExcelXls.class.getResource("Excel-97-2000-XP-2003.xls").getFile().replace("%5e", "^"));
        Workbook workbook = new HSSFWorkbook(is);
        Sheet sheet1 = workbook.getSheetAt(0);
        Row row1 = sheet1.getRow(0);
        Cell cellA1 = row1.getCell(0);
        assertEquals("Header 1", cellA1.getStringCellValue());
        workbook.close();
    }
}