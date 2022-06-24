package excel.create;

import org.apache.poi.ss.usermodel.FontFamily;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class FontEqualsTest {
    @Test
    void fontEqual() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var font1 = wb.createFont();
            font1.setFamily(FontFamily.MODERN);

            var font2 = wb.createFont();
            font2.setFamily(FontFamily.MODERN);

            assertThat(font1).isEqualTo(font2);
        }
    }
}
