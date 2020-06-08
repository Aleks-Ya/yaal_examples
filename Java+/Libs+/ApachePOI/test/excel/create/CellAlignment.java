package excel.create;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class CellAlignment {

    @Test
    @Ignore("alignment works strange")
    public void merge() throws IOException {
        File file = Files.createTempFile(CellAlignment.class.getSimpleName(), ".xls").toFile();
        System.out.println("Workbook file: " + file.getAbsolutePath());

        String valueA1 = "A A";
        String valueB1 = "B B";
        String valueC1 = "C C";
        String valueD1 = "D D";

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("my sheet");
            Row row = sheet.createRow(0);
            Cell cellA1 = createCell(workbook, row, 0, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, valueA1);
            Cell cellB1 = createCell(workbook, row, 1, HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, valueB1);
            Cell cellC1 = createCell(workbook, row, 2, HorizontalAlignment.LEFT, VerticalAlignment.TOP, valueC1);
            Cell cellD1 = createCell(workbook, row, 3, HorizontalAlignment.DISTRIBUTED, VerticalAlignment.DISTRIBUTED, valueD1);
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

            workbook.write(new FileOutputStream(file));
        }

        try (Workbook actWb = new XSSFWorkbook(new FileInputStream(file))) {
            Sheet sheet = actWb.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell cellA1 = row.getCell(0);
            Cell cellB1 = row.getCell(1);
            Cell cellC1 = row.getCell(2);
            Cell cellD1 = row.getCell(3);
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
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
//        CellStyle style = row.getSheet().getWorkbook().createCellStyle();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(alignment);
        style.setVerticalAlignment(verticalAlignment);
        cell.setCellStyle(style);
        return cell;
    }
}
