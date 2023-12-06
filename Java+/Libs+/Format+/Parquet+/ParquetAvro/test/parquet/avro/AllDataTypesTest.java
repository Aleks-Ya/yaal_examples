package parquet.avro;

import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericData;
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
import util.FileUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static util.ResourceUtil.resourceToFile;

class AllDataTypesTest {
    @Test
    void writeRead() throws IOException {
        // Write Parquet file
        var schema = new Parser().parse(resourceToFile(getClass(), "AllDataTypesTest.avsc"));

        var recordSchema = schema.getField("recordField").schema();
        GenericRecord record = new GenericData.Record(recordSchema);
        record.put("nestedIntField", 25);
        record.put("nestedStringField", "Mary");

        var expRecord = new GenericRecordBuilder(schema)
                .set("nullField", null)
                .set("booleanField", true)
                .set("intField", 7)
                .set("longField", Integer.MAX_VALUE + 1L)
                .set("floatField", 1.5F)
                .set("doubleField", 2.6D)
                .set("stringField", "hello")
                .set("arrayField", List.of("A", "B", "C"))
                .set("mapField", Map.of("hour", 8, "year", 2024))
                .set("enumField", "DIAMONDS")
                .set("recordField", record)
                .build();

        var conf = new Configuration();
        conf.setBoolean(AvroReadSupport.AVRO_COMPATIBILITY, true);
        conf.setBoolean("parquet.avro.add-list-element-records", false);
        conf.setBoolean("parquet.avro.write-old-list-structure", false);
        var path = new Path(FileUtil.createAbsentTempFile(".parquet").getPath());
        System.out.println(path);
        var outputFile = HadoopOutputFile.fromPath(path, conf);
        try (var writer = AvroParquetWriter.<GenericRecord>builder(outputFile).withSchema(schema).withConf(conf).build()) {
            writer.write(expRecord);
        }

        // Assert
        try (var reader = AvroParquetReader.<GenericRecord>builder(HadoopInputFile.fromPath(path, conf)).build()) {
            var actJson = reader.read().toString();
            var expJson = """
                    {"nullField": null,
                    "booleanField": true,
                    "intField": 7,
                    "longField": 2147483648,
                    "floatField": 1.5,
                    "doubleField": 2.6,
                    "stringField": "hello",
                    "arrayField": ["A", "B", "C"],
                    "mapField": {"hour": 8, "year": 2024},
                    "enumField": "DIAMONDS",
                    "recordField": {"nestedIntField": 25, "nestedStringField": "Mary"}}
                    """;
            assertThatJson(actJson).isEqualTo(expJson);
        }
    }
}