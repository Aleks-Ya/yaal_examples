package scala.string

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RepeatStringTest extends AnyFlatSpec with Matchers {

  it should "repeat a string N times" in {
    val s = "ab"
    val times = 3
    val repeated = s * times
    repeated shouldEqual "ababab"
  }

}
