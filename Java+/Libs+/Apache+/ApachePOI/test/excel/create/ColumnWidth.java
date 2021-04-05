package excel.create;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class ColumnWidth {

    @Test
    public void autoSize() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet();
            var row = sheet.createRow(0);

            var column0 = 0;
            var column1 = 1;
            var column2 = 2;
            var cell1 = row.createCell(column0);
            var cell2 = row.createCell(column1);
            var cell3 = row.createCell(column2);

            cell1.setCellValue("short");
            cell2.setCellValue("very wide column");
            cell3.setCellValue("manual column width");

            sheet.autoSizeColumn(column0);
            sheet.autoSizeColumn(column1);
            sheet.setColumnWidth(column2, 10000);

            var file = Files.createTempFile(ColumnWidth.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            wb.write(new FileOutputStream(file));
        }
    }
}
