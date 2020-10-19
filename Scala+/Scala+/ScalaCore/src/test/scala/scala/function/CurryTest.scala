package scala.function

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CurryTest extends AnyFlatSpec with Matchers {

  "Carrying def" should "works" in {
    def sum(a: Int)(b: Int): Int = a + b

    val sum1 = sum(1)(_)
    sum1(2) shouldEqual 3
  }

  "Carrying val" should "works" in {
    val sum: Int => Int => Int = a => b => a + b
    val sum1 = sum(1)(_)
    sum1(2) shouldEqual 3
  }
}
