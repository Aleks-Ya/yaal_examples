package parquet;

import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

/**
 * Activating dictionary and compression to reduce Parquet file size.
 */
class DictionaryAndCompression {

    @Test
    void dictionary() throws IOException {
        long fileSize1 = writeParquetFile(false, CompressionCodecName.UNCOMPRESSED);
        assertThat(fileSize1, allOf(greaterThan(500_000L), lessThan(510_000L)));

        long fileSize2 = writeParquetFile(true, CompressionCodecName.UNCOMPRESSED);
        assertThat(fileSize2, allOf(greaterThan(7_000L), lessThan(8_000L)));

        long fileSize3 = writeParquetFile(false, CompressionCodecName.SNAPPY);
        assertThat(fileSize3, allOf(greaterThan(70_000L), lessThan(72_000L)));

        long fileSize4 = writeParquetFile(true, CompressionCodecName.SNAPPY);
        assertThat(fileSize4, allOf(greaterThan(7_000L), lessThan(8_000L)));
    }

    private long writeParquetFile(boolean dictionaryEnabled, CompressionCodecName compressionCodecName) throws IOException {
        var groupSize = 1024 * 1024;
        var conf = new Configuration();
        var idField = "id";
        var numberField = "number";
        var schema = SchemaBuilder.record("DictionaryAndCompression")
                .fields()
                .name(idField).type().stringType().noDefault()
                .name(numberField).type().longType().noDefault()
                .endRecord();
        var file = FileUtil.createAbsentTempFileDeleteOnExit(".parquet");
        var path = new Path(file.getPath());
        var outputFile = HadoopOutputFile.fromPath(path, conf);
        var recordNum = 25000;
        try (var writer = AvroParquetWriter
                .<GenericRecord>builder(outputFile)
                .withSchema(schema)
                .withConf(conf)
                .withRowGroupSize(groupSize)
                .withDictionaryEncoding(dictionaryEnabled)
                .withCompressionCodec(compressionCodecName)
                .build()) {
            var random = new Random();
            for (int i = 0; i < recordNum; i++) {
                var id = random.nextInt() > 0 ? "the_id_1" : "the_id_2";
                var number = random.nextInt() > 0 ? 33 : 77;
                var record = new GenericRecordBuilder(schema)
                        .set(idField, id)
                        .set(numberField, number)
                        .build();
                writer.write(record);
            }
        }
        return Files.size(file.toPath());
    }
}