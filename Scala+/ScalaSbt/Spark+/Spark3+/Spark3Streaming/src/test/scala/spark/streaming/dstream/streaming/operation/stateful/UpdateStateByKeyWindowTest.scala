package spark.streaming.dstream.streaming.operation.stateful

import org.apache.spark.streaming.{ClockWrapperFull, Seconds}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Span}
import spark.streaming.dstream.factory.Factory

import java.nio.file.Files
import scala.collection.mutable

class UpdateStateByKeyWindowTest extends AnyFlatSpec with BeforeAndAfterAll with Eventually with Matchers {

  implicit override val patienceConfig: PatienceConfig = PatienceConfig(
    timeout = scaled(Span(5000, Millis))
  )

  it should "use updateStateByKey operation" in {
    val batchDuration = Seconds(1)
    val ssc = Factory.ssc(batchDuration, Seq(("spark.streaming.clock", "org.apache.spark.util.ManualClock")))
    ssc.checkpoint(Files.createTempDirectory("checkpoints").toString)
    val sc = ssc.sparkContext
    ClockWrapperFull.setSparkStreamingContext(ssc)
    val queue = mutable.Queue(sc.parallelize(Seq("a b c", "b b b", "c")))
    val lines = ssc.queueStream(queue)

    val stateByKey = lines
      .map(line => line.split(" "))
      .flatMap(words => words.map(word => (word, 1)))
      .reduceByKey(_ + _)
      .updateStateByKey((events, count: Option[Int]) => {
        val newCount = events.sum
        val oldCount = count.getOrElse(0)
        val sum = oldCount + newCount
        Some(sum)
      })

    stateByKey
      .map(pair => pair._1 + "+" + pair._2)
      .print()

    stateByKey
      .window(batchDuration * 2, batchDuration * 2)
      .map(pair => pair._1 + "-" + pair._2)
      .print()

    ssc.start

    ClockWrapperFull.advance(batchDuration.milliseconds)
    queue += sc.parallelize(Seq("b a c", "a c c", "d"))
    ClockWrapperFull.advance(batchDuration.milliseconds)

    Thread.sleep(1000)
    ssc.stop()
  }
}
