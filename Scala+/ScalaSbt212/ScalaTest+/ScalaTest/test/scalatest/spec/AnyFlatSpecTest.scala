package scalatest.spec

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AnyFlatSpecTest extends AnyFlatSpec with Matchers {
  "Not null" should "pass" in {
    "abc" should not be null
  }
}