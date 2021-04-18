package excel.create;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FontColor {

    @Test
    public void color() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet();
            var row = sheet.createRow(0);
            var cellRed = row.createCell(0);
            var cellGreen = row.createCell(1);
            cellRed.setCellValue("Red");
            cellGreen.setCellValue("Green");

            //With IndexedColors
            var fontRed = wb.createFont();
            fontRed.setColor(IndexedColors.RED.getIndex());
            var styleRed = wb.createCellStyle();
            styleRed.setFont(fontRed);
            cellRed.setCellStyle(styleRed);

            //With RGB
            var fontGreen = wb.createFont();
            byte[] rgb = new byte[]{(byte) 0, (byte) 128, (byte) 0};
            fontGreen.setColor(new XSSFColor(rgb, null));
            var styleGreen = wb.createCellStyle();
            styleGreen.setFont(fontGreen);
            cellGreen.setCellStyle(styleGreen);

            var file = Files.createTempFile(FontColor.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            wb.write(new FileOutputStream(file));
        }
    }
}
