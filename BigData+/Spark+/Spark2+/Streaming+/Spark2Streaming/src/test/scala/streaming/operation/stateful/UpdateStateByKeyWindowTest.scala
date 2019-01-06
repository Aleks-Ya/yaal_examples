package streaming.operation.stateful

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{ClockWrapperFull, Seconds, StreamingContext}
import org.scalatest.concurrent.Eventually
import org.scalatest.time.{Millis, Span}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.slf4j.LoggerFactory

import scala.collection.mutable

class UpdateStateByKeyWindowTest extends FlatSpec with BeforeAndAfterAll with Eventually with Matchers {
  //  private val log = LoggerFactory.getLogger(classOf[UpdateStateByKeyWindowTest])
  implicit override val patienceConfig = PatienceConfig(
    timeout = scaled(Span(5000, Millis))
  )

  it should "use updateStateByKey operation" in {
    val conf = new SparkConf()
      .setAppName(classOf[UpdateStateByKeyWindowTest].getSimpleName)
      .setMaster("local[2]")
      .set("spark.streaming.clock", "org.apache.spark.util.ManualClock")
    val batchDuration = Seconds(1)
    val ssc = new StreamingContext(conf, batchDuration)
    ssc.checkpoint("c:\\tmp\\checkpoints")
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
