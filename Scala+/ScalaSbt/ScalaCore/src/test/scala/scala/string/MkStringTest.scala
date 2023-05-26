package scala.string

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MkStringTest extends AnyFlatSpec with Matchers {

  it should "add a space after each symbol" in {
    val origin = "John"
    val spaced = origin.mkString(" ")
    spaced shouldEqual "J o h n"
  }

}
