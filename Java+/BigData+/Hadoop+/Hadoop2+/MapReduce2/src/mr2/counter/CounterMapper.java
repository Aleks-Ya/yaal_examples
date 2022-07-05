package mr2.counter;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.time.Instant;

import static mr2.counter.CounterMapper.MyCounter.LAST_TIME;
import static mr2.counter.CounterMapper.MyCounter.PERSONS;

public class CounterMapper extends Mapper<Text, Text, Text, DoubleWritable> {
    static final String COUNTER_GROUP = "my";
    static final String COUNTER = "reachPeople";

    public enum MyCounter {
        PERSONS, LAST_TIME
    }

    @Override
    public void map(Text person, Text salary, Context context) throws IOException, InterruptedException {
        var value = Double.parseDouble(salary.toString());
        var newValue = new DoubleWritable(value);
        context.write(person, newValue);
        context.getCounter(PERSONS).increment(1);
        if (value > 100_000) {
            context.getCounter(COUNTER_GROUP, COUNTER).increment(1);
        }
        context.getCounter(LAST_TIME).setValue(Instant.now().getEpochSecond());
    }
}
