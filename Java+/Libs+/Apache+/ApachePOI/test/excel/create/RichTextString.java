package excel.create;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RichTextString {

    @Test
    public void richText() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet();
            var row = sheet.createRow(0);
            var cell = row.createCell(0);

            var font = wb.createFont();
            font.setBold(true);
            font.setUnderline(Font.U_DOUBLE);

            var richTextString = new XSSFRichTextString("RichTextString");
            richTextString.applyFont(font);// Must be before setCellValue()
            cell.setCellValue(richTextString); //Must be after applyFont()
            assertThat(cell.getCellType(), equalTo(CellType.STRING));

            var file = Files.createTempFile(RichTextString.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            wb.write(new FileOutputStream(file));
        }
    }
}
