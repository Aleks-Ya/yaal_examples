package parquet.hadoop.write;


import org.apache.hadoop.conf.Configuration;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.schema.Types;
import org.junit.jupiter.api.Test;
import parquet.hadoop.Helper;

import java.io.IOException;

import static org.apache.parquet.schema.LogicalTypeAnnotation.stringType;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.assertj.core.api.Assertions.assertThat;
import static parquet.hadoop.Helper.createAbsentTempParquetPath;

/**
 * Write a Parquet file without any rows.
 */
class EmptyParquetFileTest {
    @Test
    void writeEmptyParquetFile() throws IOException {
        var conf = new Configuration();
        var field = "mystring";
        var path = createAbsentTempParquetPath();
        var schema = Types.buildMessage()
                .required(BINARY).as(stringType()).named(field)
                .named("myrecord");
        ExampleParquetWriter.builder(path).withConf(conf).withType(schema).build().close();
        assertThat(Helper.parquetToString(conf, path)).isEmpty();
    }

}