package parquet;

import org.apache.avro.Schema;
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
import util.ResourceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadWriteTest {

    @Test
    void writeRead() throws IOException {
        var conf = new Configuration();
        conf.setBoolean(AvroReadSupport.AVRO_COMPATIBILITY, true);
        conf.setBoolean("parquet.avro.add-list-element-records", false);
        conf.setBoolean("parquet.avro.write-old-list-structure", false);

        var schema = new Schema.Parser().parse(ResourceUtil.resourceToInputStream("array.avsc"));

        var file = File.createTempFile(getClass().getSimpleName(), ".tmp");
        file.deleteOnExit();
        assertTrue(file.delete());
        var path = new Path(file.getPath());

        var outputFile = HadoopOutputFile.fromPath(path, conf);
        var writer = AvroParquetWriter
                .<GenericRecord>builder(outputFile)
                .withSchema(schema)
                .withConf(conf)
                .build();

        // Write a record with an empty array.
        var emptyArray = new ArrayList<Integer>();
        var record = new GenericRecordBuilder(schema).set("myarray", emptyArray).build();
        writer.write(record);
        writer.close();

        var inputFile = HadoopInputFile.fromPath(path, conf);
        var reader = AvroParquetReader.<GenericRecord>builder(inputFile).build();
        var nextRecord = reader.read();

        assertNotNull(nextRecord);
        assertEquals(emptyArray, nextRecord.get("myarray"));
    }
}