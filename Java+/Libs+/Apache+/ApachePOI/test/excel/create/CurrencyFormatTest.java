package excel.create;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

class CurrencyFormatTest {
    private static void addUsd(Sheet sheet) {
        var workbook = sheet.getWorkbook();

        var creationHelper = workbook.getCreationHelper();
        var format = creationHelper.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(8));
        var cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);

        var row = sheet.createRow(0);

        var cell0 = row.createCell(0);
        cell0.setCellValue(1234.56);
        cell0.setCellStyle(cellStyle);

        var cell1 = row.createCell(1);
        cell1.setCellValue(-1234.56);
        cell1.setCellStyle(cellStyle);
    }

    private static void addRur(Sheet sheet) {
        var workbook = sheet.getWorkbook();

        var creationHelper = workbook.getCreationHelper();
        var format = creationHelper.createDataFormat().getFormat("₽#,##0.00_);[Red]₽#,##0.00");
        var cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);

        var row = sheet.createRow(1);

        var cell0 = row.createCell(0);
        cell0.setCellValue(1234.56);
        cell0.setCellStyle(cellStyle);

        var cell1 = row.createCell(1);
        cell1.setCellValue(-1234.56);
        cell1.setCellStyle(cellStyle);
    }

    private static void addEur(Sheet sheet) {
        var workbook = sheet.getWorkbook();

        var creationHelper = workbook.getCreationHelper();
        var format = creationHelper.createDataFormat().getFormat("€#,##0.00_);[Red]€#,##0.00");
        var cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);

        var row = sheet.createRow(2);

        var cell0 = row.createCell(0);
        cell0.setCellValue(1234.56);
        cell0.setCellStyle(cellStyle);

        var cell1 = row.createCell(1);
        cell1.setCellValue(-1234.56);
        cell1.setCellStyle(cellStyle);
    }

    @Test
    void currency() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet();

            addUsd(sheet);
            addRur(sheet);
            addEur(sheet);

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            var file = Files.createTempFile(CurrencyFormatTest.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            wb.write(new FileOutputStream(file));
        }
    }
}
