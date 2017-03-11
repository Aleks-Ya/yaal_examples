package matcher

import org.scalatest.{FlatSpec, Matchers}

class NullTest extends FlatSpec with Matchers {

  "Not null" should "pass" in {
    "abc" should not be null
  }

  "null" should "pass" in {
    val a: String = null
    a shouldBe null
  }
}