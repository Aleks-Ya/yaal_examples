package mr.counter;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CounterMapperTest {
    @Test
    public void test() throws IOException {
        MapDriver<Text, Text, Text, DoubleWritable> driver = new MapDriver<Text, Text, Text, DoubleWritable>()
                .withMapper(new CounterMapper())
                .withAll(Arrays.asList(
                        new Pair<>(new Text("John"), new Text("100.3")),
                        new Pair<>(new Text("Alex"), new Text("1000000"))
                ))
                .withAllOutput(Arrays.asList(
                        new Pair<>(new Text("John"), new DoubleWritable(100.3)),
                        new Pair<>(new Text("Alex"), new DoubleWritable(1000000D))
                ));
        driver.runTest();

        Counters counters = driver.getCounters();
        assertThat(counters.countCounters(), equalTo(3));
        assertThat(counters.findCounter(CounterMapper.MyCounter.PERSONS).getValue(), equalTo(2L));
        assertThat(counters.findCounter(CounterMapper.COUNTER_GROUP, CounterMapper.COUNTER).getValue(), equalTo(1L));
        assertThat(counters.findCounter(CounterMapper.MyCounter.LAST_TIME).getValue(), lessThanOrEqualTo(Instant.now().getEpochSecond()));
    }
}
