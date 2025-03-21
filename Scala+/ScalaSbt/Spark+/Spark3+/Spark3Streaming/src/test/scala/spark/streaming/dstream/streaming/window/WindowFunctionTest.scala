package spark.streaming.dstream.streaming.window

import factory.Factory
import org.apache.spark.streaming.Seconds
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec


class WindowFunctionTest extends AnyFlatSpec with BeforeAndAfterAll {
  "Visualize DF" should "print some info" in {
    val ssc = Factory.ssc(Seconds(5))
    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(w => (w, 1))
    val windowedWordCounts = pairs.reduceByKeyAndWindow((a: Int, b: Int) => a + b, Seconds(30), Seconds(10))
    windowedWordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
