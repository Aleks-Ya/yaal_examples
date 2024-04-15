package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StringsTest extends AnyFlatSpec with Matchers {

  it should "check string equality" in {
    "abc" shouldEqual "abc"
  }

  it should "assert that a string contains a substring" in {
    "abcdeg" should include("cd")
  }

  it should "assert toString" in {
    125.toString shouldEqual "125"
  }

}