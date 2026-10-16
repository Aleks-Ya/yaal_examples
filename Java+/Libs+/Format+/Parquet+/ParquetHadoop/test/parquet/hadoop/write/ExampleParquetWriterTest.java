package parquet.hadoop.write;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.schema.Types;
import org.junit.jupiter.api.Test;
import parquet.hadoop.Helper;

import java.io.IOException;
import java.util.Map;

import static org.apache.parquet.schema.LogicalTypeAnnotation.stringType;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.assertj.core.api.Assertions.assertThat;
import static parquet.hadoop.Helper.createAbsentTempParquetPath;
import static parquet.hadoop.Helper.readFileMetaData;

/**
 * Write a Parquet file with {@link ExampleParquetWriter}.
 */
class ExampleParquetWriterTest {
    private final Configuration conf = new Configuration();
    private final String field = "mystring";
    private final Path path = createAbsentTempParquetPath();

    @Test
    void writeEmptyParquetFile() throws IOException {
        var schema = Types.buildMessage()
                .required(BINARY).as(stringType()).named(field)
                .named("myrecord");
        ExampleParquetWriter.builder(path).withConf(conf).withType(schema).build().close();
        assertThat(Helper.parquetToString(conf, path)).isEmpty();
    }

    @Test
    void write() throws IOException {
        var value = "the string";
        var schema = Types.buildMessage()
                .required(BINARY).as(stringType()).named(field)
                .named("myrecord");
        var factory = new SimpleGroupFactory(schema);
        try (var writer = ExampleParquetWriter.builder(path).withConf(conf).withType(schema).build()) {
            var group1 = factory.newGroup().append(field, value);
            writer.write(group1);
        }
        assertThat(Helper.parquetToString(conf, path)).isEqualTo("mystring: the string\n");
    }

    @Test
    void writeCustomMetadata() throws IOException {
        var value = "the string";
        var schema = Types.buildMessage()
                .required(BINARY).as(stringType()).named(field)
                .named("myrecord");
        var factory = new SimpleGroupFactory(schema);
        try (var writer = ExampleParquetWriter.builder(path).withConf(conf).withType(schema)
                .withExtraMetaData(Map.of("color", "green"))
                .build()) {
            var group = factory.newGroup().append(field, value);
            writer.write(group);
        }
        var actMetaData = readFileMetaData(conf, path);
        assertThat(actMetaData.getKeyValueMetaData()).containsEntry("color", "green");
    }

}