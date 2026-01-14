package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WithClueTest extends AnyFlatSpec with Matchers {
  it should "print a custom message" in {
    withClue("custom clue") {
      "abc" shouldEqual "123"
    }
  }
}