package excel.create;

import org.apache.poi.ss.usermodel.FontFamily;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FontEquals {

    @Test
    public void fontEqual() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var font1 = wb.createFont();
            font1.setFamily(FontFamily.MODERN);

            var font2 = wb.createFont();
            font2.setFamily(FontFamily.MODERN);

            assertThat(font1, equalTo(font2));
        }
    }
}
