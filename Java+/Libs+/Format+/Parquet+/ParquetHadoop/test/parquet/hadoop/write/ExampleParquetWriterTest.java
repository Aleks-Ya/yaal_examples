package parquet.hadoop.write;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.schema.Types;
import org.junit.jupiter.api.Test;
import parquet.hadoop.Helper;
import util.FileUtil;

import java.io.IOException;

import static org.apache.parquet.schema.LogicalTypeAnnotation.stringType;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Write a Parquet file with {@link ExampleParquetWriter}.
 */
class ExampleParquetWriterTest {
    @Test
    void write() throws IOException {
        var conf = new Configuration();
        var field = "mystring";
        var value = "the string";
        var path = new Path(FileUtil.createAbsentTempFileDeleteOnExit(".parquet").getPath());

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

}