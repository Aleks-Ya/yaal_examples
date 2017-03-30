package mapreduce

import java.lang
import java.util.StringTokenizer

import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.Reducer

class WordCountReducer extends Reducer[LongWritable, Text, Text, IntWritable]{
  override def reduce(key: LongWritable,
                      values: lang.Iterable[Text],
                      context: Reducer[LongWritable, Text, Text, IntWritable]#Context): Unit = {

  }
}
