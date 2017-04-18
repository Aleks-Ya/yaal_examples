package module1.hw2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class MapReduceTest {
    @Test
    public void test() throws IOException {
        new MapReduceDriver<LongWritable, Text, Text, NullWritable, Text, NullWritable>()
                .withMapper(new StringToWordMapper())
                .withCombiner(new LongestWordReducer())
                .withReducer(new LongestWordReducer())
                .withAll(Arrays.asList(
                        new Pair<>(new LongWritable(0), new Text("aa bbb c")),
                        new Pair<>(new LongWritable(0), new Text("ddd ee\ngg"))
                ))
                .withOutput(new Text("bbb"), NullWritable.get())// the first longest word chose
                .runTest();
    }
}
