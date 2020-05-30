package excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class CurrencyFormat {

    @Test
    public void currency() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        addUsd(sheet);
        addRur(sheet);
        addEur(sheet);

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        File file = Files.createTempFile(CurrencyFormat.class.getSimpleName(), ".xls").toFile();
        System.out.println("Workbook file: " + file.getAbsolutePath());
        workbook.write(new FileOutputStream(file));
        workbook.close();
    }

    private static void addUsd(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();

        CreationHelper creationHelper = workbook.getCreationHelper();
        short format = creationHelper.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(8));
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);

        Row row = sheet.createRow(0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue(1234.56);
        cell0.setCellStyle(cellStyle);

        Cell cell1 = row.createCell(1);
        cell1.setCellValue(-1234.56);
        cell1.setCellStyle(cellStyle);
    }

    private static void addRur(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();

        CreationHelper creationHelper = workbook.getCreationHelper();
        short format = creationHelper.createDataFormat().getFormat("₽#,##0.00_);[Red]₽#,##0.00");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);

        Row row = sheet.createRow(1);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue(1234.56);
        cell0.setCellStyle(cellStyle);

        Cell cell1 = row.createCell(1);
        cell1.setCellValue(-1234.56);
        cell1.setCellStyle(cellStyle);
    }

    private static void addEur(Sheet sheet) {
        Workbook workbook = sheet.getWorkbook();

        CreationHelper creationHelper = workbook.getCreationHelper();
        short format = creationHelper.createDataFormat().getFormat("€#,##0.00_);[Red]€#,##0.00");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);

        Row row = sheet.createRow(2);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue(1234.56);
        cell0.setCellStyle(cellStyle);

        Cell cell1 = row.createCell(1);
        cell1.setCellValue(-1234.56);
        cell1.setCellStyle(cellStyle);
    }
}
