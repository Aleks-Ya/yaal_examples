package number

import org.scalatest.{FlatSpec, Matchers}

class ParseNumberTest extends FlatSpec with Matchers {

  it should "parse NaN" in {
    val d = "NaN".toDouble
    d.isNaN shouldBe true
  }

  it should "parse comma separated" in {
    val d = "1.1".toDouble
    val d2 = "1,1".toDouble

//    d.isNaN shouldBe true
  }

}
