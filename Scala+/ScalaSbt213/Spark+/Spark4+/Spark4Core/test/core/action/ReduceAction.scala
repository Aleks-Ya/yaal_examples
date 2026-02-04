package core.action

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReduceAction extends AnyFlatSpec with Matchers {

  it should "convert RDD to a string" in {
    val str = Factory.sc.parallelize(Seq((1, "a"), (2, "b")))
      .map(pair => pair._1 + pair._2)
      .reduce((str1, str2) => str1 + str2)
    str shouldBe "1a2b"
  }
}
