package read;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReadAsBeansPojoTest {
    @Test
    void parse() throws IOException {
        var csv = ResourceUtil.resourceToFile(getClass(), "data.csv");
        CsvToBean<PersonPojo> csvToBean = new CsvToBeanBuilder(new FileReader(csv))
                .withType(PersonPojo.class).build();
        List<PersonPojo> beans = csvToBean.parse();
        assertThat(beans).containsExactly(
                new PersonPojo(1, "John", "M"),
                new PersonPojo(2, "Mary", "F"),
                new PersonPojo(3, "Mark", "M"));
    }
}

