package mr.mrunit;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class MapReduceTest {
    @Test
    void singleInput() throws IOException {
        new MapReduceDriver<Text, LongWritable, Text, LongWritable, Text, LongWritable>()
                .withReducer(new MyReducer())
                .withMapper(new MyMapper())
                .withInput(new Text("a"), new LongWritable(2))
                .withOutput(new Text("a"), new LongWritable(4))
                .runTest();
    }
}
