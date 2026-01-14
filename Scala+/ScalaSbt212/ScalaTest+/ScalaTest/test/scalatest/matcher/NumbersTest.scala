package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NumbersTest extends AnyFlatSpec with Matchers {

  it should "assert an Int" in {
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

    result should (be > 0 and be <= 4)
    result should be(3 +- 1)
  }

  it should "assert a Long" in {
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

    result should (be > 0L and be <= 4L)
    result should be(3L +- 1L)
  }

  "NaN matcher" should "work" in {
    val d = Double.NaN
    d.isNaN shouldBe true
  }

}