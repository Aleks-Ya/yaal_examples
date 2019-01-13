package scalatest.lifecycle

import org.scalatest.{FlatSpec, Matchers}

class IgnoreTest extends FlatSpec with Matchers {

  ignore should "pass" in {
    "abc" should not be null
  }
}