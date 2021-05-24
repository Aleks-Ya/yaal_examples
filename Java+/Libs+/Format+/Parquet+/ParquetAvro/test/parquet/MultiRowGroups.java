package parquet;

import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Generate a Parquet file having >1 row groups.
 */
class MultiRowGroups {

    @Test
    void write() throws IOException {
        var groupSize = 1024 * 1024;

        var conf = new Configuration();

        var idField = "id";
        var numberField = "number";
        var schema = SchemaBuilder.record("MultiRowGroups")
                .fields()
                .name(idField).type().stringType().noDefault()
                .name(numberField).type().longType().noDefault()
                .endRecord();
        var path = new Path(FileUtil.createAbsentTempFileDeleteOnExit(".parquet").getPath());
        var outputFile = HadoopOutputFile.fromPath(path, conf);
        var recordNum = 25000;
        try (var writer = AvroParquetWriter
                .<GenericRecord>builder(outputFile)
                .withSchema(schema)
                .withConf(conf)
                .withRowGroupSize(groupSize)
                .build()) {
            var random = new Random();
            for (int i = 0; i < recordNum; i++) {
                var id = UUID.randomUUID().toString();
                var number = random.nextLong();
                var record = new GenericRecordBuilder(schema)
                        .set(idField, id)
                        .set(numberField, number)
                        .build();
                writer.write(record);
            }
        }

        var rowGroupNumber = getRowGroupNumber(conf, path);
        assertThat(rowGroupNumber, equalTo(2));
    }

    private static int getRowGroupNumber(Configuration conf, Path path) throws IOException {
        var inputFile = HadoopInputFile.fromPath(path, conf);
        try (var r = ParquetFileReader.open(inputFile)) {
            return r.getRowGroups().size();
        }
    }
}