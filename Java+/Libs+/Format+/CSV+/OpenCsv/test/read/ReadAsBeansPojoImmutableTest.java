package read;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.FileReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ReadAsBeansPojoImmutableTest {
    @Test
    @Disabled("Does not work")
    void parse() throws IOException {
        var csv = ResourceUtil.resourceToFile(getClass(), "data.csv");
        CsvToBean<PersonPojoImmutable> csvToBean = new CsvToBeanBuilder(new FileReader(csv))
                .withType(PersonPojoImmutable.class).build();
        var beans = csvToBean.parse();
        assertThat(beans).containsExactly(
                new PersonPojoImmutable(1, "John", "M"),
                new PersonPojoImmutable(2, "Mary", "F"),
                new PersonPojoImmutable(3, "Mark", "M"));
    }

    @Test
    @Disabled("Does not work")
    void parseWithMappingStrategy() throws IOException {
        var csv = ResourceUtil.resourceToFile(getClass(), "data.csv");
        var mappingStrategy = new ColumnPositionMappingStrategy<PersonPojoImmutable>();
        mappingStrategy.setType(PersonPojoImmutable.class);
        mappingStrategy.setColumnMapping("id", "name", "gender");

        CsvToBean<PersonPojoImmutable> csvToBean = new CsvToBeanBuilder(new FileReader(csv))
                .withMappingStrategy(mappingStrategy)
                .build();

        var beans = csvToBean.parse();
        assertThat(beans).containsExactly(
                new PersonPojoImmutable(1, "John", "M"),
                new PersonPojoImmutable(2, "Mary", "F"),
                new PersonPojoImmutable(3, "Mark", "M"));
    }
}

