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
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class SequenceFileReadWrite {
    @Test
    public void write() throws IOException {
        File outFile = File.createTempFile(getClass().getSimpleName(), ".seq");
        outFile.deleteOnExit();
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path path = new Path(outFile.toURI());
        IntWritable key = new IntWritable();
        Text value = new Text();
        List<String> inLines = Arrays.asList("abc", "123");
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
            int ctr = 100;
            for (String line : inLines) {
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
    public void read() throws IOException, URISyntaxException {
        URL inFile = getClass().getResource("read_sequence_file.seq");
        Configuration conf = new Configuration();
        Path path = new Path(inFile.toURI());
        SequenceFile.Reader reader = null;
        try {
            reader = new SequenceFile.Reader(
                    conf,
                    SequenceFile.Reader.file(path),
                    SequenceFile.Reader.bufferSize(4096),
                    SequenceFile.Reader.start(0)
            );
            Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), conf);
            //long position = reader.getPosition();
            //reader.seek(position);
            while (reader.next(key, value)) {
                String syncSeen = reader.syncSeen() ? "*" : "";
                System.out.printf("[%s]\t%s\t%s\n", syncSeen, key, value);
            }
        } finally {
            IOUtils.closeStream(reader);
        }
    }
}
