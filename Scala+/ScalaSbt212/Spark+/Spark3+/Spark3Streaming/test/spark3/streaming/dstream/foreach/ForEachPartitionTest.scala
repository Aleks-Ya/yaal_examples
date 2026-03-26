package spark3.streaming.dstream.foreach

import org.apache.spark.streaming.Seconds
import org.scalatest.flatspec.AnyFlatSpec
import spark3.streaming.Factory

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
