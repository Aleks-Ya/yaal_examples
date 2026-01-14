package scala.character

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CharType extends AnyFlatSpec with Matchers {
  it should "instantiate a Char" in {
    val c1 = 'a'
    val c2 = "a".toCharArray()(0)
    c1 shouldBe c2
    c2 shouldBe 'a'
  }
}
