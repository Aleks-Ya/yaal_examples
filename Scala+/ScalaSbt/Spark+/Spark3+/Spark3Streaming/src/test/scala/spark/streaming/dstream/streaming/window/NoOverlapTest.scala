package spark.streaming.dstream.streaming.window

import factory.Factory
import org.apache.spark.streaming.Seconds
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec


class NoOverlapTest extends AnyFlatSpec with BeforeAndAfterAll {
  "No overlap between windows" should "process each line once" in {
    val batchDuration = Seconds(2)
    val windowDuration = Seconds(4)
    val slideDuration = windowDuration

    val ssc = Factory.ssc(batchDuration)
    val lines = ssc.socketTextStream("localhost", 9999)
    val windowedLines = lines.window(windowDuration, slideDuration)
    windowedLines.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
