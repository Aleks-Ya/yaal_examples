package parquet.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.schema.Types;
import util.FileUtil;

import java.io.IOException;

import static org.apache.parquet.schema.LogicalTypeAnnotation.stringType;
import static org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY;

public class Helper {
    public static Path writeParquetFile(Configuration conf, String myStringField, String expString) {
        var path = new Path(FileUtil.createAbsentTempFileDeleteOnExit(".parquet").getPath());
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
}
