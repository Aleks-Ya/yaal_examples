package streaming.mkuthan_unit_testing

import org.apache.spark.rdd.RDD
import org.apache.spark.streaming._
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.concurrent.Eventually
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.{Millis, Span}
import org.scalatest.{BeforeAndAfterAll, GivenWhenThen}

import java.nio.file.Files
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class WordCountTest extends AnyFlatSpec with BeforeAndAfterAll with Matchers with Eventually with GivenWhenThen {
  private val appName = classOf[WordCountTest].getSimpleName
  private val checkpointDir = Files.createTempDirectory(appName).toString
  private val batchDuration = Seconds(1)
  implicit override val patienceConfig: PatienceConfig = PatienceConfig(timeout = scaled(Span(5000, Millis)))
  private val windowDuration = Seconds(4)
  private val slideDuration = Seconds(2)

  private var ssc: StreamingContext = _
  private var sc: SparkContext = _

  override def beforeAll(): Unit = {
    val conf = new SparkConf()
      .setAppName(appName)
      .setMaster("local[2]")
      .set("spark.streaming.clock", "org.apache.spark.util.ManualClock")
    ssc = new StreamingContext(conf, batchDuration)
    ssc.checkpoint(checkpointDir)
    sc = ssc.sparkContext


    //    ssc.start()
    //    ssc.awaitTermination()
  }

  "Sample set" should "be counted" in {
    Given("streaming context is initialized")
    val lines = mutable.Queue[RDD[String]]()
    val results = ListBuffer.empty[Array[WordCount]]
    val dstream = ssc.queueStream(lines)
    WordCount.count(ssc, dstream, windowDuration, slideDuration) {
      (wordsCount: RDD[WordCount], time: Time) => results += wordsCount.collect()
    }

    ssc.start()

    When("first set of words queued")
    lines += sc.makeRDD(Seq("a", "b"))

    Then("words counted after first slide")
    advanceClock(slideDuration)
    eventually {
      results.last should equal(Array(
        WordCount("a", 1),
        WordCount("b", 1)))
    }

    When("second set of words queued")
    lines += sc.makeRDD(Seq("b", "c"))

    Then("words counted after second slide")
    advanceClock(slideDuration)
    eventually {
      results.last should equal(Array(
        WordCount("a", 1),
        WordCount("b", 2),
        WordCount("c", 1)))
    }

    When("nothing more queued")

    Then("word counted after third slide")
    advanceClock(slideDuration)
    eventually {
      results.last should equal(Array(
        WordCount("a", 0),
        WordCount("b", 1),
        WordCount("c", 1)))
    }

    When("nothing more queued")

    Then("word counted after fourth slide")
    advanceClock(slideDuration)
    eventually {
      results.last should equal(Array(
        WordCount("a", 0),
        WordCount("b", 0),
        WordCount("c", 0)))
    }
  }

  def advanceClock(timeToAdd: Duration): Unit = {
    ClockWrapper.advance(ssc, timeToAdd)
  }


  //  private def makeInMemoryRDD() = {
  //    val lines = Seq("To be or not to be.", "That is the question.")
  //    sc.parallelize(lines)
  //  }
  //
  //  private def makeInMemoryDStream() = {
  //    val lines = mutable.Queue[RDD[String]]()
  //    val dstream = ssc.queueStream(lines)
  //    lines += sc.makeRDD(Seq("To be or not to be.", "That is the question."))
  //  }

  override def afterAll(): Unit = {
    if (ssc != null) {
      ssc.stop(stopSparkContext = false, stopGracefully = false)
      ssc = null
    }
  }
}
