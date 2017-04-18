package module1.hw2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Pattern;

public class StringToWordMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    private final Pattern separator = Pattern.compile("\\s");
    private final Text text = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = separator.split(value.toString());
        for (String word : words) {
            text.set(word);
            context.write(text, NullWritable.get());
        }
    }
}
