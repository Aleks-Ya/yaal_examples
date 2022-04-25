package core.compare

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AssertRddTuple extends AnyFlatSpec with Matchers {

  it should "use collect()" in {
    val array = Factory.sc.parallelize(Seq((1, "a"), (2, "b"))).collect()
    array should contain inOrderOnly((1, "a"), (2, "b"))
  }

  it should "use map() and reduce()" in {
    val str = Factory.sc.parallelize(Seq((1, "a"), (2, "b")))
      .map(pair => pair._1 + pair._2)
      .reduce((str1, str2) => str1 + str2)
    str shouldBe "1a2b"
  }

}
