package scalatest.exception

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class ExpectedExceptionTest extends AnyFlatSpec with Matchers {

  it should "throw exception" in {
    assertThrows[IndexOutOfBoundsException] {
      "hi".charAt(-1)
    }
  }

  it should "verify exception's message" in {
    val caught = intercept[IndexOutOfBoundsException] {
      "hi".charAt(-1)
    }
    assert(caught.getMessage.indexOf("-1") != -1)
  }

  it should "with clue (a hint)" in {
    withClue("this is a clue") {
      assertThrows[IndexOutOfBoundsException] {
        "hi".charAt(-1)
      }
    }
  }

}