package core

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class InitSparkContextTest extends FlatSpec with BeforeAndAfterAll with Matchers {
  var sc: SparkContext = _

  override def beforeAll() {
    val conf = new SparkConf()
      .setAppName(classOf[InitSparkContextTest].getSimpleName)
      .setMaster("local")
    sc = new SparkContext(conf)
  }

  "SparkContext" should "be initialized" in {
    val rdd = sc.range(1, 10)
    val sum = rdd.reduce(_ + _)
    sum shouldEqual 45
  }

  override def afterAll() {
    sc.stop()
  }
}
