package excel.create;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class RichTextStringTest {

    @Test
    void richText() throws IOException {
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
            assertThat(cell.getCellType()).isEqualTo(CellType.STRING);

            var file = Files.createTempFile(RichTextStringTest.class.getSimpleName(), ".xls").toFile();
            System.out.println("Workbook file: " + file.getAbsolutePath());
            wb.write(new FileOutputStream(file));
        }
    }
}
