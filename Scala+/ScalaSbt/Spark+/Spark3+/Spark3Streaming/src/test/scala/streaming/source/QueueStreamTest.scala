package streaming.source

import org.apache.spark.SparkConf
import org.apache.spark.streaming.scheduler.{StreamingListener, StreamingListenerBatchCompleted}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import streaming.operation.stateful.UpdateStateByKeyTest

import scala.collection.mutable

class QueueStreamTest extends AnyFlatSpec with Matchers {

  it should "create DStream from a queue" in {
    val conf = new SparkConf()
      .setAppName(classOf[UpdateStateByKeyTest].getSimpleName)
      .setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(1))
    val sc = ssc.sparkContext
    val rdd1 = sc.parallelize(Seq("aaa", "bbb", "ccc"))
    val rdd2 = sc.parallelize(Seq("John", "Mary", "Rick"))
    val rddQueue = mutable.Queue(rdd1, rdd2)
    val stream = ssc.queueStream(rddQueue)

    val sb = new StringBuilder()
    stream.foreachRDD { rdd =>
      val str = rdd.collect().mkString(",")
      sb.append(str).append("; ")
    }
    stream.count()
    waitOneBatchCompleted(ssc, rddQueue.size)
    val streamStr = sb.toString()

    streamStr shouldBe "aaa,bbb,ccc; John,Mary,Rick; "
  }

  def waitOneBatchCompleted(ssc: StreamingContext, batchNumber: Int): Unit = {
    ssc.start()
    var completedBatchNumber = 0
    ssc.addStreamingListener(new StreamingListener {
      override def onBatchCompleted(batchCompleted: StreamingListenerBatchCompleted): Unit = {
        super.onBatchCompleted(batchCompleted)
        completedBatchNumber = completedBatchNumber + 1
      }
    })
    while (completedBatchNumber < batchNumber) {
      Thread.sleep(500)
    }
    ssc.stop()
  }

}