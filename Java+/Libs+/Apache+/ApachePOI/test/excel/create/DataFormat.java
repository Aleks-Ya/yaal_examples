package excel.create;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DataFormat {

    @Test
    public void dataFormats() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet();
            var row = sheet.createRow(0);

            var cellString = createCell(row, "@");
            cellString.setCellValue("abc");

            var cellInteger = createCell(row, "0");
            cellInteger.setCellValue(222);

            var cellDecimal = createCell(row, "0.00");
            cellDecimal.setCellValue(333.33);

            var cellPercent = createCell(row, "0.00%");
            var percent = 66.66 / 100;
            cellPercent.setCellValue(percent);

            var cellUsd = createCell(row, "[$$-409]#,##0.00;[RED]-[$$-409]#,##0.00");
            cellUsd.setCellValue(-5555.55);

            var cellRur = createCell(row, "[$₽-419]#,##0.00;[RED]-[$₽-419]#,##0.00");
            cellRur.setCellValue(-8888.88);

            var cellEuro = createCell(row, "[$€-1809]#,##0.00;[RED]-[$€-1809]#,##0.00");
            cellEuro.setCellValue(-9999.99);

            var cellDate = createCell(row, "DD-MM-YYYY");
            cellDate.setCellValue(LocalDate.of(2021, 10, 25));

            var cellDateTime = createCell(row, "DD-MM-YYYY HH:MM:SS");
            cellDateTime.setCellValue(LocalDateTime.of(2021, 10, 25, 13, 45, 50));

            autoSizeColumns(row);

            var file = Files.createTempFile(DataFormat.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            wb.write(new FileOutputStream(file));
        }
    }

    private static void autoSizeColumns(XSSFRow row) {
        for (var columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
            row.getSheet().autoSizeColumn(columnIndex);
        }
    }

    private XSSFCell createCell(XSSFRow row, String format) {
        var columnIndex = row.getLastCellNum() >= 0 ? row.getLastCellNum() : 0;
        var wb = row.getSheet().getWorkbook();
        var style = createStyleWithFormat(wb, format);
        var cell = row.createCell(columnIndex);
        cell.setCellStyle(style);
        return cell;
    }

    private static XSSFCellStyle createStyleWithFormat(XSSFWorkbook wb, String format) {
        var createHelper = wb.getCreationHelper();
        var formatIndex = createHelper.createDataFormat().getFormat(format);
        var style = wb.createCellStyle();
        style.setDataFormat(formatIndex);
        return style;
    }
}
