package mr.mrunit;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MyMapper extends Mapper<Text, LongWritable, Text, LongWritable> {
    @Override
    public void map(Text key, LongWritable value, Context context) throws IOException, InterruptedException {
        var newValue = new LongWritable(value.get() * 2);
        context.write(key, newValue);
    }
}
