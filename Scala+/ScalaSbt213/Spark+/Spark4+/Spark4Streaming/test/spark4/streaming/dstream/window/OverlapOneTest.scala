package spark4.streaming.dstream.window

import org.apache.spark.streaming.Seconds
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import spark4.streaming.Factory


class OverlapOneTest extends AnyFlatSpec with BeforeAndAfterAll {
  "Overlap between windows one batch" should "process last batch twice" in {
    val batchDuration = Seconds(2)
    val windowDuration = Seconds(6)
    val slideDuration = windowDuration - batchDuration

    val ssc = Factory.ssc(batchDuration)
    val lines = ssc.socketTextStream("localhost", 9999)
    val windowedLines = lines.window(windowDuration, slideDuration)
    windowedLines.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
