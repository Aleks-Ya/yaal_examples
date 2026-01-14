package scala.number

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParseNumberTest extends AnyFlatSpec with Matchers {

  it should "parse NaN" in {
    val d = "NaN".toDouble
    d.isNaN shouldBe true
  }

  it should "parse comma separated" in {
    val d = "1.1".toDouble
  }

}
