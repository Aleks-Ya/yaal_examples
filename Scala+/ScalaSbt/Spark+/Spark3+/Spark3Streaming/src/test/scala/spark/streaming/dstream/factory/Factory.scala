package spark.streaming.dstream.factory

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}


object Factory {
  def ssc(): StreamingContext = ssc(Seconds(1))

  def ssc(duration: Duration) = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local[2]")
    new StreamingContext(conf, duration)
  }

  def ssc(duration: Duration, settings: Iterable[(String, String)]) = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local[2]")
      .setAll(settings)
    new StreamingContext(conf, duration)
  }
}
