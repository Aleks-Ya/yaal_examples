package parquet;

import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataTypesTest {

    @Test
    void primitiveDataTypes() throws IOException {
        var conf = new Configuration();

        var booleanFieldName = "boolean_field";
        var int32FieldName = "int32_field";
        var floatFieldName = "float_field";
        var doubleFieldName = "double_field";
        var byteArrayFieldName = "byte_array_field";
        var schema = SchemaBuilder.record("all_data_types")
                .fields()
                .name(booleanFieldName).type().booleanType().noDefault()
                .name(int32FieldName).type().intType().noDefault()
                .name(floatFieldName).type().floatType().noDefault()
                .name(doubleFieldName).type().doubleType().noDefault()
                .name(byteArrayFieldName).type().array().items().intType().noDefault()
                .endRecord();

        var path = new Path(FileUtil.createAbsentTempFile(".parquet").getPath());

        var expByteArrayValue = List.of(1, 3, 5);
        var expBooleanValue = true;
        var expInt32Value = 42;
        var expFloatValue = 3.14F;
        var expDoubleValue = 1.5D;
        var expRecord = new GenericRecordBuilder(schema)
                .set(booleanFieldName, expBooleanValue)
                .set(int32FieldName, expInt32Value)
                .set(floatFieldName, expFloatValue)
                .set(doubleFieldName, expDoubleValue)
                .set(byteArrayFieldName, expByteArrayValue)
                .build();

        // Write Parquet file
        var outputFile = HadoopOutputFile.fromPath(path, conf);
        var writer = AvroParquetWriter
                .<GenericRecord>builder(outputFile)
                .withSchema(schema)
                .withConf(conf)
                .build();
        writer.write(expRecord);
        writer.close();

        // Read Parquet file
        var inputFile = HadoopInputFile.fromPath(path, conf);
        var reader = AvroParquetReader.<GenericRecord>builder(inputFile).build();
        var actRecord = reader.read();

        //noinspection ConstantConditions
        assertEquals(expBooleanValue, actRecord.get(booleanFieldName));
        assertEquals(expInt32Value, actRecord.get(int32FieldName));
        assertEquals(expFloatValue, actRecord.get(floatFieldName));
        assertEquals(expDoubleValue, actRecord.get(doubleFieldName));
        assertEquals(expByteArrayValue, actRecord.get(byteArrayFieldName));
        assertEquals(expRecord, actRecord);
    }

    @Test
    void logicalDataTypes() throws IOException {
        var conf = new Configuration();

        var stringFieldName = "string_field";
        var schema = SchemaBuilder.record("all_data_types")
                .fields()
                .name(stringFieldName).type().stringType().noDefault()
                .endRecord();
        var path = new Path(FileUtil.createAbsentTempFile(".parquet").getPath());

        var expStringValue = "abc";
        var expRecord = new GenericRecordBuilder(schema)
                .set(stringFieldName, expStringValue)
                .build();

        // Write Parquet file
        var outputFile = HadoopOutputFile.fromPath(path, conf);
        var writer = AvroParquetWriter
                .<GenericRecord>builder(outputFile)
                .withSchema(schema)
                .withConf(conf)
                .build();
        writer.write(expRecord);
        writer.close();

        // Read Parquet file
        var inputFile = HadoopInputFile.fromPath(path, conf);
        var reader = AvroParquetReader.<GenericRecord>builder(inputFile).build();
        var actRecord = reader.read();

        assertEquals(expStringValue, actRecord.get(stringFieldName).toString());
        assertEquals(expRecord, actRecord);
    }

}