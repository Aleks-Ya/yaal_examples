package streaming.random

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Print to console random numbers.
  * Build: sbt "set test in assembly := {}" clean assembly
  * Run:   spark-submit --class streaming.random.PrintRandomNumbersMain spark_streaming.jar
  */
object PrintRandomNumbersMain {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local[2]")
    val batchDuration = Seconds(5)
    val ssc = new StreamingContext(conf, batchDuration)
    val receiver = new RandomNumberReceiver()
    val stream = ssc.receiverStream(receiver)
    stream.print()
    ssc.start
    ssc.awaitTermination()
  }

}
