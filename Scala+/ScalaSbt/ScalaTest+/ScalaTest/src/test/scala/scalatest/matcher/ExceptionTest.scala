package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExceptionTest extends AnyFlatSpec with Matchers {

  it should "assert class of an exception" in {
    an[IndexOutOfBoundsException] should be thrownBy "abc".charAt(-1)
  }

  it should "assert an exception" in {
    val thrown = the[IndexOutOfBoundsException] thrownBy "abc".charAt(-1)
    thrown.getMessage shouldEqual "String index out of range: -1"
    thrown.getMessage should include("out of")
    thrown should have message "String index out of range: -1"
  }

  it should "assert message of an exception (one line)" in {
    the[ArithmeticException] thrownBy 1 / 0 should have message "/ by zero"
  }

  it should "assert message of an exception (multi line)" in {
    the[IndexOutOfBoundsException] thrownBy {
      "abc".charAt(-1)
    } should have message "String index out of range: -1"
  }

  it should "assert that no exception was thrown" in {
    noException should be thrownBy 0 / 1
  }

}