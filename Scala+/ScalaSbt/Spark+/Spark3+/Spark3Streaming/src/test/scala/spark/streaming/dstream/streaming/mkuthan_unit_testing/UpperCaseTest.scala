package spark.streaming.dstream.streaming.mkuthan_unit_testing

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming._
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Span}
import spark.streaming.dstream.factory.Factory

import java.nio.file.Files
import scala.collection.mutable

class UpperCaseTest extends AnyFlatSpec with Matchers with Eventually {

  it should "convert all names to upper case" in {
    implicit val patienceConfig: PatienceConfig = PatienceConfig(timeout = scaled(Span(10000, Millis)))

    val batchDuration = Seconds(1)
    val ssc = Factory.ssc(batchDuration, Seq(("spark.streaming.clock", "org.apache.spark.util.ManualClock")))
    ssc.checkpoint(Files.createTempDirectory("checkpoints").toString)
    val sc = ssc.sparkContext
    ClockWrapperFull.setSparkStreamingContext(ssc)

    val lines = mutable.Queue[RDD[String]]()
    val results = new mutable.ListBuffer[String]
    ssc.queueStream(lines).map(_.toUpperCase)
      .foreachRDD { rdd =>
        val str = rdd.collect().mkString(",")
        results += str
      }

    ssc.start()

    lines += sc.parallelize(Seq("aaa", "bbb", "ccc"))
    ClockWrapper.advance(ssc, batchDuration)

    lines += sc.parallelize(Seq("ggg", "eee", "fff"))
    ClockWrapper.advance(ssc, batchDuration)

    eventually {
      results should contain allOf("AAA,BBB,CCC", "GGG,EEE,FFF")
    }

    ssc.stop(stopSparkContext = false, stopGracefully = false)
  }

}
