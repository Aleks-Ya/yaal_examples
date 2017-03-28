package mr.mrunit;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class ReducerTest {
    @Test
    public void processesValidRecord() throws IOException, InterruptedException {
        new ReduceDriver<Text, LongWritable, Text, LongWritable>()
                .withReducer(new MyReducer())
                .withInput(new Text("a"), Arrays.asList(new LongWritable(1), new LongWritable(2)))
                .withOutput(new Text("a"), new LongWritable(3))
                .runTest();
    }
}
