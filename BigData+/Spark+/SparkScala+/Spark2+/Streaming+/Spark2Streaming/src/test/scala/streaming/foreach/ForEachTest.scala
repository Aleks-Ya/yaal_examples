package streaming.foreach

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{ClockWrapperFull, Seconds, StreamingContext}
import org.scalatest.concurrent.Eventually
import org.scalatest.time.{Millis, Span}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.collection.mutable

class ForEachTest extends FlatSpec with BeforeAndAfterAll with Eventually with Matchers {

  implicit override val patienceConfig: PatienceConfig = PatienceConfig(
    timeout = scaled(Span(5000, Millis))
  )

  "Process DStream with foreachRDD" should "process lines" in {
    val conf = new SparkConf()
      .setAppName(classOf[ForEachTest].getSimpleName)
      .setMaster("local[2]")
      .set("spark.streaming.clock", "org.apache.spark.util.ManualClock")
    val batchDuration = Seconds(1)
    val ssc = new StreamingContext(conf, batchDuration)
    val sc = ssc.sparkContext
    ClockWrapperFull.setSparkStreamingContext(ssc)
    //    val lines = ssc.socketTextStream("localhost", 9999)
    val queue = mutable.Queue(sc.parallelize(Seq("a", "b", "c")))
    val lines = ssc.queueStream(queue)
    val result = new StringBuilder()
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
