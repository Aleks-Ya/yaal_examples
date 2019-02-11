package scalatest.matcher

import org.scalatest.{FlatSpec, Matchers}

class StringsTest extends FlatSpec with Matchers {

  it should "check string equality" in {
    "abc" shouldEqual "abc"
  }

  it should "assert that a string contains a substring" in {
    "abcdeg" should include("cd")
  }

}