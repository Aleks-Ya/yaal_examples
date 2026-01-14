package scala.string

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MultilineStringTest extends AnyFlatSpec with Matchers {

  it should "format string without problems" in {
    val text =
      """You can solve this problem
        |in several different ways.""".stripMargin
    text shouldEqual "You can solve this problem\nin several different ways."
  }
}
