package spark.streaming.dstream.streaming.transform

import factory.Factory
import org.apache.spark.streaming.Seconds
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec

class TransformTest extends AnyFlatSpec with BeforeAndAfterAll {
  "Perform arbitrary operation under RDD" should "filter lines shorter 3 chars" in {
    val batchDuration = Seconds(5)
    val ssc = Factory.ssc(batchDuration)
    val lines = ssc.socketTextStream("localhost", 9999)
    val longLines = lines.transform(rdd => rdd.filter(line => line.length > 2))
    longLines.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
