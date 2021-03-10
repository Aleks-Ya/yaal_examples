package excel.create;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RowEmpty {

    @Test
    public void row() throws IOException {
        try (var wb = new XSSFWorkbook()) {
            var sheet = wb.createSheet();
            var row = sheet.createRow(0);
            assertThat(row.getFirstCellNum(), equalTo((short) -1));
            assertThat(row.getLastCellNum(), equalTo((short) -1));
        }
    }
}
