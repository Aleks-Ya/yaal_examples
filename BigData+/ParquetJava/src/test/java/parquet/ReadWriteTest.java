package parquet;

import com.google.common.io.Resources;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroReadSupport;
import org.apache.parquet.hadoop.ParquetWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReadWriteTest {


    @Test
    public void writeRead() throws IOException {
        boolean compat = true;
        Configuration testConf = new Configuration();
        testConf.setBoolean(AvroReadSupport.AVRO_COMPATIBILITY, compat);
        testConf.setBoolean("parquet.avro.add-list-element-records", false);
        testConf.setBoolean("parquet.avro.write-old-list-structure", false);

        Schema schema = new Schema.Parser().parse(
                Resources.getResource("array.avsc").openStream());

        File tmp = File.createTempFile(getClass().getSimpleName(), ".tmp");
        tmp.deleteOnExit();
        tmp.delete();
        Path file = new Path(tmp.getPath());

        ParquetWriter<GenericRecord> writer = AvroParquetWriter
                .<GenericRecord>builder(file)
                .withSchema(schema)
                .withConf(testConf)
                .build();

        // Write a record with an empty array.
        List<Integer> emptyArray = new ArrayList<>();
        GenericData.Record record = new GenericRecordBuilder(schema)
                .set("myarray", emptyArray).build();
        writer.write(record);
        writer.close();

        AvroParquetReader<GenericRecord> reader = new AvroParquetReader<>(testConf, file);
        GenericRecord nextRecord = reader.read();

        assertNotNull(nextRecord);
        assertEquals(emptyArray, nextRecord.get("myarray"));
    }
}