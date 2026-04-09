package spark3.core.rdd.operation.action

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.core.Factory

class CountByKeyTest extends AnyFlatSpec with Matchers {

  it should "countByKey" in {
    val count = Factory.sc.parallelize(Seq((1, "a"), (2, "b"), (1, "c")))
      .countByKey()
    count should contain only(1 -> 2, 2 -> 1)
  }

}
