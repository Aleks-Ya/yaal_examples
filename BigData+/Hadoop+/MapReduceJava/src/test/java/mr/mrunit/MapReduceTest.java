package mr.mrunit;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.junit.Test;

import java.io.IOException;

public class MapReduceTest {
    @Test
    public void processesValidRecord() throws IOException {
        new MapReduceDriver<Text, LongWritable, Text, LongWritable, Text, LongWritable>()
                .withReducer(new MyReducer())
                .withMapper(new MyMapper())
                .withInput(new Text("a"), new LongWritable(2))
                .withOutput(new Text("a"), new LongWritable(4))
                .runTest();
    }
}
