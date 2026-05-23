package parquet.avro.datatype;

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

import static org.assertj.core.api.Assertions.assertThat;


class LogicalDataTypesTest {

    @Test
    void logicalDataTypes() throws IOException {
        var conf = new Configuration();

        var stringFieldName = "string_field";
        var schema = SchemaBuilder.record("all_data_types")
                .fields()
                .name(stringFieldName).type().stringType().noDefault()
                .endRecord();
        var path = new Path(FileUtil.createAbsentTempFileDeleteOnExit(".parquet").getPath());

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

        assertThat(actRecord.get(stringFieldName).toString()).isEqualTo(expStringValue);
        assertThat(actRecord).isEqualTo(expRecord);
    }

}