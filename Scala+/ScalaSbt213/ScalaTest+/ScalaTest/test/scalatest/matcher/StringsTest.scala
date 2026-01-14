package scalatest.matcher

import org.scalactic.StringNormalizations.lowerCased
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

  it should "compare strings ignoring case" in {
    val string1 = "Hello"
    val string2 = "heLLo"
    string1 should equal(string2)(after being lowerCased)
  }

  it should "compare strings ignoring line endings" in {
    val string1 = "Hello\nWorld"
    val string2 = "Hello\r\nWorld"
    string1.replace("\r\n", "\n") shouldEqual string2.replace("\r\n", "\n")
  }

}