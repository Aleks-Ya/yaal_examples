package spark.streaming.dstream.streaming.window

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec


class WindowFunctionTest extends AnyFlatSpec with BeforeAndAfterAll {

  "Visualize DF" should "print some info" in {
    val conf = new SparkConf().setAppName(classOf[WindowFunctionTest].getSimpleName).setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))
    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(w => (w, 1))
    val windowedWordCounts = pairs.reduceByKeyAndWindow((a: Int, b: Int) => a + b, Seconds(30), Seconds(10))
    windowedWordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
