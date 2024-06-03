package scala.collection.seq

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FoldTest extends AnyFlatSpec with Matchers {
  it should "foldLeft" in {
    val numbers = Seq(1, 2, 3, 4)
    val sum = numbers.foldLeft(15)(_ + _)
    sum shouldEqual 25
  }

  it should "foldRight" in {
    val numbers = Seq(1, 2, 3, 4)
    val sum = numbers.foldRight(15)(_ + _)
    sum shouldEqual 25
  }

  it should "fold (alias for foldLeft)" in {
    val numbers = Seq(1, 2, 3, 4)
    val sum = numbers.fold(15)(_ + _)
    sum shouldEqual 25
  }
}
