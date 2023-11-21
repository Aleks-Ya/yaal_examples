package scalatest.lifecycle

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class IgnoreAnyFlatSpecTest extends AnyFlatSpec with Matchers {
  ignore should "pass" in {
    "abc" should not be null
  }
}