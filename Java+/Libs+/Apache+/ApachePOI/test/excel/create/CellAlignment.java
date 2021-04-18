package excel.create;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class CellAlignment {

    @Test
    @Disabled("alignment works strange")
    public void merge() throws IOException {
        var file = Files.createTempFile(CellAlignment.class.getSimpleName(), ".xls").toFile();
        System.out.println("Workbook file: " + file.getAbsolutePath());

        var valueA1 = "A A";
        var valueB1 = "B B";
        var valueC1 = "C C";
        var valueD1 = "D D";

        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet("my sheet");
            var row = sheet.createRow(0);
            var cellA1 = createCell(wb, row, 0, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, valueA1);
            var cellB1 = createCell(wb, row, 1, HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, valueB1);
            var cellC1 = createCell(wb, row, 2, HorizontalAlignment.LEFT, VerticalAlignment.TOP, valueC1);
            var cellD1 = createCell(wb, row, 3, HorizontalAlignment.DISTRIBUTED, VerticalAlignment.DISTRIBUTED, valueD1);
            cellA1.setCellValue(valueA1);
            cellB1.setCellValue(valueB1);
            cellC1.setCellValue(valueC1);
            cellD1.setCellValue(valueD1);

//            CellUtil.setAlignment(cellA1, HorizontalAlignment.RIGHT);
//            CellUtil.setAlignment(cellB1, HorizontalAlignment.CENTER);


//            setAlignment(cellA1, HorizontalAlignment.LEFT);
//            setAlignment(cellB1, HorizontalAlignment.CENTER);
//            setAlignment(cellC1, HorizontalAlignment.RIGHT);
//            setAlignment(cellD1, HorizontalAlignment.DISTRIBUTED);

            wb.write(new FileOutputStream(file));
        }

        try (var actWb = new XSSFWorkbook(new FileInputStream(file))) {
            var sheet = actWb.getSheetAt(0);
            var row = sheet.getRow(0);
            var cellA1 = row.getCell(0);
            var cellB1 = row.getCell(1);
            var cellC1 = row.getCell(2);
            var cellD1 = row.getCell(3);
            assertEquals(valueA1, cellA1.getStringCellValue());
            assertEquals(valueB1, cellB1.getStringCellValue());
            assertEquals(valueC1, cellC1.getStringCellValue());
            assertEquals(valueD1, cellD1.getStringCellValue());
        }
    }

    private static void setAlignment(Cell cell, HorizontalAlignment alignment) {
//        CellStyle style = cell.getCellStyle();
//        CellStyle style2 = cell.getSheet().getWorkbook().createCellStyle();
//        style2.cloneStyleFrom(style);
//        style2.setAlignment(alignment);
//        style2.setVerticalAlignment(VerticalAlignment.TOP);
//        System.out.println("Style: " + style);
//        cell.setCellStyle(style2);

//        CellStyle style = cell.getCellStyle();
//        style.cloneStyleFrom(style);
//        style.setAlignment(alignment);
//        style.setVerticalAlignment(VerticalAlignment.TOP);
//        System.out.println("Style: " + style);
//        cell.setCellStyle(null);
//        cell.setCellStyle(style);

//        CellStyle style = cell.getCellStyle();
//        style.cloneStyleFrom(style);
//        style.setAlignment(alignment);
//        style.setVerticalAlignment(VerticalAlignment.TOP);
//        System.out.println("Style: " + style);
//        cell.setCellStyle(null);
//        cell.setCellStyle(style);

        CellUtil.setAlignment(cell, alignment);

    }

    /**
     * CellStyle must be set before creating another cell.
     */
    private static Cell createCell(Workbook wb, Row row, int column, HorizontalAlignment alignment,
                                   VerticalAlignment verticalAlignment, String value) {
        var cell = row.createCell(column);
        cell.setCellValue(value);
//        CellStyle style = row.getSheet().getWorkbook().createCellStyle();
        var style = wb.createCellStyle();
        style.setAlignment(alignment);
        style.setVerticalAlignment(verticalAlignment);
        cell.setCellStyle(style);
        return cell;
    }
}
