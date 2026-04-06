package spark4.core.rdd.create

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory

class HardCodedTest extends AnyFlatSpec with Matchers {

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
}
