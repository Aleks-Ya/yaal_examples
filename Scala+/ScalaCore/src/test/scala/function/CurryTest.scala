package function

import org.scalatest.{FlatSpec, Matchers}

class CurryTest extends FlatSpec with Matchers {

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
