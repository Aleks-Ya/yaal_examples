package streaming.random

import java.nio.file.Files

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Split numbers on positive and negative and print sum of both.
  * Build: sbt "set test in assembly := {}" clean assembly
  * Run:   spark-submit --class streaming.random.PositiveNegativeSumMain spark_streaming.jar
  */
object PositiveNegativeSumMain {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local[2]")
    val batchDuration = Seconds(5)
    val ssc = new StreamingContext(conf, batchDuration)
    ssc.checkpoint(Files.createTempDirectory("checkpoint_").toString)
    val receiver = new RandomNumberReceiver()
    val stream = ssc.receiverStream(receiver)

    stream
      .map(num => if (num >= 0) ("positive", num) else ("negative", num))
      .updateStateByKey((nums, sum: Option[Int]) => {
        println("Sums: " + nums)
        Some(sum.getOrElse(0) + nums.sum)
      })
      .print()

    ssc.start
    ssc.awaitTermination()
  }

}
