package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NumbersTest extends AnyFlatSpec with Matchers {

  "shouldEqual matcher" should "work" in {
    val result = 3
    result should equal(3)
    result should ===(3)
    result should be(3)
    result shouldEqual 3
    result shouldBe 3
    result shouldBe >(2)
    result shouldBe >=(3)
    result shouldBe <(4)
    result shouldBe <=(3)
  }

  "NaN matcher" should "work" in {
    val d = Double.NaN
    d.isNaN shouldBe true
  }

}