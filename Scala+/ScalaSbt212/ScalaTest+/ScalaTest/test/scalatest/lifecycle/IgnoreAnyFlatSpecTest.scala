package scalatest.lifecycle

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IgnoreAnyFlatSpecTest extends AnyFlatSpec with Matchers {
  ignore should "be ignored 1" in {
    "abc" should not be null
  }

  it should "be ignored 2" ignore {
    "abc" should not be null
  }
}