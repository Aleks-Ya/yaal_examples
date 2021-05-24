package parquet;

import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.column.ColumnReadStore;
import org.apache.parquet.column.ColumnReader;
import org.apache.parquet.column.impl.ColumnReadStoreImpl;
import org.apache.parquet.column.page.PageReadStore;
import org.apache.parquet.example.data.simple.convert.GroupRecordConverter;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.apache.parquet.schema.PrimitiveType;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadTest {

    @Test
    void read() throws IOException {
        var conf = new Configuration();

        var myStringField = "mystring";
        var expString = "the string";

        var path = writeParquetFile(conf, myStringField, expString);
        readParquetFile(conf, path, myStringField, expString);
    }

    private Path writeParquetFile(Configuration conf, String myStringField, String expString) throws IOException {
        var path = new Path(FileUtil.createAbsentTempFileDeleteOnExit(".parquet").getPath());

        // Write Parquet file
        var schema1 = SchemaBuilder.record("myrecord")
                .fields()
                .name(myStringField).type().stringType().noDefault()
                .endRecord();
        var expRecord = new GenericRecordBuilder(schema1).set(myStringField, expString).build();
        var outputFile = HadoopOutputFile.fromPath(path, conf);
        var writer = AvroParquetWriter
                .<GenericRecord>builder(outputFile)
                .withSchema(schema1)
                .withConf(conf)
                .build();
        writer.write(expRecord);
        writer.close();
        return path;
    }

    private void readParquetFile(Configuration conf, Path path, String myStringField, String expString) throws IOException {
        var rows = 0;
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
                    assertEquals(type.getPrimitiveTypeName(), PrimitiveType.PrimitiveTypeName.BINARY);

                    var columnNamePath = colDescriptor.getPath();
                    var columnName = Arrays.toString(columnNamePath);
                    assertEquals(columnName, Arrays.toString(new String[]{myStringField}));
                    colReader = colReadStore.getColumnReader(colDescriptor);
                    var totalValuesInColumnChunk = rowGroup.getPageReader(colDescriptor).getTotalValueCount();

                    //For every cell in the column chunk
                    for (var i = 0; i < totalValuesInColumnChunk; i++) {
                        actString = colReader.getBinary().toStringUsingUTF8();
                        colReader.consume();
                    }
                }
            }
            assertEquals(expString, actString);
            assertEquals(numRowGroups, 1);
        }
        System.out.println("Total number of rows: " + rows);
    }
}