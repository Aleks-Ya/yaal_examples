package parquet.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.ParquetReadOptions;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.metadata.FileMetaData;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.schema.Types;
import util.FileUtil;

import java.io.IOException;

import static org.apache.parquet.schema.LogicalTypeAnnotation.stringType;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;
import static org.assertj.core.api.Assertions.assertThat;

public class Helper {

    public static Path createAbsentTempParquetPath() {
        return new Path(FileUtil.createAbsentTempFileDeleteOnExit(".parquet").getPath());
    }

    public static Path writeParquetFile(Configuration conf, String myStringField, String expString) {
        var path = createAbsentTempParquetPath();
        var schema = Types.buildMessage()
                .required(BINARY).as(stringType()).named(myStringField)
                .named("myrecord");
        var factory = new SimpleGroupFactory(schema);
        try (var writer = ExampleParquetWriter.builder(path).withConf(conf).withType(schema).build()) {
            var group = factory.newGroup().append(myStringField, expString);
            writer.write(group);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    public static String parquetToString(Configuration conf, Path path) {
        try (var reader = ParquetReader.builder(new GroupReadSupport(), path).withConf(conf).build()) {
            var sb = new StringBuilder();
            Group group;
            while ((group = reader.read()) != null) {
                sb.append(group);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FileMetaData readFileMetaData(Configuration conf, Path path) throws IOException {
        var inputFile = HadoopInputFile.fromPath(path, conf);
        var seekableInputStream = inputFile.newStream();
        var readOptions = ParquetReadOptions.builder().build();
        var parquetMetadata = ParquetFileReader.readFooter(inputFile, readOptions, seekableInputStream);
        return parquetMetadata.getFileMetaData();
    }

    public static Group readGroup(Configuration conf, Path path) {
        try (var reader = ParquetReader.builder(new GroupReadSupport(), path).withConf(conf).build()) {
            var group = reader.read();
            assertThat(group).isNotNull();
            return group;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}