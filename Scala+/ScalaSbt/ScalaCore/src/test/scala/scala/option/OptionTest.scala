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

  it should "handle Option one-line" in {
    val expValue = "abc"
    val option: Option[String] = Some(expValue)
    val result = option.getOrElse("no value")
    result shouldEqual expValue
  }

  it should "return value from Option" in {
    val value = "abc"
    val option: Option[String] = Some(value)
    val result = option match {
      case Some(value) => s"$value modified"
      case None => "no value"
    }
    result shouldEqual "abc modified"
  }

  it should "return value from Option (one line)" in {
    val value = "abc"
    val option: Option[String] = Some(value)
    val result = option.map(v => s"$v modified").getOrElse("no value")
    result shouldEqual "abc modified"
  }

  it should "check option Class" in {
    val option: Option[Any] = Some(123)
    val result = option match {
      case Some(value: String) => s"$value is a string"
      case Some(value: Int) => s"$value is an integer"
      case None => "no value"
    }
    result shouldEqual "123 is an integer"
  }
}