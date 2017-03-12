package core

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class ScalaTupleTest extends FlatSpec with BeforeAndAfterAll with Matchers {
  var sc: SparkContext = _

  override def beforeAll() {
    val conf = new SparkConf()
      .setAppName(classOf[ScalaTupleTest].getSimpleName)
      .setMaster("local")
    sc = new SparkContext(conf)
  }

  "RDD" should "process Scala's tuple" in {
    val list = sc.parallelize(Seq("a", "b"))
      .map(word => (word, 1))
      .map(pair => pair._1 + "-" + pair._2)
      .collect
    list should contain inOrderOnly("a-1", "b-1")

  }

  override def afterAll() {
    sc.stop()
  }
}
