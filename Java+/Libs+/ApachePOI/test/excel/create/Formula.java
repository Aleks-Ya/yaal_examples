package excel.create;

import org.apache.poi.ss.formula.WorkbookEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.String.format;

public class Formula {

    @Test
    public void createFormula() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("my sheet");
            Row row = sheet.createRow(0);
            Cell cell0 = row.createCell(0);
            Cell cell1 = row.createCell(1);
            Cell cell2 = row.createCell(2);
            cell0.setCellValue(123);
            cell1.setCellValue(10.5);
            cell2.setCellFormula(format("SUM(%s:%s)", cell0.getAddress(), cell1.getAddress()));

            workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();

            File file = Files.createTempFile(Formula.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            workbook.write(new FileOutputStream(file));
        }
    }

    @Test
    public void listSupportedFunctions() {
        System.out.println("Supported functions: " + WorkbookEvaluator.getSupportedFunctionNames());
    }

}
