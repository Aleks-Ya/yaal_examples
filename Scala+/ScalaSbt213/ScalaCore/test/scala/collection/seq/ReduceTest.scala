package scala.collection.seq

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReduceTest extends AnyFlatSpec with Matchers {
  it should "reduce" in {
    val seq = Seq(1, 2, 3, 4)
    val res = seq.reduce((a, b) => a * 2 + b)
    res shouldEqual 26
  }

  it should "reduceLeft" in {
    val sb = new StringBuilder("a")
    val seq = Seq(sb, new StringBuilder("b"), new StringBuilder("c"), new StringBuilder("d"))
    val res = seq.reduceLeft((sb1, sb2) => sb1.append(sb2))
    res.toString shouldEqual "abcd"
    res shouldBe sb
  }

  it should "reduce empty" in {
    val seq = Seq.empty[Int]
    intercept[UnsupportedOperationException] {
      seq.reduce((a, b) => a * 2 + b)
    }
  }

  it should "reduceOption empty" in {
    val seq = Seq.empty[Int]
    val res = seq.reduceOption((a, b) => a * 2 + b)
    res shouldBe None
  }
}
