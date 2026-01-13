package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class EqualsTest extends AnyFlatSpec with Matchers {

  /**
   * Source: https://www.scalatest.org/user_guide/using_matchers#checkingEqualityWithMatchers
   */
  it should "assert equality" in {
    val result = 3
    result should equal(3)
    result should ===(3)
    result should be(3)
    result shouldEqual 3
    result shouldBe 3
  }

  it should "assert inequality" in {
    val result = 3
    result should not equal 4
    result should !==(4)
    result should not be 4
    result shouldNot equal(4)
    result shouldNot be(4)
  }

}