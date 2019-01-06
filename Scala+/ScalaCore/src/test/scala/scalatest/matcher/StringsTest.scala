package scalatest.matcher

import org.scalatest.{FlatSpec, Matchers}

class StringsTest extends FlatSpec with Matchers {

  "shouldEqual matcher" should "work" in {
    "abc" shouldEqual "abc"
  }

}