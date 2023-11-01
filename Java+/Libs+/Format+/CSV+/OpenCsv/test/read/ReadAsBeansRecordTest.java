package read;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ReadAsBeansRecordTest {
    @Test
    @Disabled("Does not work")
    void parse() throws IOException {
        var csv = ResourceUtil.resourceToFile(getClass(), "data.csv");
        CsvToBean<PersonRecord> csvToBean = new CsvToBeanBuilder(new FileReader(csv))
                .withType(PersonRecord.class).build();
        List<PersonRecord> beans = csvToBean.parse();
        assertThat(beans).containsExactly(
                new PersonRecord(1, "John", "M"),
                new PersonRecord(2, "Mary", "F"),
                new PersonRecord(3, "Mark", "M"));
    }
}

