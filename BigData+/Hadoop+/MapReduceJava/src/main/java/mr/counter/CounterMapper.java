package mr.counter;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.time.Instant;

import static mr.counter.CounterMapper.MyCounter.LAST_TIME;
import static mr.counter.CounterMapper.MyCounter.PERSONS;

public class CounterMapper extends Mapper<Text, Text, Text, DoubleWritable> {
    static final String COUNTER_GROUP = "my";
    static final String COUNTER = "reachPeople";

    enum MyCounter {
        PERSONS, LAST_TIME
    }

    @Override
    public void map(Text person, Text salary, Context context) throws IOException, InterruptedException {
        double value = Double.parseDouble(salary.toString());
        DoubleWritable newValue = new DoubleWritable(value);
        context.write(person, newValue);
        context.getCounter(PERSONS).increment(1);
        if (value > 100_000) {
            context.getCounter(COUNTER_GROUP, COUNTER).increment(1);
        }
        context.getCounter(LAST_TIME).setValue(Instant.now().getEpochSecond());
    }
}
