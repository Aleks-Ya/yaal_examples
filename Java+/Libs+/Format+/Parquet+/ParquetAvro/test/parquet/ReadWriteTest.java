package parquet;

import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadWriteTest {

    @Test
    void writeRead() throws IOException {
        var conf = new Configuration();
        conf.setBoolean(AvroReadSupport.AVRO_COMPATIBILITY, true);
        conf.setBoolean("parquet.avro.add-list-element-records", false);
        conf.setBoolean("parquet.avro.write-old-list-structure", false);

        var myArrayField = "myarray";
        var schema = SchemaBuilder.record("myrecord")
                .fields()
                .name(myArrayField).type().array().items().intType().noDefault()
                .endRecord();

        var parquetFile = File.createTempFile(getClass().getSimpleName(), ".parquet");
        parquetFile.deleteOnExit();
        assertTrue(parquetFile.delete());
        var path = new Path(parquetFile.getPath());

        var expArray = List.of(1, 3, 5);
        var expRecord = new GenericRecordBuilder(schema).set(myArrayField, expArray).build();

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

        assertEquals(expArray, actRecord.get(myArrayField));
        assertEquals(expRecord, actRecord);
    }
}