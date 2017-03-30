package mapreduce

import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

class WordCountMapper extends Mapper[LongWritable, Text, Text, IntWritable] {
  override def map(key: LongWritable,
                   value: Text,
                   context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit = {

  }
}
