package scala.option

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class OptionTest extends AnyFlatSpec with Matchers {

  it should "create Option" in {
    val o1 = Some(1)
    o1 should contain(1)

    val o2 = None
    o2 shouldBe None

    var o3: Option[Int] = null
    o3 = Some(3)
    o3 should contain(3)
  }

  it should "handle Option" in {
    var result: String = null
    val expValue = "abc"
    val option: Option[String] = Some(expValue)
    option match {
      case Some(value) => result = value
      case None => result = "no value"
    }
    result shouldEqual expValue
  }
}