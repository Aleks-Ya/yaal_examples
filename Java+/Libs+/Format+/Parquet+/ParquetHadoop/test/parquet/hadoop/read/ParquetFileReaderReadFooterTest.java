package parquet.hadoop.read;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.ParquetReadOptions;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.io.InputFile;
import org.apache.parquet.io.SeekableInputStream;
import org.apache.parquet.schema.MessageType;
import org.junit.jupiter.api.Test;
import parquet.hadoop.Helper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Read a Parquet file with method {@link ParquetFileReader#readFooter(InputFile, ParquetReadOptions, SeekableInputStream)}.
 */
class ParquetFileReaderReadFooterTest {
    @Test
    void readSchema() throws IOException {
        var conf = new Configuration();
        var path = Helper.writeParquetFile(conf, "mystring", "the string");
        var schema = readSchema(conf, path);
        assertThat(schema).hasToString("message myrecord {\n  required binary mystring (STRING);\n}\n");
    }

    private MessageType readSchema(Configuration conf, Path path) throws IOException {
        var inputFile = HadoopInputFile.fromPath(path, conf);
        var seekableInputStream = inputFile.newStream();
        var readOptions = ParquetReadOptions.builder().build();
        var parquetMetadata = ParquetFileReader.readFooter(inputFile, readOptions, seekableInputStream);
        return parquetMetadata.getFileMetaData().getSchema();
    }
}