package spark.streaming.dstream.foreach

import factory.Factory
import org.apache.spark.streaming.Seconds
import org.scalatest.flatspec.AnyFlatSpec

class ForEachPartitionTest extends AnyFlatSpec {
  "Process DStream with foreachRDD" should "process lines" in {
    val ssc = Factory.ssc(Seconds(5))
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
