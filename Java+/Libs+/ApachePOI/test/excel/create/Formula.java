package excel.create;

import org.apache.poi.ss.formula.WorkbookEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static java.lang.String.format;

public class Formula {

    @Test
    public void createFormula() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet("my sheet");
            var row = sheet.createRow(0);
            var cell0 = row.createCell(0);
            var cell1 = row.createCell(1);
            var cell2 = row.createCell(2);
            cell0.setCellValue(123);
            cell1.setCellValue(10.5);
            cell2.setCellFormula(format("SUM(%s:%s)", cell0.getAddress(), cell1.getAddress()));

            wb.getCreationHelper().createFormulaEvaluator().evaluateAll();

            var file = Files.createTempFile(Formula.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            wb.write(new FileOutputStream(file));
        }
    }

    @Test
    public void listSupportedFunctions() {
        System.out.println("Supported functions: " + WorkbookEvaluator.getSupportedFunctionNames());
    }

}
