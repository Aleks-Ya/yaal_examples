package excel.create;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RowEmptyTest {
    @Test
    void row() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet();
            var row = sheet.createRow(0);
            assertThat(row.getFirstCellNum()).isEqualTo((short) -1);
            assertThat(row.getLastCellNum()).isEqualTo((short) -1);
        }
    }
}
