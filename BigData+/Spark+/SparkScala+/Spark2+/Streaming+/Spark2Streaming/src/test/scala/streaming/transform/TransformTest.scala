package streaming.transform

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class TransformTest extends FlatSpec with BeforeAndAfterAll {

  "Perform arbitrary operation under RDD" should "filter lines shorter 3 chars" in {
    val conf = new SparkConf().setAppName(classOf[TransformTest].getSimpleName).setMaster("local[2]")
    val batchDuration = Seconds(5)
    val ssc = new StreamingContext(conf, batchDuration)
    val lines = ssc.socketTextStream("localhost", 9999)
    val longLines = lines.transform(rdd => rdd.filter(line => line.length > 2))
    longLines.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
