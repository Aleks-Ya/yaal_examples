package streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class StreamingTest extends FlatSpec with BeforeAndAfterAll {

  var stream: StreamingContext = _

  override def beforeAll() {
    val conf = new SparkConf().setAppName("SqlContextTest").setMaster("local")
    stream = new StreamingContext(conf, Seconds(1))
  }

  "Visualize DF" should "print some info" in {
    println("hello")
  }

  override def afterAll() {
    stream.stop()
  }
}
