package scala.string

import org.scalatest.{FlatSpec, Matchers}

class MultilineStringTest extends FlatSpec with Matchers {

  it should "format string without problems" in {
    val text =
      """You can solve this problem
        |in several different ways.""".stripMargin
    text shouldEqual "You can solve this problem\r\nin several different ways."
  }
}
