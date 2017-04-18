package module1.hw2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static java.util.Collections.singletonList;

public class ReduceTest {
    @Test
    public void test() throws IOException {
        new ReduceDriver<Text, NullWritable, Text, NullWritable>()
                .withReducer(new LongestWordReducer())
                .withAll(Arrays.asList(
                        new Pair<>(new Text("a"), singletonList(NullWritable.get())),
                        new Pair<>(new Text("ccc"), singletonList(NullWritable.get())),
                        new Pair<>(new Text("bb"), singletonList(NullWritable.get()))
                ))
                .withOutput(new Text("ccc"), NullWritable.get())
                .runTest();
    }
}
