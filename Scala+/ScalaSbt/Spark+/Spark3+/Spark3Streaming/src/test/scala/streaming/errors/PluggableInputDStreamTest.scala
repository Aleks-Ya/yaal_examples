package streaming.errors

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkException}
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

/**
  * Caused by: java.io.NotSerializableException:
  * Object of org.apache.spark.streaming.dstream.PluggableInputDStream is being serialized
  * possibly as a part of closure of an RDD operation. This is because  the DStream object
  * is being referred to from within the closure.  Please rewrite the RDD operation inside
  * this DStream to avoid this.  This has been enforced to avoid bloating of Spark tasks
  * with unnecessary objects.
  */
class PluggableInputDStreamTest extends AnyFlatSpec with BeforeAndAfter with Matchers {

  private val suffixInDriverClass = "old"
  private var ssc: StreamingContext = _

  before {
    val conf = new SparkConf()
      .setAppName(classOf[PluggableInputDStreamTest].getSimpleName)
      .setMaster("local[2]")
    ssc = new StreamingContext(conf, Seconds(1))
  }

  it should "throw exceptions" in {
    assertThrows[SparkException] {
      val queue = mutable.Queue(ssc.sparkContext.parallelize(Seq("a", "b")))
      ssc.queueStream(queue)
        .map(word => word + suffixInDriverClass)
        .print()
      ssc.start
    }
  }

  it should "works without exceptions" in {
    val queue = mutable.Queue(ssc.sparkContext.parallelize(Seq("a", "b")))
    ssc.queueStream(queue)
      .map(word => word + NotDriverObject.suffix)
      .print()
    ssc.start
  }

  it should "works without exceptions too" in {
    val queue = mutable.Queue(ssc.sparkContext.parallelize(Seq("a", "b")))
    val ndc = new NotDriverClass
    ssc.queueStream(queue)
      .map(word => word + ndc.suffix)
      .print()
    ssc.start
  }

  after {
    if (ssc != null) ssc.stop()
  }
}

object NotDriverObject extends Serializable {
  val suffix = "old"
}

class NotDriverClass extends Serializable {
  val suffix = "old"
}