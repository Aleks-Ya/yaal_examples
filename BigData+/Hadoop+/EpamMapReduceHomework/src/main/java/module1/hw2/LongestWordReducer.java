package module1.hw2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class LongestWordReducer extends Reducer<Text, NullWritable, Text, NullWritable> {
    private String longestWord = "";

    @Override
    public void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException {
        if (key.getLength() > longestWord.length()) {
            longestWord = key.toString();
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(new Text(longestWord), NullWritable.get());
    }
}
