package scala.flow

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ForTest extends AnyFlatSpec with Matchers {

  it should "iterate list (for each)" in {
    val list = 1 :: 2 :: Nil
    var sum = 0
    list.foreach((i: Int) => sum += i)
    sum shouldEqual 3
  }

  it should "iterate list (plain for)" in {
    val list = 1 :: 2 :: Nil
    var sum = 0
    for (x <- list) {
      sum += x
    }
    sum shouldEqual 3
  }

  it should "iterate range" in {
    var sum = 0
    for (x <- 1 to 3) {
      sum += x
    }
    sum shouldEqual 6
  }
}
