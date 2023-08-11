package parquet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.column.ColumnReadStore;
import org.apache.parquet.column.ColumnReader;
import org.apache.parquet.column.impl.ColumnReadStoreImpl;
import org.apache.parquet.column.page.PageReadStore;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.example.data.simple.convert.GroupRecordConverter;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.schema.PrimitiveType;
import org.apache.parquet.schema.Types;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;
import java.util.Arrays;

import static org.apache.parquet.schema.LogicalTypeAnnotation.stringType;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.assertj.core.api.Assertions.assertThat;


class ReadTest {

    @Test
    void read() throws IOException {
        var conf = new Configuration();

        var field = "mystring";
        var value = "the string";

        var path = writeParquetFile(conf, field, value);
        readParquetFile(conf, path, field, value);
    }

    private Path writeParquetFile(Configuration conf, String myStringField, String expString) throws IOException {
        var path = new Path(FileUtil.createAbsentTempFileDeleteOnExit(".parquet").getPath());

        var schema = Types.buildMessage()
                .required(BINARY).as(stringType()).named(myStringField)
                .named("myrecord");
        var factory = new SimpleGroupFactory(schema);
        try (var writer = ExampleParquetWriter.builder(path).withConf(conf).withType(schema).build()) {
            var group = factory.newGroup().append(myStringField, expString);
            writer.write(group);
        }

        return path;
    }

    private void readParquetFile(Configuration conf, Path path, String myStringField, String expString) throws IOException {
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
                ColumnReadStore colReadStore = new ColumnReadStoreImpl(rowGroup, new GroupRecordConverter(schema).getRootConverter(), schema, "blah");
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