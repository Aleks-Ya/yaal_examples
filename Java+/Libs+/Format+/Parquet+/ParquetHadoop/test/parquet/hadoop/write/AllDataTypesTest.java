package parquet.hadoop.write;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.io.api.Binary;
import org.apache.parquet.schema.Types;
import org.junit.jupiter.api.Test;
import parquet.hadoop.Helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.apache.parquet.schema.LogicalTypeAnnotation.decimalType;
import static org.apache.parquet.schema.LogicalTypeAnnotation.stringType;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.FIXED_LEN_BYTE_ARRAY;
import static org.assertj.core.api.Assertions.assertThat;
import static parquet.hadoop.Helper.createAbsentTempParquetPath;

class AllDataTypesTest {
    private final Configuration conf = new Configuration();
    private final Path path = createAbsentTempParquetPath();

    @Test
    void writeString() throws IOException {
        var stringField = "StringField";
        var stringValue = "the string";
        var schema = Types.buildMessage()
                .required(BINARY).as(stringType()).named(stringField)
                .named("AllDataTypes");
        var factory = new SimpleGroupFactory(schema);
        try (var writer = ExampleParquetWriter.builder(path).withConf(conf).withType(schema).build()) {
            var group = factory.newGroup()
                    .append(stringField, stringValue);
            writer.write(group);
        }

        var group = Helper.readGroup(conf, path);
        assertThat(group).isNotNull();
        assertThat(group.getString(stringField, 0)).isEqualTo(stringValue);
    }


    @Test
    void writeBigDecimal() throws IOException {
        var decimalField36 = "DecimalField36";
        var value36 = new BigDecimal("100.236");
        var scale3 = 3;
        var precision6 = 6;
        var binaryValue36 = Binary.fromConstantByteArray(value36.unscaledValue().toByteArray());

        var decimalField14 = "DecimalField14";
        var value14 = new BigDecimal("234.5");
        var scale1 = 1;
        var precision4 = 4;
        var binaryValue14 = Binary.fromConstantByteArray(value14.unscaledValue().toByteArray());

        var schema = Types.buildMessage()
                .required(FIXED_LEN_BYTE_ARRAY).length(binaryValue36.length()).as(decimalType(scale3, precision6)).named(decimalField36)
                .required(FIXED_LEN_BYTE_ARRAY).length(binaryValue14.length()).as(decimalType(scale1, precision4)).named(decimalField14)
                .named("AllDataTypes");
        var factory = new SimpleGroupFactory(schema);
        try (var writer = ExampleParquetWriter.builder(path).withConf(conf).withType(schema).build()) {
            var group = factory.newGroup()
                    .append(decimalField36, binaryValue36)
                    .append(decimalField14, binaryValue14);
            writer.write(group);
        }

        var group = Helper.readGroup(conf, path);
        var actDecimal = new BigDecimal(new BigInteger(group.getBinary(decimalField36, 0).getBytes()), scale3);
        assertThat(actDecimal).isEqualTo(value36);
        var actDecimal14 = new BigDecimal(new BigInteger(group.getBinary(decimalField14, 0).getBytes()), scale1);
        assertThat(actDecimal14).isEqualTo(value14);
    }

}