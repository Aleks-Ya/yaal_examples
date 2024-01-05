package spark.streaming.dstream.streaming.foreach

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.flatspec.AnyFlatSpec

class ForEachPartitionTest extends AnyFlatSpec {

  "Process DStream with foreachRDD" should "process lines" in {
    val conf = new SparkConf().setAppName(classOf[ForEachPartitionTest].getSimpleName).setMaster("local[2]")
    val batchDuration = Seconds(5)
    val ssc = new StreamingContext(conf, batchDuration)
    val lines = ssc.socketTextStream("localhost", 9999)
    lines.foreachRDD { rdd =>
      rdd.foreachPartition { partitionOfRecords =>
        partitionOfRecords.foreach { word => println(word) }
      }
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
