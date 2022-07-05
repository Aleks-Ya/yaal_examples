package mr3.counter;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class CounterMapperTest {
    @Test
    void test() throws IOException {
        var driver = new MapDriver<Text, Text, Text, DoubleWritable>()
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

        var counters = driver.getCounters();
        assertThat(counters.countCounters()).isEqualTo(3);
        assertThat(counters.findCounter(CounterMapper.MyCounter.PERSONS).getValue()).isEqualTo(2L);
        assertThat(counters.findCounter(CounterMapper.COUNTER_GROUP, CounterMapper.COUNTER).getValue()).isEqualTo(1L);
        assertThat(counters.findCounter(CounterMapper.MyCounter.LAST_TIME).getValue()).isLessThanOrEqualTo(Instant.now().getEpochSecond());
    }
}
