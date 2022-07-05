package mr2.mrunit;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class MapTest {
    @Test
    void singleInput() throws IOException {
        new MapDriver<Text, LongWritable, Text, LongWritable>()
                .withMapper(new MyMapper())
                .withInput(new Text("a"), new LongWritable(1))
                .withOutput(new Text("a"), new LongWritable(2))
                .runTest();
    }
}
