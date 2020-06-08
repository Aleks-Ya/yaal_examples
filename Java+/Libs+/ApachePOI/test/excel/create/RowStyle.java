package excel.create;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertNull;

public class RowStyle {

    /**
     * Doesn't work: row style doesn't change cell appearance.
     */
    @Test
    public void font() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            Row row = sheet.createRow(0);

            assertNull(row.getRowStyle());
            CellStyle rowStyle = workbook.createCellStyle();
            int fontIndex = rowStyle.getFontIndexAsInt();
            Font font = workbook.getFontAt(fontIndex);
            font.setBold(true);
            font.setFontHeightInPoints((short) 50);
            row.setRowStyle(rowStyle);

            Cell cellA1 = row.createCell(0);
            Cell cellA2 = row.createCell(1);

            cellA1.setCellValue("111");
            cellA2.setCellValue("222");

            File file = Files.createTempFile(RowStyle.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            workbook.write(new FileOutputStream(file));
        }
    }
}
