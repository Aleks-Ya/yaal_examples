package excel.create;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class RowStyleTest {

    @Test
    void font() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet();
            var row = sheet.createRow(0);

            var font = wb.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 14);
            font.setUnderline(Font.U_DOUBLE);

            var rowStyle = wb.createCellStyle();
            rowStyle.setFont(font);
            assertThat(row.getRowStyle()).isNull();
            row.setRowStyle(rowStyle);

            var cellA1 = row.createCell(0);
            var cellA2 = row.createCell(1);
            cellA1.setCellValue("111");
            cellA2.setCellValue("222");

            var file = Files.createTempFile(RowStyleTest.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            wb.write(new FileOutputStream(file));
        }
    }
}
