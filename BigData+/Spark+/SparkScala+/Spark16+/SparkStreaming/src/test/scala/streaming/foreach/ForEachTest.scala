package streaming.foreach

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class ForEachTest extends FlatSpec with BeforeAndAfterAll {

  "Process DStream with foreachRDD" should "process lines" in {
    val conf = new SparkConf().setAppName(classOf[ForEachTest].getSimpleName).setMaster("local[2]")
    val batchDuration = Seconds(5)
    val ssc = new StreamingContext(conf, batchDuration)
    val lines = ssc.socketTextStream("localhost", 9999)
    lines.foreachRDD { rdd =>
      rdd.foreach { word => println(word) }
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
