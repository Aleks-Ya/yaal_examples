package scala.number

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Try

class IsNumberTest extends AnyFlatSpec with Matchers {

  it should "check is a String an Int" in {
    def isInt(s: String): Boolean = {
      Try(s.toInt).toOption.isDefined
    }

    isInt("123") shouldBe true
    isInt("abc") shouldBe false
    isInt("123.1") shouldBe false
  }

}
