package streaming.errors

import java.io.NotSerializableException

import org.apache.spark.{SparkConf, SparkException}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.collection.mutable

/**
  * Caused by: java.io.NotSerializableException:
  * Object of org.apache.spark.streaming.dstream.PluggableInputDStream is being serialized
  * possibly as a part of closure of an RDD operation. This is because  the DStream object
  * is being referred to from within the closure.  Please rewrite the RDD operation inside
  * this DStream to avoid this.  This has been enforced to avoid bloating of Spark tasks
  * with unnecessary objects.
  */
class PluggableInputDStreamTest extends FlatSpec with BeforeAndAfterAll with Matchers {

  val suffix = "old"
  var ssc: StreamingContext = _

  override protected def beforeAll(): Unit = {
    val conf = new SparkConf()
      .setAppName(classOf[PluggableInputDStreamTest].getSimpleName)
      .setMaster("local[2]")
    ssc = new StreamingContext(conf, Seconds(1))
  }

  it should "works without exceptions" in {
    assertThrows[SparkException] {
      val queue = mutable.Queue(ssc.sparkContext.parallelize(Seq("a", "b")))
      ssc.queueStream(queue)
        .map(word => word + suffix)
        .print()
      ssc.start
    }
  }

}
