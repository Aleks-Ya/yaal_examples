package scala.number

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MathTest extends AnyFlatSpec with Matchers {
  it should "return max Integer" in {
    val i1 = 5;
    val i2 = 6;
    val max = Math.max(i1, i2)
    max shouldBe i2
  }

  it should "return max string length" in {
    val s1 = "a";
    val s2 = "bb";
    val max = Math.max(s1.length, s2.length)
    max shouldBe s2.length
    max shouldBe an[Int]
  }
}
