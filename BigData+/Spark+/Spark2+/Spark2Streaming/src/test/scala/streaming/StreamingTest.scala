package streaming

import java.nio.file.Files

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.collection.mutable

class StreamingTest extends FlatSpec
  with BeforeAndAfterAll
  with Matchers {

  //  "Visualize DF" should "print some info" in {
  //    val conf = new SparkConf().setAppName(classOf[StreamingTest].getSimpleName).setMaster("local[2]")
  //    val batchDuration = Seconds(5)
  //    val ssc = new StreamingContext(conf, batchDuration)
  //    val lines = ssc.socketTextStream("localhost", 9999)
  //    val words = lines.flatMap(_.split(" "))
  //    val pairs = words.map(w => (w, 1))
  //    val wordCounts = pairs.reduceByKey(_ + _)
  //    wordCounts.print()
  //    ssc.start()
  //    ssc.awaitTermination()
  //  }

  private val appName = classOf[StreamingTest].getSimpleName
    private val checkpointDir = Files.createTempDirectory(appName).toString
  var ssc: StreamingContext = _
  var sc: SparkContext = _
  var strings: ReceiverInputDStream[String] = _

  override def beforeAll(): Unit = {
    val conf = new SparkConf().setAppName(appName).setMaster("local")
    val batchDuration = Seconds(5)
    ssc = new StreamingContext(conf, batchDuration)
    ssc.checkpoint(checkpointDir)
    sc = ssc.sparkContext
    strings = ssc.receiverStream(new MyReceiver())
    strings.print()
    ssc.start()
    ssc.awaitTermination()
  }

  private def makeInMemoryRDD() = {
    val lines = Seq("To be or not to be.", "That is the question.")
    sc.parallelize(lines)
  }

  private def makeInMemoryDStream() = {
    val lines = mutable.Queue[RDD[String]]()
    val dstream = ssc.queueStream(lines)
    lines += sc.makeRDD(Seq("To be or not to be.", "That is the question."))
  }

  "Visualize DF" should "print some info" in {
  }

  class MyReceiver extends Receiver[String](StorageLevel.MEMORY_ONLY) {
    override def onStart(): Unit = {
      store("aaaaa")
    }

    override def onStop(): Unit = Unit
  }

  override def afterAll(): Unit = {
    ssc.stop(true)
  }
}
