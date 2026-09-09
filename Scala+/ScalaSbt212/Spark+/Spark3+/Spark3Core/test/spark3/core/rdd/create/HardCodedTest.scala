package spark3.core.rdd.create

import org.apache.spark.rdd.RDD
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.core.Factory

class HardCodedTest extends AnyFlatSpec with Matchers {

  it should "create an empty RDD" in {
    val rdd: RDD[Int] = Factory.sc.emptyRDD[Int]
    val list: Array[Int] = rdd.collect
    list shouldBe empty
  }

  it should "init RDD from Seq" in {
    val data = Seq(1, 2, 3)
    val list = Factory.sc.parallelize(data).collect
    list should contain inOrderOnly(1, 2, 3)
  }

  it should "init key-value RDD from Seq" in {
    val data = Seq((1, "a"), (2, "b"), (3, "c"))
    val list = Factory.sc.parallelize(data)
      .map(pair => s"${pair._1}-${pair._2}")
      .collect
    list should contain inOrderOnly("1-a", "2-b", "3-c")
  }

  it should "duplicating keys" in {
    val data = Seq((1, "a"), (2, "b"), (1, "c"))
    val list = Factory.sc.parallelize(data)
      .map(pair => s"${pair._1}-${pair._2}")
      .collect
    list should contain inOrderOnly("1-a", "2-b", "1-c")
  }

}
