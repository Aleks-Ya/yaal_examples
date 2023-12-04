package streaming.manual_clock

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{ClockWrapperFull, _}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.concurrent.Eventually
import org.scalatest.time.{Millis, Span}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class ManualClockTest extends FlatSpec
  with BeforeAndAfterAll
  with Eventually
  with Matchers {

  private val appName = classOf[ManualClockTest].getSimpleName
  private val batchDuration = Seconds(1)
  private var ssc: StreamingContext = _
  private var sc: SparkContext = _

  // default timeout for eventually trait
  implicit override val patienceConfig: PatienceConfig = PatienceConfig(
    timeout = scaled(Span(5000, Millis))
  )

  override def beforeAll(): Unit = {
    val conf = new SparkConf()
      .setAppName(appName)
      .setMaster("local[2]")
      .set("spark.streaming.clock", "org.apache.spark.util.ManualClock")
    ssc = new StreamingContext(conf, batchDuration)
    sc = ssc.sparkContext
    ClockWrapperFull.setSparkStreamingContext(ssc)
  }

  "Sample set" should "be counted" in {
    val results = ListBuffer.empty[Array[String]]
    val sourceLines = mutable.Queue[RDD[String]]()
    val dstream = ssc.queueStream(sourceLines)
    //    dstream.foreachRDD(rdd => {
    //      results += rdd.collect
    //    })
    dstream.foreachRDD(rdd => results += rdd.collect)
    ssc.start()

    val rdd = sc.makeRDD(Seq("aaa", "bbb"))
    sourceLines += rdd
    ClockWrapperFull.advance(batchDuration.milliseconds)
    eventually {
      results.last should equal(Array("aaa", "bbb"))
      results should have size 1
    }
    val rdd2 = sc.makeRDD(Seq("ccc", "ddd"))
    rdd2.foreach(println)
    sourceLines += rdd2
    println("Source lines: " + sourceLines.size)
    ClockWrapperFull.advance(batchDuration.milliseconds)
    ClockWrapperFull.advance(batchDuration.milliseconds / 2)
    eventually {
      try {
//        val last = results.last.toList
//        println("Last: " + last)
//        val expected = Array("ccc", "ddd")
//        last should equal(expected)
//        last should contain inOrderOnly ("ccc", "ddd")
        results should have size 2
      } catch {
        case e: IncompatibleClassChangeError => println("IncompatibleClassChangeError")
      }
    }
  }

  override def afterAll(): Unit = {
    if (ssc != null) {
      ssc.stop()
      ssc = null
    }
  }
}
