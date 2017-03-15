package core.source

import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class HardCodedTest extends FlatSpec with BeforeAndAfterAll with Matchers {
  var sc: SparkContext = _

  override def beforeAll() {
    val conf = new SparkConf()
      .setAppName(classOf[HardCodedTest].getSimpleName)
      .setMaster("local")
    sc = new SparkContext(conf)
  }

  it should "init RDD from Seq" in {
    val data = Seq(1,2,3)
    val list = sc.parallelize(data).collect
    list should contain inOrderOnly(1,2,3)
  }

  override def afterAll() {
    sc.stop()
  }
}
