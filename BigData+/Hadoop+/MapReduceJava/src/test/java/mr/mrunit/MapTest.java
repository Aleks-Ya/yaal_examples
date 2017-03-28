package mr.mrunit;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

import java.io.IOException;

public class MapTest {
    @Test
    public void processesValidRecord() throws IOException, InterruptedException {
        new MapDriver<Text, LongWritable, Text, LongWritable>()
                .withMapper(new MyMapper())
                .withInput(new Text("a"), new LongWritable(1))
                .withOutput(new Text("a"), new LongWritable(2))
                .runTest();
    }
}
