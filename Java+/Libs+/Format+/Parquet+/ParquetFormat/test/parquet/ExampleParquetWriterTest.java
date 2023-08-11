package parquet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.schema.Types;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;

import static org.apache.parquet.schema.LogicalTypeAnnotation.stringType;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.assertj.core.api.Assertions.assertThat;


class ExampleParquetWriterTest {

    @Test
    void write() throws IOException {
        var conf = new Configuration();
        var field = "mystring";
        var value = "the string";
        var path = new Path(FileUtil.createAbsentTempFileDeleteOnExit(".parquet").getPath());

        writeParquetFile(conf, path, field, value);

        try (var reader = ParquetReader.builder(new GroupReadSupport(), path).withConf(conf).build()) {
            var group = reader.read();
            assertThat(group).isNotNull();
            assertThat(group.getString(field, 0)).isEqualTo(value);
        }
    }

    private void writeParquetFile(Configuration conf, Path path, String myStringField, String expString) throws IOException {
        var schema = Types.buildMessage()
                .required(BINARY).as(stringType()).named(myStringField)
                .named("myrecord");
        var factory = new SimpleGroupFactory(schema);
        try (var writer = ExampleParquetWriter.builder(path).withConf(conf).withType(schema).build()) {
            var group = factory.newGroup().append(myStringField, expString);
            writer.write(group);
        }
    }
}