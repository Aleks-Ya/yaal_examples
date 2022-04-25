package core.compare

import core.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AssertRddNotTuple extends AnyFlatSpec with Matchers {

  it should "use collect()" in {
    val array = Factory.sc.parallelize(Seq("a", "b", "c")).collect()
    array should contain inOrderOnly("a", "b", "c")
  }

  it should "use map() and reduce()" in {
    val array = Factory.sc.parallelize(Seq(1, 2, 3)).map(_.toString).reduce((s1, s2) => s1 + s2)
    array shouldBe "123"
  }

  it should "use toDebugString" in {
    val str = Factory.sc.parallelize(Seq("a", "b", "c")).toDebugString
    str shouldBe "[(1) ParallelCollectionRDD[0] at parallelize at AssertRdd.scala:10 []]"
  }

  it should "use toString" in {
    val str = Factory.sc.parallelize(Seq("a", "b", "c")).toString
    str shouldBe "ParallelCollectionRDD[0] at parallelize at AssertRdd.scala: 15"
  }

}
