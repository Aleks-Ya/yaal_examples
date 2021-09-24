package hdfs.in_memory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Metadata;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class SequenceFileReadWrite {

    @Test
    void write() throws IOException {
        var outFile = File.createTempFile(getClass().getSimpleName(), ".seq");
        outFile.deleteOnExit();
        var conf = new Configuration();
        var fs = FileSystem.get(conf);
        var path = new Path(outFile.toURI());
        var key = new IntWritable();
        var value = new Text();
        var inLines = Arrays.asList("abc", "123");
        Writer writer = null;
        try {
            writer = SequenceFile.createWriter(conf, Writer.file(path),
                    Writer.keyClass(key.getClass()),
                    Writer.valueClass(value.getClass()),
                    Writer.bufferSize(fs.getConf().getInt("io.file.buffer.size", 4096)),
                    Writer.replication(fs.getDefaultReplication(path)),
                    Writer.blockSize(1073741824),
                    Writer.compression(SequenceFile.CompressionType.BLOCK, new DefaultCodec()),
                    Writer.progressable(null),
                    Writer.metadata(new Metadata()));
            var ctr = 100;
            for (var line : inLines) {
                key.set(ctr++);
                value.set(line);
                if (ctr < 150)
                    System.out.printf("[%s]\t%s\t%s\n", writer.getLength(), key, value);
                writer.append(key, value);
            }
        } finally {
            IOUtils.closeStream(writer);
        }

    }

    @Test
    void read() throws IOException, URISyntaxException {
        var inFile = getClass().getResource("read_sequence_file.seq");
        var conf = new Configuration();
        var path = new Path(Objects.requireNonNull(inFile).toURI());
        SequenceFile.Reader reader = null;
        try {
            reader = new SequenceFile.Reader(
                    conf,
                    SequenceFile.Reader.file(path),
                    SequenceFile.Reader.bufferSize(4096),
                    SequenceFile.Reader.start(0)
            );
            var key = (IntWritable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
            var value = (Text) ReflectionUtils.newInstance(reader.getValueClass(), conf);
            //long position = reader.getPosition();
            //reader.seek(position);
            while (reader.next(key, value)) {
                var syncSeen = reader.syncSeen() ? "*" : "";
                Integer keyData = key.get();
                var valueData = value.toString();
                System.out.printf("[%s]\t%s\t%s\n", syncSeen, keyData, valueData);
            }
        } finally {
            IOUtils.closeStream(reader);
        }
    }

    @Test
    void readToMap() throws IOException, URISyntaxException {
        var inFile = getClass().getResource("read_sequence_file.seq");
        var conf = new Configuration();
        var path = new Path(Objects.requireNonNull(inFile).toURI());
        SequenceFile.Reader reader = null;
        Map<Integer, String> content = new HashMap<>();
        try {
            reader = new SequenceFile.Reader(
                    conf,
                    SequenceFile.Reader.file(path),
                    SequenceFile.Reader.bufferSize(4096),
                    SequenceFile.Reader.start(0)
            );
            var key = (IntWritable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
            var value = (Text) ReflectionUtils.newInstance(reader.getValueClass(), conf);
            while (reader.next(key, value)) {
                Integer keyData = key.get();
                var valueData = value.toString();
                content.put(keyData, valueData);
            }
        } finally {
            IOUtils.closeStream(reader);
        }
        assertThat(content).containsEntry(100, "abc");
        assertThat(content).containsEntry(101, "123");
    }
}
