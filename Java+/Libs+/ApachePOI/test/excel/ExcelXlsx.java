package excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Работа с Excel-файлом формата XLSX (Excel-2007-2010-2013).
 */
public class ExcelXlsx {

    @Test
    public void readCellValueXlsx() throws IOException {
        String f = ExcelXlsx.class.getResource("Excel-2007-2010-2013.xlsx").getFile();
        Workbook workbook = new XSSFWorkbook(f);
        Sheet sheet1 = workbook.getSheetAt(0);
        Row row1 = sheet1.getRow(0);
        Cell cellA1 = row1.getCell(0);
        assertEquals("Header 1", cellA1.getStringCellValue());
        workbook.close();
    }
}
