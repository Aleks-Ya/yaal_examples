package spark.streaming.dstream.streaming.manual_clock

import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ChangeTimeTest extends AnyFlatSpec with Matchers with Eventually {

  it should "add time" in {
    val conf = new SparkConf()
      .setAppName(classOf[ChangeTimeTest].getSimpleName)
      .setMaster("local[2]")
      .set("spark.streaming.clock", "org.apache.spark.util.ManualClock")
    val batchDuration = Seconds(1)
    val ssc = new StreamingContext(conf, batchDuration)

    ClockWrapperFull.setSparkStreamingContext(ssc)

    ClockWrapperFull.getTimeMillis shouldBe 0L

    ClockWrapperFull.advance(batchDuration.milliseconds)
    ClockWrapperFull.getTimeMillis shouldBe batchDuration.milliseconds

    ClockWrapperFull.advance(5000)
    ClockWrapperFull.getTimeMillis shouldBe 6000

    ClockWrapperFull.setTime(10000)
    ClockWrapperFull.getTimeMillis shouldBe 10000
  }
}
