package scalatest.matcher

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NullMatchersTest extends AnyFlatSpec with Matchers {

  "Not null" should "pass" in {
    "abc" should not be null
  }

  "null" should "pass" in {
    val a: String = null
    a shouldBe null
  }
}