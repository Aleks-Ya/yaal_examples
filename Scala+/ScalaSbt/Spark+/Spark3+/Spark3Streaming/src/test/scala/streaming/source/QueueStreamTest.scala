package streaming.source

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import streaming.operation.stateful.UpdateStateByKeyTest

import java.util.concurrent.CountDownLatch
import scala.collection.mutable

class QueueStreamTest extends AnyFlatSpec with Matchers {

  it should "create DStream from an immutable queue" in {
    val conf = new SparkConf()
      .setAppName(classOf[UpdateStateByKeyTest].getSimpleName)
      .setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(1))
    val sc = ssc.sparkContext
    val rdd1 = sc.parallelize(Seq("aaa", "bbb", "ccc"))
    val rdd2 = sc.parallelize(Seq("John", "Mary", "Rick"))
    val rddQueue = mutable.Queue(rdd1, rdd2)
    val latch = new CountDownLatch(rddQueue.size)
    val sb = streamToString(ssc, rddQueue, latch)
    waitOneBatchCompleted(ssc, latch)
    sb.toString() shouldBe "aaa,bbb,ccc; John,Mary,Rick; "
  }

  it should "add elements to a queue in runtime" in {
    val conf = new SparkConf()
      .setAppName(classOf[UpdateStateByKeyTest].getSimpleName)
      .setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(1))
    val expRddCount = 5
    val rddQueue = startRddGeneratorThread(ssc, expRddCount)
    val latch = new CountDownLatch(expRddCount)
    val sb = streamToString(ssc, rddQueue, latch)
    waitOneBatchCompleted(ssc, latch)
    sb.toString() shouldEqual "aaa0,bbb0,ccc0; aaa1,bbb1,ccc1; aaa2,bbb2,ccc2; aaa3,bbb3,ccc3; aaa4,bbb4,ccc4; "
  }

  private def streamToString(ssc: StreamingContext, rddQueue: mutable.Queue[RDD[String]], latch: CountDownLatch) = {
    val sb = new mutable.StringBuilder()
    ssc.queueStream(rddQueue).foreachRDD { rdd =>
      if (!rdd.isEmpty()) {
        sb.append(rdd.collect().mkString(",")).append("; ")
        latch.countDown()
      }
    }
    sb
  }

  private def startRddGeneratorThread(ssc: StreamingContext, expRddCount: Int) = {
    val rddQueue = mutable.Queue[RDD[String]]()
    val t = new Thread() {
      private var counter = 0;

      override def run(): Unit = {
        while (counter < expRddCount) {
          rddQueue += ssc.sparkContext.parallelize(Seq("aaa" + counter, "bbb" + counter, "ccc" + counter))
          counter += 1
          Thread.sleep(500)
        }
      }
    }
    t.setDaemon(true)
    t.start()
    rddQueue
  }

  def waitOneBatchCompleted(ssc: StreamingContext, latch: CountDownLatch): Unit = {
    ssc.start()
    latch.await()
    ssc.stop()
  }

}