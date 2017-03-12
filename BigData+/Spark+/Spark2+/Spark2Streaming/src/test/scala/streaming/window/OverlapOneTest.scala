package streaming.window

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class OverlapOneTest extends FlatSpec with BeforeAndAfterAll {

  "Overlap between windows one batch" should "process last batch twice" in {
    val batchDuration = Seconds(2)
    val windowDuration = Seconds(6)
    val slideDuration = windowDuration - batchDuration

    val conf = new SparkConf().setAppName(classOf[OverlapOneTest].getSimpleName).setMaster("local[2]")
    val ssc = new StreamingContext(conf, batchDuration)
    val lines = ssc.socketTextStream("localhost", 9999)
    val windowedLines = lines.window(windowDuration, slideDuration)
    windowedLines.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
