package parquet.hadoop.write;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.ParquetReadOptions;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.metadata.FileMetaData;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.schema.Types;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.apache.parquet.schema.LogicalTypeAnnotation.stringType;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.assertj.core.api.Assertions.assertThat;
import static parquet.hadoop.Helper.createAbsentTempParquetPath;

/**
 * Create a Parquet file with custom metadata at file level.
 */
class ExtraFileMetaDataTest {
    @Test
    void writeCustomMetadata() throws IOException {
        var conf = new Configuration();
        var field = "mystring";
        var value = "the string";
        var path = createAbsentTempParquetPath();

        var schema = Types.buildMessage()
                .required(BINARY).as(stringType()).named(field)
                .named("myrecord");
        var factory = new SimpleGroupFactory(schema);
        try (var writer = ExampleParquetWriter.builder(path).withConf(conf).withType(schema)
                .withExtraMetaData(Map.of("color", "green"))
                .build()) {
            var group = factory.newGroup().append(field, value);
            writer.write(group);
        }
        var actMetaData = readFileMetaData(conf, path);
        assertThat(actMetaData.getKeyValueMetaData()).containsEntry("color", "green");
    }

    private static FileMetaData readFileMetaData(Configuration conf, Path path) throws IOException {
        var inputFile = HadoopInputFile.fromPath(path, conf);
        var seekableInputStream = inputFile.newStream();
        var readOptions = ParquetReadOptions.builder().build();
        var parquetMetadata = ParquetFileReader.readFooter(inputFile, readOptions, seekableInputStream);
        return parquetMetadata.getFileMetaData();
    }
}