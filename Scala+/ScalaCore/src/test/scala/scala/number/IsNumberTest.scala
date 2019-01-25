package scala.number

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Try

class IsNumberTest extends FlatSpec with Matchers {

  it should "check is a String an Int" in {
    def isInt(s: String): Boolean = {
      Try(s.toInt).toOption.isDefined
    }

    isInt("123") shouldBe true
    isInt("abc") shouldBe false
    isInt("123.1") shouldBe false
  }

}
