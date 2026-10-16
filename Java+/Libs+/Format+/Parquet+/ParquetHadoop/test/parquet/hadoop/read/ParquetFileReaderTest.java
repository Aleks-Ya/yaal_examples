package parquet.hadoop.read;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.ParquetReadOptions;
import org.apache.parquet.column.ColumnReadStore;
import org.apache.parquet.column.ColumnReader;
import org.apache.parquet.column.impl.ColumnReadStoreImpl;
import org.apache.parquet.column.page.PageReadStore;
import org.apache.parquet.example.data.simple.convert.GroupRecordConverter;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.PrimitiveType;
import org.junit.jupiter.api.Test;
import parquet.hadoop.Helper;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Read a Parquet file with {@link ParquetFileReader}.
 */
class ParquetFileReaderTest {

    @Test
    void open() throws IOException {
        var conf = new Configuration();
        var field = "mystring";
        var value = "the string";
        var path = Helper.writeParquetFile(conf, field, value);
        readParquetFile(conf, path, field, value);
    }

    @Test
    void schema() throws IOException {
        var conf = new Configuration();
        var path = Helper.writeParquetFile(conf, "mystring", "the string");
        var schema = readSchema(conf, path);
        assertThat(schema).hasToString("message myrecord {\n  required binary mystring (STRING);\n}\n");
    }

    private static MessageType readSchema(Configuration conf, Path path) throws IOException {
        var inputFile = HadoopInputFile.fromPath(path, conf);
        var seekableInputStream = inputFile.newStream();
        var readOptions = ParquetReadOptions.builder().build();
        var parquetMetadata = ParquetFileReader.readFooter(inputFile, readOptions, seekableInputStream);
        return parquetMetadata.getFileMetaData().getSchema();
    }

    private static void readParquetFile(Configuration conf, Path path, String myStringField, String expString) throws IOException {
        var rows = 0L;
        var inputFile = HadoopInputFile.fromPath(path, conf);
        try (var r = ParquetFileReader.open(inputFile)) {
            var readFooter = r.getFooter();
            var schema = readFooter.getFileMetaData().getSchema();
            PageReadStore rowGroup;

            var numRowGroups = 0;
            String actString = null;

            while (null != (rowGroup = r.readNextRowGroup())) {
                numRowGroups++;
                rows += rowGroup.getRowCount();

                ColumnReader colReader;
                var groupConverter = new GroupRecordConverter(schema).getRootConverter();
                ColumnReadStore colReadStore = new ColumnReadStoreImpl(rowGroup, groupConverter, schema, "blah");
                var descriptorList = schema.getColumns();

                //for each column
                for (var colDescriptor : descriptorList) {
                    //Get datatype of the column
                    var type = colDescriptor.getPrimitiveType();
                    assertThat(PrimitiveType.PrimitiveTypeName.BINARY).isEqualTo(type.getPrimitiveTypeName());

                    var columnNamePath = colDescriptor.getPath();
                    var columnName = Arrays.toString(columnNamePath);
                    assertThat(Arrays.toString(new String[]{myStringField})).isEqualTo(columnName);
                    colReader = colReadStore.getColumnReader(colDescriptor);
                    var totalValuesInColumnChunk = rowGroup.getPageReader(colDescriptor).getTotalValueCount();

                    //For every cell in the column chunk
                    for (var i = 0; i < totalValuesInColumnChunk; i++) {
                        actString = colReader.getBinary().toStringUsingUTF8();
                        colReader.consume();
                    }
                }
            }
            assertThat(actString).isEqualTo(expString);
            assertThat(numRowGroups).isEqualTo(1);
        }
        assertThat(rows).isEqualTo(1);
    }
}