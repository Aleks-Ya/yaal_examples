package core.transformation

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class ReduceByKeyTest extends FlatSpec with BeforeAndAfterAll with Matchers {
  var sc: SparkContext = _

  override def beforeAll() {
    val conf = new SparkConf()
      .setAppName(classOf[ReduceByKeyTest].getSimpleName)
      .setMaster("local")
    sc = new SparkContext(conf)
  }

  "RDD" should "process Scala's tuple" in {
    val list = sc.parallelize(Seq("a", "b"))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .map((tuple: (String, Int)) => tuple._1 + "-" + tuple._2)
      .collect
    println(list)
    list should contain inOrderOnly("a-1", "b-1")

  }

  override def afterAll() {
    sc.stop()
  }
}
