package excel.create;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class MergeCells {

    @Test
    public void merge() throws IOException {
        var file = Files.createTempFile(MergeCells.class.getSimpleName(), ".xls").toFile();
        System.out.println("Workbook file: " + file.getAbsolutePath());

        var valueA1 = "A";
        var valueB1 = "B";
        var valueA2 = "C";
        var valueB2 = "D";

        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet("my sheet");
            var row1 = sheet.createRow(0);
            var cellA1 = row1.createCell(0);
            var cellB1 = row1.createCell(1);
            cellA1.setCellValue(valueA1);
            cellB1.setCellValue(valueB1);

            var row2 = sheet.createRow(1);
            var cellA2 = row2.createCell(0);
            var cellB2 = row2.createCell(1);
            cellA2.setCellValue(valueA2);
            cellB2.setCellValue(valueB2);

            sheet.addMergedRegion(new CellRangeAddress(cellA1.getRowIndex(), cellB2.getRowIndex(),
                    cellA1.getColumnIndex(), cellB2.getColumnIndex()));

            sheet.validateMergedRegions();

            wb.write(new FileOutputStream(file));
        }

        try (var actWb = new XSSFWorkbook(new FileInputStream(file))) {
            var actSheet = actWb.getSheetAt(0);

            var actRow1 = actSheet.getRow(0);
            var cellA1 = actRow1.getCell(0);
            var cellB1 = actRow1.getCell(1);
            assertEquals(valueA1, cellA1.getStringCellValue());
            assertEquals(valueB1, cellB1.getStringCellValue());

            var actRow2 = actSheet.getRow(1);
            var cellA2 = actRow2.getCell(0);
            var cellB2 = actRow2.getCell(1);
            assertEquals(valueA2, cellA2.getStringCellValue());
            assertEquals(valueB2, cellB2.getStringCellValue());

            assertThat(actSheet.getNumMergedRegions(), equalTo(1));
            var mergedRegion = actSheet.getMergedRegion(0);
            assertThat(mergedRegion.formatAsString(), equalTo("A1:B2"));
        }
    }
}
