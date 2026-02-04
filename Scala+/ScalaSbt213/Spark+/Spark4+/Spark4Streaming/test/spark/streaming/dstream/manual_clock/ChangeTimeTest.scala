package spark.streaming.dstream.manual_clock

import factory.Factory
import org.apache.spark.streaming._
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Files

class ChangeTimeTest extends AnyFlatSpec with Matchers with Eventually {
  it should "add time" in {
    val batchDuration = Seconds(1)
    val ssc = Factory.ssc(batchDuration, Seq(("spark.streaming.clock", "org.apache.spark.util.ManualClock")))
    ssc.checkpoint(Files.createTempDirectory("checkpoints").toString)

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
