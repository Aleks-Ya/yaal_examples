package excel.create;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
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
        File file = Files.createTempFile(MergeCells.class.getSimpleName(), ".xls").toFile();
        System.out.println("Workbook file: " + file.getAbsolutePath());

        String valueA1 = "A";
        String valueB1 = "B";
        String valueA2 = "C";
        String valueB2 = "D";

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("my sheet");
            Row row1 = sheet.createRow(0);
            Cell cellA1 = row1.createCell(0);
            Cell cellB1 = row1.createCell(1);
            cellA1.setCellValue(valueA1);
            cellB1.setCellValue(valueB1);

            Row row2 = sheet.createRow(1);
            Cell cellA2 = row2.createCell(0);
            Cell cellB2 = row2.createCell(1);
            cellA2.setCellValue(valueA2);
            cellB2.setCellValue(valueB2);

            sheet.addMergedRegion(new CellRangeAddress(cellA1.getRowIndex(), cellB2.getRowIndex(),
                    cellA1.getColumnIndex(), cellB2.getColumnIndex()));

            sheet.validateMergedRegions();

            workbook.write(new FileOutputStream(file));
        }

        try (Workbook actWb = new XSSFWorkbook(new FileInputStream(file))) {
            Sheet actSheet = actWb.getSheetAt(0);

            Row actRow1 = actSheet.getRow(0);
            Cell cellA1 = actRow1.getCell(0);
            Cell cellB1 = actRow1.getCell(1);
            assertEquals(valueA1, cellA1.getStringCellValue());
            assertEquals(valueB1, cellB1.getStringCellValue());

            Row actRow2 = actSheet.getRow(1);
            Cell cellA2 = actRow2.getCell(0);
            Cell cellB2 = actRow2.getCell(1);
            assertEquals(valueA2, cellA2.getStringCellValue());
            assertEquals(valueB2, cellB2.getStringCellValue());

            assertThat(actSheet.getNumMergedRegions(), equalTo(1));
            CellRangeAddress mergedRegion = actSheet.getMergedRegion(0);
            assertThat(mergedRegion.formatAsString(), equalTo("A1:B2"));
        }
    }
}
