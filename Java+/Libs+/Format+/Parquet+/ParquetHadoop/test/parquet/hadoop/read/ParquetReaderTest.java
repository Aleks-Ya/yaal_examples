package parquet.hadoop.read;


import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.junit.jupiter.api.Test;
import parquet.hadoop.Helper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Read a Parquet file with {@link ParquetReader}.
 */
class ParquetReaderTest {
    @Test
    void read() throws IOException {
        var conf = new Configuration();
        var field = "mystring";
        var value = "the string";
        var path = Helper.writeParquetFile(conf, field, value);
        try (var reader = ParquetReader.builder(new GroupReadSupport(), path).withConf(conf).build()) {
            var group = reader.read();
            assertThat(group).isNotNull();
            assertThat(group.getString(field, 0)).isEqualTo(value);
        }
    }
}