package excel.create;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

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
        try (var workbook = new XSSFWorkbook()) {
            var sheet = workbook.createSheet();
            var row = sheet.createRow(0);

            assertNull(row.getRowStyle());
            var rowStyle = workbook.createCellStyle();
            var fontIndex = rowStyle.getFontIndex();
            var font = workbook.getFontAt(fontIndex);
            font.setBold(true);
            font.setFontHeightInPoints((short) 50);
            row.setRowStyle(rowStyle);

            var cellA1 = row.createCell(0);
            var cellA2 = row.createCell(1);

            cellA1.setCellValue("111");
            cellA2.setCellValue("222");

            var file = Files.createTempFile(RowStyle.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            workbook.write(new FileOutputStream(file));
        }
    }
}
