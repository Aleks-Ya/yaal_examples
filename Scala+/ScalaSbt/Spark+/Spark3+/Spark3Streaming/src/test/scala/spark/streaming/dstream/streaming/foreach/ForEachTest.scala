package spark.streaming.dstream.streaming.foreach

import factory.Factory
import org.apache.spark.streaming.{ClockWrapperFull, Seconds}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Span}

import scala.collection.mutable

class ForEachTest extends AnyFlatSpec with BeforeAndAfterAll with Eventually with Matchers {

  implicit override val patienceConfig: PatienceConfig = PatienceConfig(
    timeout = scaled(Span(5000, Millis))
  )

  "Process DStream with foreachRDD" should "process lines" in {
    val batchDuration = Seconds(1)
    val ssc = Factory.ssc(batchDuration, Seq(("spark.streaming.clock", "org.apache.spark.util.ManualClock")))
    val sc = ssc.sparkContext
    ClockWrapperFull.setSparkStreamingContext(ssc)
    //    val lines = ssc.socketTextStream("localhost", 9999)
    val queue = mutable.Queue(sc.parallelize(Seq("a", "b", "c")))
    val lines = ssc.queueStream(queue)
    val result = new mutable.StringBuilder()
    lines.foreachRDD { rdd =>
      rdd.foreach { word: String => {
        result.synchronized {
          result.append(word)
        }
      }
      }
    }
    ssc.start
    ClockWrapperFull.advance(batchDuration.milliseconds)
    eventually {
      result.synchronized {
        val resultStr = result.toString
        if (!"abc".equals(resultStr)) {
          println("res: " + resultStr)
          throw new AssertionError()
        }
      }
      //      result.toString shouldEqual "asdaaba"
    }
    ssc.stop()
  }
}
