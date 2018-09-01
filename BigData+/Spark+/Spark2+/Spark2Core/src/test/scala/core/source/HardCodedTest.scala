package core.source

import core.Factory
import org.scalatest.{FlatSpec, Matchers}

class HardCodedTest extends FlatSpec with Matchers {

  it should "init RDD from Seq" in {
    val data = Seq(1, 2, 3)
    val list = Factory.sc.parallelize(data).collect
    list should contain inOrderOnly(1, 2, 3)
  }
}
