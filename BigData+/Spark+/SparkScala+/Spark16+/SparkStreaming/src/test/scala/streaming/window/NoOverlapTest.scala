package streaming.window

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class NoOverlapTest extends FlatSpec with BeforeAndAfterAll {

  "No overlap between windows" should "process each line once" in {
    val batchDuration = Seconds(2)
    val windowDuration = Seconds(4)
    val slideDuration = windowDuration

    val conf = new SparkConf().setAppName(classOf[NoOverlapTest].getSimpleName).setMaster("local[2]")
    val ssc = new StreamingContext(conf, batchDuration)
    val lines = ssc.socketTextStream("localhost", 9999)
    val windowedLines = lines.window(windowDuration, slideDuration)
    windowedLines.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
