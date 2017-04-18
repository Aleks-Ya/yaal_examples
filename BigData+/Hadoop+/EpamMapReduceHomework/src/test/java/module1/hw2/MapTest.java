package module1.hw2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class MapTest {
    @Test
    public void test() throws IOException {
        new MapDriver<LongWritable, Text, Text, NullWritable>()
                .withMapper(new StringToWordMapper())
                .withAll(Arrays.asList(
                        new Pair<>(new LongWritable(0), new Text("aa bbb c")),
                        new Pair<>(new LongWritable(0), new Text("ddd ee\ngg"))
                ))
                .withAllOutput(Arrays.asList(
                        new Pair<>(new Text("aa"), NullWritable.get()),
                        new Pair<>(new Text("bbb"), NullWritable.get()),
                        new Pair<>(new Text("c"), NullWritable.get()),
                        new Pair<>(new Text("ddd"), NullWritable.get()),
                        new Pair<>(new Text("ee"), NullWritable.get()),
                        new Pair<>(new Text("gg"), NullWritable.get())
                ))
                .runTest();
    }
}
