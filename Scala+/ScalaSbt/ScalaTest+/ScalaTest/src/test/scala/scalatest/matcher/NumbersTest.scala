package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NumbersTest extends AnyFlatSpec with Matchers {

  "shouldEqual matcher" should "assert Int" in {
    val result: Int = 3
    result shouldEqual 3
    result should equal(3)
    result should ===(3)

    result should be(3)
    result should be > 2
    result should be >= 3
    result should be < 4
    result should be <= 3

    result shouldBe 3
    result shouldBe >(2)
    result shouldBe >=(3)
    result shouldBe <(4)
    result shouldBe <=(3)
  }

  "shouldEqual matcher" should "assert Long" in {
    val result: Long = 3L
    result shouldEqual 3
    result should equal(3)
    result should ===(3)

    result should be(3)
    result should be > 2L
    result should be >= 3L
    result should be < 4L
    result should be <= 3L

    result shouldBe 3
    result shouldBe >(2L)
    result shouldBe >=(3L)
    result shouldBe <(4L)
    result shouldBe <=(3L)
  }

  "NaN matcher" should "work" in {
    val d = Double.NaN
    d.isNaN shouldBe true
  }

}